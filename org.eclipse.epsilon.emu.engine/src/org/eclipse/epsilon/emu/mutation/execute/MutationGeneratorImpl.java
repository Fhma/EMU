/**
 * 
 * @author Faisal Alhwikem
 * @version 1.0
 * @since March-2017
 */

package org.eclipse.epsilon.emu.mutation.execute;

import org.eclipse.epsilon.emc.mutant.IProperty;
import org.eclipse.epsilon.emu.EmuModule;
import org.eclipse.epsilon.emu.mutation.IMutationGenerator;
import org.eclipse.epsilon.emu.mutation.AMOBoolean;
import org.eclipse.epsilon.emu.mutation.AMOInteger;
import org.eclipse.epsilon.emu.mutation.AMOMultiProperty;
import org.eclipse.epsilon.emu.mutation.AMOReal;
import org.eclipse.epsilon.emu.mutation.AMOSingleProperty;
import org.eclipse.epsilon.emu.mutation.AMOString;

public class MutationGeneratorImpl implements IMutationGenerator {

	@Override
	public Object mutate(Object roleBinding, IProperty property, Object value, EmuModule module,
			String mutation_action) {
		if (roleBinding == null)
			throw new NullPointerException("Undefined pattern match role binding object");
		if (property == null)
			throw new NullPointerException("Undefined feature object");
		if (module == null)
			throw new NullPointerException("Undefined EolModule object");
		if (mutation_action == null)
			throw new NullPointerException("Undefined mutation action");

		if (!property.isMultiValued()) {
			if (property.isDataType()) {
				if (mutation_action.equals(ADD_MUTATION_ACTION)) {
					Object instanceClass = (Object) property.getType();
					if (instanceClass.equals(Boolean.class) || instanceClass.equals(boolean.class))
						return AMOBoolean.additionOp(roleBinding, property, value, module);
					if (instanceClass.equals(Integer.class) || instanceClass.equals(int.class))
						return AMOInteger.additionOp(roleBinding, property, value, module);
					if (instanceClass.equals(Float.class) || instanceClass.equals(float.class))
						return AMOReal.additionOp(roleBinding, property, value, module);
					if (instanceClass.equals(String.class))
						return AMOString.additionOp(roleBinding, property, value, module);
					throw new IllegalArgumentException("Unsupported feature type");
				}
				if (mutation_action.equals(DEL_MUTATION_ACTION)) {
					Object instanceClass = (Object) property.getType();
					if (instanceClass.equals(Boolean.class) || instanceClass.equals(boolean.class))
						return AMOBoolean.deletionOp(roleBinding, property, value, module);
					if (instanceClass.equals(Integer.class) || instanceClass.equals(int.class))
						return AMOInteger.deletionOp(roleBinding, property, value, module);
					if (instanceClass.equals(Float.class) || instanceClass.equals(float.class))
						return AMOReal.deletionOp(roleBinding, property, value, module);
					if (instanceClass.equals(String.class))
						return AMOString.deletionOp(roleBinding, property, value, module);
					throw new IllegalArgumentException("Unsupported feature type");
				}
				if (mutation_action.equals(REPLACE_MUTATION_ACTION)) {
					Object instanceClass = (Object) property.getType();
					if (instanceClass.equals(Boolean.class) || instanceClass.equals(boolean.class))
						return AMOBoolean.replacementOp(roleBinding, property, value, module);
					if (instanceClass.equals(Integer.class) || instanceClass.equals(int.class))
						return AMOInteger.replacementOp(roleBinding, property, value, module);
					if (instanceClass.equals(Float.class) || instanceClass.equals(float.class))
						return AMOReal.replacementOp(roleBinding, property, value, module);
					if (instanceClass.equals(String.class))
						return AMOString.replacementOp(roleBinding, property, value, module);
					throw new IllegalArgumentException("Unsupported feature type");
				}
			} else if (property.isSingleValued()) {
				if (mutation_action.contentEquals(ADD_MUTATION_ACTION))
					return AMOSingleProperty.additionOp(roleBinding, property, value, module);
				if (mutation_action.equals(DEL_MUTATION_ACTION))
					return AMOSingleProperty.deletionOp(roleBinding, property, value, module);
				if (mutation_action.equals(REPLACE_MUTATION_ACTION))
					return AMOSingleProperty.replacementOp(roleBinding, property, value, module);

			} else
				throw new IllegalArgumentException("Unrecognizable property object " + property);
		} else {
			if (mutation_action.equals(ADD_MUTATION_ACTION))
				return AMOMultiProperty.additionOp(roleBinding, property, value, module);
			if (mutation_action.equals(DEL_MUTATION_ACTION))
				return AMOMultiProperty.deletionOp(roleBinding, property, value, module);
			if (mutation_action.equals(REPLACE_MUTATION_ACTION))
				return AMOMultiProperty.replacementOp(roleBinding, property, value, module);
		}
		return null;
	}

