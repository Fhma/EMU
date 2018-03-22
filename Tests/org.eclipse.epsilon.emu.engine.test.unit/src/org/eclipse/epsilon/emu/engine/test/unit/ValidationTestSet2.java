
package org.eclipse.epsilon.emu.engine.test.unit;

import org.junit.Test;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.eclipse.emf.ecore.EcoreFactory;
import org.eclipse.epsilon.emc.mutant.IProperty;
import org.eclipse.epsilon.emc.mutant.emf.EMFPropertyImpl;
import org.eclipse.epsilon.emu.mutation.IMutationChecker;
import org.eclipse.epsilon.emu.mutation.execute.MutationCheckerImpl;

/**
 * @author Faisal Alhwikem
 * @since April, 2017
 */
public class ValidationTestSet2 {

	private IMutationChecker gen = null;
	private EcoreFactory factory = null;

	public ValidationTestSet2() {
		gen = new MutationCheckerImpl();
		factory = EcoreFactory.eINSTANCE;
	}

	@Test
	public void single_attr_Boolean_optional() {
		IProperty _boolean = new EMFPropertyImpl(factory.createEAttribute());
		_boolean.setName("boolean_0");
		_boolean.setLowerBound(0);
		_boolean.setUpperBound(1);
		_boolean.setType(Boolean.class);

		// null as original values
		assertEquals(IMutationChecker.VALID,
				gen.checkConditions(_boolean, null, true, IMutationChecker.ADD_MUTATION_ACTION));
		assertEquals(IMutationChecker.INVALID,
				gen.checkConditions(_boolean, null, true, IMutationChecker.DEL_MUTATION_ACTION));
		assertEquals(IMutationChecker.INVALID,
				gen.checkConditions(_boolean, null, true, IMutationChecker.REPLACE_MUTATION_ACTION));

		// null as new values
		assertEquals(IMutationChecker.INVALID,
				gen.checkConditions(_boolean, true, null, IMutationChecker.ADD_MUTATION_ACTION));
		assertEquals(IMutationChecker.VALID,
				gen.checkConditions(_boolean, true, null, IMutationChecker.DEL_MUTATION_ACTION));
		assertEquals(IMutationChecker.INVALID,
				gen.checkConditions(_boolean, true, null, IMutationChecker.REPLACE_MUTATION_ACTION));

		// valid values: different values
		assertEquals(IMutationChecker.INVALID,
				gen.checkConditions(_boolean, true, false, IMutationChecker.ADD_MUTATION_ACTION));
		assertEquals(IMutationChecker.INVALID,
				gen.checkConditions(_boolean, true, false, IMutationChecker.DEL_MUTATION_ACTION));
		assertEquals(IMutationChecker.VALID,
				gen.checkConditions(_boolean, true, false, IMutationChecker.REPLACE_MUTATION_ACTION));

		// valid values: equal values
		assertEquals(IMutationChecker.INVALID,
				gen.checkConditions(_boolean, true, true, IMutationChecker.ADD_MUTATION_ACTION));
		assertEquals(IMutationChecker.INVALID,
				gen.checkConditions(_boolean, true, true, IMutationChecker.DEL_MUTATION_ACTION));

		assertEquals(IMutationChecker.INVALID,
				gen.checkConditions(_boolean, true, true, IMutationChecker.REPLACE_MUTATION_ACTION));

		// invalid values: equal values
		assertEquals(IMutationChecker.INVALID,
				gen.checkConditions(_boolean, null, null, IMutationChecker.ADD_MUTATION_ACTION));
		assertEquals(IMutationChecker.INVALID,
				gen.checkConditions(_boolean, null, null, IMutationChecker.DEL_MUTATION_ACTION));
		assertEquals(IMutationChecker.INVALID,
				gen.checkConditions(_boolean, null, null, IMutationChecker.REPLACE_MUTATION_ACTION));
	}

	@Test
	public void single_attr_Boolean_required() {
		IProperty _boolean = new EMFPropertyImpl(factory.createEAttribute());
		_boolean.setName("boolean_1");
		_boolean.setLowerBound(1);
		_boolean.setUpperBound(1);
		_boolean.setType(Boolean.class);

		// null as original values
		assertEquals(IMutationChecker.VALID,
				gen.checkConditions(_boolean, null, true, IMutationChecker.ADD_MUTATION_ACTION));
		assertEquals(IMutationChecker.INVALID,
				gen.checkConditions(_boolean, null, true, IMutationChecker.DEL_MUTATION_ACTION));
		assertEquals(IMutationChecker.INVALID,
				gen.checkConditions(_boolean, null, true, IMutationChecker.REPLACE_MUTATION_ACTION));

		// null as new values
		assertEquals(IMutationChecker.INVALID,
				gen.checkConditions(_boolean, true, null, IMutationChecker.ADD_MUTATION_ACTION));
		assertEquals(IMutationChecker.INVALID,
				gen.checkConditions(_boolean, true, null, IMutationChecker.DEL_MUTATION_ACTION));
		assertEquals(IMutationChecker.INVALID,
				gen.checkConditions(_boolean, true, null, IMutationChecker.REPLACE_MUTATION_ACTION));

		// valid values: different values
		assertEquals(IMutationChecker.INVALID,
				gen.checkConditions(_boolean, true, false, IMutationChecker.ADD_MUTATION_ACTION));
		assertEquals(IMutationChecker.INVALID,
				gen.checkConditions(_boolean, true, false, IMutationChecker.DEL_MUTATION_ACTION));
		assertEquals(IMutationChecker.VALID,
				gen.checkConditions(_boolean, true, false, IMutationChecker.REPLACE_MUTATION_ACTION));

		// valid values: equal values
		assertEquals(IMutationChecker.INVALID,
				gen.checkConditions(_boolean, true, true, IMutationChecker.ADD_MUTATION_ACTION));
		assertEquals(IMutationChecker.INVALID,
				gen.checkConditions(_boolean, true, true, IMutationChecker.DEL_MUTATION_ACTION));
		assertEquals(IMutationChecker.INVALID,
				gen.checkConditions(_boolean, true, true, IMutationChecker.REPLACE_MUTATION_ACTION));

		// invalid values: equal values
		assertEquals(IMutationChecker.INVALID,
				gen.checkConditions(_boolean, null, null, IMutationChecker.ADD_MUTATION_ACTION));
		assertEquals(IMutationChecker.INVALID,
				gen.checkConditions(_boolean, null, null, IMutationChecker.DEL_MUTATION_ACTION));
		assertEquals(IMutationChecker.INVALID,
				gen.checkConditions(_boolean, null, null, IMutationChecker.REPLACE_MUTATION_ACTION));
	}

