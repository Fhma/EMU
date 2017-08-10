package org.eclipse.epsilon.emu.engine.test.unit;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EcoreFactory;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.epsilon.emu.EmuModule;
import org.eclipse.epsilon.emu.mutation.IMutationGenerator;
import org.eclipse.epsilon.emu.mutation.execute.MutationGeneratorImpl;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import static org.junit.Assert.*;

/**
 * @author Faisal Alhwikem
 * @since 2017
 */
public class MutationTests {
	private IMutationGenerator gen = null;
	private EcoreFactory factory = null;
	private EmuModule module;
	private Object role;

	@Rule
	public final ExpectedException exception = ExpectedException.none();

	public MutationTests() {
		gen = new MutationGeneratorImpl();
		factory = EcoreFactory.eINSTANCE;
		module = new EmuModule();
		role = (Object) module;
	}

	@Test
	public void implementation() throws Exception {
		EAttribute feature = factory.createEAttribute();
		try {
			gen.mutate(null, feature, "value", module, IMutationGenerator.DEL_MUTATION_ACTION);
		} catch (Exception e) {
			assert (e instanceof NullPointerException);
		}
		try {
			gen.mutate(role, null, "value", module, IMutationGenerator.DEL_MUTATION_ACTION);
		} catch (Exception e) {
			assert (e instanceof NullPointerException);
		}
		try {
			gen.mutate(role, feature, "value", null, IMutationGenerator.DEL_MUTATION_ACTION);
		} catch (Exception e) {
			assert (e instanceof NullPointerException);
		}
		try {
			gen.mutate(role, feature, "value", module, null);
		} catch (Exception e) {
			assert (e instanceof NullPointerException);
		}
		try {
			gen.checkConditions(null, "value", "newValue", IMutationGenerator.DEL_MUTATION_ACTION);
		} catch (Exception e) {
			assert (e instanceof NullPointerException);
		}
		try {
			gen.checkConditions(feature, "value", "newValue", IMutationGenerator.DEL_MUTATION_ACTION);
		} catch (Exception e) {
			assert (e instanceof NullPointerException);
		}
	}

	@Test
	public void mutation_Boolean() throws Exception {
		EAttribute _boolean = factory.createEAttribute();
		_boolean.setName("boolean_0");
		_boolean.setLowerBound(0);
		_boolean.setUpperBound(1);
		_boolean.setEType(EcorePackage.Literals.EBOOLEAN);

		// null as a value
		assertEquals(IMutationGenerator.NOTIMPLEMENTED, gen.mutate(role, _boolean, null, module, IMutationGenerator.ADD_MUTATION_ACTION));
		assertEquals(IMutationGenerator.INVALID, gen.mutate(role, _boolean, null, module, IMutationGenerator.DEL_MUTATION_ACTION));
		assertEquals(IMutationGenerator.INVALID, gen.mutate(role, _boolean, null, module, IMutationGenerator.REPLACE_MUTATION_ACTION));

		// valid value: false
		assertEquals(IMutationGenerator.INVALID, gen.mutate(role, _boolean, false, module, IMutationGenerator.ADD_MUTATION_ACTION));
		assertNull(gen.mutate(role, _boolean, false, module, IMutationGenerator.DEL_MUTATION_ACTION));
		assertTrue((Boolean) gen.mutate(role, _boolean, false, module, IMutationGenerator.REPLACE_MUTATION_ACTION));

		// valid value: true
		assertEquals(IMutationGenerator.INVALID, gen.mutate(role, _boolean, true, module, IMutationGenerator.ADD_MUTATION_ACTION));
		assertNull(gen.mutate(role, _boolean, true, module, IMutationGenerator.DEL_MUTATION_ACTION));
		assertFalse((Boolean) gen.mutate(role, _boolean, true, module, IMutationGenerator.REPLACE_MUTATION_ACTION));

		_boolean.setLowerBound(1);

		// null as a value
		assertEquals(IMutationGenerator.NOTIMPLEMENTED, gen.mutate(role, _boolean, null, module, IMutationGenerator.ADD_MUTATION_ACTION));
		assertEquals(IMutationGenerator.INVALID, gen.mutate(role, _boolean, null, module, IMutationGenerator.DEL_MUTATION_ACTION));
		assertEquals(IMutationGenerator.INVALID, gen.mutate(role, _boolean, null, module, IMutationGenerator.REPLACE_MUTATION_ACTION));

		// valid value: false
		assertEquals(IMutationGenerator.INVALID, gen.mutate(role, _boolean, false, module, IMutationGenerator.ADD_MUTATION_ACTION));
		assertEquals(IMutationGenerator.INVALID, gen.mutate(role, _boolean, false, module, IMutationGenerator.DEL_MUTATION_ACTION));
		assertTrue((Boolean) gen.mutate(role, _boolean, false, module, IMutationGenerator.REPLACE_MUTATION_ACTION));

		// valid value: true
		assertEquals(IMutationGenerator.INVALID, gen.mutate(role, _boolean, true, module, IMutationGenerator.ADD_MUTATION_ACTION));
		assertEquals(IMutationGenerator.INVALID, gen.mutate(role, _boolean, true, module, IMutationGenerator.DEL_MUTATION_ACTION));
		assertFalse((Boolean) gen.mutate(role, _boolean, true, module, IMutationGenerator.REPLACE_MUTATION_ACTION));
	}

