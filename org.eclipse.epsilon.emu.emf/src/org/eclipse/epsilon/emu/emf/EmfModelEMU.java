package org.eclipse.epsilon.emu.emf;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.xmi.XMLResource;
import org.eclipse.epsilon.common.util.StringProperties;
import org.eclipse.epsilon.emc.emf.EmfModel;
import org.eclipse.epsilon.eol.exceptions.models.EolModelElementTypeNotFoundException;
import org.eclipse.epsilon.eol.exceptions.models.EolModelLoadingException;
import org.eclipse.epsilon.eol.exceptions.models.EolNotInstantiableModelElementTypeException;

public class EmfModelEMU extends EmfModel {
	
	public EmfModelEMU() {
		super();
	}
	
	@Override
	public boolean store() {
		if (modelImpl == null) return false;
		try {
			Map<String, Boolean> options = null;
			if (!metamodelFileUris.isEmpty()) {
				options = new HashMap<String, Boolean>();
				options.put(XMLResource.OPTION_SCHEMA_LOCATION, true);
			}
			modelImpl.save(options);
			return true;
		} catch (IOException e) {
			//e.printStackTrace();
			return false;
		}
	}
	
	@Override
	public void load(StringProperties properties) throws EolModelLoadingException {
		// TODO Auto-generated method stub
		super.load(properties);
	}
	
	@Override
	public EObject createInstance(String type)
			throws EolModelElementTypeNotFoundException, EolNotInstantiableModelElementTypeException {
		// TODO Auto-generated method stub
		return super.createInstance(type);
	}
	
	@Override
	public Object createInstance(String type, Collection<Object> parameters)
			throws EolModelElementTypeNotFoundException, EolNotInstantiableModelElementTypeException {
		// TODO Auto-generated method stub
		return super.createInstance(type, parameters);
	}
	
	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		super.dispose();
	}

}
