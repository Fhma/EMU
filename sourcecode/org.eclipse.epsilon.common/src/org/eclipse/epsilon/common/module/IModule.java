/*******************************************************************************
 * Copyright (c) 2008-2012 The University of York, Antonio Garc��a-Dom��nguez.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Dimitrios Kolovos - initial API and implementation
 *     Antonio Garc��a-Dom��nguez - add getSourceURI
 ******************************************************************************/
package org.eclipse.epsilon.common.module;

import java.io.File;
import java.net.URI;
import java.util.List;

import org.eclipse.epsilon.common.parse.AST;
import org.eclipse.epsilon.common.parse.problem.ParseProblem;

public interface IModule extends ModuleElement {
	
	boolean parse(File file) throws Exception;
	
	boolean parse(URI uri) throws Exception;
	
	boolean parse(String code) throws Exception;
	
	boolean parse(String code, File file) throws Exception;
	
	List<ModuleMarker> compile();
	
	URI getSourceUri();

	List<ParseProblem> getParseProblems();
	
	ModuleElement createAst(AST cst, ModuleElement parentAst);
}
