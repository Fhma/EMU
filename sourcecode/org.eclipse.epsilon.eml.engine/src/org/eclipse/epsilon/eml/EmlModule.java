/*******************************************************************************
 * Copyright (c) 2008 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Dimitrios Kolovos - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.eml;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.antlr.runtime.ANTLRInputStream;
import org.antlr.runtime.Lexer;
import org.antlr.runtime.TokenStream;
import org.eclipse.epsilon.common.module.IModule;
import org.eclipse.epsilon.common.module.ModuleElement;
import org.eclipse.epsilon.common.parse.AST;
import org.eclipse.epsilon.common.parse.EpsilonParser;
import org.eclipse.epsilon.common.util.AstUtil;
import org.eclipse.epsilon.eml.dom.MergeRule;
import org.eclipse.epsilon.eml.dom.EquivalentAssignmentStatement;
import org.eclipse.epsilon.eml.execute.context.EmlContext;
import org.eclipse.epsilon.eml.parse.EmlLexer;
import org.eclipse.epsilon.eml.parse.EmlParser;
import org.eclipse.epsilon.eol.dom.Import;
import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;
import org.eclipse.epsilon.eol.execute.context.Variable;
import org.eclipse.epsilon.erl.dom.NamedRuleList;
import org.eclipse.epsilon.etl.EtlModule;


public class EmlModule extends EtlModule {
	
	protected EmlContext context = new EmlContext();
	protected NamedRuleList<MergeRule> declaredMergeRules = new NamedRuleList<MergeRule>();
	protected NamedRuleList<MergeRule> mergeRules = null;
	
	@Override
	protected Lexer createLexer(ANTLRInputStream inputStream) {
		return new EmlLexer(inputStream);
	}

	@Override
	public EpsilonParser createParser(TokenStream tokenStream) {
		return new EmlParser(tokenStream);
	}

	@Override
	public String getMainRule() {
		return "emlModule";
	}

	@Override
	public HashMap<String, Class<?>> getImportConfiguration() {
		HashMap<String, Class<?>> importConfiguration = super.getImportConfiguration();
		importConfiguration.put("eml", EmlModule.class);
		return importConfiguration;
	}

	@Override
	public void build(AST cst, IModule module) {
		super.build(cst, module);
		// Parse the merge rules
		for (AST mergeRuleAst : AstUtil.getChildren(cst, EmlParser.MERGE)) {
			declaredMergeRules.add((MergeRule) module.createAst(mergeRuleAst, this));
		}
	}
	
	@Override
	public Object executeImpl() throws EolRuntimeException{
		
		context.getFrameStack().put(Variable.createReadOnlyVariable("matchTrace", context.getMatchTrace()));
		context.getFrameStack().put(Variable.createReadOnlyVariable("mergeTrace", context.getMergeTrace()));
		context.getFrameStack().put(Variable.createReadOnlyVariable("transTrace", context.getTransformationTrace()));
		context.getFrameStack().put(Variable.createReadOnlyVariable("context", context));
		context.getFrameStack().put(Variable.createReadOnlyVariable("thisModule", this));
		
		execute(getPre(), context);
		context.getMergingStrategy().mergeModels(context);
		execute(getPost(), context);
		
		return null;
	}
	
	@Override
	public EmlContext getContext() {
		return context;
	}
	
	@Override
	public ModuleElement adapt(AST cst, ModuleElement parentAst) {
		if (cst.getType() == EmlParser.MERGE) {
			return new MergeRule();
		}
		else if (cst.getType() == EmlParser.SPECIAL_ASSIGNMENT) {
			return new EquivalentAssignmentStatement();
		}
		return super.adapt(cst, parentAst);
	}
	
	public List<MergeRule> getDeclaredMergeRules(){
		return declaredMergeRules;
	}
	
	public List<MergeRule> getMergeRules() {
		if (mergeRules == null) {
			mergeRules = new NamedRuleList<MergeRule>();
			for (Import import_ : imports) {
				if (import_.isLoaded() && (import_.getModule() instanceof EmlModule)) {
					EmlModule module = (EmlModule) import_.getModule();
					mergeRules.addAll(module.getMergeRules());
				}
			}
			mergeRules.addAll(declaredMergeRules);
		}
		return mergeRules;
	}

	@Override
	protected int getPostBlockTokenType() {
		return EmlParser.POST;
	}

	@Override
	protected int getPreBlockTokenType() {
		return EmlParser.PRE;
	}	
}
