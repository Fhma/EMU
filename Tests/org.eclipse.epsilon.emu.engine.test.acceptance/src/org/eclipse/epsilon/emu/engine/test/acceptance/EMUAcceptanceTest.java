package org.eclipse.epsilon.emu.engine.test.acceptance;

import junit.framework.Test;

import org.eclipse.epsilon.emu.engine.test.unit.execution.*;
import org.eclipse.epsilon.emu.engine.test.unit.validation.ImplementationTest;
import org.eclipse.epsilon.emu.engine.test.unit.validation.ValidationBoolean;
import org.eclipse.epsilon.emu.engine.test.unit.validation.ValidationByte;
import org.eclipse.epsilon.emu.engine.test.unit.validation.ValidationDouble;
import org.eclipse.epsilon.emu.engine.test.unit.validation.ValidationFloat;
import org.eclipse.epsilon.emu.engine.test.unit.validation.ValidationInt;
import org.eclipse.epsilon.emu.engine.test.unit.validation.ValidationLong;
import org.eclipse.epsilon.emu.engine.test.unit.validation.ValidationMultiTests;
import org.eclipse.epsilon.emu.engine.test.unit.validation.ValidationShort;
import org.eclipse.epsilon.emu.engine.test.unit.validation.ValidationSingleReferenceTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
import junit.framework.JUnit4TestAdapter;

/**
 * @author Faisal Alhwikem
 * @version April 2017
 */
@RunWith(Suite.class)
@SuiteClasses({ EMUExecutionTests.class,
	ImplementationTest.class,
	ValidationBoolean.class,
	ValidationByte.class,
	ValidationDouble.class,
	ValidationFloat.class,
	ValidationInt.class,
	ValidationLong.class,
	ValidationMultiTests.class,
	ValidationShort.class,
	ValidationSingleReferenceTest.class })

public class EMUAcceptanceTest {
	public static Test suite() {
		return new JUnit4TestAdapter(EMUAcceptanceTest.class);
	}
}
