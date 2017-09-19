/**
 * 
 * @author Faisal Alhwikem
 * @version 1.0
 * @since March-2017
 */
package org.eclipse.epsilon.emu;

import java.io.File;
import java.net.URI;
import java.util.HashMap;
import java.util.List;
import org.eclipse.epsilon.common.module.ModuleElement;
import org.eclipse.epsilon.common.parse.AST;
import org.eclipse.epsilon.common.parse.problem.ParseProblem;
import org.eclipse.epsilon.emu.execute.EmuPatternMatcher;
import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;
import org.eclipse.epsilon.eol.execute.context.IEolContext;
import org.eclipse.epsilon.epl.EplModule;
import org.eclipse.epsilon.epl.dom.Domain;
import org.eclipse.epsilon.epl.dom.Pattern;
import org.eclipse.epsilon.epl.dom.Role;
import org.eclipse.epsilon.epl.parse.EplParser;
import org.eclipse.epsilon.eol.dom.ExecutableBlock;

public class EmuModule extends EplModule {

	protected HashMap<String, Integer> mutationsIndexer;

	@Override
	public ModuleElement adapt(AST cst, ModuleElement parentAst) {
		switch (cst.getType()) {
		case EplParser.PATTERN:
			return new Pattern();
		case EplParser.GUARD:
			return new ExecutableBlock<Boolean>(Boolean.class);
		case EplParser.DOMAIN:
			return new Domain();
		case EplParser.ROLE:
			return new Role();
		}
		return super.adapt(cst, parentAst);
	}

	@Override
	public Object execute() throws EolRuntimeException {
		execute(getPre(), context);
		EmuPatternMatcher patternMatcher = new EmuPatternMatcher();
		try
		{
			patternMatcher.match(this);
		} catch (Exception ex)
		{
			EolRuntimeException.propagate(ex);
		}
		execute(getPost(), context);
		return patternMatcher;
	}

	public HashMap<String, Integer> getMutationsIndexer() {
		if (mutationsIndexer == null)
			mutationsIndexer = new HashMap<String, Integer>();
		return mutationsIndexer;
	}

	@Override
	public IEolContext getContext() {
		return super.getContext();
	}

	@Override
	public boolean parse(File file) throws Exception {
		return super.parse(file);
	}

	@Override
	public boolean parse(String code, File file) throws Exception {
		return super.parse(code, file);
	}

	@Override
	public boolean parse(String code) throws Exception {
		return super.parse(code);
	}

	@Override
	public boolean parse(URI uri) throws Exception {
		return super.parse(uri);
	}

	@Override
	public List<ParseProblem> getParseProblems() {
		return super.getParseProblems();
	}

	@Override
	public boolean isRepeatWhileMatches() {
		return false;
	}
}
