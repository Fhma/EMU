package org.eclipse.epsilon.emu.parse;

import org.eclipse.epsilon.eol.models.IModel;
import org.eclipse.epsilon.eol.models.IRelativePathResolver;
import java.io.File;
import java.net.URI;
import org.eclipse.epsilon.common.util.StringProperties;
import org.eclipse.epsilon.emc.emf.EmfModel;
import org.eclipse.epsilon.emu.EmuModule;

public class EmuWorkbench {
	public static void main(String[] args) throws Exception {
		new EmuWorkbench().run();
	}

	private void run() throws Exception {

		// String sourceFile = EmuWorkbench.class.getResource("mutation_1.emu").getPath();
		String sourceFile = EmuWorkbench.class.getResource("mutation_EOL.emu").getPath();

		File output = new File("GenMutations/");

		if (output.exists() && output.isDirectory()) {
			File[] files = output.listFiles();
			for (File f : files)
				f.delete();
			output.delete();
		} else
			output.mkdirs();

		EmuModule module = new EmuModule();
		module.parse(new File(sourceFile));
		if (module.getParseProblems().size() >= 1) {
			System.out.println("Wait!! parsing problems here: " + module.getParseProblems().toString());
			return;
		}

		// String metamodel = EmuWorkbench.class.getResource("Book.ecore").getPath();
		// String baseModel = "book_example_1.xmi";
		// String baseModel = "book_example_2.xmi";

		String metamodel = EmuWorkbench.class.getResource("EOL.ecore").getPath();
		String baseModel = "EOL_model.xmi";
		String modelString = EmuWorkbench.class.getResource(baseModel).getPath();

		IModel model = createEmfModel("model1", modelString, metamodel, true, false);
		module.setMutationOutputDir(output.getPath(), EmuModule.STANDALONE_OUTPUT_TYPE);
		module.getContext().getModelRepository().addModel(model);
		module.execute();
		module.getContext().getModelRepository().dispose();
		System.out.println("Summary: valid = " + module.getValidCount() + ", invalid= " + module.getInvalidCount());
	}

	private static EmfModel createEmfModel(String name, String model, String metamodel, boolean readOnLoad, boolean storeOnDisposal) throws Exception {
		EmfModel emfModel = new EmfModel();
		StringProperties properties = new StringProperties();
		properties.put(EmfModel.PROPERTY_NAME, name);
		properties.put(EmfModel.PROPERTY_FILE_BASED_METAMODEL_URI, new URI(metamodel).toString());
		properties.put(EmfModel.PROPERTY_MODEL_URI, new URI(model).toString());
		properties.put(EmfModel.PROPERTY_READONLOAD, readOnLoad + "");
		properties.put(EmfModel.PROPERTY_STOREONDISPOSAL, storeOnDisposal + "");
		emfModel.load(properties, (IRelativePathResolver) null);
		return emfModel;
	}
}
