/**
 * 
 * @author Faisal Alhwikem
 * @version 1.0
 * @since March-2017
 */

package org.eclipse.epsilon.emu.mutation;

import org.eclipse.epsilon.emc.mutant.IProperty;
import org.eclipse.epsilon.emu.EmuModule;

public abstract class AMOReal {

	public static Object additionOp(Object roleBinding, IProperty property, Object value, EmuModule module) {
		Object instanceClass = property.getType();
		if (instanceClass.equals(Float.class) || instanceClass.equals(float.class)) {
			if (checkAdditionConditions(property, value).equals(IMutationGenerator.VALID)) {
				float newValue = (float) ((double) value);
				return (newValue + 1f);
			}
			return IMutationGenerator.INVALID;
		}
		throw new IllegalArgumentException("non-real property type " + property);
	}

	public static Object deletionOp(Object roleBinding, IProperty property, Object value, EmuModule module) {
		Object instanceClass = property.getType();
		if (instanceClass.equals(Float.class) || instanceClass.equals(float.class)) {
			if (checkDeletionConditions(property, value).equals(IMutationGenerator.VALID)) {
				float newValue = (float) ((double) value);
				return (newValue - 1f);
			}
			return IMutationGenerator.INVALID;
		}
		throw new IllegalArgumentException("non-real property type " + property);
	}

	public static Object replacementOp(Object roleBinding, IProperty property, Object value, EmuModule module) {
		Object instanceClass = property.getType();
		if (instanceClass.equals(Float.class) || instanceClass.equals(float.class)) {
			if (checkReplacementConditions(property, value).equals(IMutationGenerator.VALID)) {
				return IMutationGenerator.NOTIMPLEMENTED;
			}
			return IMutationGenerator.INVALID;
		}
		throw new IllegalArgumentException("non-real property type " + property);
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

	public static Object checkAdditionConditions(IProperty property, Object value, Object valueNew) {
		Object instanceClass = property.getType();
		if (instanceClass.equals(Float.class) || instanceClass.equals(float.class)) {
			if (checkAdditionConditions(property, value).equals(IMutationGenerator.VALID)) {
				if (valueNew == null)
					throw new IllegalArgumentException("The mutated real value must be defined: " + valueNew);
				if (valueNew == value)
					throw new IllegalArgumentException(
							"The original real value and the new value must not be equal: " + valueNew);
				return IMutationGenerator.VALID;
			}
			return IMutationGenerator.INVALID;
		}
		throw new IllegalArgumentException("non-real property type " + property);
	}

	public static Object checkDeletionConditions(IProperty property, Object value, Object valueNew) {
		Object instanceClass = property.getType();
		if (instanceClass.equals(Float.class) || instanceClass.equals(float.class)) {
			if (checkDeletionConditions(property, value).equals(IMutationGenerator.VALID)) {
				if (valueNew == null)
					throw new IllegalArgumentException("The mutated real value must be defined: " + valueNew);
				if (valueNew == value)
					throw new IllegalArgumentException(
							"The original real value and the new value must not be equal: " + valueNew);
				return IMutationGenerator.VALID;
			}
			return IMutationGenerator.INVALID;
		}
		throw new IllegalArgumentException("non-real property type " + property);
	}

	public static Object checkReplacementConditions(IProperty property, Object value, Object valueNew) {
		Object instanceClass = property.getType();
		if (instanceClass.equals(Float.class) || instanceClass.equals(float.class)) {
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
		throw new IllegalArgumentException("non-real property type " + property);
	}
}