	@Test
	public void single_attr_Integer_optional() {
		IProperty integer = new EMFPropertyImpl(factory.createEAttribute());
		integer.setName("integer_0");
		integer.setLowerBound(0);
		integer.setUpperBound(1);
		integer.setType(Integer.class);

		// null as original values
		assertEquals(IMutationChecker.VALID,
				gen.checkConditions(integer, null, 0, IMutationChecker.ADD_MUTATION_ACTION));
		assertEquals(IMutationChecker.INVALID,
				gen.checkConditions(integer, null, 0, IMutationChecker.DEL_MUTATION_ACTION));
		assertEquals(IMutationChecker.INVALID,
				gen.checkConditions(integer, null, 0, IMutationChecker.REPLACE_MUTATION_ACTION));

		// null as new values
		assertEquals(IMutationChecker.INVALID,
				gen.checkConditions(integer, 0, null, IMutationChecker.ADD_MUTATION_ACTION));
		assertEquals(IMutationChecker.VALID,
				gen.checkConditions(integer, 0, null, IMutationChecker.DEL_MUTATION_ACTION));
		assertEquals(IMutationChecker.INVALID,
				gen.checkConditions(integer, 0, null, IMutationChecker.REPLACE_MUTATION_ACTION));

		// valid values: different values
		assertEquals(IMutationChecker.INVALID,
				gen.checkConditions(integer, 0, 1, IMutationChecker.ADD_MUTATION_ACTION));
		assertEquals(IMutationChecker.INVALID,
				gen.checkConditions(integer, 0, 1, IMutationChecker.DEL_MUTATION_ACTION));
		assertEquals(IMutationChecker.VALID,
				gen.checkConditions(integer, 0, 1, IMutationChecker.REPLACE_MUTATION_ACTION));

		// valid values: equal values
		assertEquals(IMutationChecker.INVALID,
				gen.checkConditions(integer, 2, 2, IMutationChecker.ADD_MUTATION_ACTION));
		assertEquals(IMutationChecker.INVALID,
				gen.checkConditions(integer, 2, 2, IMutationChecker.DEL_MUTATION_ACTION));
		assertEquals(IMutationChecker.INVALID,
				gen.checkConditions(integer, 2, 2, IMutationChecker.REPLACE_MUTATION_ACTION));

		// invalid values: equal values
		assertEquals(IMutationChecker.INVALID,
				gen.checkConditions(integer, null, null, IMutationChecker.ADD_MUTATION_ACTION));
		assertEquals(IMutationChecker.INVALID,
				gen.checkConditions(integer, null, null, IMutationChecker.DEL_MUTATION_ACTION));
		assertEquals(IMutationChecker.INVALID,
				gen.checkConditions(integer, null, null, IMutationChecker.REPLACE_MUTATION_ACTION));
	}

	@Test
	public void single_attr_Integer_required() {
		IProperty integer = new EMFPropertyImpl(factory.createEAttribute());
		integer.setName("integer_1");
		integer.setLowerBound(1);
		integer.setUpperBound(1);
		integer.setType(Integer.class);

		// null as original values
		assertEquals(IMutationChecker.VALID,
				gen.checkConditions(integer, null, 0, IMutationChecker.ADD_MUTATION_ACTION));
		assertEquals(IMutationChecker.INVALID,
				gen.checkConditions(integer, null, 0, IMutationChecker.DEL_MUTATION_ACTION));
		assertEquals(IMutationChecker.INVALID,
				gen.checkConditions(integer, null, 0, IMutationChecker.REPLACE_MUTATION_ACTION));

		// null as new values
		assertEquals(IMutationChecker.INVALID,
				gen.checkConditions(integer, 0, null, IMutationChecker.ADD_MUTATION_ACTION));
		assertEquals(IMutationChecker.INVALID,
				gen.checkConditions(integer, 0, null, IMutationChecker.DEL_MUTATION_ACTION));
		assertEquals(IMutationChecker.INVALID,
				gen.checkConditions(integer, 0, null, IMutationChecker.REPLACE_MUTATION_ACTION));

		// valid values: different values
		assertEquals(IMutationChecker.INVALID,
				gen.checkConditions(integer, 0, 1, IMutationChecker.ADD_MUTATION_ACTION));
		assertEquals(IMutationChecker.INVALID,
				gen.checkConditions(integer, 0, 1, IMutationChecker.DEL_MUTATION_ACTION));
		assertEquals(IMutationChecker.VALID,
				gen.checkConditions(integer, 0, 1, IMutationChecker.REPLACE_MUTATION_ACTION));

		// valid values: equal values
		assertEquals(IMutationChecker.INVALID,
				gen.checkConditions(integer, 2, 2, IMutationChecker.ADD_MUTATION_ACTION));
		assertEquals(IMutationChecker.INVALID,
				gen.checkConditions(integer, 2, 2, IMutationChecker.DEL_MUTATION_ACTION));
		assertEquals(IMutationChecker.INVALID,
				gen.checkConditions(integer, 2, 2, IMutationChecker.REPLACE_MUTATION_ACTION));

		// invalid values: equal values
		assertEquals(IMutationChecker.INVALID,
				gen.checkConditions(integer, null, null, IMutationChecker.ADD_MUTATION_ACTION));
		assertEquals(IMutationChecker.INVALID,
				gen.checkConditions(integer, null, null, IMutationChecker.DEL_MUTATION_ACTION));
		assertEquals(IMutationChecker.INVALID,
				gen.checkConditions(integer, null, null, IMutationChecker.REPLACE_MUTATION_ACTION));
	}

