package org.eclipse.epsilon.emu.engine.test.unit;

import org.eclipse.emf.ecore.EcoreFactory;
import org.eclipse.epsilon.emc.mutant.IProperty;
import org.eclipse.epsilon.emc.mutant.emf.EMFPropertyImpl;
import org.eclipse.epsilon.emu.mutation.IMutationChecker;
import org.eclipse.epsilon.emu.mutation.execute.MutationCheckerImpl;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import static org.junit.Assert.*;

/**
 * @author Faisal Alhwikem
 * @since 2017
 */
public class ValidationTestSet1 {
	private IMutationChecker gen = null;
	private EcoreFactory factory = null;

	@Rule
	public final ExpectedException exception = ExpectedException.none();

	public ValidationTestSet1() {
		gen = new MutationCheckerImpl();
		factory = EcoreFactory.eINSTANCE;
	}

	@Test
	public void implementation() throws Exception {
		IProperty property = new EMFPropertyImpl(factory.createEAttribute());
		try {
			gen.checkConditions(null, "value", IMutationChecker.DEL_MUTATION_ACTION);
		} catch (Exception e) {
			assert (e instanceof NullPointerException);
		}
		try {
			gen.checkConditions(property, "value", null);
		} catch (Exception e) {
			assert (e instanceof NullPointerException);
		}
	}

	@Test
	public void mutation_Boolean() throws Exception {
		IProperty _boolean = new EMFPropertyImpl(factory.createEAttribute());
		_boolean.setName("boolean_0");
		_boolean.setLowerBound(0);
		_boolean.setUpperBound(1);
		_boolean.setType(Boolean.class);

		// null as a value
		assertEquals(IMutationChecker.VALID, gen.checkConditions(_boolean, null, IMutationChecker.ADD_MUTATION_ACTION));
		assertEquals(IMutationChecker.INVALID,
				gen.checkConditions(_boolean, null, IMutationChecker.DEL_MUTATION_ACTION));
		assertEquals(IMutationChecker.INVALID,
				gen.checkConditions(_boolean, null, IMutationChecker.REPLACE_MUTATION_ACTION));

		// valid value: false
		assertEquals(IMutationChecker.INVALID,
				gen.checkConditions(_boolean, false, IMutationChecker.ADD_MUTATION_ACTION));
		assertEquals(IMutationChecker.VALID,
				gen.checkConditions(_boolean, false, IMutationChecker.DEL_MUTATION_ACTION));
		assertEquals(IMutationChecker.VALID,
				gen.checkConditions(_boolean, false, IMutationChecker.REPLACE_MUTATION_ACTION));

		_boolean.setLowerBound(1);

		// null as a value
		assertEquals(IMutationChecker.VALID, gen.checkConditions(_boolean, null, IMutationChecker.ADD_MUTATION_ACTION));
		assertEquals(IMutationChecker.INVALID,
				gen.checkConditions(_boolean, null, IMutationChecker.DEL_MUTATION_ACTION));
		assertEquals(IMutationChecker.INVALID,
				gen.checkConditions(_boolean, null, IMutationChecker.REPLACE_MUTATION_ACTION));

		// valid value: false
		assertEquals(IMutationChecker.INVALID,
				gen.checkConditions(_boolean, false, IMutationChecker.ADD_MUTATION_ACTION));
		assertEquals(IMutationChecker.INVALID,
				gen.checkConditions(_boolean, false, IMutationChecker.DEL_MUTATION_ACTION));
		assertEquals(IMutationChecker.VALID,
				gen.checkConditions(_boolean, false, IMutationChecker.REPLACE_MUTATION_ACTION));
	}

	@Test
	public void mutation_Integer() throws Exception {
		IProperty integer = new EMFPropertyImpl(factory.createEAttribute());

		integer.setName("integer_0");
		integer.setLowerBound(0);
		integer.setUpperBound(1);
		integer.setType(Integer.class);

		// null as a value
		assertEquals(IMutationChecker.VALID, gen.checkConditions(integer, null, IMutationChecker.ADD_MUTATION_ACTION));
		assertEquals(IMutationChecker.INVALID,
				gen.checkConditions(integer, null, IMutationChecker.DEL_MUTATION_ACTION));
		assertEquals(IMutationChecker.INVALID,
				gen.checkConditions(integer, null, IMutationChecker.REPLACE_MUTATION_ACTION));

		// null as a value
		assertEquals(IMutationChecker.INVALID, gen.checkConditions(integer, 0, IMutationChecker.ADD_MUTATION_ACTION));
		assertEquals(IMutationChecker.VALID, gen.checkConditions(integer, 0, IMutationChecker.DEL_MUTATION_ACTION));
		assertEquals(IMutationChecker.VALID, gen.checkConditions(integer, 0, IMutationChecker.REPLACE_MUTATION_ACTION));

		integer.setLowerBound(1);

		// null as a value
		assertEquals(IMutationChecker.VALID, gen.checkConditions(integer, null, IMutationChecker.ADD_MUTATION_ACTION));
		assertEquals(IMutationChecker.INVALID,
				gen.checkConditions(integer, null, IMutationChecker.DEL_MUTATION_ACTION));
		assertEquals(IMutationChecker.INVALID,
				gen.checkConditions(integer, null, IMutationChecker.REPLACE_MUTATION_ACTION));

		// null as a value
		assertEquals(IMutationChecker.INVALID, gen.checkConditions(integer, 0, IMutationChecker.ADD_MUTATION_ACTION));
		assertEquals(IMutationChecker.INVALID, gen.checkConditions(integer, 0, IMutationChecker.DEL_MUTATION_ACTION));
		assertEquals(IMutationChecker.VALID, gen.checkConditions(integer, 0, IMutationChecker.REPLACE_MUTATION_ACTION));
	}

