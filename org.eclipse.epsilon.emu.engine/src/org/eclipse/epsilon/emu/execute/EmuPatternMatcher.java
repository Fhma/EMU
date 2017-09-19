/**
 * 
 * @author Faisal Alhwikem
 * @version 1.0
 * @since March-2017
 */

package org.eclipse.epsilon.emu.execute;

import java.io.File;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.epsilon.emc.emf.EmfUtil;
import org.eclipse.epsilon.emu.EmuModule;
import org.eclipse.epsilon.emu.mutation.IMutationGenerator;
import org.eclipse.epsilon.emu.mutation.execute.MutationGeneratorImpl;
import org.eclipse.epsilon.eol.dom.Annotation;
import org.eclipse.epsilon.eol.dom.AssignmentStatement;
import org.eclipse.epsilon.eol.dom.BooleanLiteral;
import org.eclipse.epsilon.eol.dom.ExecutableBlock;
import org.eclipse.epsilon.eol.dom.Expression;
import org.eclipse.epsilon.eol.dom.IntegerLiteral;
import org.eclipse.epsilon.eol.dom.NameExpression;
import org.eclipse.epsilon.eol.dom.PropertyCallExpression;
import org.eclipse.epsilon.eol.dom.RealLiteral;
import org.eclipse.epsilon.eol.dom.StatementBlock;
import org.eclipse.epsilon.eol.dom.StringLiteral;
import org.eclipse.epsilon.eol.exceptions.EolInternalException;
import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;
import org.eclipse.epsilon.eol.execute.context.FrameType;
import org.eclipse.epsilon.eol.execute.context.IEolContext;
import org.eclipse.epsilon.eol.execute.context.Variable;
import org.eclipse.epsilon.eol.models.IModel;
import org.eclipse.epsilon.epl.dom.Pattern;
import org.eclipse.epsilon.epl.execute.PatternMatch;
import org.eclipse.epsilon.epl.execute.PatternMatchModel;
import org.eclipse.epsilon.epl.execute.PatternMatcher;

public class EmuPatternMatcher extends PatternMatcher {

	protected static final String MUTATION_ACTION_ANNOTATION = "action";
	protected static final String FEATURE_ANNOTATION = "feature";
	protected static final String TYPE_ANNOTATION = "type";

	protected IModel modelThatOwnsMatching = null;
	private IMutationGenerator mutationGeneratorImpl = null;

