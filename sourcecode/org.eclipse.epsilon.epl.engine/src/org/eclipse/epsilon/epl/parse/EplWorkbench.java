/*******************************************************************************
 * Copyright (c) 2008 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Dimitrios Kolovos - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.epl.parse;

import java.io.File;
import java.io.FileOutputStream;
import java.net.URI;
import java.util.Collections;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.epsilon.common.util.StringProperties;
import org.eclipse.epsilon.emc.emf.EmfModel;
import org.eclipse.epsilon.eol.IEolModule;
import org.eclipse.epsilon.eol.models.IRelativePathResolver;
import org.eclipse.epsilon.epl.EplModule;

public class EplWorkbench {

	public static void main(String[] args) throws Exception {
		new EplWorkbench().work();
	}

	public void work() throws Exception {

		String sourceFile = EplWorkbench.class.getResource("pattern_1.epl").getPath();
		EcorePackage.eINSTANCE.eClass();

		IEolModule module = new EplModule();

		module.parse(new File(sourceFile));
		if (module.getParseProblems().size() >= 1) {
			System.out.println("Wait!! parsing problems here: " + module.getParseProblems().toString());
			return;
		}
		//String baseModel = "book_example_1.xmi";
		String baseModel = "book_example_2.xmi";

		String modelString = EplWorkbench.class.getResource(baseModel).getPath();
		String metamodel = EplWorkbench.class.getResource("Book.ecore").getPath();

		EmfModel model = createEmfModel("Model1", modelString, metamodel, true, false);
		module.getContext().getModelRepository().addModel(model);
		module.execute();

		model.getResource().save(new FileOutputStream("updated:" + baseModel), Collections.EMPTY_MAP);
		module.getContext().dispose();
	}

	private static EmfModel createEmfModel(String name, String m, String mm, boolean read, boolean write) throws Exception {
		EmfModel emfModel = new EmfModel();
		StringProperties properties = new StringProperties();
		properties.put(EmfModel.PROPERTY_NAME, name);
		properties.put(EmfModel.PROPERTY_FILE_BASED_METAMODEL_URI, new URI(mm).toString());
		properties.put(EmfModel.PROPERTY_MODEL_URI, new URI(m).toString());
		properties.put(EmfModel.PROPERTY_READONLOAD, read + "");
		properties.put(EmfModel.PROPERTY_STOREONDISPOSAL, write + "");
		emfModel.load(properties, (IRelativePathResolver) null);
		return emfModel;
	}
}