	@Test
	public void mutation_Real() throws Exception {
		IProperty real = new EMFPropertyImpl(factory.createEAttribute());
		real.setName("real_0");
		real.setLowerBound(0);
		real.setUpperBound(1);
		real.setType(Float.class);

		// null as a value
		assertEquals(IMutationChecker.VALID, gen.checkConditions(real, null, IMutationChecker.ADD_MUTATION_ACTION));
		assertEquals(IMutationChecker.INVALID, gen.checkConditions(real, null, IMutationChecker.DEL_MUTATION_ACTION));
		assertEquals(IMutationChecker.INVALID,
				gen.checkConditions(real, null, IMutationChecker.REPLACE_MUTATION_ACTION));

		// null as a value
		assertEquals(IMutationChecker.INVALID, gen.checkConditions(real, 0.0, IMutationChecker.ADD_MUTATION_ACTION));
		assertEquals(IMutationChecker.VALID, gen.checkConditions(real, 0.0, IMutationChecker.DEL_MUTATION_ACTION));
		assertEquals(IMutationChecker.VALID, gen.checkConditions(real, 0.0, IMutationChecker.REPLACE_MUTATION_ACTION));

		real.setLowerBound(1);

		// null as a value
		assertEquals(IMutationChecker.VALID, gen.checkConditions(real, null, IMutationChecker.ADD_MUTATION_ACTION));
		assertEquals(IMutationChecker.INVALID, gen.checkConditions(real, null, IMutationChecker.DEL_MUTATION_ACTION));
		assertEquals(IMutationChecker.INVALID,
				gen.checkConditions(real, null, IMutationChecker.REPLACE_MUTATION_ACTION));

		// null as a value
		assertEquals(IMutationChecker.INVALID, gen.checkConditions(real, 0.0, IMutationChecker.ADD_MUTATION_ACTION));
		assertEquals(IMutationChecker.INVALID, gen.checkConditions(real, 0.0, IMutationChecker.DEL_MUTATION_ACTION));
		assertEquals(IMutationChecker.VALID, gen.checkConditions(real, 0.0, IMutationChecker.REPLACE_MUTATION_ACTION));
	}

	@Test
	public void mutation_String() throws Exception {
		IProperty string = new EMFPropertyImpl(factory.createEAttribute());
		string.setName("string");
		string.setLowerBound(0);
		string.setType(String.class);

		// null as a value
		assertEquals(IMutationChecker.VALID, gen.checkConditions(string, null, IMutationChecker.ADD_MUTATION_ACTION));
		assertEquals(IMutationChecker.INVALID, gen.checkConditions(string, null, IMutationChecker.DEL_MUTATION_ACTION));
		assertEquals(IMutationChecker.INVALID,
				gen.checkConditions(string, null, IMutationChecker.REPLACE_MUTATION_ACTION));

		// valid value: empty string
		assertEquals(IMutationChecker.INVALID, gen.checkConditions(string, "", IMutationChecker.ADD_MUTATION_ACTION));
		assertEquals(IMutationChecker.VALID, gen.checkConditions(string, "", IMutationChecker.DEL_MUTATION_ACTION));
		assertEquals(IMutationChecker.VALID, gen.checkConditions(string, "", IMutationChecker.REPLACE_MUTATION_ACTION));

		// valid value: string
		assertEquals(IMutationChecker.INVALID,
				gen.checkConditions(string, "ABC", IMutationChecker.ADD_MUTATION_ACTION));
		assertEquals(IMutationChecker.VALID, gen.checkConditions(string, "ABC", IMutationChecker.DEL_MUTATION_ACTION));
		assertEquals(IMutationChecker.VALID,
				gen.checkConditions(string, "ABC", IMutationChecker.REPLACE_MUTATION_ACTION));

		string.setLowerBound(1);

		// null as a value
		assertEquals(IMutationChecker.VALID, gen.checkConditions(string, null, IMutationChecker.ADD_MUTATION_ACTION));
		assertEquals(IMutationChecker.INVALID, gen.checkConditions(string, null, IMutationChecker.DEL_MUTATION_ACTION));
		assertEquals(IMutationChecker.INVALID,
				gen.checkConditions(string, null, IMutationChecker.REPLACE_MUTATION_ACTION));

		// valid value: empty string
		assertEquals(IMutationChecker.INVALID, gen.checkConditions(string, "", IMutationChecker.ADD_MUTATION_ACTION));
		assertEquals(IMutationChecker.INVALID, gen.checkConditions(string, "", IMutationChecker.DEL_MUTATION_ACTION));
		assertEquals(IMutationChecker.VALID, gen.checkConditions(string, "", IMutationChecker.REPLACE_MUTATION_ACTION));

		// valid value: string
		assertEquals(IMutationChecker.INVALID,
				gen.checkConditions(string, "ABC", IMutationChecker.ADD_MUTATION_ACTION));
		assertEquals(IMutationChecker.INVALID,
				gen.checkConditions(string, "ABC", IMutationChecker.DEL_MUTATION_ACTION));
		assertEquals(IMutationChecker.VALID,
				gen.checkConditions(string, "ABC", IMutationChecker.REPLACE_MUTATION_ACTION));
	}
}
