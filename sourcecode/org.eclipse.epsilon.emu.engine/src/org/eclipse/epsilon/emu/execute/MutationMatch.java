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
package org.eclipse.epsilon.emu.execute;

import java.util.HashMap;

import org.eclipse.epsilon.emu.dom.Mutation;

public class MutationMatch {
	protected Mutation mutation;
	protected HashMap<String, Object> roleBindings = new HashMap<String, Object>();

	public MutationMatch(Mutation mutation) {
		this.mutation = mutation;
	}

	public HashMap<String, Object> getRoleBindings() {
		return roleBindings;
	}

	public Object getRoleBinding(String name) {
		return getRoleBindings().get(name);
	}

	public Mutation getMutation() {
		return mutation;
	}
}
