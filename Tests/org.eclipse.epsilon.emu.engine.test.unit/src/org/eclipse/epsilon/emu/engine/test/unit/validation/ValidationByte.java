package org.eclipse.epsilon.emu.engine.test.unit.validation;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.epsilon.common.util.StringProperties;
import org.eclipse.epsilon.emc.emf.EmfModel;
import org.eclipse.epsilon.emc.mutant.emf.EmfMutant;
import org.eclipse.epsilon.emu.EmuModule;
import org.eclipse.epsilon.emc.mutant.IMutant;
import org.eclipse.epsilon.eol.exceptions.models.EolModelLoadingException;
import org.eclipse.epsilon.eol.models.IModel;
import org.eclipse.epsilon.eol.models.IRelativePathResolver;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Faisal Alhwikem
 * @since 2017
 */
public class ValidationByte {
	private EmuModule module;

	@Before
	public void setUp() throws Exception {
		module = new EmuModule();
		String m_path = ValidationByte.class.getResource("resources/04model.xmi").getPath();
		String mm_path = ValidationByte.class.getResource("resources/01Metamodel.ecore").getPath();
		IModel model = createEmfModel("M", m_path, mm_path, true, false);
		module.getContext().getModelRepository().addModel(model);
	}

	@Test
	public void primitive_types_byte_valid() throws Exception {
		module.parse(getFile("primitive_types_byte_valid"));
		module.execute();
		assertEquals(1, getMatrixSize(module.getOperatorsMatrix().getContent().values()));
		Set<Map.Entry<String, List<String>>> operators_map = module.getOperatorsMatrix().getContent().entrySet();
		Iterator<Map.Entry<String, List<String>>> it = operators_map.iterator();
		while (it.hasNext()) {
			for (String s : it.next().getValue())
				assertNotEquals("N/A", s);
		}
	}

	@Test
	public void primitive_types_byte_invalid_replace_equal_value() throws Exception {
		module.parse(getFile("primitive_types_byte_invalid_replace_equal_value"));
		module.execute();
		assertEquals(1, getMatrixSize(module.getOperatorsMatrix().getContent().values()));
		Set<Map.Entry<String, List<String>>> operators_map = module.getOperatorsMatrix().getContent().entrySet();
		Iterator<Map.Entry<String, List<String>>> it = operators_map.iterator();
		while (it.hasNext()) {
			for (String s : it.next().getValue())
				assertEquals("N/A", s);
		}
	}

	@Test
	public void primitive_types_byte_invalid_incompatible() throws Exception {
		module.parse(getFile("primitive_types_byte_invalid_incompatible"));
		module.execute();
		assertEquals(1, getMatrixSize(module.getOperatorsMatrix().getContent().values()));
		Set<Map.Entry<String, List<String>>> operators_map = module.getOperatorsMatrix().getContent().entrySet();
		Iterator<Map.Entry<String, List<String>>> it = operators_map.iterator();
		while (it.hasNext()) {
			for (String s : it.next().getValue())
				assertEquals("N/A", s);
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
		return new File(ValidationByte.class.getResource("resources/" + s + ".emu").getPath());
	}
}
