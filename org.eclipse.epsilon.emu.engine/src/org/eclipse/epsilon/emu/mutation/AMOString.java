/**
 * 
 * @author Faisal Alhwikem
 * @version 1.0
 * @since March-2017
 */

package org.eclipse.epsilon.emu.mutation;

import java.util.Random;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.epsilon.emu.EmuModule;

public abstract class AMOString {

	public static Object additionOp(Object roleBinding, EStructuralFeature feature, Object value, EmuModule module) {
		Object instanceClass = feature.getEType().getInstanceClass();
		if (instanceClass.equals(String.class)) {
			if (checkAdditionConditions(feature, value).equals(IMutationGenerator.VALID)) {
				String s = "";
				Random rand = new Random();
				rand.setSeed(System.currentTimeMillis());
				int length;
				do {
					length = (1 + rand.nextInt()) % (10);
				} while (length <= 0);

				int min = 32; // space
				int max = 122; // z
				int c;
				while (length >= 1) {
					// obtain a random number of characters from ASCII table
					c = (min + rand.nextInt()) % (max - min + 1);
					if ((c >= 48 && c <= 57) || (c >= 65 && c <= 90) || (c >= 97 && c <= 122) || (c >= 45 && c <= 46) || c == 32 || c == 95) {
						s = s + (char) c;
						length--;
					}
				}
				return s;
			}
			return IMutationGenerator.INVALID;
		}
		throw new IllegalArgumentException("non-string feature type: " + feature);
	}

	public static Object deletionOp(Object roleBinding, EStructuralFeature feature, Object value, EmuModule module) {
		Object instanceClass = feature.getEType().getInstanceClass();
		if (instanceClass.equals(String.class)) {
			if (checkDeletionConditions(feature, value).equals(IMutationGenerator.VALID)) {
				return null;
			}
			return IMutationGenerator.INVALID;
		}
		throw new IllegalArgumentException("non-string feature type: " + feature);
	}

	public static Object replacementOp(Object roleBinding, EStructuralFeature feature, Object value, EmuModule module) {
		Object instanceClass = feature.getEType().getInstanceClass();
		if (instanceClass.equals(String.class)) {
			if (checkReplacementConditions(feature, value).equals(IMutationGenerator.VALID))
				return IMutationGenerator.NOTIMPLEMENTED;
			return IMutationGenerator.INVALID;
		}
		throw new IllegalArgumentException("non-string feature type: " + feature);
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
		Object instanceClass = feature.getEType().getInstanceClass();
		if (instanceClass.equals(String.class)) {
			if (checkAdditionConditions(feature, value).equals(IMutationGenerator.VALID)) {
				if (valueNew == null)
					throw new IllegalArgumentException("The mutated string value must be defined: " + valueNew);
				if (feature.getLowerBound() == 1) {
					String s = (String) valueNew;
					if (s.length() <= 0)
						throw new IllegalArgumentException("The new value must be a valid string: " + s);
				}
				if (valueNew.equals(value))
					throw new IllegalArgumentException("The original string value and the new value must not be equal: " + valueNew);
				return IMutationGenerator.VALID;
			}
			return IMutationGenerator.INVALID;
		}
		throw new IllegalArgumentException("non-string feature type: " + feature);
	}

	public static Object checkDeletionConditions(EStructuralFeature feature, Object value, Object valueNew) {
		Object instanceClass = feature.getEType().getInstanceClass();
		if (instanceClass.equals(String.class)) {
			if (checkDeletionConditions(feature, value).equals(IMutationGenerator.VALID)) {
				if (valueNew != null)
					return IMutationGenerator.INVALID;
				return IMutationGenerator.VALID;
			}
			return IMutationGenerator.INVALID;
		}
		throw new IllegalArgumentException("non-string feature type: " + feature);
	}

	public static Object checkReplacementConditions(EStructuralFeature feature, Object value, Object valueNew) {
		Object instanceClass = feature.getEType().getInstanceClass();
		if (instanceClass.equals(String.class)) {
			if (checkReplacementConditions(feature, value).equals(IMutationGenerator.VALID)) {
				if (valueNew == null)
					return IMutationGenerator.INVALID;
				if (valueNew.equals(value))
					return IMutationGenerator.INVALID;
				String s = (String) valueNew;
				if (feature.getLowerBound() == 1 && s.length() <= 0)
					return IMutationGenerator.INVALID;
				return IMutationGenerator.VALID;
			}
			return IMutationGenerator.INVALID;
		}
		throw new IllegalArgumentException("non-string feature type: " + feature);
	}
}