	@Test
	public void single_attr_Real_optional() {
		IProperty real = new EMFPropertyImpl(factory.createEAttribute());
		real.setName("real_0");
		real.setLowerBound(0);
		real.setUpperBound(1);
		real.setType(Float.class);

		// null as original values
		assertEquals(IMutationChecker.VALID, gen.checkConditions(real, null, 0, IMutationChecker.ADD_MUTATION_ACTION));
		assertEquals(IMutationChecker.INVALID,
				gen.checkConditions(real, null, 0, IMutationChecker.DEL_MUTATION_ACTION));
		assertEquals(IMutationChecker.INVALID,
				gen.checkConditions(real, null, 0, IMutationChecker.REPLACE_MUTATION_ACTION));
		assertEquals(IMutationChecker.INVALID,
				gen.checkConditions(real, 0, null, IMutationChecker.ADD_MUTATION_ACTION));

		assertEquals(IMutationChecker.VALID, gen.checkConditions(real, 0, null, IMutationChecker.DEL_MUTATION_ACTION));
		assertEquals(IMutationChecker.INVALID,
				gen.checkConditions(real, 0, null, IMutationChecker.REPLACE_MUTATION_ACTION));

		// valid values: different values
		assertEquals(IMutationChecker.INVALID, gen.checkConditions(real, 0, 1, IMutationChecker.ADD_MUTATION_ACTION));
		assertEquals(IMutationChecker.INVALID, gen.checkConditions(real, 0, 1, IMutationChecker.DEL_MUTATION_ACTION));
		assertEquals(IMutationChecker.VALID, gen.checkConditions(real, 0, 1, IMutationChecker.REPLACE_MUTATION_ACTION));

		// valid values: equal values
		assertEquals(IMutationChecker.INVALID, gen.checkConditions(real, 2, 2, IMutationChecker.ADD_MUTATION_ACTION));
		assertEquals(IMutationChecker.INVALID, gen.checkConditions(real, 2, 2, IMutationChecker.DEL_MUTATION_ACTION));
		assertEquals(IMutationChecker.INVALID,
				gen.checkConditions(real, 2, 2, IMutationChecker.REPLACE_MUTATION_ACTION));

		// invalid values: equal values
		assertEquals(IMutationChecker.INVALID,
				gen.checkConditions(real, null, null, IMutationChecker.ADD_MUTATION_ACTION));
		assertEquals(IMutationChecker.INVALID,
				gen.checkConditions(real, null, null, IMutationChecker.DEL_MUTATION_ACTION));
		assertEquals(IMutationChecker.INVALID,
				gen.checkConditions(real, null, null, IMutationChecker.REPLACE_MUTATION_ACTION));
	}

	@Test
	public void single_attr_Real_required() {
		IProperty real = new EMFPropertyImpl(factory.createEAttribute());
		real.setName("real_1");
		real.setLowerBound(1);
		real.setUpperBound(1);
		real.setType(Float.class);

		// null as original values
		assertEquals(IMutationChecker.VALID, gen.checkConditions(real, null, 0, IMutationChecker.ADD_MUTATION_ACTION));
		assertEquals(IMutationChecker.INVALID,
				gen.checkConditions(real, null, 0, IMutationChecker.DEL_MUTATION_ACTION));
		assertEquals(IMutationChecker.INVALID,
				gen.checkConditions(real, null, 0, IMutationChecker.REPLACE_MUTATION_ACTION));

		assertEquals(IMutationChecker.INVALID,
				gen.checkConditions(real, 0, null, IMutationChecker.ADD_MUTATION_ACTION));
		assertEquals(IMutationChecker.INVALID,
				gen.checkConditions(real, 0, null, IMutationChecker.DEL_MUTATION_ACTION));
		assertEquals(IMutationChecker.INVALID,
				gen.checkConditions(real, 0, null, IMutationChecker.REPLACE_MUTATION_ACTION));

		// valid values: different values
		assertEquals(IMutationChecker.INVALID, gen.checkConditions(real, 0, 1, IMutationChecker.ADD_MUTATION_ACTION));
		assertEquals(IMutationChecker.INVALID, gen.checkConditions(real, 0, 1, IMutationChecker.DEL_MUTATION_ACTION));
		assertEquals(IMutationChecker.VALID, gen.checkConditions(real, 0, 1, IMutationChecker.REPLACE_MUTATION_ACTION));

		// valid values: equal values
		assertEquals(IMutationChecker.INVALID, gen.checkConditions(real, 2, 2, IMutationChecker.ADD_MUTATION_ACTION));
		assertEquals(IMutationChecker.INVALID, gen.checkConditions(real, 2, 2, IMutationChecker.DEL_MUTATION_ACTION));
		assertEquals(IMutationChecker.INVALID,
				gen.checkConditions(real, 2, 2, IMutationChecker.REPLACE_MUTATION_ACTION));

		// invalid values: equal values
		assertEquals(IMutationChecker.INVALID,
				gen.checkConditions(real, null, null, IMutationChecker.ADD_MUTATION_ACTION));
		assertEquals(IMutationChecker.INVALID,
				gen.checkConditions(real, null, null, IMutationChecker.DEL_MUTATION_ACTION));
		assertEquals(IMutationChecker.INVALID,
				gen.checkConditions(real, null, null, IMutationChecker.REPLACE_MUTATION_ACTION));
	}

