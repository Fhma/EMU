/*******************************************************************************
 * Copyright (c) 2012 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Dimitrios Kolovos - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.eol.execute.introspection.java;

import java.lang.reflect.Method;

import org.eclipse.epsilon.common.module.ModuleElement;
import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;
import org.eclipse.epsilon.eol.util.ReflectionUtil;

public class ObjectMethod {
	
	protected Object object;
	protected Method method;
	
	public ObjectMethod() {}
	
	public ObjectMethod(Object object, Method method) {
		super();
		this.object = object;
		this.method = method;
	}
	
	public Object getObject() {
		return object;
	}
	
	public void setObject(Object object) {
		this.object = object;
	}
	
	public Method getMethod() {
		return method;
	}
	
	public void setMethod(Method method) {
		this.method = method;
	}
	
	public Object execute(Object[] parameters, ModuleElement ast) throws EolRuntimeException {
		return ReflectionUtil.executeMethod(object, method, parameters, ast);
	}
	
}
