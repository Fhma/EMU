/*******************************************************************************
 * Copyright (c) 2012 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Dimitrios Kolovos - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.egl.execute.context;

import java.net.URI;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.eclipse.epsilon.egl.EgxModule;
import org.eclipse.epsilon.egl.traceability.Content;
import org.eclipse.epsilon.egl.traceability.Template;
import org.eclipse.epsilon.egl.traceability.Variable;

public class EgxModuleTemplateAdapter extends Template {
		
		protected EgxModule module = null;
		
		public EgxModuleTemplateAdapter(EgxModule module) {
			this.module = module;
		}
	
		@Override
		public String getName() {
			return module.getFile().getAbsolutePath();
		}
		
		@Override
		public URI getURI() {
			return module.getUri();
		}
		
		@Override
		public Collection<Variable> getVariables() {
			return Collections.emptyList();
		}
		
		@Override
		public List<Content<Template>> getChildren() {
			return ((EgxModule) module).getInvokedTemplates();
		}
		
		@Override
		public boolean hasChildren() {
			return !getChildren().isEmpty();
		}
		
		public String toString() {
			return this.getClass().getName();
		}
		
	}
