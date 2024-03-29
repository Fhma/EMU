/*******************************************************************************
 * Copyright (c) 2008-2013 The University of York, Antonio Garcia-Dominguez.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Dimitrios Kolovos - initial API and implementation
 *     Antonio Garcia-Dominguez - refactor parse(...) methods
 ******************************************************************************/
package org.eclipse.epsilon.eol;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.antlr.runtime.ANTLRInputStream;
import org.antlr.runtime.CommonToken;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.Lexer;
import org.antlr.runtime.ParserRuleReturnScope;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.Token;
import org.antlr.runtime.TokenStream;
import org.eclipse.epsilon.common.module.AbstractModuleElement;
import org.eclipse.epsilon.common.module.IModule;
import org.eclipse.epsilon.common.module.ModuleElement;
import org.eclipse.epsilon.common.parse.AST;
import org.eclipse.epsilon.common.parse.EpsilonParseProblemManager;
import org.eclipse.epsilon.common.parse.EpsilonParser;
import org.eclipse.epsilon.common.parse.EpsilonTreeAdaptor;
import org.eclipse.epsilon.common.parse.Position;
import org.eclipse.epsilon.common.parse.problem.ParseProblem;
import org.eclipse.epsilon.common.util.AstUtil;
import org.eclipse.epsilon.eol.parse.EolLexer;
import org.eclipse.epsilon.eol.parse.EolParser;
import org.eclipse.epsilon.eol.util.ReflectionUtil;


public abstract class AbstractModule extends AbstractModuleElement implements IModule {
	
	protected EpsilonParser parser;
	
	protected ArrayList<ParseProblem> parseProblems = new ArrayList<ParseProblem>();
		
	public abstract String getMainRule();
	
	protected abstract Lexer createLexer(ANTLRInputStream inputStream);
	
	public abstract EpsilonParser createParser(TokenStream tokenStream);
	
	protected File sourceFile;
	protected URI sourceUri;
	
	public File getSourceFile() {
		return sourceFile;
	}
	
	public URI getSourceUri() {
		return sourceUri;
	}
	
	public List<ParseProblem> getParseProblems() {
		return parseProblems;
	}
	
	public boolean parse(String code) throws Exception {
		return parse(code, null);
	}
	
	public boolean parse(String code, File file) throws Exception {
		this.sourceFile = file;
		if (file != null) {
			this.sourceUri = file.toURI();
		}
		return parse(sourceUri, new ByteArrayInputStream(code.getBytes()));
	}

	public boolean parse(File file) throws Exception {
		return parse(file.toURI());
	}

	public boolean parse(URI uri) throws Exception {
		this.sourceUri = uri;

		final String uriScheme = uri.getScheme();
		if ("file".equals(uriScheme)) {
			this.sourceFile = new File(uri);
		}

		return parse(uri, uri.toURL().openStream());
	}

	protected boolean invokeMainRule(List<CommonToken> multilineComments) throws Exception {
		EpsilonParseProblemManager.INSTANCE.reset();
		AST cst = null;
		
		try {
			cst = (AST)((ParserRuleReturnScope) ReflectionUtil.executeMethod(parser,getMainRule(), new Object[]{})).getTree();
		}
		
		catch (RecognitionException ex){
			ParseProblem problem = new ParseProblem();
			problem.setLine(ex.line);
			problem.setColumn(ex.charPositionInLine);
			problem.setReason(ex.getMessage());
			getParseProblems().add(problem);			
			ex.printStackTrace();
		}
		catch (Throwable ex) {
			ParseProblem problem = new ParseProblem();
			Token next = parser.input.LT(1);
			problem.setLine(next.getLine());
			problem.setColumn(next.getCharPositionInLine());
			problem.setReason("mismatched input: '" + next.getText() + "'");
			getParseProblems().add(problem);
		}
		
		parseProblems.addAll(EpsilonParseProblemManager.INSTANCE.getParseProblems());
		EpsilonParseProblemManager.INSTANCE.reset();
		
		if (getParseProblems().size() == 0){
			assignAnnotations(cst);
			assignComments(cst, multilineComments);
			//createAst(cst, null);
			//assignAnnotations(ast);
			//buildModel();
			createAst(cst, null);
			return true;
		}
		else {
			return false;
		}
	}
	
