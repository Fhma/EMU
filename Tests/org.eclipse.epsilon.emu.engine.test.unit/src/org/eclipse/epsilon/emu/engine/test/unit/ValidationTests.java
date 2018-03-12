
package org.eclipse.epsilon.emu.engine.test.unit;

import org.junit.Test;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.eclipse.emf.ecore.EcoreFactory;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.epsilon.emc.mutant.IProperty;
import org.eclipse.epsilon.emc.mutant.emf.EMFPropertyImpl;
import org.eclipse.epsilon.emu.mutation.IMutationGenerator;
import org.eclipse.epsilon.emu.mutation.execute.MutationGeneratorImpl;

/**
 * @author Faisal Alhwikem
 * @since April, 2017
 */
public class ValidationTests {

	private IMutationGenerator gen = null;
	private EcoreFactory factory = null;

	public ValidationTests() {
		gen = new MutationGeneratorImpl();
		factory = EcoreFactory.eINSTANCE;
	}

	@Test
	public void single_attr_Boolean_optional() {
		IProperty _boolean = new EMFPropertyImpl(factory.createEAttribute());
		_boolean.setName("boolean_0");
		_boolean.setLowerBound(0);
		_boolean.setUpperBound(1);
		_boolean.setType(EcorePackage.Literals.EBOOLEAN);

		// null as original values
		assertEquals(IMutationGenerator.VALID,
				gen.checkConditions(_boolean, null, true, IMutationGenerator.ADD_MUTATION_ACTION));
		assertEquals(IMutationGenerator.INVALID,
				gen.checkConditions(_boolean, null, true, IMutationGenerator.DEL_MUTATION_ACTION));
		assertEquals(IMutationGenerator.INVALID,
				gen.checkConditions(_boolean, null, true, IMutationGenerator.REPLACE_MUTATION_ACTION));

		// null as new values
		assertEquals(IMutationGenerator.INVALID,
				gen.checkConditions(_boolean, true, null, IMutationGenerator.ADD_MUTATION_ACTION));
		assertEquals(IMutationGenerator.VALID,
				gen.checkConditions(_boolean, true, null, IMutationGenerator.DEL_MUTATION_ACTION));
		try {
			gen.checkConditions(_boolean, true, null, IMutationGenerator.REPLACE_MUTATION_ACTION);
		} catch (Exception e) {
			assert (e instanceof IllegalArgumentException);
		}

		// valid values: different values
		assertEquals(IMutationGenerator.INVALID,
				gen.checkConditions(_boolean, true, false, IMutationGenerator.ADD_MUTATION_ACTION));
		try {
			gen.checkConditions(_boolean, true, false, IMutationGenerator.DEL_MUTATION_ACTION);
		} catch (Exception e) {
			assert (e instanceof IllegalArgumentException);
		}
		assertEquals(IMutationGenerator.VALID,
				gen.checkConditions(_boolean, true, false, IMutationGenerator.REPLACE_MUTATION_ACTION));

		// valid values: equal values
		assertEquals(IMutationGenerator.INVALID,
				gen.checkConditions(_boolean, true, true, IMutationGenerator.ADD_MUTATION_ACTION));
		try {
			gen.checkConditions(_boolean, true, true, IMutationGenerator.DEL_MUTATION_ACTION);
		} catch (Exception e) {
			assert (e instanceof IllegalArgumentException);
		}
		try {
			gen.checkConditions(_boolean, true, true, IMutationGenerator.REPLACE_MUTATION_ACTION);
		} catch (Exception e) {
			assert (e instanceof IllegalArgumentException);
		}

		// invalid values: equal values
		try {
			gen.checkConditions(_boolean, null, null, IMutationGenerator.ADD_MUTATION_ACTION);
		} catch (Exception e) {
			assert (e instanceof IllegalArgumentException);
		}
		assertEquals(IMutationGenerator.INVALID,
				gen.checkConditions(_boolean, null, null, IMutationGenerator.DEL_MUTATION_ACTION));
		assertEquals(IMutationGenerator.INVALID,
				gen.checkConditions(_boolean, null, null, IMutationGenerator.REPLACE_MUTATION_ACTION));

	}

	@Test
	public void single_attr_Boolean_required() {
		IProperty _boolean = new EMFPropertyImpl(factory.createEAttribute());
		_boolean.setName("boolean_1");
		_boolean.setLowerBound(1);
		_boolean.setUpperBound(1);
		_boolean.setType(EcorePackage.Literals.EBOOLEAN);

		// null as original values
		assertEquals(IMutationGenerator.VALID,
				gen.checkConditions(_boolean, null, true, IMutationGenerator.ADD_MUTATION_ACTION));
		assertEquals(IMutationGenerator.INVALID,
				gen.checkConditions(_boolean, null, true, IMutationGenerator.DEL_MUTATION_ACTION));
		assertEquals(IMutationGenerator.INVALID,
				gen.checkConditions(_boolean, null, true, IMutationGenerator.REPLACE_MUTATION_ACTION));

		// null as new values
		assertEquals(IMutationGenerator.INVALID,
				gen.checkConditions(_boolean, true, null, IMutationGenerator.ADD_MUTATION_ACTION));
		assertEquals(IMutationGenerator.INVALID,
				gen.checkConditions(_boolean, true, null, IMutationGenerator.DEL_MUTATION_ACTION));
		try {
			gen.checkConditions(_boolean, true, null, IMutationGenerator.REPLACE_MUTATION_ACTION);
		} catch (Exception e) {
			assert (e instanceof IllegalArgumentException);
		}

		// valid values: different values
		assertEquals(IMutationGenerator.INVALID,
				gen.checkConditions(_boolean, true, false, IMutationGenerator.ADD_MUTATION_ACTION));
		assertEquals(IMutationGenerator.INVALID,
				gen.checkConditions(_boolean, true, false, IMutationGenerator.DEL_MUTATION_ACTION));
		assertEquals(IMutationGenerator.VALID,
				gen.checkConditions(_boolean, true, false, IMutationGenerator.REPLACE_MUTATION_ACTION));

		// valid values: equal values
		assertEquals(IMutationGenerator.INVALID,
				gen.checkConditions(_boolean, true, true, IMutationGenerator.ADD_MUTATION_ACTION));
		assertEquals(IMutationGenerator.INVALID,
				gen.checkConditions(_boolean, true, true, IMutationGenerator.DEL_MUTATION_ACTION));
		try {
			gen.checkConditions(_boolean, true, true, IMutationGenerator.REPLACE_MUTATION_ACTION);
		} catch (Exception e) {
			assert (e instanceof IllegalArgumentException);
		}

		// invalid values: equal values
		try {
			gen.checkConditions(_boolean, null, null, IMutationGenerator.ADD_MUTATION_ACTION);
		} catch (Exception e) {
			assert (e instanceof IllegalArgumentException);
		}
		assertEquals(IMutationGenerator.INVALID,
				gen.checkConditions(_boolean, null, null, IMutationGenerator.DEL_MUTATION_ACTION));
		assertEquals(IMutationGenerator.INVALID,
				gen.checkConditions(_boolean, null, null, IMutationGenerator.REPLACE_MUTATION_ACTION));
	}

