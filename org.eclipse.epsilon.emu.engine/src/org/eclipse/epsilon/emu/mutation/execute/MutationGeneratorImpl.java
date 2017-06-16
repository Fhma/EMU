/**
 * 
 * @author Faisal Alhwikem
 * @version 1.0
 * @since March-2017
 */

package org.eclipse.epsilon.emu.mutation.execute;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.epsilon.emu.EmuModule;
import org.eclipse.epsilon.emu.mutation.IMutationGenerator;
import org.eclipse.epsilon.emu.mutation.AMOBoolean;
import org.eclipse.epsilon.emu.mutation.AMOInteger;
import org.eclipse.epsilon.emu.mutation.AMOMultiFeature;
import org.eclipse.epsilon.emu.mutation.AMOReal;
import org.eclipse.epsilon.emu.mutation.AMOSingleRef;
import org.eclipse.epsilon.emu.mutation.AMOString;

public class MutationGeneratorImpl implements IMutationGenerator {

	@Override
	public Object mutate(Object roleBinding, EStructuralFeature feature, Object value, EmuModule module, String mutation_action) {
		if (roleBinding == null)
			throw new NullPointerException("Undefined pattern match role binding object");
		if (feature == null)
			throw new NullPointerException("Undefined feature object");
		if (module == null)
			throw new NullPointerException("Undefined EolModule object");
		if (mutation_action == null)
			throw new NullPointerException("Undefined mutation action");

		if (!feature.isMany()) {
			if (feature instanceof EAttribute) {
				if (mutation_action.equals(ADD_MUTATION_ACTION)) {
					Object instanceClass = (Object) feature.getEType().getInstanceClass();
					if (instanceClass.equals(Boolean.class) || instanceClass.equals(boolean.class))
						return AMOBoolean.additionOp(roleBinding, feature, value, module);
					if (instanceClass.equals(Integer.class) || instanceClass.equals(int.class))
						return AMOInteger.additionOp(roleBinding, feature, value, module);
					if (instanceClass.equals(Float.class) || instanceClass.equals(float.class))
						return AMOReal.additionOp(roleBinding, feature, value, module);
					if (instanceClass.equals(String.class))
						return AMOString.additionOp(roleBinding, feature, value, module);
					throw new IllegalArgumentException("Unsupported feature type");
				}
				if (mutation_action.equals(DEL_MUTATION_ACTION)) {
					Object instanceClass = (Object) feature.getEType().getInstanceClass();
					if (instanceClass.equals(Boolean.class) || instanceClass.equals(boolean.class))
						return AMOBoolean.deletionOp(roleBinding, feature, value, module);
					if (instanceClass.equals(Integer.class) || instanceClass.equals(int.class))
						return AMOInteger.deletionOp(roleBinding, feature, value, module);
					if (instanceClass.equals(Float.class) || instanceClass.equals(float.class))
						return AMOReal.deletionOp(roleBinding, feature, value, module);
					if (instanceClass.equals(String.class))
						return AMOString.deletionOp(roleBinding, feature, value, module);
					throw new IllegalArgumentException("Unsupported feature type");
				}
				if (mutation_action.equals(REPLACE_MUTATION_ACTION)) {
					Object instanceClass = (Object) feature.getEType().getInstanceClass();
					if (instanceClass.equals(Boolean.class) || instanceClass.equals(boolean.class))
						return AMOBoolean.replacementOp(roleBinding, feature, value, module);
					if (instanceClass.equals(Integer.class) || instanceClass.equals(int.class))
						return AMOInteger.replacementOp(roleBinding, feature, value, module);
					if (instanceClass.equals(Float.class) || instanceClass.equals(float.class))
						return AMOReal.replacementOp(roleBinding, feature, value, module);
					if (instanceClass.equals(String.class))
						return AMOString.replacementOp(roleBinding, feature, value, module);
					throw new IllegalArgumentException("Unsupported feature type");
				}
			}
			else if (feature instanceof EReference) {
				if (mutation_action.contentEquals(ADD_MUTATION_ACTION))
					return AMOSingleRef.additionOp(roleBinding, feature, value, module);
				if (mutation_action.equals(DEL_MUTATION_ACTION))
					return AMOSingleRef.deletionOp(roleBinding, feature, value, module);
				if (mutation_action.equals(REPLACE_MUTATION_ACTION))
					return AMOSingleRef.replacementOp(roleBinding, feature, value, module);

			}
			else
				throw new IllegalArgumentException("Unrecognizable feature object " + feature);
		}
		else {
			if (mutation_action.equals(ADD_MUTATION_ACTION))
				return AMOMultiFeature.additionOp(roleBinding, feature, value, module);
			if (mutation_action.equals(DEL_MUTATION_ACTION))
				return AMOMultiFeature.deletionOp(roleBinding, feature, value, module);
			if (mutation_action.equals(REPLACE_MUTATION_ACTION))
				return AMOMultiFeature.replacementOp(roleBinding, feature, value, module);
		}
		return null;
	}

