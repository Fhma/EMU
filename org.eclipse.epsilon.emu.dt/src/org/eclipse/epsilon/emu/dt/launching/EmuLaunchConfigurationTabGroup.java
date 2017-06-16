/**
 * 
 * @author Faisal Alhwikem
 * @version 1.0
 * @since March-2017
 */
package org.eclipse.epsilon.emu.dt.launching;

import org.eclipse.debug.ui.ILaunchConfigurationTab;
import org.eclipse.epsilon.common.dt.launching.tabs.EpsilonLaunchConfigurationTabGroup;
import org.eclipse.epsilon.emu.dt.launching.tabs.EmuSourceConfigurationTab;

public class EmuLaunchConfigurationTabGroup extends EpsilonLaunchConfigurationTabGroup{

	@Override
	public ILaunchConfigurationTab getSourceConfigurationTab() {
		return new EmuSourceConfigurationTab();
	}

}
