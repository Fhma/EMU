/*******************************************************************************
 * Copyright (c) 2018 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Dimitrios Kolovos - initial API and implementation
 *     Faisal Alhwikem
 ******************************************************************************/
package org.eclipse.epsilon.emu.execute;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.epsilon.emc.mutant.IMutant;
import org.eclipse.epsilon.emc.mutant.IProperty;
import org.eclipse.epsilon.emu.EmuModule;
import org.eclipse.epsilon.emu.dom.Mutation;
import org.eclipse.epsilon.emu.exceptions.EmuRuntimeException;
import org.eclipse.epsilon.epl.dom.Role;
import org.eclipse.epsilon.eol.dom.ExecutableBlock;
import org.eclipse.epsilon.eol.exceptions.EolInternalException;
import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;
import org.eclipse.epsilon.eol.execute.context.Frame;
import org.eclipse.epsilon.eol.execute.context.FrameType;
import org.eclipse.epsilon.eol.execute.context.IEolContext;
import org.eclipse.epsilon.eol.execute.context.Variable;
import org.eclipse.epsilon.epl.combinations.CombinationGenerator;
import org.eclipse.epsilon.epl.combinations.CombinationGeneratorListener;
import org.eclipse.epsilon.epl.combinations.CompositeCombinationGenerator;
import org.eclipse.epsilon.epl.combinations.CompositeCombinationValidator;
import org.eclipse.epsilon.epl.combinations.DynamicListCombinationGenerator;
import org.eclipse.epsilon.epl.dom.NoMatch;
import org.eclipse.epsilon.common.parse.Region;

public class EmuPatternMatcher {

	protected Frame frame = null;
	protected List<MutationMatch> matches = null;
	protected IProperty property;

	public Object match(EmuModule module, Mutation currentMutation) throws Exception {
		frame = null;
		IEolContext context = module.getContext();
		matches = match(currentMutation, context);

		if (matches.size() <= 0) {
			return matches;
		}

		int index = 1;

		for (MutationMatch match : matches) {

			prepareForMutation(module, match, context);

			Object old_value = null;

			if (match.getMutation().getMaxNumberOfMutations() != EmuModule.INFINITY_MUTATION && index > match.getMutation().getMaxNumberOfMutations())
				return matches;

			boolean isSingleProperty = property.isSingleValued();

			if (!isSingleProperty) {
				List<Object> toList = new ArrayList<Object>();
				@SuppressWarnings("unchecked")
				Collection<Object> collection = (Collection<Object>) property.getValue();
				for (Object obj : collection)
					toList.add(obj);
				old_value = (Object) toList;
			} else {
				old_value = property.getValue();
			}
			String operatorName = property.getCurrentContainerTypeName() + "_" + property.getName() + "_";
			String actionStr = null;
			if (currentMutation.getMutationActionType() == EmuModule.ADDITION_MUTATION_TYPE) {
				operatorName += EmuModule.ADDITION_KEYWORD;
				actionStr = EmuModule.ADDITION_KEYWORD;
			} else if (currentMutation.getMutationActionType() == EmuModule.DELETION_MUTATION_TYPE) {
				operatorName += EmuModule.DELETION_KEYWORD;
				actionStr = EmuModule.DELETION_KEYWORD;
			} else {
				operatorName += EmuModule.REPLACEMENT_KEYWORD;
				actionStr = EmuModule.REPLACEMENT_KEYWORD;
			}
			Region r = currentMutation.getRegion();

			String mu = "mutation lines: " + r.getStart().getLine() + "-" + r.getEnd().getLine();

			ExecutableBlock<Void> action = match.getMutation().getActionBlock();

			if (action == null)
				throw new EmuRuntimeException("No action block define in " + mu, currentMutation);

			context.getFrameStack().enterLocal(FrameType.UNPROTECTED, action);
			for (String componentName : match.getRoleBindings().keySet()) {
				context.getFrameStack().put(Variable.createReadOnlyVariable(componentName, match.getRoleBinding(componentName)));
			}
			context.getModelRepository().getTransactionSupport().startTransaction();

			try {
				context.getExecutorFactory().execute(action, context);
			} catch (EolInternalException e) {
				context.getModelRepository().getTransactionSupport().rollbackTransaction();
				context.getFrameStack().leaveLocal(action);
				continue;
			} catch (Exception e) {
				throw new EmuRuntimeException(e.getMessage(), currentMutation);
			}

			Object new_value = property.getValue();

			boolean validAction = false;

			if (isSingleProperty)
				validAction = property.checkSingleAMOConditions(old_value, new_value, actionStr);
			else
				validAction = property.checkMultiAMOConditions(old_value, new_value, actionStr);

			if (validAction) {
				module.storeMutant(property.getModel(), operatorName);
				module.incrementOperatorIndex(operatorName);
				module.incrementValid();
				index++;
			} else
				module.incrementInvalid();
			context.getModelRepository().getTransactionSupport().rollbackTransaction();
			context.getFrameStack().leaveLocal(action);
		}
		return matches;
	}

