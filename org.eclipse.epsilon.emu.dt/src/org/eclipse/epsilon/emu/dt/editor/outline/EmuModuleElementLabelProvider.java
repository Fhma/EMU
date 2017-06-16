/**
 * 
 * @author Faisal Alhwikem
 * @version 1.0
 * @since March-2017
 */
package org.eclipse.epsilon.emu.dt.editor.outline;

import org.eclipse.epsilon.emu.dt.EmuPlugin;
import org.eclipse.epsilon.epl.dom.Pattern;
import org.eclipse.epsilon.epl.dt.editor.outline.EplModuleElementLabelProvider;
import org.eclipse.swt.graphics.Image;

public class EmuModuleElementLabelProvider extends EplModuleElementLabelProvider{
	
	Image patternImage = EmuPlugin.getDefault().createImage("icons/pattern.gif");
	
	@Override
	public String getText(Object element) {
		if (element instanceof Pattern) {
			return ((Pattern) element).getName();
		}
		return super.getText(element);
	}
	
	@Override
	public Image getImage(Object element) {
	
		if (element instanceof Pattern) {
			return patternImage;
		}
		else {
			return super.getImage(element);
		}
	}
}
