/**
 * 
 * @author Faisal Alhwikem
 * @version 1.0
 * @since March-2017
 */

package org.eclipse.epsilon.emu.mutation;

import org.eclipse.epsilon.emc.mutant.IProperty;
import org.eclipse.epsilon.emu.EmuModule;

public abstract class AMOSingleProperty {

	public static Object additionOp(Object roleBinding, IProperty property, Object value, EmuModule module) {
		if (checkAdditionConditions(property, value).equals(IMutationGenerator.VALID)) {
			return IMutationGenerator.NOTIMPLEMENTED;
		}
		return IMutationGenerator.INVALID;
	}

	public static Object deletionOp(Object roleBinding, IProperty property, Object value, EmuModule module) {
		if (checkDeletionConditions(property, value).equals(IMutationGenerator.VALID)) {
			return IMutationGenerator.NOTIMPLEMENTED;
		}
		return IMutationGenerator.INVALID;
	}

	public static Object replacementOp(Object roleBinding, IProperty property, Object value, EmuModule module) {
		if (checkReplacementConditions(property, value).equals(IMutationGenerator.VALID)) {
			return IMutationGenerator.NOTIMPLEMENTED;
		}
		return IMutationGenerator.INVALID;
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

	public static Object checkAdditionConditions(IProperty property, Object value, Object valueNew) {
		if (checkAdditionConditions(property, value).equals(IMutationGenerator.VALID)) {
			if (valueNew == null)
				return IMutationGenerator.INVALID;
			if (valueNew == value)
				return IMutationGenerator.INVALID;
			return IMutationGenerator.VALID;
		}
		return IMutationGenerator.INVALID;
	}

	public static Object checkDeletionConditions(IProperty property, Object value, Object valueNew) {
		if (checkDeletionConditions(property, value).equals(IMutationGenerator.VALID)) {
			if (valueNew != null)
				return IMutationGenerator.INVALID;
			return IMutationGenerator.VALID;
		}
		return IMutationGenerator.INVALID;
	}

	public static Object checkReplacementConditions(IProperty property, Object value, Object valueNew) {
		if (checkReplacementConditions(property, value).equals(IMutationGenerator.VALID)) {
			if (valueNew == null)
				return IMutationGenerator.INVALID;
			if (valueNew.equals(value))
				return IMutationGenerator.INVALID;
			return IMutationGenerator.VALID;
		}
		return IMutationGenerator.INVALID;
	}
}
