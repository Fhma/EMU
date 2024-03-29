/*******************************************************************************
 * Copyright (c) 2009 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Louis Rose - initial API and implementation
 ******************************************************************************
 *
 * $Id$
 */
package org.eclipse.epsilon.flock;

import org.antlr.runtime.ANTLRInputStream;
import org.antlr.runtime.Lexer;
import org.antlr.runtime.TokenStream;
import org.eclipse.epsilon.common.module.IModule;
import org.eclipse.epsilon.common.module.ModuleElement;
import org.eclipse.epsilon.common.parse.AST;
import org.eclipse.epsilon.common.parse.EpsilonParser;
import org.eclipse.epsilon.eol.dom.ExecutableBlock;
import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;
import org.eclipse.epsilon.eol.execute.context.IEolContext;
import org.eclipse.epsilon.eol.models.IModel;
import org.eclipse.epsilon.erl.ErlModule;
import org.eclipse.epsilon.flock.model.domain.MigrationStrategy;
import org.eclipse.epsilon.flock.model.domain.rules.MigrateRule;
import org.eclipse.epsilon.flock.model.domain.typemappings.Deletion;
import org.eclipse.epsilon.flock.model.domain.typemappings.PackageDeletion;
import org.eclipse.epsilon.flock.model.domain.typemappings.PackageRetyping;
import org.eclipse.epsilon.flock.model.domain.typemappings.Retyping;
import org.eclipse.epsilon.flock.model.domain.typemappings.TypeMappingConstruct;
import org.eclipse.epsilon.flock.parse.FlockLexer;
import org.eclipse.epsilon.flock.parse.FlockParser;

public class FlockModule extends ErlModule implements IFlockModule {
	
	private MigrationStrategy strategy;
	protected IFlockContext context = new FlockContext();
	
	@Override
	protected Lexer createLexer(ANTLRInputStream inputStream) {
		return new FlockLexer(inputStream);
	}

	@Override
	public EpsilonParser createParser(TokenStream tokenStream) {
		return new FlockParser(tokenStream);
	}
	
	@Override
	public String getMainRule() {
		return "flockModule";
	}
	
	@Override
	public ModuleElement adapt(AST cst, ModuleElement parentAst) {
		switch(cst.getType()) {
			case FlockParser.GUARD:
				return new ExecutableBlock<Boolean>(Boolean.class);
			
			case FlockParser.DELETE:
				return new Deletion();
			
			case FlockParser.RETYPE:
				return new Retyping();
			
			case FlockParser.MIGRATE:
				return new MigrateRule();
				
			case FlockParser.RETYPEPACKAGE:
				return new PackageRetyping();
			
			case FlockParser.DELETEPACKAGE:
				return new PackageDeletion();
	
			case FlockParser.BLOCK:
				if (cst.getParent() != null && cst.getParent().getType() == FlockParser.MIGRATE)
					return new ExecutableBlock<Void>(Void.class);
		}
		
		return super.adapt(cst, parentAst);
	}
	
	@Override
	public void build(AST cst, IModule module) {
		super.build(cst, module);
		
		strategy = new MigrationStrategy();
		
		for (AST childAst : cst.getChildren()) {
			
			ModuleElement moduleElement = module.createAst(childAst, module);
			
			if (moduleElement instanceof MigrateRule) {
				strategy.addRule((MigrateRule) moduleElement);
			}
			else if (moduleElement instanceof Retyping || moduleElement instanceof PackageRetyping || moduleElement instanceof Deletion || moduleElement instanceof PackageDeletion) {
				strategy.addTypeMappingConstruct((TypeMappingConstruct) moduleElement);
			}
			
		}
	}

	public FlockResult execute(IModel original, IModel migrated) throws EolRuntimeException {
		context.setOriginalModel(original);
		context.setMigratedModel(migrated);
			
		return (FlockResult) execute();
	}

	public FlockResult executeImpl() throws EolRuntimeException {
		FlockResult result = null;
		
		execute(getPre(), context);
		result = context.execute(strategy);
		execute(getPost(), context);
		
		return result;
	}
	
	public MigrationStrategy getStrategy() {
		return strategy;
	}
	
	public IFlockContext getContext() {
		return context;
	}
	
	@Override
	protected int getPreBlockTokenType() {
		return FlockParser.PRE;
	}

	@Override
	protected int getPostBlockTokenType() {
		return FlockParser.POST;
	}

	@Override
	public void setContext(IEolContext context) {
		if (context instanceof IFlockContext) {
			this.context = (IFlockContext) context;
		}
	}
}
