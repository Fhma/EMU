/*******************************************************************************
 * Copyright (c) 2008 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Dimitrios Kolovos - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.erl.strategy;

import java.util.Collection;
import java.util.List;

import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;
import org.eclipse.epsilon.eol.execute.context.IEolContext;

public interface IEquivalentProvider {
	
	public Object getEquivalent(Collection<?> collection, IEolContext context, List<String> rules) throws EolRuntimeException;
	
	public Object getEquivalent(Object source, IEolContext context, List<String> rules) throws EolRuntimeException;
	
	public Collection<?> getEquivalents(Collection<?> source, IEolContext context, List<String> rules) throws EolRuntimeException;
	
	public Collection<?> getEquivalents(Object source, IEolContext context, List<String> rules) throws EolRuntimeException;
	
}