	@Override
	public Object checkConditions(IProperty property, Object old_value, Object new_value, String mutation_action) {
		if (property == null)
			throw new NullPointerException("Undefined property object");
		if (mutation_action == null)
			throw new NullPointerException("Undefined mutation action");

		if (!property.isMultiValued()) {
			if (property.isDataType()) {
				// single-valued attribute
				if (mutation_action.equals(ADD_MUTATION_ACTION)) {
					Object instanceClass = (Object) property.getType();
					if (instanceClass.equals(Boolean.class) || instanceClass.equals(boolean.class))
						return AMOBoolean.checkAdditionConditions(property, old_value, new_value);
					if (instanceClass.equals(Integer.class) || instanceClass.equals(int.class))
						return AMOInteger.checkAdditionConditions(property, old_value, new_value);
					if (instanceClass.equals(Float.class) || instanceClass.equals(float.class))
						return AMOReal.checkAdditionConditions(property, old_value, new_value);
					if (instanceClass.equals(String.class))
						return AMOString.checkAdditionConditions(property, old_value, new_value);
					throw new IllegalArgumentException("Unsupported property type");
				}
				if (mutation_action.equals(DEL_MUTATION_ACTION)) {
					Object instanceClass = (Object) property.getType();
					if (instanceClass.equals(Boolean.class) || instanceClass.equals(boolean.class))
						return AMOBoolean.checkDeletionConditions(property, old_value, new_value);
					if (instanceClass.equals(Integer.class) || instanceClass.equals(int.class))
						return AMOInteger.checkDeletionConditions(property, old_value, new_value);
					if (instanceClass.equals(Float.class) || instanceClass.equals(float.class))
						return AMOReal.checkDeletionConditions(property, old_value, new_value);
					if (instanceClass.equals(String.class))
						return AMOString.checkDeletionConditions(property, old_value, new_value);
					throw new IllegalArgumentException("Unsupported property type");
				}
				if (mutation_action.equals(REPLACE_MUTATION_ACTION)) {
					Object instanceClass = (Object) property.getType();
					if (instanceClass.equals(Boolean.class) || instanceClass.equals(boolean.class))
						return AMOBoolean.checkReplacementConditions(property, old_value, new_value);
					if (instanceClass.equals(Integer.class) || instanceClass.equals(int.class))
						return AMOInteger.checkReplacementConditions(property, old_value, new_value);
					if (instanceClass.equals(Float.class) || instanceClass.equals(float.class))
						return AMOReal.checkReplacementConditions(property, old_value, new_value);
					if (instanceClass.equals(String.class))
						return AMOString.checkReplacementConditions(property, old_value, new_value);
					throw new IllegalArgumentException("Unsupported property type");
				}
			} else {
				// single-valued reference
				if (mutation_action.equals(ADD_MUTATION_ACTION))
					return AMOSingleProperty.checkAdditionConditions(property, old_value, new_value);
				if (mutation_action.equals(DEL_MUTATION_ACTION))
					return AMOSingleProperty.checkDeletionConditions(property, old_value, new_value);
				if (mutation_action.equals(REPLACE_MUTATION_ACTION))
					return AMOSingleProperty.checkReplacementConditions(property, old_value, new_value);
			}
		} else {
			if (mutation_action.equals(ADD_MUTATION_ACTION))
				return AMOMultiProperty.checkAdditionConditions(property, old_value, new_value);
			if (mutation_action.equals(DEL_MUTATION_ACTION))
				return AMOMultiProperty.checkDeletionConditions(property, old_value, new_value);
			if (mutation_action.equals(REPLACE_MUTATION_ACTION))
				return AMOMultiProperty.checkReplacementConditions(property, old_value, new_value);
		}
		return true;
	}
}
