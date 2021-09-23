/*******************************************************************************
 * Copyright (c) 2018 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Dimitrios Kolovos - initial API and implementation
 *     Faisal Alhwikem
 ******************************************************************************/
package org.eclipse.epsilon.emu;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.antlr.runtime.ANTLRInputStream;
import org.antlr.runtime.Lexer;
import org.antlr.runtime.TokenStream;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Path;
import org.eclipse.epsilon.common.module.IModule;
import org.eclipse.epsilon.common.module.ModuleElement;
import org.eclipse.epsilon.common.parse.AST;
import org.eclipse.epsilon.common.parse.EpsilonParser;
import org.eclipse.epsilon.common.util.AstUtil;
import org.eclipse.epsilon.common.util.StringProperties;
import org.eclipse.epsilon.emc.emf.EmfModel;
import org.eclipse.epsilon.emc.mutant.IMutant;
import org.eclipse.epsilon.emc.mutant.emf.EmfMutant;
import org.eclipse.epsilon.emu.dom.Mutation;
import org.eclipse.epsilon.emu.exceptions.EmuRuntimeException;
import org.eclipse.epsilon.emu.execute.EmuPatternMatcher;
import org.eclipse.epsilon.emu.parse.EmuLexer;
import org.eclipse.epsilon.emu.parse.EmuParser;
import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;
import org.eclipse.epsilon.eol.execute.context.IEolContext;
import org.eclipse.epsilon.eol.models.IModel;
import org.eclipse.epsilon.eol.models.IRelativePathResolver;
import org.eclipse.epsilon.epl.dom.Cardinality;
import org.eclipse.epsilon.epl.dom.Domain;
import org.eclipse.epsilon.epl.dom.Role;
import org.eclipse.epsilon.eol.dom.ExecutableBlock;
import org.eclipse.epsilon.erl.ErlModule;

public class EmuModule extends ErlModule {

	// keywords that are used in the grammar and dom
	public static final String ADDITION_KEYWORD = "byAdd";
	public static final String DELETION_KEYWORD = "byDelete";
	public static final String REPLACEMENT_KEYWORD = "byReplace";

	public static final String DEFAULT_OUTPUT_DIR_PATH = "GenMutations";
	public static final String DEFAULT_FILE_EXTENSION = ".mutant";

	public static final int DEFAULT_OUTPUT_TYPE = 1;
	public static final int CUSTOM_OUTPUT_TYPE = 2;
	public static final int STANDALONE_OUTPUT_TYPE = 3;

	protected String mutationDirPath = DEFAULT_OUTPUT_DIR_PATH;
	protected String outputFileExtension = DEFAULT_FILE_EXTENSION;
	protected String rawOutputPath = null;
	
	public static final int ADDITION_MUTATION_TYPE = 1;
	public static final int DELETION_MUTATION_TYPE = 2;
	public static final int REPLACEMENT_MUTATION_TYPE = 3;
	public static final int UNKNOWN_MUTATION_TYPE = -1;
	
	public static final int INFINITY_MUTATION = -1;

	protected LinkedHashMap<String, Integer> operatorIndexMap = new LinkedHashMap<String, Integer>();
	protected IFolder mutationDir = null;
	protected List<Mutation> mutationList = new ArrayList<Mutation>();
	protected String root = null;
	protected int mutationOutputDirType = -1;

	protected int valid_count = 0;
	protected int invalid_count = 0;

	public EmuModule() {
	}

	@Override
	public ModuleElement adapt(AST cst, ModuleElement parentAst) {
		switch (cst.getType()) {
		case EmuParser.MUTATION:
			return new Mutation();
		case EmuParser.ROLE:
			return new Role();
		case EmuParser.CARDINALITY:
			return new Cardinality();
		case EmuParser.DOMAIN:
			return new Domain();
		case EmuParser.ACTIVE:
			return new ExecutableBlock<Boolean>(Boolean.class);
		case EmuParser.OPTIONAL:
			return new ExecutableBlock<Boolean>(Boolean.class);
		case EmuParser.GUARD:
			return new ExecutableBlock<Boolean>(Boolean.class);
		case EmuParser.ACTION:
			return new ExecutableBlock<Void>(Void.class);
		}
		return super.adapt(cst, parentAst);
	}

	@Override
	public void build(AST cst, IModule module) {
		super.build(cst, module);
		for (AST mutationAst : AstUtil.getChildren(cst, EmuParser.MUTATION)) {
			mutationList.add((Mutation) module.createAst(mutationAst, this));
		}
	}

	public List<Mutation> getDeclaredMutations() {
		return mutationList;
	}

	@Override
	public Object executeImpl() throws EolRuntimeException {

		// convert entered models to IMutant type models
		convertModels2IMutantModels(context);
		prepareContext(context);
		initialiseMutationDir();
		execute(getPre(), context);
		// go through all mutations separately
		EmuPatternMatcher patternMatcher = new EmuPatternMatcher();
		try {
			for (Mutation mu : mutationList) {
				patternMatcher.match(this, mu);
			}
		} catch (Exception ex) {
			EmuRuntimeException.propagate(ex);
		}
		execute(getPost(), context);

		if (mutationDir != null) {
			try {

				mutationDir.refreshLocal(1, null);
			} catch (Exception e) {
				throw new EolRuntimeException(e.getMessage());
			}
		}
		return patternMatcher;
	}