	public PatternMatchModel match(EmuModule module) throws Exception {
		frame = null;
		IEolContext context = module.getContext();
		PatternMatchModel model = new PatternMatchModel();

		if (module.getMaximumLevel() > 0)
		{
			model.setName(module.getPatternMatchModelName());
			context.getModelRepository().addModel(model);
		}

		model.setPatterns(module.getPatterns());

		for (int level = 0; level <= module.getMaximumLevel(); level++)
		{
			for (Pattern pattern : module.getPatterns())
			{
				// Check annotations given by this pattern
				checkAnnotations(pattern);

				if (pattern.getLevel() == level)
				{
					List<PatternMatch> pattern_matches = match(pattern, context);
					for (PatternMatch match : pattern_matches)
					{
						model.addMatch(match);
					}
				}
			}

			Tuple values;
			int index;
			File folder;
			File file = module.getSourceFile();

			if (file != null)
			{
				String absPath = file.getAbsolutePath();
				int length = absPath.length();
				folder = new File(absPath.substring(0, length - 4) + "_mutations/");
			} else
				folder = new File("mutations/");

			folder.mkdir();
			String location = folder.toURI().toString();

			for (PatternMatch match : model.getMatches())
			{
				if (match.getPattern().getLevel() == level)
				{
					Object valid_mutant = null;
					values = prepareForMutation(module, match, context);
					String mutation_action = getAnnotationValue(match.getPattern(), MUTATION_ACTION_ANNOTATION, context);

					ExecutableBlock<Void> do_ = getDoBlock(values, module, match, context);
					if (do_ != null)
					{
						context.getFrameStack().enterLocal(FrameType.UNPROTECTED, do_);
						for (String componentName : match.getRoleBindings().keySet())
						{
							context.getFrameStack().put(Variable.createReadOnlyVariable(componentName, match.getRoleBinding(componentName)));
						}
						context.getModelRepository().getTransactionSupport().startTransaction();

						try
						{
							context.getExecutorFactory().executeAST(do_, context);
						} catch (EolInternalException e)
						{
							context.getModelRepository().getTransactionSupport().rollbackTransaction();
							context.getFrameStack().leaveLocal(do_);
							System.err.println("Invalid mutation by pattern [" + match.getPattern().getName() + "] in [" + module.getSourceFile() + "]");
							continue;
						} catch (Exception e)
						{
							throw new Exception(e);
						}

						Object newValue = getModelThatOwnsMatching().getPropertyGetter().invoke(values.getRoleBinding(), values.getFeature().getName());
						valid_mutant = getMutationGenerator().checkConditions(values.getFeature(), values.getValue(), newValue, mutation_action);

						if (valid_mutant == IMutationGenerator.VALID)
						{
							String operatorName = values.getType().getName() + "_" + values.getFeature().getName() + "_"
									+ getAnnotationValue(match.getPattern(), MUTATION_ACTION_ANNOTATION, context);

							if (module.getMutationsIndexer().get(operatorName) != null)
								index = module.getMutationsIndexer().get(operatorName) + 1;
							else
								index = 1;
							String mutant = location + operatorName + "_" + index + ".xmi";
							module.getMutationsIndexer().put(operatorName, index);
							getModelThatOwnsMatching().store(mutant);
						}
						context.getModelRepository().getTransactionSupport().rollbackTransaction();
						context.getFrameStack().leaveLocal(do_);
					}
					if (valid_mutant == null || (valid_mutant != null && !valid_mutant.equals(IMutationGenerator.VALID)))
						System.err.println("Invalid mutation by pattern [" + match.getPattern().getName() + "] in [" + module.getSourceFile() + "]");
				}
			}
		}
		return model;
	}

	private Tuple prepareForMutation(EmuModule module, PatternMatch match, IEolContext context) throws Exception {

		// check which role has the target instance and type
		String instanceName = null;
		Object roleBinding = null;
		EClass targetType = null;
		Set<Map.Entry<String, Object>> RoleBindingSet = match.getRoleBindings().entrySet();
		int counter = 0;

		if (RoleBindingSet.size() > 1)
		{
			Iterator<Map.Entry<String, Object>> it = RoleBindingSet.iterator();
			while (it.hasNext())
			{
				Map.Entry<String, Object> pair = it.next();
				if (pair.getValue() instanceof EObject)
				{
					EObject eObj = (EObject) pair.getValue();
					if (eObj.eClass() instanceof EClass)
					{
						EClass eClass = (EClass) eObj.eClass();
						if (eClass.getName().equals(getAnnotationValue(match.getPattern(), TYPE_ANNOTATION, context)))
						{
							// found target type and only allowing one matching role for that type
							counter++;
							targetType = eClass;
							instanceName = pair.getKey();
							roleBinding = pair.getValue();
						}
					}
				}
			}
		} else
		{
			Set<String> key = match.getRoleBindings().keySet();
			for (String s : key)
				instanceName = s;
			counter = 1;
			roleBinding = match.getRoleBinding(instanceName);
			if (roleBinding instanceof EObject)
			{
				roleBinding = (EObject) roleBinding;
				if (((EObject) roleBinding).eClass() instanceof EClass)
					targetType = (EClass) ((EObject) roleBinding).eClass();
			}
		}

		if (counter == 0)
			throw new IllegalArgumentException(
					"Unable to find a target type [" + getAnnotationValue(match.getPattern(), TYPE_ANNOTATION, context) + "] within the matching roles.");
		if (counter > 1)
			throw new IllegalArgumentException("Found multiple roles for the given type [" + getAnnotationValue(match.getPattern(), TYPE_ANNOTATION, context) + "].");

		if (targetType == null)
			throw new IllegalArgumentException("Unrecognizable Object " + roleBinding);

		String propertyName = getAnnotationValue(match.getPattern(), FEATURE_ANNOTATION, context);

		setModelThatOwnsMatching(roleBinding, context);
		EStructuralFeature feature = EmfUtil.getEStructuralFeature(targetType, propertyName);

		Object feature_value = ((EObject) roleBinding).eGet(feature);

		return new Tuple(roleBinding, instanceName, targetType, feature, feature_value);
	}

