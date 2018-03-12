package org.eclipse.epsilon.emu.emf.dt;

import org.eclipse.epsilon.emc.emf.dt.EmfModelConfigurationDialog;

public class EmuEmfModelConfigurationDialog extends EmfModelConfigurationDialog {
	public EmuEmfModelConfigurationDialog() {
		super();
	}
	
	@Override
	protected String getModelName() {
		return "EMF Mutant Model";
	}
	
	@Override
	protected String getModelType() {
		return "EMF Mutant";
	}
}
