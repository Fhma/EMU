/*********************************************************************
* Copyright (c) 2008 The University of York.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/
package org.eclipse.epsilon.emu.dt.editor.outline;

import java.util.List;

import org.eclipse.epsilon.common.module.ModuleElement;
import org.eclipse.epsilon.emu.EmuModule;
import org.eclipse.epsilon.emu.dom.Mutation;
import org.eclipse.epsilon.erl.dt.editor.outline.ErlModuleContentProvider;

public class EmuModuleContentProvider extends ErlModuleContentProvider {

	@Override
	public List<ModuleElement> getVisibleChildren(ModuleElement moduleElement) {
		List<ModuleElement> visible = super.getVisibleChildren(moduleElement);

		if (moduleElement.getClass() == EmuModule.class) {
			EmuModule module = (EmuModule) moduleElement;
			visible.addAll(module.getImports());
			visible.addAll(module.getDeclaredModelDeclarations());
			visible.addAll(module.getDeclaredPre());
			visible.addAll(module.getDeclaredMutations());
			visible.addAll(module.getDeclaredPost());
			visible.addAll(module.getDeclaredOperations());
		}

		return visible;
	}

	@Override
	public ModuleElement getFocusedModuleElement(ModuleElement moduleElement) {
		if (moduleElement instanceof Mutation) {
			return ((Mutation) moduleElement).getNameExpression();
		}
		return super.getFocusedModuleElement(moduleElement);
	}

}