	@Test
	public void single_attr_Integer_optional() {
		IProperty integer = new EMFPropertyImpl(factory.createEAttribute());
		integer.setName("integer_0");
		integer.setLowerBound(0);
		integer.setUpperBound(1);
		integer.setType(EcorePackage.Literals.EINT);

		// null as original values
		assertEquals(IMutationGenerator.INVALID,
				gen.checkConditions(integer, null, 0, IMutationGenerator.ADD_MUTATION_ACTION));
		assertEquals(IMutationGenerator.INVALID,
				gen.checkConditions(integer, null, 0, IMutationGenerator.DEL_MUTATION_ACTION));
		assertEquals(IMutationGenerator.VALID,
				gen.checkConditions(integer, null, 0, IMutationGenerator.REPLACE_MUTATION_ACTION));

		// null as new values
		try {
			gen.checkConditions(integer, 0, null, IMutationGenerator.ADD_MUTATION_ACTION);
		} catch (Exception e) {
			assert (e instanceof IllegalArgumentException);
		}
		try {
			gen.checkConditions(integer, 0, null, IMutationGenerator.DEL_MUTATION_ACTION);
		} catch (Exception e) {
			assert (e instanceof IllegalArgumentException);
		}
		assertEquals(IMutationGenerator.VALID,
				gen.checkConditions(integer, 0, null, IMutationGenerator.REPLACE_MUTATION_ACTION));

		// valid values: different values
		assertEquals(IMutationGenerator.VALID,
				gen.checkConditions(integer, 0, 1, IMutationGenerator.ADD_MUTATION_ACTION));
		assertEquals(IMutationGenerator.VALID,
				gen.checkConditions(integer, 0, 1, IMutationGenerator.DEL_MUTATION_ACTION));
		assertEquals(IMutationGenerator.VALID,
				gen.checkConditions(integer, 0, 1, IMutationGenerator.REPLACE_MUTATION_ACTION));

		// valid values: equal values
		try {
			gen.checkConditions(integer, 2, 2, IMutationGenerator.ADD_MUTATION_ACTION);
		} catch (Exception e) {
			assert (e instanceof IllegalArgumentException);
		}
		try {
			gen.checkConditions(integer, 2, 2, IMutationGenerator.DEL_MUTATION_ACTION);
		} catch (Exception e) {
			assert (e instanceof IllegalArgumentException);
		}
		assertEquals(IMutationGenerator.INVALID,
				gen.checkConditions(integer, 2, 2, IMutationGenerator.REPLACE_MUTATION_ACTION));

		// invalid values: equal values
		assertEquals(IMutationGenerator.INVALID,
				gen.checkConditions(integer, null, null, IMutationGenerator.ADD_MUTATION_ACTION));
		assertEquals(IMutationGenerator.INVALID,
				gen.checkConditions(integer, null, null, IMutationGenerator.DEL_MUTATION_ACTION));
		assertEquals(IMutationGenerator.INVALID,
				gen.checkConditions(integer, null, null, IMutationGenerator.REPLACE_MUTATION_ACTION));
	}

	@Test
	public void single_attr_Integer_required() {
		IProperty integer = new EMFPropertyImpl(factory.createEAttribute());
		integer.setName("integer_1");
		integer.setLowerBound(1);
		integer.setUpperBound(1);
		integer.setType(EcorePackage.Literals.EINT);

		// null as original values
		assertEquals(IMutationGenerator.INVALID,
				gen.checkConditions(integer, null, 0, IMutationGenerator.ADD_MUTATION_ACTION));
		assertEquals(IMutationGenerator.INVALID,
				gen.checkConditions(integer, null, 0, IMutationGenerator.DEL_MUTATION_ACTION));
		assertEquals(IMutationGenerator.VALID,
				gen.checkConditions(integer, null, 0, IMutationGenerator.REPLACE_MUTATION_ACTION));

		// null as new values
		try {
			gen.checkConditions(integer, 0, null, IMutationGenerator.ADD_MUTATION_ACTION);
		} catch (Exception e) {
			assert (e instanceof IllegalArgumentException);
		}
		try {
			gen.checkConditions(integer, 0, null, IMutationGenerator.DEL_MUTATION_ACTION);
		} catch (Exception e) {
			assert (e instanceof IllegalArgumentException);
		}
		assertEquals(IMutationGenerator.VALID,
				gen.checkConditions(integer, 0, null, IMutationGenerator.REPLACE_MUTATION_ACTION));

		// valid values: different values
		assertEquals(IMutationGenerator.VALID,
				gen.checkConditions(integer, 0, 1, IMutationGenerator.ADD_MUTATION_ACTION));
		assertEquals(IMutationGenerator.VALID,
				gen.checkConditions(integer, 0, 1, IMutationGenerator.DEL_MUTATION_ACTION));
		assertEquals(IMutationGenerator.VALID,
				gen.checkConditions(integer, 0, 1, IMutationGenerator.REPLACE_MUTATION_ACTION));

		// valid values: equal values
		try {
			gen.checkConditions(integer, 2, 2, IMutationGenerator.ADD_MUTATION_ACTION);
		} catch (Exception e) {
			assert (e instanceof IllegalArgumentException);
		}
		try {
			gen.checkConditions(integer, 2, 2, IMutationGenerator.DEL_MUTATION_ACTION);
		} catch (Exception e) {
			assert (e instanceof IllegalArgumentException);
		}
		assertEquals(IMutationGenerator.INVALID,
				gen.checkConditions(integer, 2, 2, IMutationGenerator.REPLACE_MUTATION_ACTION));

		// invalid values: equal values
		assertEquals(IMutationGenerator.INVALID,
				gen.checkConditions(integer, null, null, IMutationGenerator.ADD_MUTATION_ACTION));
		assertEquals(IMutationGenerator.INVALID,
				gen.checkConditions(integer, null, null, IMutationGenerator.DEL_MUTATION_ACTION));
		assertEquals(IMutationGenerator.INVALID,
				gen.checkConditions(integer, null, null, IMutationGenerator.REPLACE_MUTATION_ACTION));
	}

