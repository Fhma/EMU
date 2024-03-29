/*******************************************************************************
 * Copyright (c) 2008 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * Contributors:
 *     Dimitrios Kolovos - initial API and implementation
******************************************************************************/

package org.eclipse.epsilon.workflow.tasks.emf;

import org.apache.tools.ant.BuildException;
import org.eclipse.epsilon.common.util.StringProperties;
import org.eclipse.epsilon.emc.emf.EmfMetaModel;
import org.eclipse.epsilon.emc.emf.EmfModel;
import org.eclipse.epsilon.eol.exceptions.models.EolModelLoadingException;
import org.eclipse.epsilon.workflow.tasks.EpsilonTask;
import org.eclipse.epsilon.workflow.tasks.ShutdownProjectRepositoryListener;

public class LoadRegisteredMetaModelTask extends EpsilonTask {

	protected String name;
	protected String aliases;
	protected String metamodelUri;
	
	@Override
	public void executeImpl() throws BuildException {
		
		EmfMetaModel model = new EmfMetaModel();
		
		StringProperties properties = new StringProperties();
		properties.put(EmfModel.PROPERTY_NAME, name + "");
		properties.put(EmfModel.PROPERTY_ALIASES, aliases + "");
		properties.put(EmfModel.PROPERTY_METAMODEL_URI, metamodelUri + "");
		
		try {
			model.load(properties);
			getProjectRepository().addModel(model);
		} catch (EolModelLoadingException e) {
			e.printStackTrace();
			throw new BuildException(e);
		}
		
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAliases() {
		return aliases;
	}

	public void setAliases(String aliases) {
		this.aliases = aliases;
	}

	public String getMetamodelUri() {
		return metamodelUri;
	}

	public void setMetamodelUri(String metamodelUri) {
		this.metamodelUri = metamodelUri;
	}
	
}
 
