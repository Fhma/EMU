/**
 * 
 * @author Faisal Alhwikem
 * @version 1.0
 * @since March-2017
 */

package org.eclipse.epsilon.emu.mutation;

import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.epsilon.emu.EmuModule;

public abstract class AMOReal {

	public static Object additionOp(Object roleBinding, EStructuralFeature feature, Object value, EmuModule module) {
		Object instanceClass = feature.getEType().getInstanceClass();
		if (instanceClass.equals(Float.class) || instanceClass.equals(float.class)) {
			if (checkAdditionConditions(feature, value).equals(IMutationGenerator.VALID)) {
				float newValue = (float) ((double) value);
				return (newValue + 1f);
			}
			return IMutationGenerator.INVALID;
		}
		throw new IllegalArgumentException("non-real feature type " + feature);
	}

	public static Object deletionOp(Object roleBinding, EStructuralFeature feature, Object value, EmuModule module) {
		Object instanceClass = feature.getEType().getInstanceClass();
		if (instanceClass.equals(Float.class) || instanceClass.equals(float.class)) {
			if (checkDeletionConditions(feature, value).equals(IMutationGenerator.VALID)) {
				float newValue = (float) ((double) value);
				return (newValue - 1f);
			}
			return IMutationGenerator.INVALID;
		}
		throw new IllegalArgumentException("non-real feature type " + feature);
	}

	public static Object replacementOp(Object roleBinding, EStructuralFeature feature, Object value, EmuModule module) {
		Object instanceClass = feature.getEType().getInstanceClass();
		if (instanceClass.equals(Float.class) || instanceClass.equals(float.class)) {
			if (checkReplacementConditions(feature, value).equals(IMutationGenerator.VALID)) {
				return IMutationGenerator.NOTIMPLEMENTED;
			}
			return IMutationGenerator.INVALID;
		}
		throw new IllegalArgumentException("non-real feature type " + feature);
	}

	private static Object checkAdditionConditions(EStructuralFeature feature, Object value) {
		if (value == null)
			return IMutationGenerator.INVALID;
		return IMutationGenerator.VALID;
	}

	private static Object checkDeletionConditions(EStructuralFeature feature, Object value) {
		if (value == null)
			return IMutationGenerator.INVALID;
		return IMutationGenerator.VALID;
	}

	private static Object checkReplacementConditions(EStructuralFeature feature, Object value) {
		return IMutationGenerator.VALID;
	}

	public static Object checkAdditionConditions(EStructuralFeature feature, Object value, Object valueNew) {
		Object instanceClass = feature.getEType().getInstanceClass();
		if (instanceClass.equals(Float.class) || instanceClass.equals(float.class)) {
			if (checkAdditionConditions(feature, value).equals(IMutationGenerator.VALID)) {
				if (valueNew == null)
					throw new IllegalArgumentException("The mutated real value must be defined: " + valueNew);
				if (valueNew == value)
					throw new IllegalArgumentException("The original real value and the new value must not be equal: " + valueNew);
				return IMutationGenerator.VALID;
			}
			return IMutationGenerator.INVALID;
		}
		throw new IllegalArgumentException("non-real feature type " + feature);
	}

	public static Object checkDeletionConditions(EStructuralFeature feature, Object value, Object valueNew) {
		Object instanceClass = feature.getEType().getInstanceClass();
		if (instanceClass.equals(Float.class) || instanceClass.equals(float.class)) {
			if (checkDeletionConditions(feature, value).equals(IMutationGenerator.VALID)) {
				if (valueNew == null)
					throw new IllegalArgumentException("The mutated real value must be defined: " + valueNew);
				if (valueNew == value)
					throw new IllegalArgumentException("The original real value and the new value must not be equal: " + valueNew);
				return IMutationGenerator.VALID;
			}
			return IMutationGenerator.INVALID;
		}
		throw new IllegalArgumentException("non-real feature type " + feature);
	}

	public static Object checkReplacementConditions(EStructuralFeature feature, Object value, Object valueNew) {
		Object instanceClass = feature.getEType().getInstanceClass();
		if (instanceClass.equals(Float.class) || instanceClass.equals(float.class)) {
			if (checkReplacementConditions(feature, value).equals(IMutationGenerator.VALID)) {
				if (value == valueNew)
					return IMutationGenerator.INVALID;
				return IMutationGenerator.VALID;
			}
			return IMutationGenerator.INVALID;
		}
		throw new IllegalArgumentException("non-real feature type " + feature);
	}
}