	@Test
	public void single_attr_Real_optional() {
		IProperty real = new EMFPropertyImpl(factory.createEAttribute());
		real.setName("real_0");
		real.setLowerBound(0);
		real.setUpperBound(1);
		real.setType(EcorePackage.Literals.EFLOAT);

		// null as original values
		assertEquals(IMutationGenerator.INVALID,
				gen.checkConditions(real, null, 0, IMutationGenerator.ADD_MUTATION_ACTION));
		assertEquals(IMutationGenerator.INVALID,
				gen.checkConditions(real, null, 0, IMutationGenerator.DEL_MUTATION_ACTION));
		assertEquals(IMutationGenerator.VALID,
				gen.checkConditions(real, null, 0, IMutationGenerator.REPLACE_MUTATION_ACTION));

		// null as new values
		try {
			gen.checkConditions(real, 0, null, IMutationGenerator.ADD_MUTATION_ACTION);
		} catch (Exception e) {
			assert (e instanceof IllegalArgumentException);
		}
		try {
			gen.checkConditions(real, 0, null, IMutationGenerator.DEL_MUTATION_ACTION);
		} catch (Exception e) {
			assert (e instanceof IllegalArgumentException);
		}
		assertEquals(IMutationGenerator.VALID,
				gen.checkConditions(real, 0, null, IMutationGenerator.REPLACE_MUTATION_ACTION));

		// valid values: different values
		assertEquals(IMutationGenerator.VALID, gen.checkConditions(real, 0, 1, IMutationGenerator.ADD_MUTATION_ACTION));
		assertEquals(IMutationGenerator.VALID, gen.checkConditions(real, 0, 1, IMutationGenerator.DEL_MUTATION_ACTION));
		assertEquals(IMutationGenerator.VALID,
				gen.checkConditions(real, 0, 1, IMutationGenerator.REPLACE_MUTATION_ACTION));

		// valid values: equal values
		try {
			gen.checkConditions(real, 2, 2, IMutationGenerator.ADD_MUTATION_ACTION);
		} catch (Exception e) {
			assert (e instanceof IllegalArgumentException);
		}
		try {
			gen.checkConditions(real, 2, 2, IMutationGenerator.DEL_MUTATION_ACTION);
		} catch (Exception e) {
			assert (e instanceof IllegalArgumentException);
		}
		assertEquals(IMutationGenerator.INVALID,
				gen.checkConditions(real, 2, 2, IMutationGenerator.REPLACE_MUTATION_ACTION));

		// invalid values: equal values
		assertEquals(IMutationGenerator.INVALID,
				gen.checkConditions(real, null, null, IMutationGenerator.ADD_MUTATION_ACTION));
		assertEquals(IMutationGenerator.INVALID,
				gen.checkConditions(real, null, null, IMutationGenerator.DEL_MUTATION_ACTION));
		assertEquals(IMutationGenerator.INVALID,
				gen.checkConditions(real, null, null, IMutationGenerator.REPLACE_MUTATION_ACTION));
	}

	@Test
	public void single_attr_Real_required() {
		IProperty real = new EMFPropertyImpl(factory.createEAttribute());
		real.setName("real_1");
		real.setLowerBound(1);
		real.setUpperBound(1);
		real.setType(EcorePackage.Literals.EFLOAT);

		// null as original values
		assertEquals(IMutationGenerator.INVALID,
				gen.checkConditions(real, null, 0, IMutationGenerator.ADD_MUTATION_ACTION));
		assertEquals(IMutationGenerator.INVALID,
				gen.checkConditions(real, null, 0, IMutationGenerator.DEL_MUTATION_ACTION));
		assertEquals(IMutationGenerator.VALID,
				gen.checkConditions(real, null, 0, IMutationGenerator.REPLACE_MUTATION_ACTION));

		// null as new values
		try {
			gen.checkConditions(real, 0, null, IMutationGenerator.ADD_MUTATION_ACTION);
		} catch (Exception e) {
			assert (e instanceof IllegalArgumentException);
		}
		try {
			gen.checkConditions(real, 0, null, IMutationGenerator.DEL_MUTATION_ACTION);
		} catch (Exception e) {
			assert (e instanceof IllegalArgumentException);
		}
		assertEquals(IMutationGenerator.VALID,
				gen.checkConditions(real, 0, null, IMutationGenerator.REPLACE_MUTATION_ACTION));

		// valid values: different values
		assertEquals(IMutationGenerator.VALID, gen.checkConditions(real, 0, 1, IMutationGenerator.ADD_MUTATION_ACTION));
		assertEquals(IMutationGenerator.VALID, gen.checkConditions(real, 0, 1, IMutationGenerator.DEL_MUTATION_ACTION));
		assertEquals(IMutationGenerator.VALID,
				gen.checkConditions(real, 0, 1, IMutationGenerator.REPLACE_MUTATION_ACTION));

		// valid values: equal values
		try {
			gen.checkConditions(real, 2, 2, IMutationGenerator.ADD_MUTATION_ACTION);
		} catch (Exception e) {
			assert (e instanceof IllegalArgumentException);
		}
		try {
			gen.checkConditions(real, 2, 2, IMutationGenerator.DEL_MUTATION_ACTION);
		} catch (Exception e) {
			assert (e instanceof IllegalArgumentException);
		}
		assertEquals(IMutationGenerator.INVALID,
				gen.checkConditions(real, 2, 2, IMutationGenerator.REPLACE_MUTATION_ACTION));

		// invalid values: equal values
		assertEquals(IMutationGenerator.INVALID,
				gen.checkConditions(real, null, null, IMutationGenerator.ADD_MUTATION_ACTION));
		assertEquals(IMutationGenerator.INVALID,
				gen.checkConditions(real, null, null, IMutationGenerator.DEL_MUTATION_ACTION));
		assertEquals(IMutationGenerator.INVALID,
				gen.checkConditions(real, null, null, IMutationGenerator.REPLACE_MUTATION_ACTION));
	}

