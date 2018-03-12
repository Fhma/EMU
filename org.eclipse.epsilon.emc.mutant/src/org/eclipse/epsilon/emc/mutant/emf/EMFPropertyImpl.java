package org.eclipse.epsilon.emc.mutant.emf;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.epsilon.emc.mutant.IProperty;

public class EMFPropertyImpl implements IProperty {

	private EStructuralFeature sf = null;

	public EMFPropertyImpl(EStructuralFeature feature) {
		sf = feature;
	}

	@Override
	public int getLowerBound() {
		return sf.getLowerBound();
	}

	@Override
	public int getUpperBound() {
		return sf.getUpperBound();
	}

	@Override
	public boolean isRequired() {
		return sf.isRequired();
	}

	@Override
	public boolean isSingleValued() {
		return !isMultiValued();
	}

	@Override
	public boolean isMultiValued() {
		return sf.isMany();
	}

	@Override
	public boolean isReadOnly() {
		return !(sf.isChangeable());
	}

	@Override
	public boolean isDerived() {
		return sf.isDerived();
	}

	@Override
	public boolean isSerializable() {
		return sf.isTransient();
	}

	@Override
	public String getName() {
		return sf.getName();
	}

	@Override
	public Object getType() {
		return sf.getEType().getInstanceClass();
	}

	@Override
	public void setLowerBound(int lb) {
		sf.setLowerBound(lb);

	}

	@Override
	public void setUpperBound(int ub) {
		sf.setUpperBound(ub);

	}

	@Override
	public void setName(String name) {
		sf.setName(name);

	}

	@Override
	public void setType(Object type) {
		sf.setEType((EDataType) type);
	}

	@Override
	public boolean isDataType() {
		if (sf instanceof EAttribute)
			return true;
		return false;
	}

}
