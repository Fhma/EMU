/**
 * 
 * @author Faisal Alhwikem
 * @version 1.0
 * @since March-2017
 */
package org.eclipse.epsilon.emu.mutation;

import java.util.Collection;
import org.eclipse.epsilon.emc.mutant.IProperty;
import org.eclipse.epsilon.emu.EmuModule;

@SuppressWarnings("unchecked")
public abstract class AMOMultiProperty {
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
		if (value == null)
			return IMutationGenerator.INVALID;
		if (property.getUpperBound() != -1) {
			Collection<Object> col = (Collection<Object>) value;
			if (col.size() + 1 > property.getUpperBound())
				return IMutationGenerator.INVALID;
		}
		return IMutationGenerator.VALID;
	}

	private static Object checkDeletionConditions(IProperty property, Object value) {
		if (value == null)
			return IMutationGenerator.INVALID;
		Collection<Object> col = (Collection<Object>) value;
		if (col.size() - 1 < property.getLowerBound())
			return IMutationGenerator.INVALID;
		return IMutationGenerator.VALID;
	}

	private static Object checkReplacementConditions(IProperty property, Object value) {
		if (value == null)
			return IMutationGenerator.INVALID;
		Collection<Object> col = (Collection<Object>) value;
		if (col.isEmpty())
			return IMutationGenerator.INVALID;
		return IMutationGenerator.VALID;
	}

	public static Object checkAdditionConditions(IProperty property, Object value, Object valueNew) {
		if (checkAdditionConditions(property, value).equals(IMutationGenerator.VALID)) {
			if (valueNew == null)
				return IMutationGenerator.INVALID;
			Collection<Object> oldCol = (Collection<Object>) value;
			Collection<Object> newCol = (Collection<Object>) valueNew;
			if (oldCol.size() + 1 != newCol.size())
				return IMutationGenerator.INVALID;
			return IMutationGenerator.VALID;
		}
		return IMutationGenerator.INVALID;
	}

	public static Object checkDeletionConditions(IProperty property, Object value, Object valueNew) {
		if (checkDeletionConditions(property, value).equals(IMutationGenerator.VALID)) {
			if (valueNew == null)
				return IMutationGenerator.INVALID;
			Collection<Object> oldCol = (Collection<Object>) value;
			Collection<Object> newCol = (Collection<Object>) valueNew;
			if (newCol.contains(null))
				return IMutationGenerator.INVALID;
			if (newCol.size() < property.getLowerBound())
				return IMutationGenerator.INVALID;
			if (oldCol.size() - 1 != newCol.size())
				return IMutationGenerator.INVALID;
			return IMutationGenerator.VALID;
		}
		return IMutationGenerator.INVALID;
	}

	public static Object checkReplacementConditions(IProperty property, Object value, Object valueNew) {
		if (checkReplacementConditions(property, value).equals(IMutationGenerator.VALID)) {
			if (valueNew == null)
				return IMutationGenerator.INVALID;
			Collection<Object> newCol = (Collection<Object>) valueNew;
			if (newCol.contains(null))
				return IMutationGenerator.INVALID;
			Collection<Object> oldSet = (Collection<Object>) value;
			int oldSize = oldSet.size();
			if (oldSize == newCol.size()) {
				oldSet.retainAll(newCol);
				if (oldSet.size() + 1 != oldSize)
					return IMutationGenerator.INVALID;
			} else
				return IMutationGenerator.INVALID;
			return IMutationGenerator.VALID;
		}
		return IMutationGenerator.INVALID;
	}
}
