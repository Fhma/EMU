/**
 * 
 * @author Faisal Alhwikem
 * @version 1.0
 * @since March-2017
 */

package org.eclipse.epsilon.emu.execute;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.eclipse.epsilon.emc.mutant.IMutant;
import org.eclipse.epsilon.emc.mutant.IProperty;
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
	public static final String INVALID_MUTATION = "N/A";

	protected IModel modelThatOwnsMatching = null;
	private IMutationGenerator mutationGeneratorImpl = null;

	public PatternMatchModel match(EmuModule module) throws Exception {
		frame = null;
		IEolContext context = module.getContext();
		PatternMatchModel model = new PatternMatchModel();

		if (module.getMaximumLevel() > 0) {
			model.setName(module.getPatternMatchModelName());
			context.getModelRepository().addModel(model);
		}

		model.setPatterns(module.getPatterns());

		for (int level = 0; level <= module.getMaximumLevel(); level++) {
			for (Pattern pattern : module.getPatterns()) {
				// Check annotations given by this pattern
				checkAnnotations(pattern);

				if (pattern.getLevel() == level) {
					List<PatternMatch> pattern_matches = match(pattern, context);
					for (PatternMatch match : pattern_matches) {
						model.addMatch(match);
					}
				}
			}

			Tuple values;
			int index;

			String location = module.getMutantsDir().toURI().toString();

			for (PatternMatch match : model.getMatches()) {
				if (match.getPattern().getLevel() == level) {

					Object valid_mutant = null;
					values = prepareForMutation(module, match, context);
					String mutation_action = getAnnotationValue(match.getPattern(), MUTATION_ACTION_ANNOTATION,
							context);

					ExecutableBlock<Void> do_ = getDoBlock(values, module, match, context);
					String operatorName = values.getType() + "_" + values.getProperty().getName() + "_"
							+ getAnnotationValue(match.getPattern(), MUTATION_ACTION_ANNOTATION, context);

					if (do_ != null) {
						context.getFrameStack().enterLocal(FrameType.UNPROTECTED, do_);
						for (String componentName : match.getRoleBindings().keySet()) {
							context.getFrameStack().put(Variable.createReadOnlyVariable(componentName,
									match.getRoleBinding(componentName)));
						}

						context.getModelRepository().getTransactionSupport().startTransaction();

						try {
							context.getExecutorFactory().executeAST(do_, context);
						} catch (EolInternalException e) {
							context.getModelRepository().getTransactionSupport().rollbackTransaction();
							context.getFrameStack().leaveLocal(do_);
							module.getOperatorsMatrix().getValue(operatorName).add(INVALID_MUTATION);
							continue;
						} catch (Exception e) {
							throw new Exception(e);
						}

						Object newValue = getModelThatOwnsMatching().getPropertyGetter().invoke(values.getRoleBinding(),
								values.getProperty().getName());
						valid_mutant = getMutationGenerator().checkConditions(values.getProperty(), values.getValue(),
								newValue, mutation_action);

						if (valid_mutant == IMutationGenerator.VALID) {
							index = module.getOperatorsMatrix().getValue(operatorName).size() + 1;
							String mutant = location + operatorName + "_" + index + ".xmi";
							module.getOperatorsMatrix().getValue(operatorName).add(mutant);
							getModelThatOwnsMatching().store(mutant);
						}
						context.getModelRepository().getTransactionSupport().rollbackTransaction();
						context.getFrameStack().leaveLocal(do_);
					}
					if (valid_mutant == null
							|| (valid_mutant != null && !valid_mutant.equals(IMutationGenerator.VALID))) {
						module.getOperatorsMatrix().getValue(operatorName).add(INVALID_MUTATION);
					}
				}
			}
		}
		return model;
	}

	private Tuple prepareForMutation(EmuModule module, PatternMatch match, IEolContext context) throws Exception {
		
		// check which role has the target instance and type
		String instanceName = null;
		Object roleBinding = null;
		String targetType = null;
		Set<Map.Entry<String, Object>> RoleBindingSet = match.getRoleBindings().entrySet();
		boolean found = false;
		IMutant owning_model = null;
		Iterator<Map.Entry<String, Object>> it = RoleBindingSet.iterator();
		while (it.hasNext() && !found) {
			Map.Entry<String, Object> pair = it.next();
			if (RoleBindingSet.size() > 1
					&& !pair.getKey().equals(getAnnotationValue(match.getPattern(), TYPE_ANNOTATION, context)))
				continue;
			owning_model = (IMutant) module.getContext().getModelRepository().getOwningModel(pair.getValue());
			boolean res = owning_model.getPropertyGetter().hasProperty(pair.getValue(),
					getAnnotationValue(match.getPattern(), FEATURE_ANNOTATION, context));

			if (res) {
				instanceName = pair.getKey();
				roleBinding = pair.getValue();
				targetType = owning_model.getTypeNameOf(roleBinding);
				found = true;
			}
		}

		if (!found)
			throw new IllegalArgumentException(
					"Unable to find the target type or property within the matching roles of pattern ["
							+ match.getPattern().getName() + "]");

		if (roleBinding == null)
			throw new IllegalArgumentException("Unrecognizable Object " + roleBinding);

		String propertyName = getAnnotationValue(match.getPattern(), FEATURE_ANNOTATION, context);

		setModelThatOwnsMatching(roleBinding, context);

		// TODO: follow Epsilon EMC principle and redo
		// EStructuralFeature feature = EmfUtil.getEStructuralFeature((EClass)
		// ((EObject) roleBinding).eClass(), propertyName);
		IProperty feature = owning_model.getProperty(roleBinding, propertyName);

		Object feature_value = getModelThatOwnsMatching().getPropertyGetter().invoke(roleBinding, propertyName);

		return new Tuple(roleBinding, instanceName, targetType, feature, feature_value);
	}

	private ExecutableBlock<Void> getDoBlock(Tuple values, EmuModule module, PatternMatch match, IEolContext context)
			throws Exception {
		String propertyName = values.getProperty().getName();
		String mutation_action;

		mutation_action = getAnnotationValue(match.getPattern(), MUTATION_ACTION_ANNOTATION, context);

		Object newValue = null;
		StatementBlock statBlock = null;

		newValue = getMutationGenerator().mutate(values.getRoleBinding(), values.getProperty(), values.getValue(),
				module, mutation_action);
		if (newValue != null && newValue.equals(IMutationGenerator.NOTIMPLEMENTED))
			return match.getPattern().getDo();
		else if (newValue != null && newValue.equals(IMutationGenerator.INVALID))
			return null;
		else {
			Expression exp_value = null;
			Object instanceClass = values.property.getType();
			if (instanceClass != null) {
				if (instanceClass.equals(Integer.class) || instanceClass.equals(int.class)) {
					exp_value = newValue == null ? new IntegerLiteral() : new IntegerLiteral((Integer) newValue);
				} else if (instanceClass.equals(Float.class) || instanceClass.equals(Float.class)) {
					exp_value = newValue == null ? new RealLiteral() : new RealLiteral((Float) newValue);
				} else if (instanceClass.equals(Double.class) || instanceClass.equals(double.class)) {
					exp_value = newValue == null ? new RealLiteral() : new RealLiteral((Double) newValue);
				} else if (instanceClass.equals(Boolean.class) || instanceClass.equals(boolean.class)) {
					exp_value = newValue == null ? new BooleanLiteral() : new BooleanLiteral((Boolean) newValue);
				} else if (instanceClass.equals(String.class)) {
					exp_value = newValue == null ? new StringLiteral() : new StringLiteral((String) newValue);
				}
			} else {
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

	private StatementBlock generateStatement(String instanceName, String propertyName, Expression newValue,
			PatternMatch match, EmuModule module) {
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

		if (newValue != null) {
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
		modelThatOwnsMatching = context.getModelRepository().getOwningModel(obj);
	}

	private void checkAnnotations(Pattern pattern) throws Exception {
		if (!pattern.hasAnnotation(MUTATION_ACTION_ANNOTATION))
			throw new Exception("Mutation action is not specified.");
		if (!pattern.hasAnnotation(FEATURE_ANNOTATION))
			throw new Exception("Feature name is not specified.");

		for (Annotation anno : pattern.getAnnotationBlock().getAnnotations()) {
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
		for (Annotation anno : pattern.getAnnotationBlock().getAnnotations()) {
			if (anno.getName().equals(name))
				try {
					return anno.getValue(context).toString();
				} catch (EolRuntimeException e) {
					throw new Exception(e.getMessage());
				}
		}
		throw new Exception(
				"Unable to find the value of annotation [" + name + "] in pattern [" + pattern.getName() + "]");
	}

	private class Tuple {
		private Object roleBinding;
		private String instanceName;
		private String type;
		private IProperty property;
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
		 * @param property
		 *            the property that is to be mutated
		 * @param value
		 *            the value is going to be mutated
		 * @return return a new instance of a tuple
		 */
		public Tuple(Object binding, String instanceName, String type, IProperty property, Object value) {
			roleBinding = binding;
			this.instanceName = instanceName;
			this.type = type;
			this.property = property;
			if (property.isMultiValued()) {
				List<Object> toList = new ArrayList<Object>();
				@SuppressWarnings("unchecked")
				Collection<Object> collection = (Collection<Object>) value;
				for (Object obj : collection)
					toList.add(obj);
				this.value = (Object) toList;
			} else
				this.value = value;
		}

		public String getType() {
			return type;
		}

		public Object getRoleBinding() {
			return roleBinding;
		}

		public String getInstanceName() {
			return instanceName;
		}

		public IProperty getProperty() {
			return property;
		}

		public Object getValue() {
			return value;
		}
	}
}
