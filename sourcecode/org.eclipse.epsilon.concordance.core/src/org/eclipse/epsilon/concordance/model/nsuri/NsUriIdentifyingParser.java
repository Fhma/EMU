/*******************************************************************************
 * Copyright (c) 2009 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Louis Rose - initial API and implementation
 ******************************************************************************
 *
 * $Id$
 */
package org.eclipse.epsilon.concordance.model.nsuri;

import java.io.IOException;
import java.io.StringReader;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

public class NsUriIdentifyingParser {

	private final String xmi;
	
	private XMLReader reader;
	private NsUriIdentifyingContentHandler handler;
	
	
	public NsUriIdentifyingParser(String xmi) {
		this.xmi = xmi;
	}

	public String parse() throws SAXException, IOException {		
		try {
			initialiseXmlReader();
			
			doParse();
			
			return handler.getNsUri();
		
		} catch (SAXParseException e) {
			return null;
		}		
	}
	
	private void initialiseXmlReader() throws SAXException {
		reader  = XMLReaderFactory.createXMLReader();
		handler = new NsUriIdentifyingContentHandler();
		
		reader.setContentHandler(handler);
	}
	
	private void doParse() throws IOException, SAXException {
		reader.parse(new InputSource(new StringReader(xmi)));
	}
}
