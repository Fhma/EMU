/**
 * 
 * @author Faisal Alhwikem
 * @version 1.0
 * @since March-2017
 */
 package org.eclipse.epsilon.emu.mutation;

import java.util.Collection;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.epsilon.emu.EmuModule;

@SuppressWarnings("unchecked")
public abstract class AMOMultiFeature {
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
		if (value == null)
			return IMutationGenerator.INVALID;
		if (feature.getUpperBound() != -1) {
			Collection<Object> col = (Collection<Object>) value;
			if (col.size() + 1 > feature.getUpperBound())
				return IMutationGenerator.INVALID;
		}
		return IMutationGenerator.VALID;
	}

	private static Object checkDeletionConditions(EStructuralFeature feature, Object value) {
		if (value == null)
			return IMutationGenerator.INVALID;
		Collection<Object> col = (Collection<Object>) value;
		if (col.size() - 1 < feature.getLowerBound())
			return IMutationGenerator.INVALID;
		return IMutationGenerator.VALID;
	}

	private static Object checkReplacementConditions(EStructuralFeature feature, Object value) {
		if (value == null)
			return IMutationGenerator.INVALID;
		Collection<Object> col = (Collection<Object>) value;
		if (col.isEmpty())
			return IMutationGenerator.INVALID;
		return IMutationGenerator.VALID;
	}

	public static Object checkAdditionConditions(EStructuralFeature feature, Object value, Object valueNew) {
		if (checkAdditionConditions(feature, value).equals(IMutationGenerator.VALID)) {
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

	public static Object checkDeletionConditions(EStructuralFeature feature, Object value, Object valueNew) {
		if (checkDeletionConditions(feature, value).equals(IMutationGenerator.VALID)) {
			if (valueNew == null)
				return IMutationGenerator.INVALID;
			Collection<Object> oldCol = (Collection<Object>) value;
			Collection<Object> newCol = (Collection<Object>) valueNew;
			if (newCol.contains(null))
				return IMutationGenerator.INVALID;
			if (newCol.size() < feature.getLowerBound())
				return IMutationGenerator.INVALID;
			if (oldCol.size() - 1 != newCol.size())
				return IMutationGenerator.INVALID;
			return IMutationGenerator.VALID;
		}
		return IMutationGenerator.INVALID;
	}

	public static Object checkReplacementConditions(EStructuralFeature feature, Object value, Object valueNew) {
		if (checkReplacementConditions(feature, value).equals(IMutationGenerator.VALID)) {
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
