/*******************************************************************************
 * Copyright (c) 2008 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Dimitrios Kolovos - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.common.dt.editor.outline;

import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.swt.graphics.Image;


public abstract class ModuleElementLabelProvider implements ILabelProvider {
	
	public abstract Image getImage(Object element);

	public String getText(Object element) {
		//ModuleElement el = (ModuleElement) element;
		//Visitor v = new Visitor();
		//v.visit(el.getAst());
		//return "(" + v.startLine + ":" + v.startColumn + "-" + v.endLine + ":" + v.endColumn + ") " + el.toString();
		return String.valueOf(element);
	}

	public void addListener(ILabelProviderListener listener) {
		// TODO Auto-generated method stub
		
	}

	public void dispose() {
		// TODO Auto-generated method stub
		
	}

	public boolean isLabelProperty(Object element, String property) {
		// TODO Auto-generated method stub
		return false;
	}

	public void removeListener(ILabelProviderListener listener) {
		// TODO Auto-generated method stub
		
	}
	
}
