/*******************************************************************************
 * Copyright (c) 2008 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Dimitrios Kolovos - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.emu.dt.editor.outline;

import org.eclipse.epsilon.emu.dom.Mutation;
import org.eclipse.epsilon.emu.dt.EmuPlugin;
import org.eclipse.epsilon.erl.dt.editor.outline.ErlModuleElementLabelProvider;
import org.eclipse.swt.graphics.Image;

public class EmuModuleElementLabelProvider extends ErlModuleElementLabelProvider {

	// Image patternImage = EmuPlugin.getDefault().createImage("icons/pattern.gif");
	Image patternImage = EmuPlugin.getDefault().createImage("icons/pattern.gif");

	@Override
	public String getText(Object element) {
		if (element instanceof Mutation) {
			return ((Mutation) element).getName();
		}
		return super.getText(element);
	}

	@Override
	public Image getImage(Object element) {

		if (element instanceof Mutation) {
			return patternImage;
		} else {
			return super.getImage(element);
		}
	}

}
