/*******************************************************************************
 * Copyright (c) 2012 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Dimitrios Kolovos - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.epl.combinations;

import java.util.List;

public interface CombinationGeneratorListener<T> {
	
	public void generated(List<T> next);
	
	public void reset();
	
}
