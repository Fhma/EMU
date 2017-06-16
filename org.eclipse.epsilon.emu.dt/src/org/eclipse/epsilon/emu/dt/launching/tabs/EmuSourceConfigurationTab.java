/**
 * 
 * @author Faisal Alhwikem
 * @version 1.0
 * @since March-2017
 */

package org.eclipse.epsilon.emu.dt.launching.tabs;

import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.epsilon.common.dt.EpsilonPlugin;
import org.eclipse.epsilon.common.dt.launching.AbstractSourceConfigurationTab;
import org.eclipse.epsilon.emu.dt.EmuPlugin;
import org.eclipse.swt.widgets.Composite;

public class EmuSourceConfigurationTab extends AbstractSourceConfigurationTab{
	
	//protected Text mutantsPath;
	
	@Override
	public EpsilonPlugin getPlugin() {
		return EmuPlugin.getDefault();
	}

	@Override
	public String getImagePath() {
		return "icons/epl.gif";
	}

	@Override
	public String getFileExtension() {
		return "emu";
	}

	@Override
	public String getSelectionTitle() {
		return "Select an EMU Program";
	}

	@Override
	public String getSelectionSubtitle() {
		return "EMU Programs in Workspace";
	}

	public String getLaunchConfigurationKey() {
		return "SOURCE.EMU";
	}
	
	@Override
	public void createControl(Composite parent) {
		super.createControl(parent);
	}
	
	@Override
	public void performApply(ILaunchConfigurationWorkingCopy configuration) {
		super.performApply(configuration);
		// any extra
	}
	
	@Override
	public void initializeFrom(ILaunchConfiguration configuration) {
		super.initializeFrom(configuration);
	}
}
