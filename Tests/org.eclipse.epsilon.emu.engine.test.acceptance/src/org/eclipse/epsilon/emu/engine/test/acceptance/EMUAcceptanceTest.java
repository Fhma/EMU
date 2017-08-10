package org.eclipse.epsilon.emu.engine.test.acceptance;

import junit.framework.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
import junit.framework.JUnit4TestAdapter;
import org.eclipse.epsilon.emu.engine.test.unit.*;

/**
 * @author Faisal Alhwikem
 * @version April 2017
 */
@RunWith(Suite.class)
@SuiteClasses({EMUExecutionTests.class, MutationTests.class, ValidationTests.class})
public class EMUAcceptanceTest {
	public static Test suite() {
		return new JUnit4TestAdapter(EMUAcceptanceTest.class);
	}
}
