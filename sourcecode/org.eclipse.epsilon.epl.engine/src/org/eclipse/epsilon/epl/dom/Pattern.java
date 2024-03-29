/*******************************************************************************
 * Copyright (c) 2012 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Dimitrios Kolovos - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.epl.dom;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.epsilon.common.module.IModule;
import org.eclipse.epsilon.common.parse.AST;
import org.eclipse.epsilon.common.util.AstUtil;
import org.eclipse.epsilon.eol.dom.Annotation;
import org.eclipse.epsilon.eol.dom.ExecutableBlock;
import org.eclipse.epsilon.eol.dom.SimpleAnnotation;
import org.eclipse.epsilon.epl.parse.EplParser;
import org.eclipse.epsilon.erl.dom.NamedRule;

public class Pattern extends NamedRule {
	
	protected List<Role> roles = new ArrayList<Role>();
	protected ExecutableBlock<Void> do_ = null;
	protected ExecutableBlock<Boolean> match = null;
	protected ExecutableBlock<Void> noMatch = null;
	protected ExecutableBlock<Void> onMatch = null;
	protected int level = 0;
	
	public Pattern() {}
	
	@SuppressWarnings("unchecked")
	public void build(AST cst, IModule module) {
		super.build(cst, module);
		Annotation levelAnnotation = getAnnotation("level");
		if (levelAnnotation != null && levelAnnotation instanceof SimpleAnnotation) {
			SimpleAnnotation simpleLevelAnnotation = (SimpleAnnotation) levelAnnotation;
			level = Integer.parseInt(simpleLevelAnnotation.getValue());
		}
		
		match = (ExecutableBlock<Boolean>) module.createAst(AstUtil.getChild(cst, EplParser.MATCH), this);
		onMatch = (ExecutableBlock<Void>) module.createAst(AstUtil.getChild(cst, EplParser.ONMATCH), this);
		noMatch = (ExecutableBlock<Void>) module.createAst(AstUtil.getChild(cst, EplParser.NOMATCH), this);
		do_ = (ExecutableBlock<Void>) module.createAst(AstUtil.getChild(cst, EplParser.DO), this);
		
		for (AST roleAst : AstUtil.getChildren(cst, EplParser.ROLE)) {
			roles.add((Role) module.createAst(roleAst, this));
		}
	}
	
	public List<Role> getRoles() {
		return roles;
	}
	
	public ExecutableBlock<Void> getOnMatch() {
		return onMatch;
	}
	
	public void setOnMatch(ExecutableBlock<Void> onMatch) {
		this.onMatch = onMatch;
	}
	
	public int getLevel() {
		return level;
	}
	
	public void setLevel(int level) {
		this.level = level;
	}

	public ExecutableBlock<Void> getDo() {
		return do_;
	}

	public void setDo(ExecutableBlock<Void> do_) {
		this.do_ = do_;
	}

	public ExecutableBlock<Boolean> getMatch() {
		return match;
	}

	public void setMatch(ExecutableBlock<Boolean> match) {
		this.match = match;
	}

	public ExecutableBlock<Void> getNoMatch() {
		return noMatch;
	}

	public void setNoMatch(ExecutableBlock<Void> noMatch) {
		this.noMatch = noMatch;
	}
	
}