	@Test
	public void mutation_Integer() throws Exception {
		EAttribute integer = factory.createEAttribute();
		integer.setName("integer_0");
		integer.setLowerBound(0);
		integer.setUpperBound(1);
		integer.setEType(EcorePackage.Literals.EINT);

		// null as a value
		assertNotNull(gen.mutate(role, integer, null, module, IMutationGenerator.ADD_MUTATION_ACTION));
		assertEquals(IMutationGenerator.INVALID, gen.mutate(role, integer, null, module, IMutationGenerator.DEL_MUTATION_ACTION));
		// Not implemented value
		assertNotNull(gen.mutate(role, integer, null, module, IMutationGenerator.REPLACE_MUTATION_ACTION));

		assertEquals(2, (int) gen.mutate(role, integer, 1, module, IMutationGenerator.ADD_MUTATION_ACTION));
		assertEquals(0, (int) gen.mutate(role, integer, 1, module, IMutationGenerator.DEL_MUTATION_ACTION));
		assertEquals(-79, (int) gen.mutate(role, integer, -80, module, IMutationGenerator.ADD_MUTATION_ACTION));
		assertEquals(-81, (int) gen.mutate(role, integer, -80, module, IMutationGenerator.DEL_MUTATION_ACTION));
	}

	@Test
	public void mutation_Real() throws Exception {
		EAttribute real = factory.createEAttribute();
		real.setName("real_0");
		real.setLowerBound(0);
		real.setUpperBound(1);
		real.setEType(EcorePackage.Literals.EFLOAT);

		// null as a value
		assertEquals(IMutationGenerator.INVALID, gen.mutate(role, real, null, module, IMutationGenerator.ADD_MUTATION_ACTION));
		assertEquals(IMutationGenerator.INVALID, gen.mutate(role, real, null, module, IMutationGenerator.DEL_MUTATION_ACTION));
		// Not implemented value
		assertEquals(IMutationGenerator.NOTIMPLEMENTED, gen.mutate(role, real, null, module, IMutationGenerator.REPLACE_MUTATION_ACTION));

		assertEquals(2.2, (float) gen.mutate(role, real, 1.2, module, IMutationGenerator.ADD_MUTATION_ACTION), 0.0001);
		assertEquals(0.1, (float) gen.mutate(role, real, 1.1, module, IMutationGenerator.DEL_MUTATION_ACTION), 0.0001);
		assertEquals(-79.7853, (float) gen.mutate(role, real, -(80.7853), module, IMutationGenerator.ADD_MUTATION_ACTION), 0.0001);
		assertEquals(-81.7853, (float) gen.mutate(role, real, -(80.7853), module, IMutationGenerator.DEL_MUTATION_ACTION), 0.0001);
	}

	@Test
	public void mutation_String() throws Exception {
		EAttribute string = factory.createEAttribute();
		string.setName("string");
		string.setLowerBound(0);
		string.setEType(EcorePackage.Literals.ESTRING);

		// null as a value
		assertNotNull(gen.mutate(role, string, null, module, IMutationGenerator.ADD_MUTATION_ACTION));
		assertEquals(IMutationGenerator.INVALID, gen.mutate(role, string, null, module, IMutationGenerator.DEL_MUTATION_ACTION));
		assertEquals(IMutationGenerator.INVALID, gen.mutate(role, string, null, module, IMutationGenerator.REPLACE_MUTATION_ACTION));

		// valid value: empty string
		String result;
		result = (String) gen.mutate(role, string, "", module, IMutationGenerator.ADD_MUTATION_ACTION);
		assertEquals(IMutationGenerator.INVALID, result);
		result = (String) gen.mutate(role, string, "", module, IMutationGenerator.DEL_MUTATION_ACTION);
		assertNull(result);

		// valid value: normal string
		result = (String) gen.mutate(role, string, "ABC", module, IMutationGenerator.ADD_MUTATION_ACTION);
		assertEquals(IMutationGenerator.INVALID, result);
		result = (String) gen.mutate(role, string, "ABC", module, IMutationGenerator.DEL_MUTATION_ACTION);
		assertNull(result);
	}
}
