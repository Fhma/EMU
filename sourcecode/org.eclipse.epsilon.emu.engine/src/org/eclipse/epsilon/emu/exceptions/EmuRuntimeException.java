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
package org.eclipse.epsilon.emu.exceptions;

import org.eclipse.epsilon.common.module.ModuleElement;
import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;

public class EmuRuntimeException extends EolRuntimeException {

	private static final long serialVersionUID = 1L;

	public EmuRuntimeException(String reason, ModuleElement ast) {
		super(reason, ast);
	}
}
