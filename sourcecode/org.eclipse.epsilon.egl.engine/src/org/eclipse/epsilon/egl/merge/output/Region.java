/*******************************************************************************
 * Copyright (c) 2008 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Louis Rose - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.egl.merge.output;

public class Region {

	protected String contents = "";
	
	public Region(String contents) {
		if (contents==null)
			throw new NullPointerException("contents cannot be null");
		
		this.contents = contents;
	}
	
	public String getContents() {
		return contents;
	}
	
	public void setContents(String contents) {
		this.contents = contents;
	}
	
	@Override
	public String toString() {
		return contents;
	}
	
	@Override
	public boolean equals(Object o) {
		if (o == null) return false;
		if (!(o instanceof Region)) return false;
		
		final Region that = (Region)o;
		
		return contents.equals(that.contents);
	}
	
	@Override
	public int hashCode() {
		return contents.hashCode();
	}
}