	@Override
	public Object checkConditions(EStructuralFeature feature, Object old_value, Object new_value, String mutation_action) {
		if (feature == null)
			throw new NullPointerException("Undefined feature object");
		if (mutation_action == null)
			throw new NullPointerException("Undefined mutation action");

		if (!feature.isMany()) {
			if (feature instanceof EAttribute) {
				// single-valued attribute
				if (mutation_action.equals(ADD_MUTATION_ACTION)) {
					Object instanceClass = (Object) feature.getEType().getInstanceClass();
					if (instanceClass.equals(Boolean.class) || instanceClass.equals(boolean.class))
						return AMOBoolean.checkAdditionConditions(feature, old_value, new_value);
					if (instanceClass.equals(Integer.class) || instanceClass.equals(int.class))
						return AMOInteger.checkAdditionConditions(feature, old_value, new_value);
					if (instanceClass.equals(Float.class) || instanceClass.equals(float.class))
						return AMOReal.checkAdditionConditions(feature, old_value, new_value);
					if (instanceClass.equals(String.class))
						return AMOString.checkAdditionConditions(feature, old_value, new_value);
					throw new IllegalArgumentException("Unsupported feature type");
				}
				if (mutation_action.equals(DEL_MUTATION_ACTION)) {
					Object instanceClass = (Object) feature.getEType().getInstanceClass();
					if (instanceClass.equals(Boolean.class) || instanceClass.equals(boolean.class))
						return AMOBoolean.checkDeletionConditions(feature, old_value, new_value);
					if (instanceClass.equals(Integer.class) || instanceClass.equals(int.class))
						return AMOInteger.checkDeletionConditions(feature, old_value, new_value);
					if (instanceClass.equals(Float.class) || instanceClass.equals(float.class))
						return AMOReal.checkDeletionConditions(feature, old_value, new_value);
					if (instanceClass.equals(String.class))
						return AMOString.checkDeletionConditions(feature, old_value, new_value);
					throw new IllegalArgumentException("Unsupported feature type");
				}
				if (mutation_action.equals(REPLACE_MUTATION_ACTION)) {
					Object instanceClass = (Object) feature.getEType().getInstanceClass();
					if (instanceClass.equals(Boolean.class) || instanceClass.equals(boolean.class))
						return AMOBoolean.checkReplacementConditions(feature, old_value, new_value);
					if (instanceClass.equals(Integer.class) || instanceClass.equals(int.class))
						return AMOInteger.checkReplacementConditions(feature, old_value, new_value);
					if (instanceClass.equals(Float.class) || instanceClass.equals(float.class))
						return AMOReal.checkReplacementConditions(feature, old_value, new_value);
					if (instanceClass.equals(String.class))
						return AMOString.checkReplacementConditions(feature, old_value, new_value);
					throw new IllegalArgumentException("Unsupported feature type");
				}
			}
			else {
				// single-valued reference
				if (mutation_action.equals(ADD_MUTATION_ACTION))
					return AMOSingleRef.checkAdditionConditions(feature, old_value, new_value);
				if (mutation_action.equals(DEL_MUTATION_ACTION))
					return AMOSingleRef.checkDeletionConditions(feature, old_value, new_value);
				if (mutation_action.equals(REPLACE_MUTATION_ACTION))
					return AMOSingleRef.checkReplacementConditions(feature, old_value, new_value);
			}
		}
		else {
			if (mutation_action.equals(ADD_MUTATION_ACTION))
				return AMOMultiFeature.checkAdditionConditions(feature, old_value, new_value);
			if (mutation_action.equals(DEL_MUTATION_ACTION))
				return AMOMultiFeature.checkDeletionConditions(feature, old_value, new_value);
			if (mutation_action.equals(REPLACE_MUTATION_ACTION))
				return AMOMultiFeature.checkReplacementConditions(feature, old_value, new_value);
		}
		return true;
	}
}
