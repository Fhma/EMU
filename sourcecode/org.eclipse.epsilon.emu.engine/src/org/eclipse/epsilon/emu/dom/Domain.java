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
package org.eclipse.epsilon.emu.dom;

import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;
import org.eclipse.epsilon.eol.execute.context.IEolContext;
import org.eclipse.epsilon.eol.types.EolType;
import org.eclipse.epsilon.epl.combinations.DynamicList;
import org.eclipse.epsilon.epl.dom.Role;

public class Domain extends org.eclipse.epsilon.epl.dom.Domain {

	public Domain() {
		super();
	}

	public void setRole(Role role) {
		super.setRole(role);
	}

	public DynamicList<Object> getValues(final IEolContext context, final EolType type) throws EolRuntimeException {
		return super.getValues(context, type);
	}

	public boolean isDynamic() {
		return super.isDynamic();
	}
}