	public ModuleElement createAst(AST cst, ModuleElement parentAst) {
		if (cst == null) return null;
		ModuleElement moduleElement = adapt(cst, parentAst);
		if (moduleElement != null) {
			moduleElement.setUri(cst.getUri());
			//moduleElement.setFile(cst.getFile());
			moduleElement.setModule(cst.getModule());
			//try {
			//	moduleElement.setBasename(cst.getBasename());
			//}
			//catch (Exception ex) {}
			moduleElement.setRegion(cst.getRegion());
			moduleElement.build(cst, this);
			if (parentAst != null) {
				moduleElement.setParent(parentAst);
				parentAst.getChildren().add(moduleElement);
			}
		}
		return moduleElement;
	}
	
	public abstract ModuleElement adapt(AST cst, ModuleElement parentAst);
	
	protected List<CommonToken> extractComments(CommonTokenStream stream) {
		List<CommonToken> comments = new ArrayList<CommonToken>();
		
		for (Object t : stream.getTokens()) {
			CommonToken token = (CommonToken) t;
			if (token.getType() == EolLexer.COMMENT || token.getType() == EolParser.LINE_COMMENT) {
				comments.add(token);
			}
		}
		return comments;
	}
	
	private boolean parse(URI uri, final InputStream iStream) throws Exception {
		parseProblems.clear();
		Scanner s = new java.util.Scanner(iStream);
		try {
			// Replace tabs with spaces to get consistent column numbers in ASTs
			s.useDelimiter("\\A");
		    String contents = s.hasNext() ? s.next() : "";
		    ByteArrayInputStream noTabsStream = new ByteArrayInputStream(contents.replaceAll("\t", " ").getBytes());
		    
		    final Lexer lexer = createLexer(new ANTLRInputStream(noTabsStream));
		    
			final CommonTokenStream stream = new CommonTokenStream(lexer);
			
			List<CommonToken> multilineComments = extractComments(stream);
			
			final EpsilonTreeAdaptor adaptor = new EpsilonTreeAdaptor(uri, this);

			parser = createParser(stream);
			parser.setDeepTreeAdaptor(adaptor);

			return invokeMainRule(multilineComments);
		}
		catch (Exception ex) {
			parseProblems.add(new ParseProblem("Exception during parsing: " + ex.getLocalizedMessage(), ParseProblem.ERROR));
			throw ex;
		}
		finally {
			s.close();
		}
	}
	
	protected void assignComments(AST root, List<CommonToken> comments) {
		for (CommonToken comment : comments) {
			assignComment(root, comment);
		}
	}
	
	protected void assignComment(AST root, CommonToken comment) {
		for (AST ast : root.getDescendants()) {
			Position start = ast.getRegion().getStart();
			if (!ast.isImaginary() && comment.getLine() <= start.getLine() && comment.getCharPositionInLine() <= start.getColumn()) {
				ast.getCommentTokens().add(comment);
				return;
			}
		}
	}
	
	
	protected void assignAnnotations(AST ast) {
		List<AST> children = AstUtil.getChildren(ast);
		for (AST child : children) {
			if (child.getType() == EolParser.ANNOTATIONBLOCK) {
				
				AST target = null;
				
				//HACK to support main-less EOL programs
				if (child.getNextSibling() != null) {
					if (child.getNextSibling().getType() != EolParser.BLOCK) {
						target = child.getNextSibling();
					}
					else if (child.getNextSibling().getNextSibling() != null) {
						target = child.getNextSibling().getNextSibling();
					}
				}
				
				if (target != null) {
					target.setAnnotationsAst(child);
				}
				
			} else {
				assignAnnotations(child);
			}
		}
	}
	
}
