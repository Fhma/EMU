/*******************************************************************************
 * Copyright (c) 2008 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * Contributors:
 *     Dimitrios Kolovos - initial API and implementation
******************************************************************************/

package org.eclipse.epsilon.egl.dom;

import org.eclipse.epsilon.egl.execute.context.IEglContext;
import org.eclipse.epsilon.egl.internal.EglPreprocessorContext;
import org.eclipse.epsilon.egl.output.IOutputBuffer;
import org.eclipse.epsilon.egl.output.OutputBuffer;
import org.eclipse.epsilon.eol.dom.Operation;
import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;
import org.eclipse.epsilon.eol.execute.Return;
import org.eclipse.epsilon.eol.execute.context.IEolContext;
import org.eclipse.epsilon.eol.execute.context.Variable;

public class TemplateOperation extends Operation {

	@Override
	protected Object executeBody(IEolContext context) throws EolRuntimeException {
		final IEglContext parentContext = ((EglPreprocessorContext)context).getEglContext();
		final IOutputBuffer out = new OutputBuffer(parentContext);
		context.getFrameStack().put(Variable.createReadOnlyVariable("out", out));
		super.executeBody(context);
		return new Return(out.toString());
	}
	
}
 
