/*********************************************************************
* Copyright (c) 2008 The University of York.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/
package org.eclipse.epsilon.flexmi.dt;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.text.rules.IWordDetector;

public class SingleCharacterWordDetector implements IWordDetector {

	private List<Character> chars = new ArrayList<Character>();
    
	public SingleCharacterWordDetector(char...chars) {
		for (char c : chars) {
			this.chars.add(c);
		}
	}
	
    public void addChar(char c) {
        chars.add(c);
    }
     
    public boolean isWordPart(char c) {
        return false;
    }
 
    public boolean isWordStart(char c) {
        return chars.contains(c);
    }

}
