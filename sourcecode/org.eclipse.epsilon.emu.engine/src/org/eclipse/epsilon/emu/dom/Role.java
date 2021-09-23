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

import java.util.List;
import org.eclipse.epsilon.common.module.IModule;
import org.eclipse.epsilon.common.parse.AST;
import org.eclipse.epsilon.eol.dom.ExecutableBlock;
import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;
import org.eclipse.epsilon.eol.execute.context.IEolContext;
import org.eclipse.epsilon.epl.combinations.DynamicList;
import org.eclipse.epsilon.epl.dom.Cardinality;
import org.eclipse.epsilon.epl.dom.Domain;

public class Role extends org.eclipse.epsilon.epl.dom.Role {

	public Role() {
		super();
	}

	@Override
	public void build(AST cst, IModule module) {
		super.build(cst, module);
	}

	public Cardinality getCardinality() {
		return super.getCardinality();
	}

	public boolean isActive(IEolContext context) throws EolRuntimeException {
		return super.isActive(context, false);
	}

	public boolean isActive(IEolContext context, boolean forceRecompute) throws EolRuntimeException {
		return super.isActive(context, forceRecompute);
	}

	public boolean isOptional(IEolContext context) throws EolRuntimeException {
		return super.isOptional(context);
	}

	public boolean isNegative() {
		return super.isNegative();
	}

	public List<String> getNames() {
		return super.getNames();
	}

	public Domain getDomain() {
		return super.getDomain();
	}

	public ExecutableBlock<Boolean> getGuard() {
		return super.getGuard();
	}

	public List<Object> getInstances(final IEolContext context) throws EolRuntimeException {
		return super.getInstances(context);
	}

	protected List<Object> getAll(final DynamicList<Object> instances, final IEolContext context) {
		return super.getAll(instances, context);
	}

	protected List<Object> getNegative(final DynamicList<Object> instances, final IEolContext context) {
		return super.getNegative(instances, context);
	}
}
