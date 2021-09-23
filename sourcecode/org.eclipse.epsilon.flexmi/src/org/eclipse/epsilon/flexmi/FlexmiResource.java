/*********************************************************************
* Copyright (c) 2008 The University of York.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/
package org.eclipse.epsilon.flexmi;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

import org.apache.commons.lang3.text.StrLookup;
import org.apache.commons.lang3.text.StrSubstitutor;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.ENamedElement;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceImpl;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.eclipse.epsilon.flexmi.templates.Template;
import org.eclipse.epsilon.flexmi.xml.Location;
import org.eclipse.epsilon.flexmi.xml.PseudoSAXParser;
import org.eclipse.epsilon.flexmi.xml.PseudoSAXParser.Handler;
import org.eclipse.epsilon.flexmi.xml.Xml;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.ProcessingInstruction;
import org.w3c.dom.Text;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;

public class FlexmiResource extends ResourceImpl implements Handler {
	
	public static final String ROOT_NODE_NAME = "_";
	
	protected EObjectTraceManager eObjectTraceManager = new EObjectTraceManager();
	protected List<UnresolvedReference> unresolvedReferences = new ArrayList<UnresolvedReference>();
	protected Stack<Object> objectStack = new Stack<Object>();
	protected Node currentNode = null;
	protected List<String> scripts = new ArrayList<String>();
	protected HashMap<String, EClass> eClassCache = new HashMap<String, EClass>();
	protected HashMap<EClass, List<EClass>> allSubtypesCache = new HashMap<EClass, List<EClass>>();
	protected StringSimilarityProvider stringSimilarityProvider = new CachedStringSimilarityProvider(new DefaultStringSimilarityProvider());
	protected Stack<URI> parsedFragmentURIStack = new Stack<URI>();
	protected Set<URI> parsedFragmentURIs = new HashSet<URI>();
	protected List<Template> templates = new ArrayList<Template>();
	protected BiMap<String, EObject> fullyQualifiedIDs = HashBiMap.create();
	protected FrameStack frameStack = new FrameStack();
	protected StrSubstitutor substitutor = new StrSubstitutor(new StrLookup<String>() {
		@Override
		public String lookup(String name) {
			return fullyQualifiedIDs.inverse().get(frameStack.getVariable(name));
		}
	});
	
	public void startProcessingFragment(URI uri) {
		parsedFragmentURIStack.push(uri);
		parsedFragmentURIs.add(uri);
	}
	
	public void endProcessingFragment() {
		parsedFragmentURIStack.pop();
	}
	
	public Set<URI> getParsedFragmentURIs() {
		return parsedFragmentURIs;
	}
	
	public List<Template> getTemplates() {
		return templates;
	}
	
	public Template getTemplate(String name) {
		for (Template template : templates) {
			if (template.getName().equals(name)) {
				return template;
			}
		}
		return null;
	}
	
	public static void main(String[] args) throws Exception {
		
		// Load the metamodel
		ResourceSet metamodelResourceSet = new ResourceSetImpl();
		metamodelResourceSet.getPackageRegistry().put(EcorePackage.eINSTANCE.getNsURI(), EcorePackage.eINSTANCE);
		metamodelResourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put("*", new XMIResourceFactoryImpl());
		Resource metamodelResource = metamodelResourceSet.createResource(URI.createFileURI(new File("models/messaging.ecore").getAbsolutePath()));
		metamodelResource.load(null);
		
		EPackage metamodel = (EPackage) metamodelResource.getContents().get(0);
		
		// Load the model
		ResourceSet modelResourceSet = new ResourceSetImpl();
		modelResourceSet.getPackageRegistry().put(metamodel.getNsURI(), metamodel);
		modelResourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put("*", new FlexmiResourceFactory());
		Resource modelResource = modelResourceSet.createResource(URI.createFileURI(new File("models/messaging.flexmi").getAbsolutePath()));
		modelResource.load(null);
		
		System.out.println(modelResource.getEObject("tom"));
		
	}
	
	public FlexmiResource(URI uri) {
		super(uri);
	}
	
	@Override
	protected void doLoad(InputStream inputStream, Map<?, ?> options)
			throws IOException {
		try {
			doLoadImpl(inputStream, options);
		}
		catch (IOException ioException) {
			throw ioException;
		}
		catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}
	
	protected void setEObjectId(EObject eObject, String id) {
		getIntrinsicIDToEObjectMap().put(id, eObject);
		EObject containerWithId = eObject.eContainer();
		while (containerWithId != null && !fullyQualifiedIDs.containsValue(containerWithId)) {
			containerWithId = containerWithId.eContainer();
		}
		if (containerWithId != null) {
			fullyQualifiedIDs.put(fullyQualifiedIDs.inverse().get(containerWithId) + "." + id, eObject);
		}
		else {
			fullyQualifiedIDs.put(id, eObject);
		}
	}
	
	public void doLoadImpl(InputStream inputStream, Map<?, ?> options) throws Exception {
		getContents().clear();
		unresolvedReferences.clear();
		objectStack.clear();
		scripts.clear();
		eClassCache.clear();
		allSubtypesCache.clear();
		setIntrinsicIDToEObjectMap(new HashMap<String, EObject>());
		
		new PseudoSAXParser().parse(this, inputStream, this);
	}
	
	@Override
	public EObject getEObject(String uriFragment) {
		EObject eObject = super.getEObject(uriFragment);
		if (eObject == null && uriFragment.indexOf(".") > -1) {
			return fullyQualifiedIDs.get(uriFragment);
		}
		return eObject;
	}
	
	@Override
	public void startDocument(Document document) {}

	@SuppressWarnings("unchecked")
	@Override
	public void startElement(Element element) {
		frameStack.pushFrame();
		currentNode = element;
		String name = element.getNodeName();
		
		//Remove prefixes
		//TODO: Add option to disable this
		if (name.indexOf(":") > -1) {
			name = name.substring(name.indexOf(":")+1);
		}
		
		
		
		//Replace variables in attributes
		for (Node attribute : Xml.getAttributes(element)) {
			attribute.setNodeValue(substitutor.replace(attribute.getNodeValue()));
		}
		
		EObject eObject = null;
		EClass eClass = null;
		
		// We're at the root
		if (objectStack.isEmpty()) {
			eClass = eClassForName(name);
			if (eClass != null) {
				eObject = eClass.getEPackage().getEFactoryInstance().create(eClass);
				getContents().add(eObject);
				setAttributes(eObject, element);
			}
			else {
				addParseWarning("Could not map element " + name + " to an EObject");
			}
			objectStack.push(eObject);
		}
		else {
			Object peek = objectStack.peek();
			
			// We find an orphan element
			if (peek == null) {
				objectStack.push(null);
				addParseWarning("Could not map element " + name + " to an EObject");
				return;
			}
			// The parent is an already-established containment slot
			else if (peek instanceof EReferenceSlot) {
				EReferenceSlot containmentSlot = (EReferenceSlot) peek;
				eClass = (EClass) eNamedElementForName(name, getAllSubtypes(containmentSlot.getEReference().getEReferenceType()));
				
				if (eClass != null) {
					eObject = eClass.getEPackage().getEFactoryInstance().create(eClass);
					containmentSlot.newValue(eObject);
					objectStack.push(eObject);
					setAttributes(eObject, element);
				}
				else {
					objectStack.push(null);
					addParseWarning("Could not map element " + name + " to an EObject");
				}
			}
			// The parent is an EObject
			else if (peek instanceof EObject) {
				EObject parent = (EObject) peek;
				
				if (element.getAttributes().getLength() == 0 && element.getChildNodes().getLength() == 1 && element.getFirstChild() instanceof Text) {
					EAttribute eAttribute = (EAttribute) eNamedElementForName(name, parent.eClass().getEAllAttributes());
					
					if (eAttribute != null) {
						setEAttributeValue(parent, eAttribute, name, element.getTextContent().trim());
						eObjectTraceManager.trace(parent, getCurrentURI(), getLineNumber(element));
						objectStack.push(null);
						return;
					}
				}
				
				EReference containment = null;
				
				// No attributes -> Check whether there is a containment reference with that name
				if (element.getAttributes().getLength() == 0) {
					containment = (EReference) eNamedElementForName(name, parent.eClass().getEAllContainments());
					
					if (containment != null) {
						EReferenceSlot containmentSlot = new EReferenceSlot(containment, parent);
						eObjectTraceManager.trace(parent, getCurrentURI(), getLineNumber(element));
						objectStack.push(containmentSlot);
						return;
					}
				}
				
				// Check for the best class or containment reference
				Set<ENamedElement> candidates = new HashSet<ENamedElement>();
				for (EReference eReference : parent.eClass().getEAllContainments()) {
					candidates.addAll(getAllSubtypes(eReference.getEReferenceType()));				
				}
				
				candidates.addAll(parent.eClass().getEAllContainments());
				
				// Search for the best match between containment refs and class names
				ENamedElement topCandidate = eNamedElementForName(name, candidates);
				
				if (topCandidate instanceof EClass) {
					// Get the best match and an appropriate containment reference
					eClass = (EClass) topCandidate;
					if (eClass != null) {
						Set<EReference> candidateEReferences = new HashSet<EReference>();
						for (EReference eReference : parent.eClass().getEAllContainments()) {
							if (getAllSubtypes(eReference.getEReferenceType()).contains(eClass)) {
								candidateEReferences.add(eReference);
							}
						}
						containment = (EReference) eNamedElementForName(name, candidateEReferences);
					}
				}
				else {
					containment = (EReference) topCandidate;
					if (containment != null) {
						eClass = (EClass) eNamedElementForName(name, getAllSubtypes(containment.getEReferenceType()));
					}
				}
				
				// Found an appropriate containment reference
				if (containment != null && eClass != null) {
					eObject = eClass.getEPackage().getEFactoryInstance().create(eClass);
					if (containment.isMany()) {
						((List<EObject>) parent.eGet(containment)).add(eObject);
					}
					else {
						parent.eSet(containment, eObject);
					}
					setAttributes(eObject, element);
					objectStack.push(eObject);
				}
				// No luck - add warning
				else {
					objectStack.push(null);
					addParseWarning("Could not map element " + name + " to an EObject");
				}
			}
		}
	}

	@Override
	public void endElement(Element element) {
		frameStack.pop();
		Object object = objectStack.pop();
		if (object != null && object instanceof EObject) {
			EObject eObject = (EObject) object;
			eObjectTraceManager.trace(eObject, getCurrentURI(), getLineNumber(element));
		}
	}

	@Override
	public void processingInstruction(ProcessingInstruction processingInstruction) {
		currentNode = processingInstruction;
		
		String key = processingInstruction.getTarget();
		String value = processingInstruction.getData();
		
		if ("nsuri".equalsIgnoreCase(key)) {
			EPackage ePackage = EPackage.Registry.INSTANCE.getEPackage(value);
			if (ePackage != null) getResourceSet().getPackageRegistry().put(ePackage.getNsURI(), ePackage);
			else addParseWarning("Failed to locate EPackage for nsURI " + value + " ");
		}
		else if ("eol".equalsIgnoreCase(key)) {
			scripts.add(value);
		}
		else {
			addParseWarning("Unknown processing instruction: " + key);
		}
	}

	@Override
	public void endDocument(Document document) {
		resolveReferences();
	}
	
	public List<UnresolvedReference> getUnresolvedReferences() {
		return unresolvedReferences;
	}
	
	protected void addParseWarning(String message) {
		addParseWarning(message, getLineNumber(currentNode));
	}
	
	protected void addParseWarning(String message, int line) {
		addParseWarning(message, getCurrentURI(), line);
	}
	
	protected void addParseWarning(String message, URI uri, int line) {
		getWarnings().add(new FlexmiDiagnostic(message, uri, line));
	}
	
	protected void resolveReferences() {
		List<UnresolvedReference> unresolvableReferences = new ArrayList<UnresolvedReference>();
		
		for (UnresolvedReference unresolvedReference : unresolvedReferences) {
			EReference eReference = unresolvedReference.getEReference();
			if (eReference.isMany()) {
				
				if ("*".equals(unresolvedReference.getValue())) {
					Iterator<EObject> it = this.getAllContents();
					while (it.hasNext()) {
						EObject candidate = it.next();
						if (eReference.getEReferenceType().isInstance(candidate)) {
							new EReferenceSlot(eReference, unresolvedReference.getEObject()).newValue(candidate);
						}
					}
				}
				else {
					if (!resolveReference(unresolvedReference)) unresolvableReferences.add(unresolvedReference);
				}
			}
			else {
				if (!resolveReference(unresolvedReference)) unresolvableReferences.add(unresolvedReference);
			}
		}
		
		for (UnresolvedReference reference : unresolvableReferences) {
			addParseWarning("Could not resolve target " + reference.getValue() + " for reference " + reference.getAttributeName() + " (" + reference.getEReference().getName() + ")", reference.getUri(), reference.getLine());
		}
	}
	
	protected boolean resolveReference(UnresolvedReference unresolvedReference) {
		EObject candidate = getEObject(unresolvedReference.getValue());
		if (!unresolvedReference.resolve(candidate)) {
			for (Resource resource : getResourceSet().getResources()) {
				if (resource != this) {
					candidate = resource.getEObject(unresolvedReference.getValue());
					if (unresolvedReference.resolve(candidate)) return true;
				}
			}
			return false;
		}
		return true;
	}
	
	public int getLineNumber(Node node) {
		Location location = (Location) node.getUserData(Location.ID);
		if (location != null) {
			return location.getStartLine();
		}
		return 0;
	}
	
	
	protected void setAttributes(EObject eObject, Element element) {
		
		NamedNodeMap attributes = element.getAttributes();
		List<EStructuralFeature> eStructuralFeatures = getCandidateStructuralFeaturesForAttribute(eObject.eClass());
		
		if (attributes.getLength() == 0 || eStructuralFeatures.size() == 0) return;
		
		// Find the _var attribute, create a variable and remove it from the node
		Node varAttribute = attributes.getNamedItem("_var");
		if (varAttribute != null) {
			frameStack.setVariable(varAttribute.getNodeValue(), eObject);
			attributes.removeNamedItem("_var");
		}
		/*
		if (!(eObject.eClass().getEStructuralFeature("id") instanceof EAttribute)) {
			if (attributes.getNamedItem("id") != null) {
				String value = attributes.getNamedItem("id").getNodeValue();
				attributes.removeNamedItem("id");
				setEObjectId(eObject, value);
			}
		}*/
		
		Map<Node, EStructuralFeature> allocation = new AttributeStructuralFeatureAllocator().allocate(attributes, eStructuralFeatures);
		
		for (Node attribute : allocation.keySet()) {
			String name = attribute.getNodeName();
			String value = attribute.getNodeValue();
			
			EStructuralFeature sf = allocation.get(attribute);
			
			if (sf instanceof EAttribute) {
				setEAttributeValue(eObject, (EAttribute) sf, name, value);
			}
			else if (sf instanceof EReference) {
				EReference eReference = (EReference) sf;
				if (eReference.isMany()) {
					for (String valuePart : value.split(",")) {
						unresolvedReferences.add(new UnresolvedReference(eObject, getCurrentURI(), eReference, name, valuePart.trim(), getLineNumber(element)));
					}
				}
				else {
					unresolvedReferences.add(new UnresolvedReference(eObject, getCurrentURI(), eReference, name, value, getLineNumber(element)));
				}
			}
		}
		
	}
	
	public URI getCurrentURI() {
		return parsedFragmentURIStack.isEmpty() ? this.getURI() : parsedFragmentURIStack.peek();
	}
	
	@SuppressWarnings("unchecked")
	protected void setEAttributeValue(EObject eObject, EAttribute eAttribute, String attributeName, String value) {
		if (eAttribute.isMany()) {
			for (String valuePart : value.split(",")) {
				Object eValue = getEValue(eAttribute, attributeName, valuePart.trim());
				if (eValue == null) continue;
				((List<Object>) eObject.eGet(eAttribute)).add(eValue);
			}
		}
		else {
			Object eValue = getEValue(eAttribute, attributeName, value);
			if (eValue == null) return;
			eObject.eSet(eAttribute, eValue);
			EAttribute idAttribute = eObject.eClass().getEIDAttribute();
			if (eAttribute == idAttribute || (idAttribute == null && eAttribute.getName().equalsIgnoreCase("name"))) {
				setEObjectId(eObject, value);
			}
		}
	}
	
	protected Object getEValue(EAttribute eAttribute, String attributeName, String value) {
		try {
			return eAttribute.getEAttributeType().getEPackage().getEFactoryInstance().createFromString(eAttribute.getEAttributeType(), value);
		}
		catch (Exception ex) {
			addParseWarning(ex.getMessage() + " in the value of " + attributeName);
			return null;
		}
	}
	
	protected List<EStructuralFeature> getCandidateStructuralFeaturesForAttribute(EClass eClass) {
		List<EStructuralFeature> eStructuralFeatures = new ArrayList<EStructuralFeature>();
		for (EStructuralFeature sf : eClass.getEAllStructuralFeatures()) {
			if (sf.isChangeable() && (sf instanceof EAttribute || ((sf instanceof EReference) && !((EReference) sf).isContainment()))) {
				eStructuralFeatures.add(sf);
			}
		}
		return eStructuralFeatures;
	}
	
	protected List<EClass> getAllConcreteEClasses() {
		List<EClass> eClasses = new ArrayList<EClass>();
		Iterator<Object> it = getResourceSet().getPackageRegistry().values().iterator();
		while (it.hasNext()) {
			EPackage ePackage = (EPackage) it.next();
			for (EClassifier eClassifier : ePackage.getEClassifiers()) {
				if (eClassifier instanceof EClass && !((EClass) eClassifier).isAbstract()) {
					eClasses.add((EClass) eClassifier);
				}
			}
		}
		return eClasses;
	}
	
	protected List<EClass> getAllSubtypes(EClass eClass) {
		List<EClass> allSubtypes = allSubtypesCache.get(eClass);
		if (allSubtypes == null) {
			allSubtypes = new ArrayList<EClass>();
			for (EClass candidate : getAllConcreteEClasses()) {
				if (candidate.getEAllSuperTypes().contains(eClass)) {
					allSubtypes.add(candidate);
				}
			}
			if (!eClass.isAbstract()) allSubtypes.add(eClass);
			allSubtypesCache.put(eClass, allSubtypes);
		}
		return allSubtypes;
	}
	
	protected EClass eClassForName(String name) {
		EClass eClass = eClassCache.get(name);
		if (eClass == null) {
			eClass = (EClass) eNamedElementForName(name, getAllConcreteEClasses());
			eClassCache.put(name, eClass);
		}
		return eClass;
		
	}
	
	protected ENamedElement eNamedElementForName(String name, Collection<? extends ENamedElement> candidates) {
		ENamedElement eNamedElement = eNamedElementForName(name, candidates, false);
		if (eNamedElement == null) eNamedElement = eNamedElementForName(name, candidates, true);
		return eNamedElement;
	}
	
	public EObjectTraceManager getEObjectTraceManager() {
		return eObjectTraceManager;
	}
	
	protected ENamedElement eNamedElementForName(String name, Collection<? extends ENamedElement> candidates, boolean fuzzy) {
		
		if (fuzzy) {
			int maxSimilarity = 0;
			ENamedElement bestMatch = null;
			for (ENamedElement candidate : candidates) {
				int similarity = stringSimilarityProvider.getSimilarity(candidate.getName().toLowerCase(), name.toLowerCase());
				if (similarity > maxSimilarity) {
					maxSimilarity = similarity;
					bestMatch = candidate;
				}
			}
			
			if (maxSimilarity == 0 && candidates.size() == 1) {
				return candidates.iterator().next();
			}
			
			return bestMatch;			
		}
		else {
			for (ENamedElement candidate : candidates) {
				if (candidate.getName().equalsIgnoreCase(name)) return candidate;
			}
		}
		
		return null;
	}
	
	protected boolean isTemplateElement(Element element) {
		return objectStack.isEmpty() && Template.NODE_NAME.equals(element.getNodeName());
	}
	
}
