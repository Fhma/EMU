/**
 * 
 * @author Faisal Alhwikem
 * @version 1.0
 * @since March-2017
 */
package org.eclipse.epsilon.emu.dt.wizards;

import org.eclipse.epsilon.epl.dt.wizards.NewEplFileWizard;

public class NewEmuFileWizard extends NewEplFileWizard {

	@Override
	public String getTitle() {
		return "New EMU Program";
	}

	@Override
	public String getExtension() {
		return "emu";
	}

	@Override
	public String getDescription() {
		return "This wizard creates a new emu program file with *.emu extension";
	}
}
