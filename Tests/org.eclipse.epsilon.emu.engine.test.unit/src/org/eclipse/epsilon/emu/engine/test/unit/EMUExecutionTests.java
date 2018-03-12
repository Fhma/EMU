package org.eclipse.epsilon.emu.engine.test.unit;

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
import org.junit.Test;

import junit.framework.TestCase;

/**
 * @author Faisal Alhwikem
 * @since 2017
 */
public class EMUExecutionTests extends TestCase {

	private EmuModule module;

	@Override
	protected void setUp() throws Exception {
		module = new EmuModule();
		IModel model = createEmfModel("Model",
				EMUExecutionTests.class.getResource("resources/petrinet_example_1.xmi").getPath(),
				EMUExecutionTests.class.getResource("resources/PetriNet.ecore").getPath(), true, false);
		module.getContext().getModelRepository().addModel(model);
		super.setUp();
	}

	private IMutant createEmfModel(String name, String model, String metamodel, boolean readOnLoad,
			boolean storeOnDisposal) throws EolModelLoadingException, URISyntaxException {
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

	@Test
	public void test_invalid_1() throws Exception {
		module.parse(new File(
				EMUExecutionTests.class.getResource("resources/invalid_multi_attr_add_input_1.emu").getPath()));
		// module.execute();
		// assertEquals(0, getMatrixSize(module.getMutationMatrix()));
		try {
			module.execute();
		} catch (Exception e) {
			assert (e instanceof Exception);
		}
	}

	@Test
	public void test_invalid_2() throws Exception {
		module.parse(new File(
				EMUExecutionTests.class.getResource("resources/invalid_multi_attr_add_input_2.emu").getPath()));
		module.execute();
		assertEquals(1, getMatrixSize(module.getOperatorsMatrix().getContent().values()));
	}

	@Test
	public void test_invalid_3() throws Exception {
		module.parse(new File(
				EMUExecutionTests.class.getResource("resources/invalid_multi_attr_delete_input_1.emu").getPath()));
		module.execute();
		assertEquals(1, getMatrixSize(module.getOperatorsMatrix().getContent().values()));
	}

	@Test
	public void test_invalid_4() throws Exception {
		module.parse(new File(
				EMUExecutionTests.class.getResource("resources/invalid_multi_attr_delete_input_2.emu").getPath()));
		module.execute();
		assertEquals(1, getMatrixSize(module.getOperatorsMatrix().getContent().values()));
	}

	@Test
	public void test_invalid_5() throws Exception {
		module.parse(new File(
				EMUExecutionTests.class.getResource("resources/invalid_multi_attr_replace_input_1.emu").getPath()));
		module.execute();
		assertEquals(1, getMatrixSize(module.getOperatorsMatrix().getContent().values()));
	}

	@Test
	public void test_invalid_6() throws Exception {
		module.parse(new File(
				EMUExecutionTests.class.getResource("resources/invalid_multi_attr_replace_input_2.emu").getPath()));
		module.execute();
		assertEquals(1, getMatrixSize(module.getOperatorsMatrix().getContent().values()));
	}

	@Test
	public void test_invalid_7() throws Exception {
		module.parse(
				new File(EMUExecutionTests.class.getResource("resources/invalid_multi_ref_add_input_1.emu").getPath()));
		module.execute();
		assertEquals(1, getMatrixSize(module.getOperatorsMatrix().getContent().values()));
	}

	@Test
	public void test_invalid_8() throws Exception {
		module.parse(new File(
				EMUExecutionTests.class.getResource("resources/invalid_multi_ref_delete_input_1.emu").getPath()));
		module.execute();
		assertEquals(1, getMatrixSize(module.getOperatorsMatrix().getContent().values()));
	}

	@Test
	public void test_invalid_9() throws Exception {
		module.parse(new File(
				EMUExecutionTests.class.getResource("resources/invalid_multi_ref_delete_input_1.emu").getPath()));
		module.execute();
		assertEquals(1, getMatrixSize(module.getOperatorsMatrix().getContent().values()));
	}

	@Test
	public void test_invalid_10() throws Exception {
		module.parse(new File(
				EMUExecutionTests.class.getResource("resources/invalid_multi_ref_delete_input_2.emu").getPath()));
		module.execute();
		assertEquals(1, getMatrixSize(module.getOperatorsMatrix().getContent().values()));
	}

	@Test
	public void test_invalid_11() throws Exception {
		module.parse(new File(
				EMUExecutionTests.class.getResource("resources/invalid_multi_ref_replace_input_1.emu").getPath()));
		module.execute();
		assertEquals(1, getMatrixSize(module.getOperatorsMatrix().getContent().values()));
	}

	@Test
	public void test_invalid_12() throws Exception {
		module.parse(new File(
				EMUExecutionTests.class.getResource("resources/invalid_multi_ref_replace_input_2.emu").getPath()));
		module.execute();
		assertEquals(1, getMatrixSize(module.getOperatorsMatrix().getContent().values()));
	}

	@Test
	public void test_invalid_13() throws Exception {
		module.parse(new File(EMUExecutionTests.class.getResource("resources/invalid_parsing_input_1.emu").getPath()));
		try {
			module.execute();
		} catch (Exception e) {
			assert (e instanceof Exception);
		}
	}

	@Test
	public void test_invalid_14() throws Exception {
		module.parse(new File(EMUExecutionTests.class.getResource("resources/invalid_parsing_input_2.emu").getPath()));
		assertTrue(module.getParseProblems().size() > 0);
	}

	@Test
	public void test_invalid_15() throws Exception {
		module.parse(new File(EMUExecutionTests.class.getResource("resources/invalid_parsing_input_3.emu").getPath()));
		try {
			module.execute();
		} catch (Exception e) {
			assert (e instanceof Exception);
		}
	}

	@Test
	public void test_invalid_16() throws Exception {
		module.parse(new File(
				EMUExecutionTests.class.getResource("resources/invalid_single_attr_0..1_add_input_1.emu").getPath()));
		module.execute();
		assertEquals(1, getMatrixSize(module.getOperatorsMatrix().getContent().values()));
	}

	@Test
	public void test_invalid_17() throws Exception {
		module.parse(new File(EMUExecutionTests.class
				.getResource("resources/invalid_single_attr_0..1_delete_input_1.emu").getPath()));
		module.execute();
		assertEquals(1, getMatrixSize(module.getOperatorsMatrix().getContent().values()));
	}

	@Test
	public void test_invalid_18() throws Exception {
		module.parse(new File(EMUExecutionTests.class
				.getResource("resources/invalid_single_attr_0..1_replace_input_1.emu").getPath()));
		module.execute();
		assertEquals(1, getMatrixSize(module.getOperatorsMatrix().getContent().values()));
	}

	@Test
	public void test_invalid_19() throws Exception {
		module.parse(new File(EMUExecutionTests.class
				.getResource("resources/invalid_single_attr_0..1_replace_input_2.emu").getPath()));
		module.execute();
		assertEquals(1, getMatrixSize(module.getOperatorsMatrix().getContent().values()));
	}

	@Test
	public void test_invalid_20() throws Exception {
		module.parse(new File(EMUExecutionTests.class
				.getResource("resources/invalid_single_attr_0..1_replace_input_3.emu").getPath()));
		module.execute();
		assertEquals(1, getMatrixSize(module.getOperatorsMatrix().getContent().values()));
	}

	@Test
	public void test_invalid_21() throws Exception {
		module.parse(new File(
				EMUExecutionTests.class.getResource("resources/invalid_single_ref_0..1_add_input_1.emu").getPath()));
		module.execute();
		assertEquals(1, getMatrixSize(module.getOperatorsMatrix().getContent().values()));
	}

	@Test
	public void test_invalid_22() throws Exception {
		module.parse(new File(
				EMUExecutionTests.class.getResource("resources/invalid_single_ref_0..1_add_input_2.emu").getPath()));
		module.execute();
		assertEquals(1, getMatrixSize(module.getOperatorsMatrix().getContent().values()));
	}

	@Test
	public void test_invalid_23() throws Exception {
		module.parse(new File(
				EMUExecutionTests.class.getResource("resources/invalid_single_ref_0..1_delete_input_1.emu").getPath()));
		module.execute();
		assertEquals(1, getMatrixSize(module.getOperatorsMatrix().getContent().values()));
	}

	@Test
	public void test_invalid_24() throws Exception {
		module.parse(new File(EMUExecutionTests.class
				.getResource("resources/invalid_single_ref_0..1_replace_input_1.emu").getPath()));
		module.execute();
		assertEquals(1, getMatrixSize(module.getOperatorsMatrix().getContent().values()));
	}

	@Test
	public void test_invalid_25() throws Exception {
		module.parse(new File(EMUExecutionTests.class
				.getResource("resources/invalid_single_ref_0..1_replace_input_2.emu").getPath()));
		module.execute();
		assertEquals(1, getMatrixSize(module.getOperatorsMatrix().getContent().values()));
	}

	@Test
	public void test_invalid_26() throws Exception {
		module.parse(new File(EMUExecutionTests.class
				.getResource("resources/invalid_single_ref_0..1_replace_input_3.emu").getPath()));
		module.execute();
		assertEquals(1, getMatrixSize(module.getOperatorsMatrix().getContent().values()));
	}

	@Test
	public void test_invalid_27() throws Exception {
		module.parse(new File(
				EMUExecutionTests.class.getResource("resources/invalid_single_ref_1_delete_input_1.emu").getPath()));
		module.execute();
		assertEquals(1, getMatrixSize(module.getOperatorsMatrix().getContent().values()));
	}

	@Test
	public void test_invalid_28() throws Exception {
		module.parse(new File(
				EMUExecutionTests.class.getResource("resources/invalid_single_ref_1_replace_input_1.emu").getPath()));
		module.execute();
		assertEquals(1, getMatrixSize(module.getOperatorsMatrix().getContent().values()));
	}

	@Test
	public void test_valid_1() throws Exception {
		module.parse(new File(EMUExecutionTests.class.getResource("resources/multi_attr_add_input_0.emu").getPath()));
		module.execute();
		assertEquals(0, getMatrixSize(module.getOperatorsMatrix().getContent().values()));
	}

	@Test
	public void test_valid_2() throws Exception {
		module.parse(new File(EMUExecutionTests.class.getResource("resources/multi_attr_add_input_1.emu").getPath()));
		module.execute();
		assertEquals(1, getMatrixSize(module.getOperatorsMatrix().getContent().values()));
	}

	@Test
	public void test_valid_3() throws Exception {
		module.parse(new File(EMUExecutionTests.class.getResource("resources/multi_attr_add_input_2.emu").getPath()));
		module.execute();
		assertEquals(2, getMatrixSize(module.getOperatorsMatrix().getContent().values()));
	}

	@Test
	public void test_valid_4() throws Exception {
		module.parse(
				new File(EMUExecutionTests.class.getResource("resources/multi_attr_delete_input_0.emu").getPath()));
		module.execute();
		assertEquals(0, getMatrixSize(module.getOperatorsMatrix().getContent().values()));
	}

	@Test
	public void test_valid_5() throws Exception {
		module.parse(
				new File(EMUExecutionTests.class.getResource("resources/multi_attr_delete_input_1.emu").getPath()));
		module.execute();
		assertEquals(1, getMatrixSize(module.getOperatorsMatrix().getContent().values()));
	}

	@Test
	public void test_valid_6() throws Exception {
		module.parse(
				new File(EMUExecutionTests.class.getResource("resources/multi_attr_delete_input_2.emu").getPath()));
		module.execute();
		assertEquals(2, getMatrixSize(module.getOperatorsMatrix().getContent().values()));
	}

	@Test
	public void test_valid_7() throws Exception {
		module.parse(
				new File(EMUExecutionTests.class.getResource("resources/multi_attr_replace_input_0.emu").getPath()));
		module.execute();
		assertEquals(0, getMatrixSize(module.getOperatorsMatrix().getContent().values()));
	}

	@Test
	public void test_valid_8() throws Exception {
		module.parse(
				new File(EMUExecutionTests.class.getResource("resources/multi_attr_replace_input_1.emu").getPath()));
		module.execute();
		assertEquals(1, getMatrixSize(module.getOperatorsMatrix().getContent().values()));
	}

	@Test
	public void test_valid_9() throws Exception {
		module.parse(
				new File(EMUExecutionTests.class.getResource("resources/multi_attr_replace_input_2.emu").getPath()));
		module.execute();
		assertEquals(2, getMatrixSize(module.getOperatorsMatrix().getContent().values()));
	}

	@Test
	public void test_valid_10() throws Exception {
		module.parse(new File(EMUExecutionTests.class.getResource("resources/multi_ref_add_input_0.emu").getPath()));
		module.execute();
		assertEquals(0, getMatrixSize(module.getOperatorsMatrix().getContent().values()));
	}

	@Test
	public void test_valid_11() throws Exception {
		module.parse(new File(EMUExecutionTests.class.getResource("resources/multi_ref_add_input_1.emu").getPath()));
		module.execute();
		assertEquals(1, getMatrixSize(module.getOperatorsMatrix().getContent().values()));
	}

	@Test
	public void test_valid_12() throws Exception {
		module.parse(new File(EMUExecutionTests.class.getResource("resources/multi_ref_add_input_2.emu").getPath()));
		module.execute();
		assertEquals(2, getMatrixSize(module.getOperatorsMatrix().getContent().values()));
	}

	@Test
	public void test_valid_13() throws Exception {
		module.parse(new File(EMUExecutionTests.class.getResource("resources/multi_ref_delete_input_0.emu").getPath()));
		module.execute();
		assertEquals(0, getMatrixSize(module.getOperatorsMatrix().getContent().values()));
	}

	@Test
	public void test_valid_14() throws Exception {
		module.parse(new File(EMUExecutionTests.class.getResource("resources/multi_ref_delete_input_1.emu").getPath()));
		module.execute();
		assertEquals(1, getMatrixSize(module.getOperatorsMatrix().getContent().values()));
	}

	@Test
	public void test_valid_15() throws Exception {
		module.parse(new File(EMUExecutionTests.class.getResource("resources/multi_ref_delete_input_2.emu").getPath()));
		module.execute();
		assertEquals(2, getMatrixSize(module.getOperatorsMatrix().getContent().values()));
	}

	@Test
	public void test_valid_16() throws Exception {
		module.parse(
				new File(EMUExecutionTests.class.getResource("resources/multi_ref_replace_input_0.emu").getPath()));
		module.execute();
		assertEquals(0, getMatrixSize(module.getOperatorsMatrix().getContent().values()));
	}

	@Test
	public void test_valid_17() throws Exception {
		module.parse(
				new File(EMUExecutionTests.class.getResource("resources/multi_ref_replace_input_1.emu").getPath()));
		module.execute();
		assertEquals(1, getMatrixSize(module.getOperatorsMatrix().getContent().values()));
	}

	@Test
	public void test_valid_18() throws Exception {
		module.parse(
				new File(EMUExecutionTests.class.getResource("resources/multi_ref_replace_input_2.emu").getPath()));
		module.execute();
		assertEquals(2, getMatrixSize(module.getOperatorsMatrix().getContent().values()));
	}

	@Test
	public void test_valid_19() throws Exception {
		module.parse(new File(EMUExecutionTests.class.getResource("resources/parsing_input_0.emu").getPath()));
		module.execute();
		assertEquals(0, getMatrixSize(module.getOperatorsMatrix().getContent().values()));
	}

	@Test
	public void test_valid_20() throws Exception {
		module.parse(new File(EMUExecutionTests.class.getResource("resources/parsing_input_1.emu").getPath()));
		module.execute();
		assertEquals(0, getMatrixSize(module.getOperatorsMatrix().getContent().values()));
	}

	@Test
	public void test_valid_21() throws Exception {
		module.parse(new File(EMUExecutionTests.class.getResource("resources/parsing_input_2.emu").getPath()));
		module.execute();
		assertEquals(2, getMatrixSize(module.getOperatorsMatrix().getContent().values()));
	}

	@Test
	public void test_valid_22() throws Exception {
		module.parse(new File(EMUExecutionTests.class.getResource("resources/parsing_input_3.emu").getPath()));
		module.execute();
		assertEquals(1, getMatrixSize(module.getOperatorsMatrix().getContent().values()));
	}

	@Test
	public void test_valid_23() throws Exception {
		module.parse(
				new File(EMUExecutionTests.class.getResource("resources/single_attr_0..1_add_input_0.emu").getPath()));
		module.execute();
		assertEquals(0, getMatrixSize(module.getOperatorsMatrix().getContent().values()));
	}

	@Test
	public void test_valid_24() throws Exception {
		module.parse(
				new File(EMUExecutionTests.class.getResource("resources/single_attr_0..1_add_input_1.emu").getPath()));
		module.execute();
		assertEquals(1, getMatrixSize(module.getOperatorsMatrix().getContent().values()));
	}

	@Test
	public void test_valid_25() throws Exception {
		module.parse(
				new File(EMUExecutionTests.class.getResource("resources/single_attr_0..1_add_input_2.emu").getPath()));
		module.execute();
		assertEquals(2, getMatrixSize(module.getOperatorsMatrix().getContent().values()));
	}

	@Test
	public void test_valid_26() throws Exception {
		module.parse(new File(
				EMUExecutionTests.class.getResource("resources/single_attr_0..1_delete_input_0.emu").getPath()));
		module.execute();
		assertEquals(0, getMatrixSize(module.getOperatorsMatrix().getContent().values()));
	}

	@Test
	public void test_valid_27() throws Exception {
		module.parse(new File(
				EMUExecutionTests.class.getResource("resources/single_attr_0..1_delete_input_1.emu").getPath()));
		module.execute();
		assertEquals(1, getMatrixSize(module.getOperatorsMatrix().getContent().values()));
	}

	@Test
	public void test_valid_28() throws Exception {
		module.parse(new File(
				EMUExecutionTests.class.getResource("resources/single_attr_0..1_delete_input_2.emu").getPath()));
		module.execute();
		assertEquals(2, getMatrixSize(module.getOperatorsMatrix().getContent().values()));
	}

	@Test
	public void test_valid_29() throws Exception {
		module.parse(new File(
				EMUExecutionTests.class.getResource("resources/single_attr_0..1_replace_input_0.emu").getPath()));
		module.execute();
		assertEquals(0, getMatrixSize(module.getOperatorsMatrix().getContent().values()));
	}

	@Test
	public void test_valid_30() throws Exception {
		module.parse(new File(
				EMUExecutionTests.class.getResource("resources/single_attr_0..1_replace_input_1.emu").getPath()));
		module.execute();
		assertEquals(1, getMatrixSize(module.getOperatorsMatrix().getContent().values()));
	}

	@Test
	public void test_valid_31() throws Exception {
		module.parse(new File(
				EMUExecutionTests.class.getResource("resources/single_attr_0..1_replace_input_2.emu").getPath()));
		module.execute();
		assertEquals(2, getMatrixSize(module.getOperatorsMatrix().getContent().values()));
	}

	@Test
	public void test_valid_32() throws Exception {
		module.parse(
				new File(EMUExecutionTests.class.getResource("resources/single_attr_1_add_input_0.emu").getPath()));
		module.execute();
		assertEquals(0, getMatrixSize(module.getOperatorsMatrix().getContent().values()));
	}

	@Test
	public void test_valid_33() throws Exception {
		module.parse(
				new File(EMUExecutionTests.class.getResource("resources/single_attr_1_add_input_1.emu").getPath()));
		module.execute();
		assertEquals(1, getMatrixSize(module.getOperatorsMatrix().getContent().values()));
	}

	@Test
	public void test_valid_34() throws Exception {
		module.parse(
				new File(EMUExecutionTests.class.getResource("resources/single_attr_1_add_input_2.emu").getPath()));
		module.execute();
		assertEquals(2, getMatrixSize(module.getOperatorsMatrix().getContent().values()));
	}

	@Test
	public void test_valid_35() throws Exception {
		module.parse(
				new File(EMUExecutionTests.class.getResource("resources/single_attr_1_delete_input_0.emu").getPath()));
		module.execute();
		assertEquals(0, getMatrixSize(module.getOperatorsMatrix().getContent().values()));
	}

	@Test
	public void test_valid_36() throws Exception {
		module.parse(
				new File(EMUExecutionTests.class.getResource("resources/single_attr_1_delete_input_1.emu").getPath()));
		module.execute();
		assertEquals(1, getMatrixSize(module.getOperatorsMatrix().getContent().values()));
	}

	@Test
	public void test_valid_37() throws Exception {
		module.parse(
				new File(EMUExecutionTests.class.getResource("resources/single_attr_1_delete_input_2.emu").getPath()));
		module.execute();
		assertEquals(2, getMatrixSize(module.getOperatorsMatrix().getContent().values()));
	}

	@Test
	public void test_valid_38() throws Exception {
		module.parse(
				new File(EMUExecutionTests.class.getResource("resources/single_attr_1_replace_input_0.emu").getPath()));
		module.execute();
		assertEquals(0, getMatrixSize(module.getOperatorsMatrix().getContent().values()));
	}

	@Test
	public void test_valid_39() throws Exception {
		module.parse(
				new File(EMUExecutionTests.class.getResource("resources/single_attr_1_replace_input_1.emu").getPath()));
		module.execute();
		assertEquals(1, getMatrixSize(module.getOperatorsMatrix().getContent().values()));
	}

	@Test
	public void test_valid_40() throws Exception {
		module.parse(
				new File(EMUExecutionTests.class.getResource("resources/single_attr_1_replace_input_2.emu").getPath()));
		module.execute();
		assertEquals(2, getMatrixSize(module.getOperatorsMatrix().getContent().values()));
	}

	@Test
	public void test_valid_41() throws Exception {
		module.parse(
				new File(EMUExecutionTests.class.getResource("resources/single_ref_0..1_add_input_0.emu").getPath()));
		module.execute();
		assertEquals(0, getMatrixSize(module.getOperatorsMatrix().getContent().values()));
	}

	@Test
	public void test_valid_42() throws Exception {
		module.parse(
				new File(EMUExecutionTests.class.getResource("resources/single_ref_0..1_add_input_1.emu").getPath()));
		module.execute();
		assertEquals(1, getMatrixSize(module.getOperatorsMatrix().getContent().values()));
	}

	@Test
	public void test_valid_43() throws Exception {
		module.parse(
				new File(EMUExecutionTests.class.getResource("resources/single_ref_0..1_add_input_2.emu").getPath()));
		module.execute();
		assertEquals(0, getMatrixSize(module.getOperatorsMatrix().getContent().values()));
	}

	@Test
	public void test_valid_44() throws Exception {
		module.parse(new File(
				EMUExecutionTests.class.getResource("resources/single_ref_0..1_delete_input_0.emu").getPath()));
		module.execute();
		assertEquals(0, getMatrixSize(module.getOperatorsMatrix().getContent().values()));
	}

	@Test
	public void test_valid_45() throws Exception {
		module.parse(new File(
				EMUExecutionTests.class.getResource("resources/single_ref_0..1_delete_input_1.emu").getPath()));
		module.execute();
		assertEquals(1, getMatrixSize(module.getOperatorsMatrix().getContent().values()));
	}

	@Test
	public void test_valid_46() throws Exception {
		module.parse(new File(
				EMUExecutionTests.class.getResource("resources/single_ref_0..1_delete_input_2.emu").getPath()));
		module.execute();
		assertEquals(2, getMatrixSize(module.getOperatorsMatrix().getContent().values()));
	}

	@Test
	public void test_valid_47() throws Exception {
		module.parse(new File(
				EMUExecutionTests.class.getResource("resources/single_ref_0..1_replace_input_0.emu").getPath()));
		module.execute();
		assertEquals(0, getMatrixSize(module.getOperatorsMatrix().getContent().values()));
	}

	@Test
	public void test_valid_48() throws Exception {
		module.parse(new File(
				EMUExecutionTests.class.getResource("resources/single_ref_0..1_replace_input_1.emu").getPath()));
		module.execute();
		assertEquals(1, getMatrixSize(module.getOperatorsMatrix().getContent().values()));
	}

	@Test
	public void test_valid_49() throws Exception {
		module.parse(new File(
				EMUExecutionTests.class.getResource("resources/single_ref_0..1_replace_input_2.emu").getPath()));
		module.execute();
		assertEquals(2, getMatrixSize(module.getOperatorsMatrix().getContent().values()));
	}

	@Test
	public void test_valid_50() throws Exception {
		module.parse(
				new File(EMUExecutionTests.class.getResource("resources/single_ref_1_replace_input_0.emu").getPath()));
		module.execute();
		assertEquals(0, getMatrixSize(module.getOperatorsMatrix().getContent().values()));
	}

	@Test
	public void test_valid_51() throws Exception {
		module.parse(
				new File(EMUExecutionTests.class.getResource("resources/single_ref_1_replace_input_1.emu").getPath()));
		module.execute();
		assertEquals(1, getMatrixSize(module.getOperatorsMatrix().getContent().values()));
	}

	@Test
	public void test_valid_52() throws Exception {
		module.parse(
				new File(EMUExecutionTests.class.getResource("resources/single_ref_1_replace_input_2.emu").getPath()));
		module.execute();
		assertEquals(2, getMatrixSize(module.getOperatorsMatrix().getContent().values()));
	}
}
