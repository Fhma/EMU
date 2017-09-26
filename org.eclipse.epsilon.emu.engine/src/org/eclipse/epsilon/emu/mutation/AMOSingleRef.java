/**
 * 
 * @author Faisal Alhwikem
 * @version 1.0
 * @since March-2017
 */

package org.eclipse.epsilon.emu.mutation;

import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.epsilon.emu.EmuModule;

public abstract class AMOSingleRef {

	public static Object additionOp(Object roleBinding, EStructuralFeature feature, Object value, EmuModule module) {
		if (checkAdditionConditions(feature, value).equals(IMutationGenerator.VALID)) {
			return IMutationGenerator.NOTIMPLEMENTED;
		}
		return IMutationGenerator.INVALID;
	}

	public static Object deletionOp(Object roleBinding, EStructuralFeature feature, Object value, EmuModule module) {
		if (checkDeletionConditions(feature, value).equals(IMutationGenerator.VALID)) {
			return IMutationGenerator.NOTIMPLEMENTED;
		}
		return IMutationGenerator.INVALID;
	}

	public static Object replacementOp(Object roleBinding, EStructuralFeature feature, Object value, EmuModule module) {
		if (checkReplacementConditions(feature, value).equals(IMutationGenerator.VALID)) {
			return IMutationGenerator.NOTIMPLEMENTED;
		}
		return IMutationGenerator.INVALID;
	}

	private static Object checkAdditionConditions(EStructuralFeature feature, Object value) {
		if (value != null)
			return IMutationGenerator.INVALID;
		return IMutationGenerator.VALID;
	}

	private static Object checkDeletionConditions(EStructuralFeature feature, Object value) {
		if (feature.getLowerBound() == 1)
			return IMutationGenerator.INVALID;
		if (value == null)
			return IMutationGenerator.INVALID;
		return IMutationGenerator.VALID;
	}

	private static Object checkReplacementConditions(EStructuralFeature feature, Object value) {
		if (value == null)
			return IMutationGenerator.INVALID;
		return IMutationGenerator.VALID;
	}

	public static Object checkAdditionConditions(EStructuralFeature feature, Object value, Object valueNew) {
		if (checkAdditionConditions(feature, value).equals(IMutationGenerator.VALID)) {
			if (valueNew == null)
				return IMutationGenerator.INVALID;
			if (valueNew == value)
				return IMutationGenerator.INVALID;
			return IMutationGenerator.VALID;
		}
		return IMutationGenerator.INVALID;
	}

	public static Object checkDeletionConditions(EStructuralFeature feature, Object value, Object valueNew) {
		if (checkDeletionConditions(feature, value).equals(IMutationGenerator.VALID)) {
			if (valueNew != null)
				return IMutationGenerator.INVALID;
			return IMutationGenerator.VALID;
		}
		return IMutationGenerator.INVALID;
	}

	public static Object checkReplacementConditions(EStructuralFeature feature, Object value, Object valueNew) {
		if (checkReplacementConditions(feature, value).equals(IMutationGenerator.VALID)) {
			if (valueNew == null)
				return IMutationGenerator.INVALID;
			if (valueNew.equals(value))
				return IMutationGenerator.INVALID;
			return IMutationGenerator.VALID;
		}
		return IMutationGenerator.INVALID;
	}
}
