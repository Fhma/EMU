/*******************************************************************************
 * Copyright (c) 2009 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Louis Rose - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.flock.dt.wizards;

import org.eclipse.epsilon.common.dt.wizards.AbstractNewFileWizard2;

public class NewFlockMigrationStrategyFileWizard extends AbstractNewFileWizard2 {

	@Override
	public String getTitle() {
		return "New Epsilon Flock Migration Strategy";
	}

	@Override
	public String getExtension() {
		return "mig";
	}

	@Override
	public String getDescription() {
		return "This wizard creates a new Epsilon Flock Migration Strategy file with *.mig extension";
	}
}