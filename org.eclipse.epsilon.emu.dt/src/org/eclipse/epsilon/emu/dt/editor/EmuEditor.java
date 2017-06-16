/**
 * 
 * @author Faisal Alhwikem
 * @version 1.0
 * @since March-2017
 */
package org.eclipse.epsilon.emu.dt.editor;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.epsilon.common.dt.editor.outline.ModuleContentProvider;
import org.eclipse.epsilon.common.dt.editor.outline.ModuleElementLabelProvider;
import org.eclipse.epsilon.common.module.IModule;
import org.eclipse.epsilon.emu.EmuModule;
import org.eclipse.epsilon.emu.dt.editor.outline.EmuModuleContentProvider;
import org.eclipse.epsilon.emu.dt.editor.outline.EmuModuleElementLabelProvider;
import org.eclipse.epsilon.epl.dt.editor.EplEditor;
import org.eclipse.epsilon.epl.dt.editor.EplEditorStaticTemplateContributor;

public class EmuEditor extends EplEditor{
	
	public EmuEditor() {
		this.addTemplateContributor(new EplEditorStaticTemplateContributor());
	}
	
	@Override
	public List<String> getKeywords() {
		
		List<String> keywords = new ArrayList<String>();
		
		keywords.add("pre");
		keywords.add("post");
		keywords.add("pattern");
		keywords.add("guard");
		keywords.add("do");
		keywords.add("from");

		keywords.addAll(super.getKeywords());
		
		return keywords;
	}
	
	@Override
	public IModule createModule(){
		return new EmuModule();
	}
	
	@Override
	public ModuleElementLabelProvider createModuleElementLabelProvider() {
		return new EmuModuleElementLabelProvider();
	}
	
	@Override
	protected ModuleContentProvider createModuleContentProvider() {
		return new EmuModuleContentProvider();
	}
}
