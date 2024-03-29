/*******************************************************************************
 * Copyright (c) 2012 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Dimitrios Kolovos - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.eol.execute.operations.contributors.compatibility;

import org.eclipse.epsilon.eol.execute.operations.contributors.OperationContributor;

public class StringCompatibilityOperationContributor extends OperationContributor {

	@Override
	public boolean contributesTo(Object target) {
		return target instanceof String;
	}

	/**
	 * In the previous version of EOL, EolString.replace
	 * mapped to String.replaceAll.
	 * @param regex
	 * @param replacement
	 * @return
	 */
	public String replace(String regex, String replacement) {
		return ((String) target).replaceAll(regex, replacement);
	}

	/**
	 * Java charAt returns a 'char': keep it for backwards compatibility (e.g.
	 * Ecore2Thrift needs it), but also provide a 'characterAt' EOL version since
	 * EOL does not have that primitive type.
	 */
	public String characterAt(int index) {
		return ((String) target).charAt(index) + "";
	}
}
