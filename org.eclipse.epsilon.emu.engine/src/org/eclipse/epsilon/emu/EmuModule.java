/**
 * 
 * @author Faisal Alhwikem
 * @version 1.0
 * @since March-2017
 */
package org.eclipse.epsilon.emu;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.List;
import org.eclipse.epsilon.common.module.ModuleElement;
import org.eclipse.epsilon.common.parse.AST;
import org.eclipse.epsilon.common.parse.problem.ParseProblem;
import org.eclipse.epsilon.emu.execute.EmuPatternMatcher;
import org.eclipse.epsilon.emu.mutation.matrix.OMatrix;
import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;
import org.eclipse.epsilon.eol.execute.context.IEolContext;
import org.eclipse.epsilon.epl.EplModule;
import org.eclipse.epsilon.epl.dom.Domain;
import org.eclipse.epsilon.epl.dom.Pattern;
import org.eclipse.epsilon.epl.dom.Role;
import org.eclipse.epsilon.epl.parse.EplParser;
import org.eclipse.epsilon.eol.dom.ExecutableBlock;

public class EmuModule extends EplModule {

	protected File mutants_dir;
	protected OMatrix operatorsMatrix;

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

	public OMatrix getOperatorsMatrix() {
		if (operatorsMatrix == null)
			operatorsMatrix = new OMatrix(getMutantsDir().getAbsolutePath() + "/" + getMutantsDir().getName());
		return operatorsMatrix;
	}

	public File getMutantsDir() {
		if (mutants_dir == null) {
			File source_file = this.getSourceFile();
			if (source_file != null) {
				String path = source_file.getAbsolutePath();
				path = path.substring(0, path.length() - 4);
				mutants_dir = new File(path + "_mutants/");
			} else
				mutants_dir = new File("mutations/");
			mutants_dir.mkdir();
		}
		return mutants_dir;
	}

	public void saveOperatorsMatrix() throws IOException {
		getOperatorsMatrix().saveMatrix();
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
