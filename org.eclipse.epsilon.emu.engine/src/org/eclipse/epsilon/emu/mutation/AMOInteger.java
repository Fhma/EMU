/**
 * 
 * @author Faisal Alhwikem
 * @version 1.0
 * @since March-2017
 */

package org.eclipse.epsilon.emu.mutation;

import org.eclipse.epsilon.emc.mutant.IProperty;
import org.eclipse.epsilon.emu.EmuModule;

public abstract class AMOInteger {

	public static Object additionOp(Object roleBinding, IProperty property, Object value, EmuModule module) {
		Object instanceClass = property.getType();
		if (instanceClass.equals(Integer.class) || instanceClass.equals(int.class)) {
			if (checkAdditionConditions(property, value).equals(IMutationGenerator.VALID)) {
				int newValue = ((int) value) + 1;
				return (newValue);
			}
			return IMutationGenerator.INVALID;
		}
		throw new IllegalArgumentException("non-integer property type: " + property);
	}

	public static Object deletionOp(Object roleBinding, IProperty property, Object value, EmuModule module) {
		Object instanceClass = property.getType();
		if (instanceClass.equals(Integer.class) || instanceClass.equals(int.class)) {
			if (checkDeletionConditions(property, value).equals(IMutationGenerator.VALID)) {
				int newValue = ((int) value) - 1;
				return (newValue);
			}
			return IMutationGenerator.INVALID;
		}
		throw new IllegalArgumentException("non-integer property type: " + property);
	}

	public static Object replacementOp(Object roleBinding, IProperty property, Object value, EmuModule module) {
		Object instanceClass = property.getType();
		if (instanceClass.equals(Integer.class) || instanceClass.equals(int.class)) {
			if (checkReplacementConditions(property, value).equals(IMutationGenerator.VALID)) {
				return IMutationGenerator.NOTIMPLEMENTED;
			}
			return IMutationGenerator.INVALID;
		}
		throw new IllegalArgumentException("non-integer property type: " + property);
	}

	private static Object checkAdditionConditions(IProperty property, Object value) {
		if (value == null)
			return IMutationGenerator.INVALID;
		return IMutationGenerator.VALID;
	}

	private static Object checkDeletionConditions(IProperty property, Object value) {
		if (value == null)
			return IMutationGenerator.INVALID;
		return IMutationGenerator.VALID;
	}

	private static Object checkReplacementConditions(IProperty property, Object value) {
		return IMutationGenerator.VALID;
	}

	public static Object checkAdditionConditions(IProperty property, Object value, Object newValue) {
		Object instanceClass = property.getType();
		if (instanceClass.equals(Integer.class) || instanceClass.equals(int.class)) {
			if (checkAdditionConditions(property, value).equals(IMutationGenerator.VALID)) {
				if (newValue == null)
					throw new IllegalArgumentException("The mutated integer value must be defined: " + newValue);
				if (newValue.equals(value))
					throw new IllegalArgumentException(
							"The original integer value and the new value must not be equal: " + newValue);
				return IMutationGenerator.VALID;
			}
			return IMutationGenerator.INVALID;
		}
		throw new IllegalArgumentException("non-integer property type: " + property);
	}

	public static Object checkDeletionConditions(IProperty property, Object value, Object newValue) {
		Object instanceClass = property.getType();
		if (instanceClass.equals(Integer.class) || instanceClass.equals(int.class)) {
			if (checkDeletionConditions(property, value).equals(IMutationGenerator.VALID)) {
				if (newValue == null)
					throw new IllegalArgumentException("The mutated integer value must be defined: " + newValue);
				if (newValue.equals(value))
					throw new IllegalArgumentException(
							"The original integer value and the new value must not be equal: " + newValue);
				return IMutationGenerator.VALID;
			}
			return IMutationGenerator.INVALID;
		}
		throw new IllegalArgumentException("non-integer property type: " + property);
	}

	public static Object checkReplacementConditions(IProperty property, Object value, Object valueNew) {
		Object instanceClass = property.getType();
		if (instanceClass.equals(Integer.class) || instanceClass.equals(int.class)) {
			if (checkReplacementConditions(property, value).equals(IMutationGenerator.VALID)) {
				if (value != null && value.equals(valueNew))
					return IMutationGenerator.INVALID;
				if (valueNew != null && valueNew.equals(value))
					return IMutationGenerator.INVALID;
				if (valueNew == value)
					return IMutationGenerator.INVALID;
				return IMutationGenerator.VALID;
			}
			return IMutationGenerator.INVALID;
		}
		throw new IllegalArgumentException("non-integer property type: " + property);
	}
}
