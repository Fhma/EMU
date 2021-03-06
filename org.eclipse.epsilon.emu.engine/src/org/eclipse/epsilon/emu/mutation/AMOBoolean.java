/**
 * 
 * @author Faisal Alhwikem
 * @version 1.0
 * @since March-2017
 */

package org.eclipse.epsilon.emu.mutation;

import org.eclipse.epsilon.emc.mutant.IProperty;
import org.eclipse.epsilon.emu.EmuModule;

public abstract class AMOBoolean {

	public static Object additionOp(Object roleBinding, IProperty feature, Object value, EmuModule module) {
		Object instanceClass = feature.getType();
		if (instanceClass.equals(Boolean.class) || instanceClass.equals(boolean.class)) {
			if (checkAdditionConditions(feature, value).equals(IMutationGenerator.VALID)) {
				return IMutationGenerator.NOTIMPLEMENTED;
			}
			return IMutationGenerator.INVALID;
		}
		throw new IllegalArgumentException("non-boolean feature type: " + feature);
	}

	public static Object deletionOp(Object roleBinding, IProperty property, Object value, EmuModule module) {
		Object instanceClass = property.getType();
		if (instanceClass.equals(Boolean.class) || instanceClass.equals(boolean.class)) {
			if (checkDeletionConditions(property, value).equals(IMutationGenerator.VALID)) {
				return null;
			}
			return IMutationGenerator.INVALID;
		}
		throw new IllegalArgumentException("non-boolean feature type: " + property);
	}

	public static Object replacementOp(Object roleBinding, IProperty property, Object value, EmuModule module) {
		Object instanceClass = property.getType();
		if (instanceClass.equals(Boolean.class) || instanceClass.equals(boolean.class)) {
			if (checkReplacementConditions(property, value).equals(IMutationGenerator.VALID)) {
				boolean state = (boolean) value;
				return (!state);
			}
			return IMutationGenerator.INVALID;
		}
		throw new IllegalArgumentException("non-boolean feature type: " + property);
	}

	private static Object checkAdditionConditions(IProperty property, Object value) {
		if (value != null)
			return IMutationGenerator.INVALID;
		return IMutationGenerator.VALID;
	}

	private static Object checkDeletionConditions(IProperty property, Object value) {
		if (property.getLowerBound() == 1)
			return IMutationGenerator.INVALID;
		if (value == null)
			return IMutationGenerator.INVALID;
		return IMutationGenerator.VALID;
	}

	private static Object checkReplacementConditions(IProperty property, Object value) {
		if (value == null)
			return IMutationGenerator.INVALID;
		return IMutationGenerator.VALID;
	}

	public static Object checkAdditionConditions(IProperty property, Object value, Object newValue) {
		Object instanceClass = property.getType();
		if (instanceClass.equals(Boolean.class) || instanceClass.equals(boolean.class)) {
			if (checkAdditionConditions(property, value).equals(IMutationGenerator.VALID)) {
				if (newValue == null)
					return IMutationGenerator.INVALID;
				if (newValue.equals(value))
					return IMutationGenerator.INVALID;
				return IMutationGenerator.VALID;
			}
			return IMutationGenerator.INVALID;
		}
		throw new IllegalArgumentException("non-boolean feature type: " + property);
	}

	public static Object checkDeletionConditions(IProperty property, Object value, Object newValue) {
		Object instanceClass = property.getType();
		if (instanceClass.equals(Boolean.class) || instanceClass.equals(boolean.class)) {
			if (checkDeletionConditions(property, value).equals(IMutationGenerator.VALID)) {
				if (newValue != null)
					throw new IllegalArgumentException("The mutated Object value must not be defined: " + newValue);
				return IMutationGenerator.VALID;
			}
			return IMutationGenerator.INVALID;
		}
		throw new IllegalArgumentException("non-boolean feature type: " + property);
	}

	public static Object checkReplacementConditions(IProperty property, Object value, Object newValue) {
		Object instanceClass = property.getType();
		if (instanceClass.equals(Boolean.class) || instanceClass.equals(boolean.class)) {
			if (checkReplacementConditions(property, value).equals(IMutationGenerator.VALID)) {
				if (newValue == null)
					throw new IllegalArgumentException("The mutated Object value must be defined: " + newValue);
				if (newValue.equals(value))
					throw new IllegalArgumentException(
							"The original Object value and mutated Object value must be different: " + value);
				return IMutationGenerator.VALID;
			}
			return IMutationGenerator.INVALID;
		}
		throw new IllegalArgumentException("non-boolean feature type: " + property);
	}
}
