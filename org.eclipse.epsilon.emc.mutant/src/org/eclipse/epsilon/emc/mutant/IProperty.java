package org.eclipse.epsilon.emc.mutant;

public interface IProperty {

	public int getLowerBound();

	public int getUpperBound();

	public boolean isRequired();

	public boolean isSingleValued();

	public boolean isMultiValued();

	public boolean isReadOnly();

	public boolean isDerived();

	public boolean isSerializable();

	public String getName();

	public Object getType();

	public void setLowerBound(int lb);

	public void setUpperBound(int ub);

	public void setName(String name);

	public void setType(Object type);
	
	public boolean isDataType();
}
