/*******************************************************************************
 * Copyright (c) 2008 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Dimitrios Kolovos - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.erl.dom;

import java.util.Collections;
import java.util.List;

import org.eclipse.epsilon.common.module.IModule;
import org.eclipse.epsilon.common.parse.AST;
import org.eclipse.epsilon.common.util.AstUtil;
import org.eclipse.epsilon.eol.dom.AnnotatableModuleElement;
import org.eclipse.epsilon.eol.dom.NameExpression;

public class NamedRule extends AnnotatableModuleElement {
	
	protected NameExpression nameExpression;
	
	public NamedRule() {}
	
	@Override
	public void build(AST cst, IModule module) {
		super.build(cst, module);
		nameExpression = (NameExpression) module.createAst(getNameAst(cst), this);
	}
	
	protected AST getNameAst(AST cst) {
		return cst.getFirstChild();
	}
	
	public String getName() { 
		if (nameExpression != null) {
			return nameExpression.getName();
		}
		else {
			return "";
		}
	}
	
	public NameExpression getNameExpression() {
		return nameExpression;
	}
	
	public void setNameExpression(NameExpression nameExpression) {
		this.nameExpression = nameExpression;
	}
	
	public List<?> getModuleElements() {
		return Collections.emptyList();
	}
	
	@Override
	public String toString(){
		return getName();
	}
}