	@Test
	public void single_attr_String_optional() {
		// Optional attribute
		IProperty string = new EMFPropertyImpl(factory.createEAttribute());
		string.setName("string");
		string.setLowerBound(0);
		string.setUpperBound(1);
		string.setType(EcorePackage.Literals.ESTRING);

		// null as original values
		assertEquals(IMutationGenerator.VALID,
				gen.checkConditions(string, null, "string", IMutationGenerator.ADD_MUTATION_ACTION));
		assertEquals(IMutationGenerator.INVALID,
				gen.checkConditions(string, null, "string", IMutationGenerator.DEL_MUTATION_ACTION));
		assertEquals(IMutationGenerator.INVALID,
				gen.checkConditions(string, null, "string", IMutationGenerator.REPLACE_MUTATION_ACTION));

		// null as new values
		try {
			gen.checkConditions(string, "string", null, IMutationGenerator.ADD_MUTATION_ACTION);
		} catch (Exception e) {
			assert (e instanceof NullPointerException);
		}
		try {
			gen.checkConditions(string, "string", null, IMutationGenerator.DEL_MUTATION_ACTION);
		} catch (Exception e) {
			assert (e instanceof NullPointerException);
		}
		assertEquals(IMutationGenerator.INVALID,
				gen.checkConditions(string, "string", null, IMutationGenerator.REPLACE_MUTATION_ACTION));

		// empty string as original values
		assertEquals(IMutationGenerator.INVALID,
				gen.checkConditions(string, "", "string", IMutationGenerator.ADD_MUTATION_ACTION));
		assertEquals(IMutationGenerator.INVALID,
				gen.checkConditions(string, "", "string", IMutationGenerator.DEL_MUTATION_ACTION));
		assertEquals(IMutationGenerator.VALID,
				gen.checkConditions(string, "", "string", IMutationGenerator.REPLACE_MUTATION_ACTION));

		// empty string as new values
		assertEquals(IMutationGenerator.INVALID,
				gen.checkConditions(string, "string", "", IMutationGenerator.ADD_MUTATION_ACTION));
		assertEquals(IMutationGenerator.INVALID,
				gen.checkConditions(string, "string", "", IMutationGenerator.DEL_MUTATION_ACTION));
		assertEquals(IMutationGenerator.VALID,
				gen.checkConditions(string, "string", "", IMutationGenerator.REPLACE_MUTATION_ACTION));

		// empty string as original and new values
		try {
			gen.checkConditions(string, "", "", IMutationGenerator.ADD_MUTATION_ACTION);
		} catch (Exception e) {
			assert (e instanceof IllegalArgumentException);
		}
		assertEquals(IMutationGenerator.INVALID,
				gen.checkConditions(string, "", "", IMutationGenerator.DEL_MUTATION_ACTION));
		assertEquals(IMutationGenerator.INVALID,
				gen.checkConditions(string, "", "", IMutationGenerator.REPLACE_MUTATION_ACTION));

		// valid string: different values
		assertEquals(IMutationGenerator.INVALID,
				gen.checkConditions(string, "string1", "string2", IMutationGenerator.ADD_MUTATION_ACTION));
		assertEquals(IMutationGenerator.INVALID,
				gen.checkConditions(string, "string1", "string2", IMutationGenerator.DEL_MUTATION_ACTION));
		assertEquals(IMutationGenerator.VALID,
				gen.checkConditions(string, "string1", "string2", IMutationGenerator.REPLACE_MUTATION_ACTION));

		// valid string: equal values
		try {
			gen.checkConditions(string, "string1", "string1", IMutationGenerator.ADD_MUTATION_ACTION);
		} catch (Exception e) {
			assert (e instanceof IllegalArgumentException);
		}
		try {
			assertEquals(IMutationGenerator.INVALID,
					gen.checkConditions(string, "string1", "string1", IMutationGenerator.DEL_MUTATION_ACTION));
		} catch (Exception e) {
			assert (e instanceof IllegalArgumentException);
		}
		assertEquals(IMutationGenerator.INVALID,
				gen.checkConditions(string, "string1", "string1", IMutationGenerator.REPLACE_MUTATION_ACTION));

		// invalid string: equal values
		try {
			assertEquals(IMutationGenerator.INVALID,
					gen.checkConditions(string, null, null, IMutationGenerator.ADD_MUTATION_ACTION));
		} catch (Exception e) {
			assert (e instanceof IllegalArgumentException);
		}
		assertEquals(IMutationGenerator.INVALID,
				gen.checkConditions(string, null, null, IMutationGenerator.DEL_MUTATION_ACTION));
		assertEquals(IMutationGenerator.INVALID,
				gen.checkConditions(string, null, null, IMutationGenerator.REPLACE_MUTATION_ACTION));
	}

