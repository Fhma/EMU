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

import org.eclipse.epsilon.common.module.IModule;
import org.eclipse.epsilon.common.parse.AST;

public class Cardinality extends org.eclipse.epsilon.epl.dom.Cardinality {

	public Cardinality() {
		super();
	}

	public Cardinality(int lowerBound, int upperBound) {
		super(lowerBound, upperBound);
	}

	@Override
	public void build(AST cst, IModule module) {
		super.build(cst, module);
	}

	@Override
	protected int getBound(String text) {
		return super.getBound(text);
	}

	@Override
	public int getUpperBound() {
		return super.getUpperBound();
	}

	@Override
	public int getLowerBound() {
		return super.getLowerBound();
	}

	@Override
	public boolean isUnbounded() {
		return super.isUnbounded();
	}

	@Override
	public boolean isMany() {
		return super.isMany();
	}

	@Override
	public boolean isOne() {
		return super.isOne();
	}

	@Override
	public boolean isInBounds(int n) {
		return super.isInBounds(n);
	}
}
