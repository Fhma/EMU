package org.eclipse.epsilon.emc.mutant.emf;

import org.eclipse.emf.common.util.URI;
import org.eclipse.epsilon.emc.emf.AbstractEmfModel;

public abstract class AbstractEmfMutant extends AbstractEmfModel {

	@Override
	public boolean store(URI uri) {
		modelImpl.setURI(uri);
		return store();
	}
}