	private ExecutableBlock<Void> getDoBlock(Tuple values, EmuModule module, PatternMatch match, IEolContext context) throws Exception {
		String propertyName = values.getFeature().getName();
		String mutation_action;

		mutation_action = getAnnotationValue(match.getPattern(), MUTATION_ACTION_ANNOTATION, context);

		Object newValue = null;
		StatementBlock statBlock = null;

		newValue = getMutationGenerator().mutate(values.getRoleBinding(), values.getFeature(), values.getValue(), module, mutation_action);
		if (newValue != null && newValue.equals(IMutationGenerator.NOTIMPLEMENTED))
			return match.getPattern().getDo();
		else if (newValue != null && newValue.equals(IMutationGenerator.INVALID))
			return null;
		else
		{
			Expression exp_value = null;
			Object instanceClass = values.feature.getEType().getInstanceClass();
			if (instanceClass != null)
			{
				if (instanceClass.equals(Integer.class) || instanceClass.equals(int.class))
				{
					exp_value = newValue == null ? new IntegerLiteral() : new IntegerLiteral((Integer) newValue);
				} else if (instanceClass.equals(Float.class) || instanceClass.equals(Float.class))
				{
					exp_value = newValue == null ? new RealLiteral() : new RealLiteral((Float) newValue);
				} else if (instanceClass.equals(Double.class) || instanceClass.equals(double.class))
				{
					exp_value = newValue == null ? new RealLiteral() : new RealLiteral((Double) newValue);
				} else if (instanceClass.equals(Boolean.class) || instanceClass.equals(boolean.class))
				{
					exp_value = newValue == null ? new BooleanLiteral() : new BooleanLiteral((Boolean) newValue);
				} else if (instanceClass.equals(String.class))
				{
					exp_value = newValue == null ? new StringLiteral() : new StringLiteral((String) newValue);
				}
			} else
			{
				exp_value = newValue == null ? null : (Expression) newValue;
			}

			statBlock = generateStatement(values.getInstanceName(), propertyName, exp_value, match, module);
			ExecutableBlock<Void> exeBlock = match.getPattern().getDo();
			if (exeBlock == null)
				exeBlock = generateExecutableBlock(module, statBlock.getUri());
			exeBlock.setBody(statBlock);
			statBlock.setParent(exeBlock);
			match.getPattern().setDo(exeBlock);
			return exeBlock;
		}
	}

	private ExecutableBlock<Void> generateExecutableBlock(EmuModule module, URI uri) {
		ExecutableBlock<Void> exeBlock = new ExecutableBlock<Void>(Void.class);
		exeBlock.setModule(module);
		exeBlock.setRole("do");
		exeBlock.setText("do");
		exeBlock.setUri(uri);
		return exeBlock;
	}

	private StatementBlock generateStatement(String instanceName, String propertyName, Expression newValue, PatternMatch match, EmuModule module) {
		NameExpression targetExp = new NameExpression(instanceName);
		targetExp.setTypeName(false);
		targetExp.setModule(module);
		targetExp.setUri(match.getPattern().getUri());

		NameExpression propertyExp = new NameExpression(propertyName);
		propertyExp.setTypeName(false);
		propertyExp.setModule(module);
		propertyExp.setUri(match.getPattern().getUri());

		PropertyCallExpression callExp = new PropertyCallExpression(targetExp, propertyExp);
		callExp.setModule(module);
		callExp.setUri(targetExp.getUri());
		targetExp.setParent(callExp);
		propertyExp.setParent(callExp);

		if (newValue != null)
		{
			newValue.setModule(module);
			newValue.setUri(callExp.getUri());
		}

		AssignmentStatement assignStat = new AssignmentStatement(callExp, newValue);
		assignStat.setModule(module);
		assignStat.setUri(callExp.getUri());
		callExp.setParent(assignStat);
		if (newValue != null)
			newValue.setParent(assignStat);

		StatementBlock statBlock = new StatementBlock(assignStat);
		statBlock.setModule(module);
		statBlock.setUri(assignStat.getUri());
		assignStat.setParent(statBlock);
		return statBlock;
	}