	@Test
	public void single_attr_String_optional() {
		// Optional attribute
		IProperty string = new EMFPropertyImpl(factory.createEAttribute());
		string.setName("string");
		string.setLowerBound(0);
		string.setUpperBound(1);
		string.setType(String.class);

		// null as original values
		assertEquals(IMutationChecker.VALID,
				gen.checkConditions(string, null, "string", IMutationChecker.ADD_MUTATION_ACTION));
		assertEquals(IMutationChecker.INVALID,
				gen.checkConditions(string, null, "string", IMutationChecker.DEL_MUTATION_ACTION));
		assertEquals(IMutationChecker.INVALID,
				gen.checkConditions(string, null, "string", IMutationChecker.REPLACE_MUTATION_ACTION));

		// null as new values
		assertEquals(IMutationChecker.INVALID,
				gen.checkConditions(string, "string", null, IMutationChecker.ADD_MUTATION_ACTION));

		assertEquals(IMutationChecker.VALID,
				gen.checkConditions(string, "string", null, IMutationChecker.DEL_MUTATION_ACTION));
		assertEquals(IMutationChecker.INVALID,
				gen.checkConditions(string, "string", null, IMutationChecker.REPLACE_MUTATION_ACTION));

		// empty string as original values
		assertEquals(IMutationChecker.INVALID,
				gen.checkConditions(string, "", "string", IMutationChecker.ADD_MUTATION_ACTION));
		assertEquals(IMutationChecker.INVALID,
				gen.checkConditions(string, "", "string", IMutationChecker.DEL_MUTATION_ACTION));
		assertEquals(IMutationChecker.VALID,
				gen.checkConditions(string, "", "string", IMutationChecker.REPLACE_MUTATION_ACTION));

		// empty string as new values
		assertEquals(IMutationChecker.INVALID,
				gen.checkConditions(string, "string", "", IMutationChecker.ADD_MUTATION_ACTION));
		assertEquals(IMutationChecker.INVALID,
				gen.checkConditions(string, "string", "", IMutationChecker.DEL_MUTATION_ACTION));
		assertEquals(IMutationChecker.VALID,
				gen.checkConditions(string, "string", "", IMutationChecker.REPLACE_MUTATION_ACTION));

		// empty string as original and new values
		assertEquals(IMutationChecker.INVALID,
				gen.checkConditions(string, "", "", IMutationChecker.ADD_MUTATION_ACTION));
		assertEquals(IMutationChecker.INVALID,
				gen.checkConditions(string, "", "", IMutationChecker.DEL_MUTATION_ACTION));
		assertEquals(IMutationChecker.INVALID,
				gen.checkConditions(string, "", "", IMutationChecker.REPLACE_MUTATION_ACTION));

		// valid string: different values
		assertEquals(IMutationChecker.INVALID,
				gen.checkConditions(string, "string1", "string2", IMutationChecker.ADD_MUTATION_ACTION));
		assertEquals(IMutationChecker.INVALID,
				gen.checkConditions(string, "string1", "string2", IMutationChecker.DEL_MUTATION_ACTION));
		assertEquals(IMutationChecker.VALID,
				gen.checkConditions(string, "string1", "string2", IMutationChecker.REPLACE_MUTATION_ACTION));

		// valid string: equal values
		assertEquals(IMutationChecker.INVALID,
				gen.checkConditions(string, "string1", "string1", IMutationChecker.ADD_MUTATION_ACTION));
		assertEquals(IMutationChecker.INVALID,
				gen.checkConditions(string, "string1", "string1", IMutationChecker.DEL_MUTATION_ACTION));
		assertEquals(IMutationChecker.INVALID,
				gen.checkConditions(string, "string1", "string1", IMutationChecker.REPLACE_MUTATION_ACTION));

		// invalid string: equal values
		assertEquals(IMutationChecker.INVALID,
				gen.checkConditions(string, null, null, IMutationChecker.ADD_MUTATION_ACTION));
		assertEquals(IMutationChecker.INVALID,
				gen.checkConditions(string, null, null, IMutationChecker.DEL_MUTATION_ACTION));
		assertEquals(IMutationChecker.INVALID,
				gen.checkConditions(string, null, null, IMutationChecker.REPLACE_MUTATION_ACTION));
	}

