/*******************************************************************************
 * Copyright (c) 2008 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Dimitrios Kolovos - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.eol.execute.operations.simple.assertions;

import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;


public class AssertErrorOperation extends AssertOperation {

	@Override
	public boolean conditionSatisfied(Object condition) {
		return condition instanceof EolRuntimeException;
	}

	@Override
	public boolean getTolerateExceptionInParameter(int parameterIndex) {
		return parameterIndex == 0;
	}

	@Override
	public boolean isOverridable() {
		return false;
	}
	
}
