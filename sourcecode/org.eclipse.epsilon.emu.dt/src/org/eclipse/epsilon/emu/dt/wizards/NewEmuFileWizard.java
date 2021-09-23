/*******************************************************************************
 * Copyright (c) 2008 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Dimitrios Kolovos - initial API and implementation
 *     Faisal Alhwikem
 ******************************************************************************/
package org.eclipse.epsilon.emu.dt.wizards;

import org.eclipse.epsilon.common.dt.wizards.AbstractNewFileWizard2;

public class NewEmuFileWizard extends AbstractNewFileWizard2 {

	@Override
	public String getTitle() {
		return "New ENU Program";
	}

	@Override
	public String getExtension() {
		return "emu";
	}

	@Override
	public String getDescription() {
		return "This wizard creates a new EMU program file with *.emu extension";
	}
}
