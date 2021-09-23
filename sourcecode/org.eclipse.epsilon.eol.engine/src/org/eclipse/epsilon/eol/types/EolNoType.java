/*******************************************************************************
 * Copyright (c) 2008 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Dimitrios Kolovos - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.eol.types;

import java.util.List;

import org.eclipse.epsilon.eol.exceptions.EolIllegalOperationParametersException;
import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;

public class EolNoType extends EolType {
	
	public static EolNoType Instance  = new EolNoType();
	public static EolNoTypeInstance NoInstance = new EolNoTypeInstance();
	
	@Override
	public Object createInstance() throws EolRuntimeException {
		return null;
	}

	@Override
	public Object createInstance(List<Object> parameters)
			throws EolRuntimeException {
		throw new EolIllegalOperationParametersException("createInstance");
	}
	
	@Override
	public String getName() {
		return "_NOTYPE";
	}

	@Override
	public boolean isKind(Object o) {
		return o == EolNoType.NoInstance;
	}

	@Override
	public boolean isType(Object o) {
		return o == EolNoType.NoInstance;
	}
	
	public static class EolNoTypeInstance{}
	
}