	@Test
	public void single_attr_String_required() {
		IProperty string = new EMFPropertyImpl(factory.createEAttribute());
		string.setName("string");
		string.setLowerBound(1);
		string.setUpperBound(1);
		string.setType(String.class);

		// null as original values
		assertEquals(IMutationChecker.VALID,
				gen.checkConditions(string, null, "string", IMutationChecker.ADD_MUTATION_ACTION));
		assertEquals(IMutationChecker.INVALID,
				gen.checkConditions(string, null, "string", IMutationChecker.DEL_MUTATION_ACTION));
		assertEquals(IMutationChecker.INVALID,
				gen.checkConditions(string, null, "string", IMutationChecker.REPLACE_MUTATION_ACTION));

		// null as new values
		assertEquals(IMutationChecker.INVALID,
				gen.checkConditions(string, "string", null, IMutationChecker.ADD_MUTATION_ACTION));
		assertEquals(IMutationChecker.INVALID,
				gen.checkConditions(string, "string", null, IMutationChecker.DEL_MUTATION_ACTION));
		assertEquals(IMutationChecker.INVALID,
				gen.checkConditions(string, "string", null, IMutationChecker.REPLACE_MUTATION_ACTION));

		// empty string as original values
		assertEquals(IMutationChecker.INVALID,
				gen.checkConditions(string, "", "string", IMutationChecker.ADD_MUTATION_ACTION));
		assertEquals(IMutationChecker.INVALID,
				gen.checkConditions(string, "", "string", IMutationChecker.DEL_MUTATION_ACTION));
		assertEquals(IMutationChecker.VALID,
				gen.checkConditions(string, "", "string", IMutationChecker.REPLACE_MUTATION_ACTION));

		// empty string as new values
		assertEquals(IMutationChecker.INVALID,
				gen.checkConditions(string, "string", "", IMutationChecker.ADD_MUTATION_ACTION));
		assertEquals(IMutationChecker.INVALID,
				gen.checkConditions(string, "string", "", IMutationChecker.DEL_MUTATION_ACTION));
		assertEquals(IMutationChecker.INVALID,
				gen.checkConditions(string, "string", "", IMutationChecker.REPLACE_MUTATION_ACTION));

		// empty string as original and new values
		assertEquals(IMutationChecker.INVALID,
				gen.checkConditions(string, "", "", IMutationChecker.ADD_MUTATION_ACTION));
		assertEquals(IMutationChecker.INVALID,
				gen.checkConditions(string, "", "", IMutationChecker.DEL_MUTATION_ACTION));
		assertEquals(IMutationChecker.INVALID,
				gen.checkConditions(string, "", "", IMutationChecker.REPLACE_MUTATION_ACTION));

		// valid string: different values
		assertEquals(IMutationChecker.INVALID,
				gen.checkConditions(string, "string1", "string2", IMutationChecker.ADD_MUTATION_ACTION));
		assertEquals(IMutationChecker.INVALID,
				gen.checkConditions(string, "string1", "string2", IMutationChecker.DEL_MUTATION_ACTION));
		assertEquals(IMutationChecker.VALID,
				gen.checkConditions(string, "string1", "string2", IMutationChecker.REPLACE_MUTATION_ACTION));

		// valid string: equal values
		assertEquals(IMutationChecker.INVALID,
				gen.checkConditions(string, "string1", "string1", IMutationChecker.ADD_MUTATION_ACTION));
		assertEquals(IMutationChecker.INVALID,
				gen.checkConditions(string, "string1", "string1", IMutationChecker.DEL_MUTATION_ACTION));
		assertEquals(IMutationChecker.INVALID,
				gen.checkConditions(string, "string1", "string1", IMutationChecker.REPLACE_MUTATION_ACTION));

		// invalid string: equal values
		assertEquals(IMutationChecker.INVALID,
				gen.checkConditions(string, null, null, IMutationChecker.ADD_MUTATION_ACTION));
		assertEquals(IMutationChecker.INVALID,
				gen.checkConditions(string, null, null, IMutationChecker.DEL_MUTATION_ACTION));
		assertEquals(IMutationChecker.INVALID,
				gen.checkConditions(string, null, null, IMutationChecker.REPLACE_MUTATION_ACTION));
	}

	@Test
	public void single_ref_optional() {
		IProperty ref = new EMFPropertyImpl(factory.createEReference());
		ref.setName("reference_0");
		ref.setLowerBound(0);
		ref.setUpperBound(1);

		// null as original values
		assertEquals(IMutationChecker.VALID,
				gen.checkConditions(ref, null, "value", IMutationChecker.ADD_MUTATION_ACTION));
		assertEquals(IMutationChecker.INVALID,
				gen.checkConditions(ref, null, "value", IMutationChecker.DEL_MUTATION_ACTION));
		assertEquals(IMutationChecker.INVALID,
				gen.checkConditions(ref, null, "value", IMutationChecker.REPLACE_MUTATION_ACTION));

		// null as new values
		assertEquals(IMutationChecker.INVALID,
				gen.checkConditions(ref, "value", null, IMutationChecker.ADD_MUTATION_ACTION));
		assertEquals(IMutationChecker.VALID,
				gen.checkConditions(ref, "value", null, IMutationChecker.DEL_MUTATION_ACTION));
		assertEquals(IMutationChecker.INVALID,
				gen.checkConditions(ref, "value", null, IMutationChecker.REPLACE_MUTATION_ACTION));

		// valid values: different
		assertEquals(IMutationChecker.INVALID,
				gen.checkConditions(ref, "value1", "value2", IMutationChecker.ADD_MUTATION_ACTION));
		assertEquals(IMutationChecker.INVALID,
				gen.checkConditions(ref, "value1", "value2", IMutationChecker.DEL_MUTATION_ACTION));
		assertEquals(IMutationChecker.VALID,
				gen.checkConditions(ref, "value1", "value2", IMutationChecker.REPLACE_MUTATION_ACTION));

		// valid values: equals
		assertEquals(IMutationChecker.INVALID,
				gen.checkConditions(ref, "value", "value", IMutationChecker.ADD_MUTATION_ACTION));
		assertEquals(IMutationChecker.INVALID,
				gen.checkConditions(ref, "value", "value", IMutationChecker.DEL_MUTATION_ACTION));
		assertEquals(IMutationChecker.INVALID,
				gen.checkConditions(ref, "value", "value", IMutationChecker.REPLACE_MUTATION_ACTION));

		// invalid values: equals
		assertEquals(IMutationChecker.INVALID,
				gen.checkConditions(ref, null, null, IMutationChecker.ADD_MUTATION_ACTION));
		assertEquals(IMutationChecker.INVALID,
				gen.checkConditions(ref, null, null, IMutationChecker.DEL_MUTATION_ACTION));
		assertEquals(IMutationChecker.INVALID,
				gen.checkConditions(ref, null, null, IMutationChecker.REPLACE_MUTATION_ACTION));

	}

