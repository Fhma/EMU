/*******************************************************************************
 * Copyright (c) 2014 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Louis Rose - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.eol.execute.introspection.recording;

public class PropertyAccess implements IPropertyAccess {
	
	protected final Object modelElement;
	protected final String propertyName;
	
	public PropertyAccess(Object modelElement, String propertyName) {
		this.modelElement = modelElement;
		this.propertyName = propertyName;
	}

	@Override
	public Object getModelElement() {
		return this.modelElement;
	}

	@Override
	public String getPropertyName() {
		return this.propertyName;
	}

	@Override
	public boolean equals(Object object) {
		if (!(object instanceof PropertyAccess))
			return false;
		
		final PropertyAccess other = (PropertyAccess)object;
		
		return modelElement.equals(other.modelElement) &&
		       propertyName.equals(other.propertyName);
	}
	
	@Override
	public int hashCode() {
		return modelElement.hashCode() + propertyName.hashCode();
	}
	
	@Override
	public String toString() {
		return "PropertyAccess of '" + propertyName + "' on model element: '" + modelElement + "'";
	}
}