	private IModel getModelThatOwnsMatching() {
		return modelThatOwnsMatching;
	}

	private void setModelThatOwnsMatching(Object obj, IEolContext context) {
		for (IModel model : context.getModelRepository().getModels())
		{
			if (model.owns(obj))
			{
				this.modelThatOwnsMatching = model;
				return;
			}
		}
	}

	private void checkAnnotations(Pattern pattern) throws Exception {
		if (!pattern.hasAnnotation(MUTATION_ACTION_ANNOTATION))
			throw new Exception("Mutation action is not specified.");
		if (!pattern.hasAnnotation(FEATURE_ANNOTATION))
			throw new Exception("Feature name is not specified.");

		for (Annotation anno : pattern.getAnnotationBlock().getAnnotations())
		{
			if (anno.getName().equals(MUTATION_ACTION_ANNOTATION) && !anno.hasValue())
				throw new Exception("Value of mutation action is not specified.");
			if (anno.getName().equals(FEATURE_ANNOTATION) && !anno.hasValue())
				throw new Exception("Value of feature is not specified.");
		}
	}

	public IMutationGenerator getMutationGenerator() {
		if (mutationGeneratorImpl == null)
			mutationGeneratorImpl = new MutationGeneratorImpl();
		return mutationGeneratorImpl;
	}

	private String getAnnotationValue(Pattern pattern, String name, final IEolContext context) throws Exception {
		for (Annotation anno : pattern.getAnnotationBlock().getAnnotations())
		{
			if (anno.getName().equals(name))
				try
				{
					return anno.getValue(context).toString();
				} catch (EolRuntimeException e)
				{
					throw new Exception(e.getMessage());
				}
		}
		throw new Exception("Unable to find the value of annotation [" + name + "] in pattern [" + pattern.getName() + "]");
	}

	private class Tuple {
		private Object roleBinding;
		private String instanceName;
		private EClass type;
		private EStructuralFeature feature;
		private Object value;

		/**
		 * Generate a tuple of values that are used for mutation
		 * 
		 * @param binding:
		 *            the binding role of the pattern in which this mutation is based on
		 * @param instanceName
		 *            the instance name specified in the matching role of the pattern
		 * @param type
		 *            the meta-type of the object of the binding
		 * @param feature
		 *            the feature that its value is going to be mutated
		 * @param value
		 *            the value is going to be mutated
		 * @return return a new instance of a tuple
		 */
		public Tuple(Object binding, String instanceName, EClass type, EStructuralFeature feature, Object value) {
			roleBinding = binding;
			this.instanceName = instanceName;
			this.type = type;
			this.feature = feature;
			if (feature.isMany())
			{
				List<Object> toList = new ArrayList<Object>();
				@SuppressWarnings("unchecked")
				Collection<Object> collection = (Collection<Object>) value;
				for (Object obj : collection)
					toList.add(obj);
				this.value = (Object) toList;
			} else
				this.value = value;
		}

		public EClass getType() {
			return type;
		}

		public Object getRoleBinding() {
			return roleBinding;
		}

		public String getInstanceName() {
			return instanceName;
		}

		public EStructuralFeature getFeature() {
			return feature;
		}

		public Object getValue() {
			return value;
		}

		@Override
		public String toString() {
			String s;
			s = "Tuble" + this.hashCode() + "{";
			s += "Role Binding = " + roleBinding + ", ";
			s += "Instance:Type = " + instanceName + ":" + type.getName() + ", ";
			s += "Feature = " + feature.getName() + ", ";
			s += "Value = " + value;
			return s + "}";
		}
	}
}