	@Test
	public void single_attr_String_required() {
		IProperty string = new EMFPropertyImpl(factory.createEAttribute());
		string.setName("string");
		string.setLowerBound(1);
		string.setUpperBound(1);
		string.setType(EcorePackage.Literals.ESTRING);

		// null as original values
		assertEquals(IMutationGenerator.VALID,
				gen.checkConditions(string, null, "string", IMutationGenerator.ADD_MUTATION_ACTION));
		assertEquals(IMutationGenerator.INVALID,
				gen.checkConditions(string, null, "string", IMutationGenerator.DEL_MUTATION_ACTION));
		assertEquals(IMutationGenerator.INVALID,
				gen.checkConditions(string, null, "string", IMutationGenerator.REPLACE_MUTATION_ACTION));

		// null as new values
		try {
			gen.checkConditions(string, "string", null, IMutationGenerator.ADD_MUTATION_ACTION);
		} catch (Exception e) {
			assert (e instanceof NullPointerException);
		}
		try {
			gen.checkConditions(string, "string", null, IMutationGenerator.DEL_MUTATION_ACTION);
		} catch (Exception e) {
			assert (e instanceof NullPointerException);
		}
		assertEquals(IMutationGenerator.INVALID,
				gen.checkConditions(string, "string", null, IMutationGenerator.REPLACE_MUTATION_ACTION));

		// empty string as original values
		assertEquals(IMutationGenerator.INVALID,
				gen.checkConditions(string, "", "string", IMutationGenerator.ADD_MUTATION_ACTION));
		assertEquals(IMutationGenerator.INVALID,
				gen.checkConditions(string, "", "string", IMutationGenerator.DEL_MUTATION_ACTION));
		assertEquals(IMutationGenerator.VALID,
				gen.checkConditions(string, "", "string", IMutationGenerator.REPLACE_MUTATION_ACTION));

		// empty string as new values
		try {
			gen.checkConditions(string, "string", "", IMutationGenerator.ADD_MUTATION_ACTION);
		} catch (Exception e) {
			assert (e instanceof IllegalArgumentException);
		}
		try {
			gen.checkConditions(string, "string", "", IMutationGenerator.DEL_MUTATION_ACTION);
		} catch (Exception e) {
			assert (e instanceof IllegalArgumentException);
		}
		assertEquals(IMutationGenerator.INVALID,
				gen.checkConditions(string, "string", "", IMutationGenerator.REPLACE_MUTATION_ACTION));

		// empty string as original and new values
		try {
			gen.checkConditions(string, "", "", IMutationGenerator.ADD_MUTATION_ACTION);
		} catch (Exception e) {
			assert (e instanceof IllegalArgumentException);
		}
		assertEquals(IMutationGenerator.INVALID,
				gen.checkConditions(string, "", "", IMutationGenerator.DEL_MUTATION_ACTION));
		assertEquals(IMutationGenerator.INVALID,
				gen.checkConditions(string, "", "", IMutationGenerator.REPLACE_MUTATION_ACTION));

		// valid string: different values
		assertEquals(IMutationGenerator.INVALID,
				gen.checkConditions(string, "string1", "string2", IMutationGenerator.ADD_MUTATION_ACTION));
		assertEquals(IMutationGenerator.INVALID,
				gen.checkConditions(string, "string1", "string2", IMutationGenerator.DEL_MUTATION_ACTION));
		assertEquals(IMutationGenerator.VALID,
				gen.checkConditions(string, "string1", "string2", IMutationGenerator.REPLACE_MUTATION_ACTION));

		// valid string: equal values
		try {
			gen.checkConditions(string, "string1", "string1", IMutationGenerator.ADD_MUTATION_ACTION);
		} catch (Exception e) {
			assert (e instanceof IllegalArgumentException);
		}
		try {
			assertEquals(IMutationGenerator.INVALID,
					gen.checkConditions(string, "string1", "string1", IMutationGenerator.DEL_MUTATION_ACTION));
		} catch (Exception e) {
			assert (e instanceof IllegalArgumentException);
		}
		assertEquals(IMutationGenerator.INVALID,
				gen.checkConditions(string, "string1", "string1", IMutationGenerator.REPLACE_MUTATION_ACTION));

		// invalid string: equal values
		try {
			assertEquals(IMutationGenerator.INVALID,
					gen.checkConditions(string, null, null, IMutationGenerator.ADD_MUTATION_ACTION));
		} catch (Exception e) {
			assert (e instanceof IllegalArgumentException);
		}
		assertEquals(IMutationGenerator.INVALID,
				gen.checkConditions(string, null, null, IMutationGenerator.DEL_MUTATION_ACTION));
		assertEquals(IMutationGenerator.INVALID,
				gen.checkConditions(string, null, null, IMutationGenerator.REPLACE_MUTATION_ACTION));
	}