	/**
	 * Match an EPL pattern in the given context
	 * 
	 * @param pattern The pattern
	 * @param context THe current context
	 * @return A list of Pattern Matches
	 * @throws Exception
	 */
	public List<MutationMatch> match(final Mutation mutation, final IEolContext context) throws Exception {

		List<MutationMatch> patternMatches = new ArrayList<MutationMatch>();
		CompositeCombinationGenerator<Object> generator = initGenerator(mutation, context);

		while (generator.hasMore()) {
			List<List<Object>> candidate = generator.getNext();
			populateFrame(mutation, context, candidate);
			patternMatches.add(createPatternMatch(mutation, candidate));
			context.getFrameStack().leaveLocal(mutation);
		}
		// context.getFrameStack().leaveLocal(mutation);
		return patternMatches;
	}

	/**
	 * Add the candidate objects to the frame
	 * 
	 * @param mutation  the pattern being executed
	 * @param context   the context
	 * @param candidate the list of candidate objects
	 */
	protected void populateFrame(final Mutation mutation, final IEolContext context, List<List<Object>> candidate) {
		frame = context.getFrameStack().enterLocal(FrameType.PROTECTED, mutation);
	}

	/**
	 * Create a new CompositeCombinationGenerator, add the generator for each role and attach the validator.
	 * 
	 * @param mutation the pattern being executed
	 * @param context  the context
	 * @return the created generator
	 * @throws EolRuntimeException
	 */
	protected CompositeCombinationGenerator<Object> initGenerator(final Mutation mutation, final IEolContext context) throws EolRuntimeException {
		CompositeCombinationGenerator<Object> generator = new CompositeCombinationGenerator<Object>();

		for (Role role : mutation.getRoles()) {
			generator.addCombinationGenerator(createCombinationGenerator(role, context));
		}

		generator.setValidator(new CompositeCombinationValidator<Object>() {

			@Override
			public boolean isValid(List<List<Object>> combination) throws Exception {

				frame = context.getFrameStack().enterLocal(FrameType.PROTECTED, mutation);
				boolean result = true;
				int i = 0;
				Role role = null;
				for (List<Object> values : combination) {
					role = mutation.getRoles().get(i);
					for (Variable variable : getVariables(values, role)) {
						frame.put(variable);
					}
					i++;
				}
				if (role.getGuard() != null) {
					result = role.getGuard().execute(context);
				}
				context.getFrameStack().leaveLocal(mutation);
				return result;
			}
		});
		return generator;
	}

	protected List<Variable> getVariables(List<Object> combination, Role role) {
		ArrayList<Variable> variables = new ArrayList<Variable>();
		int i = 0;
		for (String name : role.getNames()) {
			variables.add(Variable.createReadOnlyVariable(name, combination.get(i)));
			i++;
		}
		return variables;
	}

	protected CombinationGenerator<Object> createCombinationGenerator(final Role role, final IEolContext context) throws EolRuntimeException {
		DynamicListCombinationGenerator<Object> combinationGenerator = null;

		combinationGenerator = new DynamicListCombinationGenerator<Object>(role.getInstances(context), role.getNames().size()) {
			@Override
			public Boolean checkOptional() {
				return false;
			}
		};

		// FixedCombinationGenerator<Object> combinationGenerator = null;
		// combinationGenerator = new FixedCombinationGenerator<Object>(component.getInstances(context),
		// component.getNames().size());

		combinationGenerator.addListener(new CombinationGeneratorListener<Object>() {
			@Override
			public void generated(List<Object> next) {
				// if (next != null)

				if (next == null) {
					for (String name : role.getNames()) {
						context.getFrameStack().put(Variable.createReadOnlyVariable(name, NoMatch.INSTANCE));
					}
				} else {
					for (Variable variable : getVariables(next, role)) {
						context.getFrameStack().put(variable);
					}
				}
			}

			@Override
			public void reset() {
				for (String variableName : role.getNames()) {
					context.getFrameStack().remove(variableName);
				}
			}
		});

		return combinationGenerator;
	}

	protected MutationMatch createPatternMatch(Mutation mutation, List<List<Object>> combination) {

		MutationMatch patternMatch = new MutationMatch(mutation);
		int i = 0;
		for (Role role : mutation.getRoles()) {
			for (Variable variable : getVariables(combination.get(i), role)) {
				patternMatch.getRoleBindings().put(variable.getName(), variable.getValue());
			}

			i++;
		}
		return patternMatch;
	}

	private void prepareForMutation(EmuModule module, MutationMatch match, IEolContext context) throws Exception {

		// check which role has the target instance and type
		String property_name = match.getMutation().getName();

		Set<Map.Entry<String, Object>> bindings = match.getRoleBindings().entrySet();

		if (match.roleBindings.size() <= 0)
			throw new EmuRuntimeException("No binding roles defined for mutation line " + match.getMutation().getRegion().getStart().getLine(), match.getMutation());

		Iterator<Map.Entry<String, Object>> it = bindings.iterator();
		Map.Entry<String, Object> role = it.next();

		if (role == null)
			throw new EmuRuntimeException("No binding roles defined for mutation line " + match.getMutation().getRegion().getStart().getLine(), match.getMutation());

		Object b_role_type = role.getValue();
		IMutant _model = (IMutant) module.getContext().getModelRepository().getOwningModel(b_role_type);

		property = _model.getProperty(b_role_type, property_name);
		property.setContainerInstance(_model, b_role_type);
	}
}
