/*********************************************************************
* Copyright (c) 2008 The University of York.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/
package org.eclipse.epsilon.eol.dom;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.epsilon.common.module.IModule;
import org.eclipse.epsilon.common.module.ModuleElement;
import org.eclipse.epsilon.common.parse.AST;
import org.eclipse.epsilon.eol.compile.context.EolCompilationContext;
import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;
import org.eclipse.epsilon.eol.execute.Return;
import org.eclipse.epsilon.eol.execute.context.IEolContext;

public class StatementBlock extends AbstractExecutableModuleElement {
	
	protected List<Statement> statements = new ArrayList<Statement>();
	
	public StatementBlock(Statement...statements) {
		for (Statement statement : statements) {
			this.statements.add(statement);
		}
	}
	
	@Override
	public void build(AST cst, IModule module) {
		super.build(cst, module);
		for (AST ast : cst.getChildren()) {
			ModuleElement moduleElement = module.createAst(ast, this);
			if (moduleElement instanceof Statement) {
				statements.add((Statement) moduleElement);
			}
			else {
				ExpressionStatement expressionStatement = new ExpressionStatement((Expression) moduleElement);
				expressionStatement.setParent(this);
				this.getChildren().add(expressionStatement);
				statements.add(expressionStatement);
			}
		}
	}
	
	public List<Statement> getStatements() {
		return statements;
	}
	
	@Override
	public Object execute(IEolContext context) throws EolRuntimeException {
		for (Statement statement : statements) {
			context.getFrameStack().setCurrentStatement(statement);
			Object result = context.getExecutorFactory().execute(statement, context);
			if (result instanceof Return) {
				return result;
			}
		}
		return null;
	}
	
	@Override
	public void compile(EolCompilationContext context) {
		for (Statement statement : statements) {
			statement.compile(context);
		}
	}

	@Override
	public String toString(){
		return "{...}";
	}
}
