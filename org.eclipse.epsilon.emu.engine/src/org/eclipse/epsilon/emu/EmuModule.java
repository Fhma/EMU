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
import java.util.Map;
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

	protected Map<String, List<String>> mutationMatrix;

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
		try {
			patternMatcher.match(this);
		} catch (Exception ex) {
			EolRuntimeException.propagate(ex);
		}
		execute(getPost(), context);
		return patternMatcher;
	}

	public Map<String, List<String>> getMutationMatrix() {
		if (mutationMatrix == null)
			mutationMatrix = new HashMap<String, List<String>>();
		return mutationMatrix;
	}

	@Override
	public IEolContext getContext() {
		return super.getContext();
	}

	@Override
	public boolean parse(File file) throws Exception {
		// TODO Auto-generated method stub
		return super.parse(file);
	}

	@Override
	public boolean parse(String code, File file) throws Exception {
		// TODO Auto-generated method stub
		return super.parse(code, file);
	}

	@Override
	public boolean parse(String code) throws Exception {
		// TODO Auto-generated method stub
		return super.parse(code);
	}

	@Override
	public boolean parse(URI uri) throws Exception {
		// TODO Auto-generated method stub
		return super.parse(uri);
	}

	@Override
	public List<ParseProblem> getParseProblems() {
		return super.getParseProblems();
	}
}