	@Test
	public void single_ref_optional() {
		IProperty ref = new EMFPropertyImpl(factory.createEReference());
		ref.setName("reference_0");
		ref.setLowerBound(0);
		ref.setUpperBound(1);

		// null as original values
		assertEquals(IMutationGenerator.VALID,
				gen.checkConditions(ref, null, "value", IMutationGenerator.ADD_MUTATION_ACTION));
		assertEquals(IMutationGenerator.INVALID,
				gen.checkConditions(ref, null, "value", IMutationGenerator.DEL_MUTATION_ACTION));
		assertEquals(IMutationGenerator.INVALID,
				gen.checkConditions(ref, null, "value", IMutationGenerator.REPLACE_MUTATION_ACTION));

		// null as new values
		assertEquals(IMutationGenerator.INVALID,
				gen.checkConditions(ref, "value", null, IMutationGenerator.ADD_MUTATION_ACTION));
		assertEquals(IMutationGenerator.VALID,
				gen.checkConditions(ref, "value", null, IMutationGenerator.DEL_MUTATION_ACTION));
		assertEquals(IMutationGenerator.INVALID,
				gen.checkConditions(ref, "value", null, IMutationGenerator.REPLACE_MUTATION_ACTION));

		// valid values: different
		assertEquals(IMutationGenerator.INVALID,
				gen.checkConditions(ref, "value1", "value2", IMutationGenerator.ADD_MUTATION_ACTION));
		assertEquals(IMutationGenerator.INVALID,
				gen.checkConditions(ref, "value1", "value2", IMutationGenerator.DEL_MUTATION_ACTION));
		assertEquals(IMutationGenerator.VALID,
				gen.checkConditions(ref, "value1", "value2", IMutationGenerator.REPLACE_MUTATION_ACTION));

		// valid values: equals
		assertEquals(IMutationGenerator.INVALID,
				gen.checkConditions(ref, "value", "value", IMutationGenerator.ADD_MUTATION_ACTION));
		assertEquals(IMutationGenerator.INVALID,
				gen.checkConditions(ref, "value", "value", IMutationGenerator.DEL_MUTATION_ACTION));
		assertEquals(IMutationGenerator.INVALID,
				gen.checkConditions(ref, "value", "value", IMutationGenerator.REPLACE_MUTATION_ACTION));

		// invalid values: equals
		assertEquals(IMutationGenerator.INVALID,
				gen.checkConditions(ref, null, null, IMutationGenerator.ADD_MUTATION_ACTION));
		assertEquals(IMutationGenerator.INVALID,
				gen.checkConditions(ref, null, null, IMutationGenerator.DEL_MUTATION_ACTION));
		assertEquals(IMutationGenerator.INVALID,
				gen.checkConditions(ref, null, null, IMutationGenerator.REPLACE_MUTATION_ACTION));

	}

	@Test
	public void single_ref_required() {
		IProperty ref = new EMFPropertyImpl(factory.createEReference());
		ref.setName("reference_1");
		ref.setLowerBound(1);
		ref.setUpperBound(1);

		// null as original values
		assertEquals(IMutationGenerator.VALID,
				gen.checkConditions(ref, null, "value", IMutationGenerator.ADD_MUTATION_ACTION));
		assertEquals(IMutationGenerator.INVALID,
				gen.checkConditions(ref, null, "value", IMutationGenerator.DEL_MUTATION_ACTION));
		assertEquals(IMutationGenerator.INVALID,
				gen.checkConditions(ref, null, "value", IMutationGenerator.REPLACE_MUTATION_ACTION));

		// null as new values
		assertEquals(IMutationGenerator.INVALID,
				gen.checkConditions(ref, "value", null, IMutationGenerator.ADD_MUTATION_ACTION));
		assertEquals(IMutationGenerator.INVALID,
				gen.checkConditions(ref, "value", null, IMutationGenerator.DEL_MUTATION_ACTION));
		assertEquals(IMutationGenerator.INVALID,
				gen.checkConditions(ref, "value", null, IMutationGenerator.REPLACE_MUTATION_ACTION));

		// valid values: different
		assertEquals(IMutationGenerator.INVALID,
				gen.checkConditions(ref, "value1", "value2", IMutationGenerator.ADD_MUTATION_ACTION));
		assertEquals(IMutationGenerator.INVALID,
				gen.checkConditions(ref, "value1", "value2", IMutationGenerator.DEL_MUTATION_ACTION));
		assertEquals(IMutationGenerator.VALID,
				gen.checkConditions(ref, "value1", "value2", IMutationGenerator.REPLACE_MUTATION_ACTION));

		// valid values: equals
		assertEquals(IMutationGenerator.INVALID,
				gen.checkConditions(ref, "value", "value", IMutationGenerator.ADD_MUTATION_ACTION));
		assertEquals(IMutationGenerator.INVALID,
				gen.checkConditions(ref, "value", "value", IMutationGenerator.DEL_MUTATION_ACTION));
		assertEquals(IMutationGenerator.INVALID,
				gen.checkConditions(ref, "value", "value", IMutationGenerator.REPLACE_MUTATION_ACTION));

		// invalid values: equals
		assertEquals(IMutationGenerator.INVALID,
				gen.checkConditions(ref, null, null, IMutationGenerator.ADD_MUTATION_ACTION));
		assertEquals(IMutationGenerator.INVALID,
				gen.checkConditions(ref, null, null, IMutationGenerator.DEL_MUTATION_ACTION));
		assertEquals(IMutationGenerator.INVALID,
				gen.checkConditions(ref, null, null, IMutationGenerator.REPLACE_MUTATION_ACTION));
	}

