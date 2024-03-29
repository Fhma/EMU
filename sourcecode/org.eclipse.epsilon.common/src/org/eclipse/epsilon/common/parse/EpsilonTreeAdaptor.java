/*******************************************************************************
 * Copyright (c) 2008 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Dimitrios Kolovos - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.common.parse;

import java.io.File;
import java.net.URI;

import org.antlr.runtime.Token;
import org.antlr.runtime.tree.CommonTreeAdaptor;
import org.eclipse.epsilon.common.module.IModule;


public class EpsilonTreeAdaptor extends CommonTreeAdaptor {
	
	protected URI uri = null;
	protected IModule module = null;
	
	public EpsilonTreeAdaptor(File file) {
		if (file != null) {
			this.uri = file.toURI();
		}
	}
	
	public EpsilonTreeAdaptor(File file, IModule module) {
		if (file != null) {
			this.uri = file.toURI();
		}
		this.module = module;
	}

	public EpsilonTreeAdaptor(URI uri) {
		this.uri = uri;
	}
	
	public EpsilonTreeAdaptor(URI uri, IModule module) {
		this.uri = uri;
		this.module = module;
	}
	
	
    public AST create(Token token) {
        return new AST(token, uri, module);
    }
    
}
