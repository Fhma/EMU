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
package org.eclipse.epsilon.emu.dt.editor;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.epsilon.common.dt.editor.outline.ModuleContentProvider;
import org.eclipse.epsilon.common.dt.editor.outline.ModuleElementLabelProvider;
import org.eclipse.epsilon.common.module.IModule;
import org.eclipse.epsilon.emu.EmuModule;
import org.eclipse.epsilon.emu.dt.editor.outline.EmuModuleContentProvider;
import org.eclipse.epsilon.emu.dt.editor.outline.EmuModuleElementLabelProvider;
import org.eclipse.epsilon.eol.dt.editor.EolEditor;

public class EmuEditor extends EolEditor {

	public EmuEditor() {
		this.addTemplateContributor(new EmuEditorStaticTemplateContributor());
	}

	@Override
	public List<String> getKeywords() {

		List<String> keywords = new ArrayList<String>();

		keywords.add("pre");
		keywords.add("post");
		keywords.add("mutate");
		keywords.add("from");
		keywords.add("no");
		keywords.add("optional");
		keywords.add("active");
		keywords.add("guard");
		keywords.add("byAdd");
		keywords.add("byDelete");
		keywords.add("byReplace");
		keywords.addAll(super.getKeywords());

		return keywords;
	}

	@Override
	public ModuleElementLabelProvider createModuleElementLabelProvider() {
		return new EmuModuleElementLabelProvider();
	}

	@Override
	protected ModuleContentProvider createModuleContentProvider() {
		return new EmuModuleContentProvider();
	}

	@Override
	public IModule createModule() {
		return new EmuModule();
	}

}
