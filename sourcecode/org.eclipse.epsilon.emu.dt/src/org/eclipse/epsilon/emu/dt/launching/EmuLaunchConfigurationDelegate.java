/*******************************************************************************
 * Copyright (c) 2018 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Dimitrios Kolovos - initial API and implementation
 *     Faisal Alhwikem
 ******************************************************************************/
package org.eclipse.epsilon.emu.dt.launching;

import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.epsilon.emu.EmuModule;
import org.eclipse.epsilon.eol.IEolModule;
import org.eclipse.epsilon.eol.dt.debug.EolDebugger;
import org.eclipse.epsilon.eol.dt.launching.EpsilonLaunchConfigurationDelegate;

public class EmuLaunchConfigurationDelegate extends EpsilonLaunchConfigurationDelegate {

	@Override
	public IEolModule createModule() {
		return new EmuModule();
	}

	@Override
	protected EolDebugger createDebugger() {
		return new EmuDebugger();
	}

	@Override
	public void aboutToExecute(ILaunchConfiguration configuration, String mode, ILaunch launch, IProgressMonitor progressMonitor, IEolModule module) throws Exception {
		super.aboutToExecute(configuration, mode, launch, progressMonitor, module);
		EmuModule emuModule = (EmuModule) module;
		String path = configuration.getAttribute(EmuLaunchConfigurationAttributes.OUTPUT_DIR_PATH, "");

		if (path.length() <= 0)
			emuModule.setMutationOutputDir(path, 1);
		else
			emuModule.setMutationOutputDir(path, 2);

		emuModule.setOutputFileExtension(configuration.getAttribute(EmuLaunchConfigurationAttributes.OUTPUT_FILE_EXTENTION, ".mutant"));
		emuModule.setRoot(ResourcesPlugin.getWorkspace().getRoot().getRawLocation().toPortableString());
	}

	@Override
	public void executed(ILaunchConfiguration configuration, String mode, ILaunch launch, IProgressMonitor progressMonitor, IEolModule module, Object result) throws Exception {
		// TODO Auto-generated method stub
		super.executed(configuration, mode, launch, progressMonitor, module, result);
		this.result = null;
	}
}
