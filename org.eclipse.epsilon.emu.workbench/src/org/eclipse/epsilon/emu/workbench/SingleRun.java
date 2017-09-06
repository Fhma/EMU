/**
 * 
 * @author Faisal Alhwikem
 * @version 1.0
 * @since March, 2017
 */
package org.eclipse.epsilon.emu.workbench;

import org.eclipse.epsilon.eol.exceptions.models.EolModelLoadingException;

import org.eclipse.epsilon.eol.models.IModel;
import org.eclipse.epsilon.eol.models.IRelativePathResolver;
import org.eclipse.epsilon.emu.emf.EmfModelEMU;
import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;

import org.eclipse.epsilon.common.util.StringProperties;
import org.eclipse.epsilon.emc.emf.EmfModel;
import org.eclipse.epsilon.emu.EmuModule;

public class SingleRun {
	public static void main(String[] args) {
		new SingleRun().run();
	}

	public void run() {

		String sourceFile = "EMU_script/input.emu";
		String modelString = SingleRun.class.getResource("resources/petrinet_example_1.xmi").getPath();
		String metamodel = SingleRun.class.getResource("resources/PetriNet.ecore").getPath();

		
		EmuModule module = new EmuModule();

		try {
			module.parse(new File(sourceFile));
			if (module.getParseProblems().size() >= 1) {
				System.out.println("Parsing Problems: " + module.getParseProblems().toString());
				return;
			}
			IModel model = createEmfModel("Model", modelString, metamodel, true, false);
			module.getContext().getModelRepository().addModel(model);
			module.execute();
			module.getContext().getModelRepository().dispose();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static EmfModel createEmfModel(String name, String model, String metamodel, boolean readOnLoad, boolean storeOnDisposal) throws EolModelLoadingException, URISyntaxException {
		EmfModelEMU emfModel = new EmfModelEMU();
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
