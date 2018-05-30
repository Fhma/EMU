package org.eclipse.epsilon.emu.engine.test.unit.validation;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collection;
import java.util.List;
import org.eclipse.epsilon.common.util.StringProperties;
import org.eclipse.epsilon.emc.emf.EmfModel;
import org.eclipse.epsilon.emc.mutant.emf.EmfMutant;
import org.eclipse.epsilon.emu.EmuModule;
import org.eclipse.epsilon.emc.mutant.IMutant;
import org.eclipse.epsilon.eol.exceptions.models.EolModelLoadingException;
import org.eclipse.epsilon.eol.models.IModel;
import org.eclipse.epsilon.eol.models.IRelativePathResolver;

import org.junit.Before;
import org.junit.Test;

/**
 * @author Faisal Alhwikem
 * @since 2017
 */
public class ImplementationTest {
	private EmuModule module;

	@Before
	public void setUp() throws Exception {
		module = new EmuModule();
		String m_path = ImplementationTest.class.getResource("resources/04model.xmi").getPath();
		String mm_path = ImplementationTest.class.getResource("resources/01Metamodel.ecore").getPath();
		IModel model = createEmfModel("M", m_path, mm_path, true, false);
		module.getContext().getModelRepository().addModel(model);
	}

	@Test
	public void implementation_null_action() throws Exception {
		module.parse(getFile("implementation_null_action"));
		try {
			module.execute();
		} catch (Exception e) {
			assert (e instanceof Exception);
		}
	}

	@Test
	public void implementation_null_feature() throws Exception {
		module.parse(getFile("implementation_null_feature"));
		try {
			module.execute();
		} catch (Exception e) {
			assert (e instanceof Exception);
		}
	}

	@Test
	public void implementation_null_role() throws Exception {
		module.parse(getFile("implementation_null_role"));
		try {
			module.execute();
		} catch (Exception e) {
			assert (e instanceof Exception);
		}
	}

	@Test
	public void primitive_types_add() throws Exception {
		module.parse(getFile("primitive_types_add"));
		try {
			module.execute();
		} catch (Exception e) {
			assert (e instanceof Exception);
		}
	}

	@Test
	public void primitive_types_delete() throws Exception {
		module.parse(getFile("primitive_types_delete"));
		try {
			module.execute();
		} catch (Exception e) {
			assert (e instanceof Exception);
		}
	}

	private IMutant createEmfModel(String name, String model, String metamodel, boolean readOnLoad, boolean storeOnDisposal) throws EolModelLoadingException, URISyntaxException {
		IMutant emfModel = new EmfMutant();
		StringProperties properties = new StringProperties();
		properties.put(EmfModel.PROPERTY_NAME, name);
		properties.put(EmfModel.PROPERTY_FILE_BASED_METAMODEL_URI, new URI(metamodel).toString());
		properties.put(EmfModel.PROPERTY_MODEL_URI, new URI(model).toString());
		properties.put(EmfModel.PROPERTY_READONLOAD, readOnLoad + "");
		properties.put(EmfModel.PROPERTY_STOREONDISPOSAL, storeOnDisposal + "");
		emfModel.load(properties, (IRelativePathResolver) null);
		return emfModel;
	}

	private int getMatrixSize(Collection<List<String>> col) {
		int files = 0;
		for (List<String> list : col) {
			files += list.size();
		}
		return files;
	}

	private File getFile(String s) {
		return new File(ImplementationTest.class.getResource("resources/" + s + ".emu").getPath());
	}
}