	public boolean storeMutant(IMutant _model, String operatorName) {
		int index = getOperatorIndex(operatorName) + 1;
		String path;
		if (mutationOutputDirType == STANDALONE_OUTPUT_TYPE)
			path = mutationDirPath + operatorName + "_" + index + getOutputFileExtension();
		else
			path = mutationDirPath + File.separatorChar + operatorName + "_" + index + getOutputFileExtension();
		_model.store(path);
		return true;
	}

	public void initialiseMutationDir() {
		if (mutationOutputDirType == DEFAULT_OUTPUT_TYPE) {
			if (root != null) {
				String str = context.getModule().getSourceUri().getPath();
				str = str.substring(0, str.lastIndexOf(File.separatorChar));
				str = str.replaceAll(root, "");
				File targetFolder = new File(str, DEFAULT_OUTPUT_DIR_PATH);
				targetFolder.mkdirs();
				str = str + File.separatorChar + DEFAULT_OUTPUT_DIR_PATH;
				mutationDir = ResourcesPlugin.getWorkspace().getRoot().getFolder(new Path(str));
				mutationDirPath = mutationDir.getFullPath().toOSString();
			}
		} else if (mutationOutputDirType == CUSTOM_OUTPUT_TYPE) {
			mutationDir = ResourcesPlugin.getWorkspace().getRoot().getFolder(new Path(rawOutputPath));
			mutationDirPath = mutationDir.getFullPath().toOSString();
		} else if (mutationOutputDirType == STANDALONE_OUTPUT_TYPE) {
			File targetFolder = new File(DEFAULT_OUTPUT_DIR_PATH);
			targetFolder.mkdirs();
			mutationDirPath = targetFolder.toURI().toString();
		}
	}

	public void setMutationOutputDir(String path, int type) {
		if (type == DEFAULT_OUTPUT_TYPE) {
			mutationOutputDirType = DEFAULT_OUTPUT_TYPE;
			rawOutputPath = DEFAULT_OUTPUT_DIR_PATH;
		} else if (type == CUSTOM_OUTPUT_TYPE) {
			mutationOutputDirType = CUSTOM_OUTPUT_TYPE;
			rawOutputPath = path;
		} else if (type == STANDALONE_OUTPUT_TYPE) {
			mutationOutputDirType = STANDALONE_OUTPUT_TYPE;
			rawOutputPath = DEFAULT_OUTPUT_DIR_PATH;
		}
	}

	public int getOperatorIndex(String operatorName) {
		if (operatorIndexMap.get(operatorName) != null) {
			return operatorIndexMap.get(operatorName);
		} else {
			operatorIndexMap.put(operatorName, 0);
			return 0;
		}
	}

	public void incrementOperatorIndex(String operatorName) {
		updateOperatorIndex(operatorName, operatorIndexMap.get(operatorName) + 1);
	}

	public void updateOperatorIndex(String operatorName, int newIndex) {
		operatorIndexMap.put(operatorName, newIndex);
	}

	protected void convertModels2IMutantModels(IEolContext context) throws EmuRuntimeException {
		List<IMutant> models = new ArrayList<IMutant>();
		try {
			for (IModel m : context.getModelRepository().getModels()) {
				if (m instanceof EmfModel) {
					models.add(convertEmf2EmfMutant((EmfModel) m));
				}
				// TODO: more supported model types csv, graphml etc...
			}
		} catch (Exception e) {
			throw new EmuRuntimeException("Unable to convert models to IMutant models: " + e.getMessage(), this);
		}
		context.getModelRepository().getModels().clear();
		context.getModelRepository().getModels().addAll(models);
	}

	private IMutant convertEmf2EmfMutant(EmfModel emfModel) throws Exception {
		IMutant model = new EmfMutant();
		StringProperties properties = new StringProperties();
		properties.put(EmfModel.PROPERTY_NAME, emfModel.getName());
		// properties.put(EmfModel.PROPERTY_METAMODEL_URI, emfModel.getMetamodelUris().get);
		properties.put(EmfModel.PROPERTY_MODEL_URI, emfModel.getModelFile());
		properties.put(EmfModel.PROPERTY_READONLOAD, true + "");
		properties.put(EmfModel.PROPERTY_STOREONDISPOSAL, false + "");
		model.load(properties, (IRelativePathResolver) null);
		return model;
	}

	@Override
	protected Lexer createLexer(ANTLRInputStream inputStream) {
		return new EmuLexer(inputStream);
	}

	@Override
	public EpsilonParser createParser(TokenStream tokenStream) {
		return new EmuParser(tokenStream);
	}

	public int incrementValid() {
		return valid_count++;
	}

	public int incrementInvalid() {
		return invalid_count++;
	}

	public int getValidCount() {
		return valid_count;
	}

	public int getInvalidCount() {
		return invalid_count;
	}

	@Override
	protected int getPreBlockTokenType() {
		return EmuParser.PRE;
	}

	@Override
	protected int getPostBlockTokenType() {
		return EmuParser.POST;
	}

	@Override
	public String getMainRule() {
		return "emuModule";
	}

	public String getOutputFileExtension() {
		return outputFileExtension;
	}

	public void setOutputFileExtension(String outputFileExtension) {
		if (outputFileExtension.startsWith("."))
			this.outputFileExtension = outputFileExtension;
		else
			this.outputFileExtension = "." + outputFileExtension;
	}

	public String getMutationDirPath() {
		return mutationDirPath;
	}

	public void setRoot(String root) {
		this.root = root;
	}
}