	@Test
	public void single_ref_required() {
		IProperty ref = new EMFPropertyImpl(factory.createEReference());
		ref.setName("reference_1");
		ref.setLowerBound(1);
		ref.setUpperBound(1);

		// null as original values
		assertEquals(IMutationChecker.VALID,
				gen.checkConditions(ref, null, "value", IMutationChecker.ADD_MUTATION_ACTION));
		assertEquals(IMutationChecker.INVALID,
				gen.checkConditions(ref, null, "value", IMutationChecker.DEL_MUTATION_ACTION));
		assertEquals(IMutationChecker.INVALID,
				gen.checkConditions(ref, null, "value", IMutationChecker.REPLACE_MUTATION_ACTION));

		// null as new values
		assertEquals(IMutationChecker.INVALID,
				gen.checkConditions(ref, "value", null, IMutationChecker.ADD_MUTATION_ACTION));
		assertEquals(IMutationChecker.INVALID,
				gen.checkConditions(ref, "value", null, IMutationChecker.DEL_MUTATION_ACTION));
		assertEquals(IMutationChecker.INVALID,
				gen.checkConditions(ref, "value", null, IMutationChecker.REPLACE_MUTATION_ACTION));

		// valid values: different
		assertEquals(IMutationChecker.INVALID,
				gen.checkConditions(ref, "value1", "value2", IMutationChecker.ADD_MUTATION_ACTION));
		assertEquals(IMutationChecker.INVALID,
				gen.checkConditions(ref, "value1", "value2", IMutationChecker.DEL_MUTATION_ACTION));
		assertEquals(IMutationChecker.VALID,
				gen.checkConditions(ref, "value1", "value2", IMutationChecker.REPLACE_MUTATION_ACTION));

		// valid values: equals
		assertEquals(IMutationChecker.INVALID,
				gen.checkConditions(ref, "value", "value", IMutationChecker.ADD_MUTATION_ACTION));
		assertEquals(IMutationChecker.INVALID,
				gen.checkConditions(ref, "value", "value", IMutationChecker.DEL_MUTATION_ACTION));
		assertEquals(IMutationChecker.INVALID,
				gen.checkConditions(ref, "value", "value", IMutationChecker.REPLACE_MUTATION_ACTION));

		// invalid values: equals
		assertEquals(IMutationChecker.INVALID,
				gen.checkConditions(ref, null, null, IMutationChecker.ADD_MUTATION_ACTION));
		assertEquals(IMutationChecker.INVALID,
				gen.checkConditions(ref, null, null, IMutationChecker.DEL_MUTATION_ACTION));
		assertEquals(IMutationChecker.INVALID,
				gen.checkConditions(ref, null, null, IMutationChecker.REPLACE_MUTATION_ACTION));
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
		assertEquals(IMutationChecker.INVALID,
				gen.checkConditions(refs, null, col1, IMutationChecker.ADD_MUTATION_ACTION));

		// Assertions: null as new value
		assertEquals(IMutationChecker.INVALID,
				gen.checkConditions(refs, col1, null, IMutationChecker.ADD_MUTATION_ACTION));

		// Assertions: valid addition
		assertEquals(IMutationChecker.VALID,
				gen.checkConditions(refs, col1, col2, IMutationChecker.ADD_MUTATION_ACTION));

		// Assertions: upper bound
		assertEquals(IMutationChecker.INVALID,
				gen.checkConditions(refs, col2, null, IMutationChecker.ADD_MUTATION_ACTION));

		// Assertions: size violation 1
		assertEquals(IMutationChecker.INVALID,
				gen.checkConditions(refs, col1, col1, IMutationChecker.ADD_MUTATION_ACTION));

		// Assertions: size violation 2
		assertEquals(IMutationChecker.INVALID,
				gen.checkConditions(refs, col1, col0, IMutationChecker.ADD_MUTATION_ACTION));

		// Assertions: size violation 3
		assertEquals(IMutationChecker.INVALID,
				gen.checkConditions(refs, col0, col2, IMutationChecker.ADD_MUTATION_ACTION));

		/*********** Deletion validation ************/
		// Assertions: null as original value
		assertEquals(IMutationChecker.INVALID,
				gen.checkConditions(refs, null, col1, IMutationChecker.DEL_MUTATION_ACTION));

		// Assertions: null as new value
		assertEquals(IMutationChecker.INVALID,
				gen.checkConditions(refs, col2, null, IMutationChecker.DEL_MUTATION_ACTION));

		// Assertions: valid deletion
		assertEquals(IMutationChecker.VALID,
				gen.checkConditions(refs, col2, col1, IMutationChecker.DEL_MUTATION_ACTION));

		// Assertions: size violation 1
		assertEquals(IMutationChecker.INVALID,
				gen.checkConditions(refs, col1, col2, IMutationChecker.DEL_MUTATION_ACTION));

		// Assertions: size violation 2
		assertEquals(IMutationChecker.INVALID,
				gen.checkConditions(refs, col2, col0, IMutationChecker.DEL_MUTATION_ACTION));

		// Assertions: lower bound
		assertEquals(IMutationChecker.INVALID,
				gen.checkConditions(refs, col0, null, IMutationChecker.DEL_MUTATION_ACTION));

		/*********** Replacement validation ************/
		// Assertions: null as original value
		assertEquals(IMutationChecker.INVALID,
				gen.checkConditions(refs, null, col1, IMutationChecker.REPLACE_MUTATION_ACTION));

		// Assertions: null as new value
		assertEquals(IMutationChecker.INVALID,
				gen.checkConditions(refs, col2, null, IMutationChecker.REPLACE_MUTATION_ACTION));

		// Assertions: size violation 1
		assertEquals(IMutationChecker.INVALID,
				gen.checkConditions(refs, col1, col2, IMutationChecker.REPLACE_MUTATION_ACTION));

		// Assertions: size violation 2
		assertEquals(IMutationChecker.INVALID,
				gen.checkConditions(refs, col2, col1, IMutationChecker.REPLACE_MUTATION_ACTION));

		// Assertions: size violation 3
		assertEquals(IMutationChecker.INVALID,
				gen.checkConditions(refs, col2, col0, IMutationChecker.REPLACE_MUTATION_ACTION));

		// Assertions: size violation 4
		assertEquals(IMutationChecker.INVALID,
				gen.checkConditions(refs, col0, col2, IMutationChecker.REPLACE_MUTATION_ACTION));

		// Assertions: size violation 5
		assertEquals(IMutationChecker.INVALID,
				gen.checkConditions(refs, col1, col0, IMutationChecker.REPLACE_MUTATION_ACTION));

		// Assertions: valid replacement
		col0.add("value_0");
		assertEquals(IMutationChecker.VALID,
				gen.checkConditions(refs, col0, col1, IMutationChecker.REPLACE_MUTATION_ACTION));

		col0.clear();

		refs = new EMFPropertyImpl(factory.createEReference());
		refs.setName("refs_0_-1");
		refs.setLowerBound(0);
		refs.setUpperBound(-1);

		/*********** Addition validation ************/
		// Assertions: null as original value
		assertEquals(IMutationChecker.INVALID,
				gen.checkConditions(refs, null, col1, IMutationChecker.ADD_MUTATION_ACTION));

		// Assertions: null as new value
		assertEquals(IMutationChecker.INVALID,
				gen.checkConditions(refs, col1, null, IMutationChecker.ADD_MUTATION_ACTION));

		// Assertions: valid addition
		assertEquals(IMutationChecker.VALID,
				gen.checkConditions(refs, col1, col2, IMutationChecker.ADD_MUTATION_ACTION));

		// Assertions: size violation 1
		assertEquals(IMutationChecker.INVALID,
				gen.checkConditions(refs, col1, col1, IMutationChecker.ADD_MUTATION_ACTION));

		// Assertions: size violation 2
		assertEquals(IMutationChecker.INVALID,
				gen.checkConditions(refs, col1, col0, IMutationChecker.ADD_MUTATION_ACTION));

		// Assertions: size violation 3
		assertEquals(IMutationChecker.INVALID,
				gen.checkConditions(refs, col0, col2, IMutationChecker.ADD_MUTATION_ACTION));

		/*********** Deletion validation ************/
		// Assertions: null as original value
		assertEquals(IMutationChecker.INVALID,
				gen.checkConditions(refs, null, col1, IMutationChecker.DEL_MUTATION_ACTION));

		// Assertions: null as new value
		assertEquals(IMutationChecker.INVALID,
				gen.checkConditions(refs, col2, null, IMutationChecker.DEL_MUTATION_ACTION));

		// Assertions: valid deletion
		assertEquals(IMutationChecker.VALID,
				gen.checkConditions(refs, col2, col1, IMutationChecker.DEL_MUTATION_ACTION));

		// Assertions: size violation 1
		assertEquals(IMutationChecker.INVALID,
				gen.checkConditions(refs, col1, col2, IMutationChecker.DEL_MUTATION_ACTION));

		// Assertions: size violation 2
		assertEquals(IMutationChecker.INVALID,
				gen.checkConditions(refs, col2, col0, IMutationChecker.DEL_MUTATION_ACTION));

		// Assertions: lower bound
		assertEquals(IMutationChecker.INVALID,
				gen.checkConditions(refs, col0, null, IMutationChecker.DEL_MUTATION_ACTION));

		/*********** Replacement validation ************/
		// Assertions: null as original value
		assertEquals(IMutationChecker.INVALID,
				gen.checkConditions(refs, null, col1, IMutationChecker.REPLACE_MUTATION_ACTION));

		// Assertions: null as new value
		assertEquals(IMutationChecker.INVALID,
				gen.checkConditions(refs, col2, null, IMutationChecker.REPLACE_MUTATION_ACTION));

		// Assertions: size violation 1
		assertEquals(IMutationChecker.INVALID,
				gen.checkConditions(refs, col1, col2, IMutationChecker.REPLACE_MUTATION_ACTION));

		// Assertions: size violation 2
		assertEquals(IMutationChecker.INVALID,
				gen.checkConditions(refs, col2, col1, IMutationChecker.REPLACE_MUTATION_ACTION));

		// Assertions: size violation 3
		assertEquals(IMutationChecker.INVALID,
				gen.checkConditions(refs, col2, col0, IMutationChecker.REPLACE_MUTATION_ACTION));

		// Assertions: size violation 4
		assertEquals(IMutationChecker.INVALID,
				gen.checkConditions(refs, col0, col2, IMutationChecker.REPLACE_MUTATION_ACTION));

		// Assertions: size violation 5
		assertEquals(IMutationChecker.INVALID,
				gen.checkConditions(refs, col1, col0, IMutationChecker.REPLACE_MUTATION_ACTION));

		// Assertions: valid replacement
		col0.add("value_0");
		assertEquals(IMutationChecker.VALID,
				gen.checkConditions(refs, col0, col1, IMutationChecker.REPLACE_MUTATION_ACTION));

		col0.clear();

		refs = new EMFPropertyImpl(factory.createEReference());
		refs.setName("refs_1_2");
		refs.setLowerBound(1);
		refs.setUpperBound(3);

		/*********** Addition validation ************/
		// Assertions: null as original value
		assertEquals(IMutationChecker.INVALID,
				gen.checkConditions(refs, null, col1, IMutationChecker.ADD_MUTATION_ACTION));

		// Assertions: null as new value
		assertEquals(IMutationChecker.INVALID,
				gen.checkConditions(refs, col1, null, IMutationChecker.ADD_MUTATION_ACTION));

		// Assertions: valid addition
		assertEquals(IMutationChecker.VALID,
				gen.checkConditions(refs, col1, col2, IMutationChecker.ADD_MUTATION_ACTION));

		// Assertions: size violation 1
		assertEquals(IMutationChecker.INVALID,
				gen.checkConditions(refs, col1, col1, IMutationChecker.ADD_MUTATION_ACTION));

		// Assertions: size violation 2
		assertEquals(IMutationChecker.INVALID,
				gen.checkConditions(refs, col1, col0, IMutationChecker.ADD_MUTATION_ACTION));

		// Assertions: size violation 3
		assertEquals(IMutationChecker.INVALID,
				gen.checkConditions(refs, col0, col2, IMutationChecker.ADD_MUTATION_ACTION));

		/*********** Deletion validation ************/
		// Assertions: null as original value
		assertEquals(IMutationChecker.INVALID,
				gen.checkConditions(refs, null, col1, IMutationChecker.DEL_MUTATION_ACTION));

		// Assertions: null as new value
		assertEquals(IMutationChecker.INVALID,
				gen.checkConditions(refs, col2, null, IMutationChecker.DEL_MUTATION_ACTION));

		// Assertions: valid deletion
		assertEquals(IMutationChecker.VALID,
				gen.checkConditions(refs, col2, col1, IMutationChecker.DEL_MUTATION_ACTION));

		// Assertions: size violation 1
		assertEquals(IMutationChecker.INVALID,
				gen.checkConditions(refs, col1, col2, IMutationChecker.DEL_MUTATION_ACTION));

		// Assertions: size violation 2
		assertEquals(IMutationChecker.INVALID,
				gen.checkConditions(refs, col2, col0, IMutationChecker.DEL_MUTATION_ACTION));

		// Assertions: lower bound
		assertEquals(IMutationChecker.INVALID,
				gen.checkConditions(refs, col1, null, IMutationChecker.DEL_MUTATION_ACTION));

		/*********** Replacement validation ************/
		// Assertions: null as original value
		assertEquals(IMutationChecker.INVALID,
				gen.checkConditions(refs, null, col1, IMutationChecker.REPLACE_MUTATION_ACTION));

		// Assertions: null as new value
		assertEquals(IMutationChecker.INVALID,
				gen.checkConditions(refs, col2, null, IMutationChecker.REPLACE_MUTATION_ACTION));

		// Assertions: size violation 1
		assertEquals(IMutationChecker.INVALID,
				gen.checkConditions(refs, col1, col2, IMutationChecker.REPLACE_MUTATION_ACTION));

		// Assertions: size violation 2
		assertEquals(IMutationChecker.INVALID,
				gen.checkConditions(refs, col2, col1, IMutationChecker.REPLACE_MUTATION_ACTION));

		// Assertions: size violation 3
		assertEquals(IMutationChecker.INVALID,
				gen.checkConditions(refs, col2, col0, IMutationChecker.REPLACE_MUTATION_ACTION));

		// Assertions: size violation 4
		assertEquals(IMutationChecker.INVALID,
				gen.checkConditions(refs, col0, col2, IMutationChecker.REPLACE_MUTATION_ACTION));

		// Assertions: size violation 5
		assertEquals(IMutationChecker.INVALID,
				gen.checkConditions(refs, col1, col0, IMutationChecker.REPLACE_MUTATION_ACTION));

		// Assertions: valid replacement
		col0.add("value_0");
		assertEquals(IMutationChecker.VALID,
				gen.checkConditions(refs, col0, col1, IMutationChecker.REPLACE_MUTATION_ACTION));
	}
}
