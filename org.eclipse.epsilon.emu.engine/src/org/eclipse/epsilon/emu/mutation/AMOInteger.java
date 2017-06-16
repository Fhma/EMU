/**
 * 
 * @author Faisal Alhwikem
 * @version 1.0
 * @since March-2017
 */

package org.eclipse.epsilon.emu.mutation;

import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.epsilon.emu.EmuModule;

public abstract class AMOInteger {

	public static Object additionOp(Object roleBinding, EStructuralFeature feature, Object value, EmuModule module) {
		Object instanceClass = feature.getEType().getInstanceClass();
		if (instanceClass.equals(Integer.class) || instanceClass.equals(int.class)) {
			if (checkAdditionConditions(feature, value).equals(IMutationGenerator.VALID)) {
				int newValue = ((int) value) + 1;
				return (newValue);
			}
			return IMutationGenerator.INVALID;
		}
		throw new IllegalArgumentException("non-integer feature type: " + feature);
	}

	public static Object deletionOp(Object roleBinding, EStructuralFeature feature, Object value, EmuModule module) {
		Object instanceClass = feature.getEType().getInstanceClass();
		if (instanceClass.equals(Integer.class) || instanceClass.equals(int.class)) {
			if (checkDeletionConditions(feature, value).equals(IMutationGenerator.VALID)) {
				int newValue = ((int) value) - 1;
				return (newValue);
			}
			return IMutationGenerator.INVALID;
		}
		throw new IllegalArgumentException("non-integer feature type: " + feature);
	}

	public static Object replacementOp(Object roleBinding, EStructuralFeature feature, Object value, EmuModule module) {
		Object instanceClass = feature.getEType().getInstanceClass();
		if (instanceClass.equals(Integer.class) || instanceClass.equals(int.class)) {
			if (checkReplacementConditions(feature, value).equals(IMutationGenerator.VALID)) {
				return IMutationGenerator.NOTIMPLEMENTED;
			}
			return IMutationGenerator.INVALID;
		}
		throw new IllegalArgumentException("non-integer feature type: " + feature);
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

	public static Object checkAdditionConditions(EStructuralFeature feature, Object value, Object newValue) {
		Object instanceClass = feature.getEType().getInstanceClass();
		if (instanceClass.equals(Integer.class) || instanceClass.equals(int.class)) {
			if (checkAdditionConditions(feature, value).equals(IMutationGenerator.VALID)) {
				if (newValue == null)
					throw new IllegalArgumentException("The mutated integer value must be defined: " + newValue);
				if (newValue == value)
					throw new IllegalArgumentException("The original integer value and the new value must not be equal: " + newValue);
				return IMutationGenerator.VALID;
			}
			return IMutationGenerator.INVALID;
		}
		throw new IllegalArgumentException("non-integer feature type: " + feature);
	}

	public static Object checkDeletionConditions(EStructuralFeature feature, Object value, Object newValue) {
		Object instanceClass = feature.getEType().getInstanceClass();
		if (instanceClass.equals(Integer.class) || instanceClass.equals(int.class)) {
			if (checkDeletionConditions(feature, value).equals(IMutationGenerator.VALID)) {
				if (newValue == null)
					throw new IllegalArgumentException("The mutated integer value must be defined: " + newValue);
				if (value == newValue)
					throw new IllegalArgumentException("The original integer value and the new value must not be equal: " + newValue);
				return IMutationGenerator.VALID;
			}
			return IMutationGenerator.INVALID;
		}
		throw new IllegalArgumentException("non-integer feature type: " + feature);
	}

	public static Object checkReplacementConditions(EStructuralFeature feature, Object value, Object valueNew) {
		Object instanceClass = feature.getEType().getInstanceClass();
		if (instanceClass.equals(Integer.class) || instanceClass.equals(int.class)) {
			if (checkReplacementConditions(feature, value).equals(IMutationGenerator.VALID)) {
				if (value == valueNew)
					return IMutationGenerator.INVALID;
				return IMutationGenerator.VALID;
			}
			return IMutationGenerator.INVALID;
		}
		throw new IllegalArgumentException("non-integer feature type: " + feature);
	}
}
