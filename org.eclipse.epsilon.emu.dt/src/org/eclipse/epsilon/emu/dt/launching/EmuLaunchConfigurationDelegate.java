/**
 * 
 * @author Faisal Alhwikem
 * @version 1.0
 * @since March-2017
 */

package org.eclipse.epsilon.emu.dt.launching;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.epsilon.eol.IEolExecutableModule;
import org.eclipse.epsilon.emu.EmuModule;
import org.eclipse.epsilon.eol.dt.launching.EpsilonLaunchConfigurationDelegate;

public class EmuLaunchConfigurationDelegate extends EpsilonLaunchConfigurationDelegate {
	
	@Override
	public IEolExecutableModule createModule() {
		return new EmuModule();
	}
	
	@Override
	public void aboutToExecute(ILaunchConfiguration configuration, String mode,
			ILaunch launch, IProgressMonitor progressMonitor,
			IEolExecutableModule module) throws Exception {
		super.aboutToExecute(configuration, mode, launch, progressMonitor, module);
	}
}