	@Test
	public void multi_ref() throws Exception {
		IProperty refs = new EMFPropertyImpl(factory.createEReference());
		refs.setName("refs_0_2");
		refs.setLowerBound(0);
		refs.setUpperBound(2);

		List<String> col0 = new ArrayList<String>();
		List<String> col1 = new ArrayList<String>();
		List<String> col2 = new ArrayList<String>();
		Collections.addAll(col1, "value_1");
		Collections.addAll(col2, "value_1", "value_2");

		/*********** Addition validation ************/
		// Assertions: null as original value
		assertEquals(IMutationGenerator.INVALID,
				gen.checkConditions(refs, null, col1, IMutationGenerator.ADD_MUTATION_ACTION));

		// Assertions: null as new value
		assertEquals(IMutationGenerator.INVALID,
				gen.checkConditions(refs, col1, null, IMutationGenerator.ADD_MUTATION_ACTION));

		// Assertions: valid addition
		assertEquals(IMutationGenerator.VALID,
				gen.checkConditions(refs, col1, col2, IMutationGenerator.ADD_MUTATION_ACTION));

		// Assertions: upper bound
		assertEquals(IMutationGenerator.INVALID,
				gen.checkConditions(refs, col2, null, IMutationGenerator.ADD_MUTATION_ACTION));

		// Assertions: size violation 1
		assertEquals(IMutationGenerator.INVALID,
				gen.checkConditions(refs, col1, col1, IMutationGenerator.ADD_MUTATION_ACTION));

		// Assertions: size violation 2
		assertEquals(IMutationGenerator.INVALID,
				gen.checkConditions(refs, col1, col0, IMutationGenerator.ADD_MUTATION_ACTION));

		// Assertions: size violation 3
		assertEquals(IMutationGenerator.INVALID,
				gen.checkConditions(refs, col0, col2, IMutationGenerator.ADD_MUTATION_ACTION));

		/*********** Deletion validation ************/
		// Assertions: null as original value
		assertEquals(IMutationGenerator.INVALID,
				gen.checkConditions(refs, null, col1, IMutationGenerator.DEL_MUTATION_ACTION));

		// Assertions: null as new value
		assertEquals(IMutationGenerator.INVALID,
				gen.checkConditions(refs, col2, null, IMutationGenerator.DEL_MUTATION_ACTION));

		// Assertions: valid deletion
		assertEquals(IMutationGenerator.VALID,
				gen.checkConditions(refs, col2, col1, IMutationGenerator.DEL_MUTATION_ACTION));

		// Assertions: size violation 1
		assertEquals(IMutationGenerator.INVALID,
				gen.checkConditions(refs, col1, col2, IMutationGenerator.DEL_MUTATION_ACTION));

		// Assertions: size violation 2
		assertEquals(IMutationGenerator.INVALID,
				gen.checkConditions(refs, col2, col0, IMutationGenerator.DEL_MUTATION_ACTION));

		// Assertions: lower bound
		assertEquals(IMutationGenerator.INVALID,
				gen.checkConditions(refs, col0, null, IMutationGenerator.DEL_MUTATION_ACTION));

		/*********** Replacement validation ************/
		// Assertions: null as original value
		assertEquals(IMutationGenerator.INVALID,
				gen.checkConditions(refs, null, col1, IMutationGenerator.REPLACE_MUTATION_ACTION));

		// Assertions: null as new value
		assertEquals(IMutationGenerator.INVALID,
				gen.checkConditions(refs, col2, null, IMutationGenerator.REPLACE_MUTATION_ACTION));

		// Assertions: size violation 1
		assertEquals(IMutationGenerator.INVALID,
				gen.checkConditions(refs, col1, col2, IMutationGenerator.REPLACE_MUTATION_ACTION));

		// Assertions: size violation 2
		assertEquals(IMutationGenerator.INVALID,
				gen.checkConditions(refs, col2, col1, IMutationGenerator.REPLACE_MUTATION_ACTION));

		// Assertions: size violation 3
		assertEquals(IMutationGenerator.INVALID,
				gen.checkConditions(refs, col2, col0, IMutationGenerator.REPLACE_MUTATION_ACTION));

		// Assertions: size violation 4
		assertEquals(IMutationGenerator.INVALID,
				gen.checkConditions(refs, col0, col2, IMutationGenerator.REPLACE_MUTATION_ACTION));

		// Assertions: size violation 5
		assertEquals(IMutationGenerator.INVALID,
				gen.checkConditions(refs, col1, col0, IMutationGenerator.REPLACE_MUTATION_ACTION));

		// Assertions: valid replacement
		col0.add("value_0");
		assertEquals(IMutationGenerator.VALID,
				gen.checkConditions(refs, col0, col1, IMutationGenerator.REPLACE_MUTATION_ACTION));

		col0.clear();

		refs = new EMFPropertyImpl(factory.createEReference());
		refs.setName("refs_0_-1");
		refs.setLowerBound(0);
		refs.setUpperBound(-1);

		/*********** Addition validation ************/
		// Assertions: null as original value
		assertEquals(IMutationGenerator.INVALID,
				gen.checkConditions(refs, null, col1, IMutationGenerator.ADD_MUTATION_ACTION));

		// Assertions: null as new value
		assertEquals(IMutationGenerator.INVALID,
				gen.checkConditions(refs, col1, null, IMutationGenerator.ADD_MUTATION_ACTION));

		// Assertions: valid addition
		assertEquals(IMutationGenerator.VALID,
				gen.checkConditions(refs, col1, col2, IMutationGenerator.ADD_MUTATION_ACTION));

		// Assertions: size violation 1
		assertEquals(IMutationGenerator.INVALID,
				gen.checkConditions(refs, col1, col1, IMutationGenerator.ADD_MUTATION_ACTION));

		// Assertions: size violation 2
		assertEquals(IMutationGenerator.INVALID,
				gen.checkConditions(refs, col1, col0, IMutationGenerator.ADD_MUTATION_ACTION));

		// Assertions: size violation 3
		assertEquals(IMutationGenerator.INVALID,
				gen.checkConditions(refs, col0, col2, IMutationGenerator.ADD_MUTATION_ACTION));

		/*********** Deletion validation ************/
		// Assertions: null as original value
		assertEquals(IMutationGenerator.INVALID,
				gen.checkConditions(refs, null, col1, IMutationGenerator.DEL_MUTATION_ACTION));

		// Assertions: null as new value
		assertEquals(IMutationGenerator.INVALID,
				gen.checkConditions(refs, col2, null, IMutationGenerator.DEL_MUTATION_ACTION));

		// Assertions: valid deletion
		assertEquals(IMutationGenerator.VALID,
				gen.checkConditions(refs, col2, col1, IMutationGenerator.DEL_MUTATION_ACTION));

		// Assertions: size violation 1
		assertEquals(IMutationGenerator.INVALID,
				gen.checkConditions(refs, col1, col2, IMutationGenerator.DEL_MUTATION_ACTION));

		// Assertions: size violation 2
		assertEquals(IMutationGenerator.INVALID,
				gen.checkConditions(refs, col2, col0, IMutationGenerator.DEL_MUTATION_ACTION));

		// Assertions: lower bound
		assertEquals(IMutationGenerator.INVALID,
				gen.checkConditions(refs, col0, null, IMutationGenerator.DEL_MUTATION_ACTION));

		/*********** Replacement validation ************/
		// Assertions: null as original value
		assertEquals(IMutationGenerator.INVALID,
				gen.checkConditions(refs, null, col1, IMutationGenerator.REPLACE_MUTATION_ACTION));

		// Assertions: null as new value
		assertEquals(IMutationGenerator.INVALID,
				gen.checkConditions(refs, col2, null, IMutationGenerator.REPLACE_MUTATION_ACTION));

		// Assertions: size violation 1
		assertEquals(IMutationGenerator.INVALID,
				gen.checkConditions(refs, col1, col2, IMutationGenerator.REPLACE_MUTATION_ACTION));

		// Assertions: size violation 2
		assertEquals(IMutationGenerator.INVALID,
				gen.checkConditions(refs, col2, col1, IMutationGenerator.REPLACE_MUTATION_ACTION));

		// Assertions: size violation 3
		assertEquals(IMutationGenerator.INVALID,
				gen.checkConditions(refs, col2, col0, IMutationGenerator.REPLACE_MUTATION_ACTION));

		// Assertions: size violation 4
		assertEquals(IMutationGenerator.INVALID,
				gen.checkConditions(refs, col0, col2, IMutationGenerator.REPLACE_MUTATION_ACTION));

		// Assertions: size violation 5
		assertEquals(IMutationGenerator.INVALID,
				gen.checkConditions(refs, col1, col0, IMutationGenerator.REPLACE_MUTATION_ACTION));

		// Assertions: valid replacement
		col0.add("value_0");
		assertEquals(IMutationGenerator.VALID,
				gen.checkConditions(refs, col0, col1, IMutationGenerator.REPLACE_MUTATION_ACTION));

		col0.clear();

		refs = new EMFPropertyImpl(factory.createEReference());
		refs.setName("refs_1_2");
		refs.setLowerBound(1);
		refs.setUpperBound(3);

		/*********** Addition validation ************/
		// Assertions: null as original value
		assertEquals(IMutationGenerator.INVALID,
				gen.checkConditions(refs, null, col1, IMutationGenerator.ADD_MUTATION_ACTION));

		// Assertions: null as new value
		assertEquals(IMutationGenerator.INVALID,
				gen.checkConditions(refs, col1, null, IMutationGenerator.ADD_MUTATION_ACTION));

		// Assertions: valid addition
		assertEquals(IMutationGenerator.VALID,
				gen.checkConditions(refs, col1, col2, IMutationGenerator.ADD_MUTATION_ACTION));

		// Assertions: size violation 1
		assertEquals(IMutationGenerator.INVALID,
				gen.checkConditions(refs, col1, col1, IMutationGenerator.ADD_MUTATION_ACTION));

		// Assertions: size violation 2
		assertEquals(IMutationGenerator.INVALID,
				gen.checkConditions(refs, col1, col0, IMutationGenerator.ADD_MUTATION_ACTION));

		// Assertions: size violation 3
		assertEquals(IMutationGenerator.INVALID,
				gen.checkConditions(refs, col0, col2, IMutationGenerator.ADD_MUTATION_ACTION));

		/*********** Deletion validation ************/
		// Assertions: null as original value
		assertEquals(IMutationGenerator.INVALID,
				gen.checkConditions(refs, null, col1, IMutationGenerator.DEL_MUTATION_ACTION));

		// Assertions: null as new value
		assertEquals(IMutationGenerator.INVALID,
				gen.checkConditions(refs, col2, null, IMutationGenerator.DEL_MUTATION_ACTION));

		// Assertions: valid deletion
		assertEquals(IMutationGenerator.VALID,
				gen.checkConditions(refs, col2, col1, IMutationGenerator.DEL_MUTATION_ACTION));

		// Assertions: size violation 1
		assertEquals(IMutationGenerator.INVALID,
				gen.checkConditions(refs, col1, col2, IMutationGenerator.DEL_MUTATION_ACTION));

		// Assertions: size violation 2
		assertEquals(IMutationGenerator.INVALID,
				gen.checkConditions(refs, col2, col0, IMutationGenerator.DEL_MUTATION_ACTION));

		// Assertions: lower bound
		assertEquals(IMutationGenerator.INVALID,
				gen.checkConditions(refs, col1, null, IMutationGenerator.DEL_MUTATION_ACTION));

		/*********** Replacement validation ************/
		// Assertions: null as original value
		assertEquals(IMutationGenerator.INVALID,
				gen.checkConditions(refs, null, col1, IMutationGenerator.REPLACE_MUTATION_ACTION));

		// Assertions: null as new value
		assertEquals(IMutationGenerator.INVALID,
				gen.checkConditions(refs, col2, null, IMutationGenerator.REPLACE_MUTATION_ACTION));

		// Assertions: size violation 1
		assertEquals(IMutationGenerator.INVALID,
				gen.checkConditions(refs, col1, col2, IMutationGenerator.REPLACE_MUTATION_ACTION));

		// Assertions: size violation 2
		assertEquals(IMutationGenerator.INVALID,
				gen.checkConditions(refs, col2, col1, IMutationGenerator.REPLACE_MUTATION_ACTION));

		// Assertions: size violation 3
		assertEquals(IMutationGenerator.INVALID,
				gen.checkConditions(refs, col2, col0, IMutationGenerator.REPLACE_MUTATION_ACTION));

		// Assertions: size violation 4
		assertEquals(IMutationGenerator.INVALID,
				gen.checkConditions(refs, col0, col2, IMutationGenerator.REPLACE_MUTATION_ACTION));

		// Assertions: size violation 5
		assertEquals(IMutationGenerator.INVALID,
				gen.checkConditions(refs, col1, col0, IMutationGenerator.REPLACE_MUTATION_ACTION));

		// Assertions: valid replacement
		col0.add("value_0");
		assertEquals(IMutationGenerator.VALID,
				gen.checkConditions(refs, col0, col1, IMutationGenerator.REPLACE_MUTATION_ACTION));
	}
}
