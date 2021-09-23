package org.eclipse.epsilon.emc.mutant.emf;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.epsilon.emc.mutant.IMutant;
import org.eclipse.epsilon.emc.mutant.IProperty;
import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;
import org.eclipse.epsilon.eol.exceptions.models.EolModelElementTypeNotFoundException;

public class EMFPropertyImpl implements IProperty {

	private EStructuralFeature sf = null;

	private IMutant _model;
	private Object instance;
	private Object instanceType;

	public EMFPropertyImpl(EStructuralFeature feature) {
		sf = feature;
	}

	/**
	 * {@inheritdoc}
	 */
	@Override
	public boolean isSingleValued() {
		return !isMultiValued();
	}

	/**
	 * {@inheritdoc}
	 */
	@Override
	public boolean isMultiValued() {
		return sf.isMany();
	}

	/**
	 * {@inheritdoc}
	 */
	@Override
	public String getName() {
		return sf.getName();
	}

	/**
	 * {@inheritdoc}
	 */
	@Override
	public IMutant getModel() {
		return _model;
	}

	/**
	 * {@inheritdoc}
	 */
	@Override
	public Object getValue() {
		Object value = null;
		try {
			value = getModel().getPropertyGetter().invoke(getCurrentContainerInstance(), getName());
		} catch (EolRuntimeException e) {
			e.printStackTrace();
		}
		return value;
	}

	/**
	 * {@inheritdoc}
	 */
	@Override
	public Object getCurrentContainerInstance() {
		return instance;
	}

	/**
	 * {@inheritdoc}
	 */
	@Override
	public String getCurrentContainerTypeName() {
		String s[] = getModel().getTypeNameOf(instance).split(":");
		return s[s.length - 1];
	}

	/**
	 * {@inheritdoc}
	 */
	@Override
	public Object getCurrentContainerTypeObject() {
		return instanceType;
	}

	/**
	 * {@inheritdoc}
	 */
	@Override
	public void setContainerInstance(IMutant model, Object instance) {
		this._model = model;
		this.instance = instance;

		if (sf instanceof EAttribute) {
			instanceType = sf.getEType().getInstanceClass();
		}

		if (sf instanceof EReference) {
			EClass c = (EClass) sf.getEType();
			instanceType = c.getName();
		}
		return;
	}

	private boolean canBeAddMutationSingle(Object oldValue) {
		if (isPrimitiveType()) {
			return false;
		} else {
			// 1.2. single-valued not primitive type
			if (isDefined(oldValue))
				return false;
		}
		return true;
	}

	@SuppressWarnings("unchecked")
	private boolean canBeAddMutationMulti(Object oldValue) {
		if (isUndefined(oldValue))
			return false;
		Collection<Object> col = (Collection<Object>) oldValue;
		if (col.size() + 1 > sf.getUpperBound())
			return false;
		return true;
	}

	private boolean canBeDeleteMutationSingle(Object oldValue) {
		if (isPrimitiveType()) {
			return false;
		} else {
			// 1.2. single-valued not primitive type
			if (this.sf.isRequired())
				return false;
			if (isUndefined(oldValue))
				return false;
		}
		return true;
	}

	@SuppressWarnings("unchecked")
	private boolean canBeDeleteMutationMulti(Object oldValue) {
		if (isUndefined(oldValue))
			return false;

		Collection<Object> col = (Collection<Object>) oldValue;
		if (col.size() - 1 < sf.getLowerBound())
			return false;
		return true;
	}

	private boolean canBeReplaceMutationSingle(Object oldValue) {
		// 1.1. is primitive type
		if (isPrimitiveType()) {
			if (isUndefined(oldValue))
				return false;
		} else {
			// 1.2. single-valued not primitive type
			if (isUndefined(oldValue))
				return false;
		}
		return true;
	}

	private boolean canBeReplaceMutationMulti(Object oldValue) {
		// 2. multi-valued
		if (isUndefined(oldValue))
			return false;
		@SuppressWarnings("unchecked")
		Collection<Object> col = (Collection<Object>) oldValue;
		if (col.isEmpty())
			return false;
		return true;
	}

	private boolean canBeAddMutationSingle(Object oldValue, Object newValue) {
		if (!canBeAddMutationSingle(oldValue))
			return false;
		// 1. single-valued primitive type
		if (isPrimitiveType()) {
			return false;
		} else {
			// 1.2. single-valued not primitive type
			if (isUndefined(newValue))
				return false;
			if (!isCompatibleToProperty(newValue))
				return false;
			if (areEqual(oldValue, newValue))
				return false;
		}
		return true;
	}

	@SuppressWarnings("unchecked")
	private boolean canBeAddMutationMulti(Object oldValue, Object newValue) {
		if (!canBeAddMutationMulti(oldValue))
			return false;

		if (isUndefined(newValue))
			return false;

		Collection<Object> oldCol = (Collection<Object>) oldValue;
		Collection<Object> newCol = (Collection<Object>) newValue;
		if (oldCol.size() + 1 != newCol.size())
			return false;
		return true;
	}

	private boolean canBeDeleteMutationSingle(Object oldValue, Object newValue) {
		if (!canBeDeleteMutationSingle(oldValue))
			return false;

		if (isPrimitiveType()) {
			return false;
		} else {
			// 1.2. single-valued not primitive type
			if (isDefined(newValue))
				return false;
		}
		return true;
	}

	@SuppressWarnings("unchecked")
	private boolean canBeDeleteMutationMulti(Object oldValue, Object newValue) {
		if (!canBeDeleteMutationMulti(oldValue))
			return false;

		if (isUndefined(newValue))
			return false;
		Collection<Object> oldCol = (Collection<Object>) oldValue;
		Collection<Object> newCol = (Collection<Object>) newValue;
		if (newCol.contains(null))
			return false;
		if (newCol.size() < sf.getLowerBound())
			return false;
		if (oldCol.size() - 1 != newCol.size())
			return false;
		return true;
	}

	private boolean canBeReplaceMutationSingle(Object oldValue, Object newValue) {
		if (!canBeReplaceMutationSingle(oldValue))
			return false;

		if (isPrimitiveType()) {
			if (isUndefined(newValue))
				return false;
			if (areEqual(oldValue, newValue))
				return false;
			if (!isCompatibleToProperty(newValue))
				return false;
		} else {
			// 1.2. single-valued not primitive type
			if (isUndefined(newValue))
				return false;
			if (!isCompatibleToProperty(newValue))
				return false;
			if (areEqual(oldValue, newValue))
				return false;
		}
		return true;
	}

	private boolean canBeReplaceMutationMulti(Object oldValue, Object newValue) {
		if (!canBeReplaceMutationMulti(oldValue))
			return false;

		// 3. multi-valued
		if (isUndefined(newValue))
			return false;
		if (areEqual(oldValue, newValue))
			return false;
		return true;
	}

	private boolean isPrimitiveType() {
		if (instanceType.equals(boolean.class) || instanceType.equals(byte.class) || instanceType.equals(short.class) || instanceType.equals(int.class) || instanceType.equals(long.class) || instanceType.equals(float.class) || instanceType.equals(double.class) || instanceType.equals(char.class)) {
			return true;
		}
		return false;
	}

	/**
	 * {@inheritdoc}
	 */
	@Override
	public boolean isDefined(Object value) {
		return value != null;
	}

	/**
	 * {@inheritdoc}
	 */
	@Override
	public boolean isUndefined(Object value) {
		return !(isDefined(value));
	}

	/**
	 * {@inheritdoc}
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public boolean areEqual(final Object value1, final Object value2) {
		if (isUndefined(value1) || isUndefined(value2))
			return false;

		if (isSingleValued()) {
			if (isPrimitiveType()) {
				return value1 == value2;
			}
			if (isJavaType()) {
				return value1.equals(value2);
			}
			return value1.equals(value2);
		} else {
			boolean equal = false;
			// 1. check same types
			Class t1 = value1.getClass();
			Class t2 = value2.getClass();
			equal = equal && t1.isInstance(value2) ? true : false;
			equal = equal && t2.isInstance(value1) ? true : false;
			if (!equal)
				return equal;

			// 2. check same length
			final Collection col1 = (Collection) value1;
			final Collection col2 = (Collection) value2;
			equal = equal && (col1.size() == col2.size()) ? true : false;
			if (!equal)
				return equal;

			// 3. check each element individually
			List<Object> items = new ArrayList<Object>();
			for (Object o : col1) {
				items.add(o);
			}

			for (Object o : col2) {
				equal = equal && (items.contains(o)) ? true : false;
				if (equal)
					items.remove(o);
				else
					break;
			}
			equal = equal && items.size() == 0 ? true : false;
			return equal;
		}
	}

	/**
	 * {@inheritdoc}
	 */
	@Override
	public boolean isCompatibleToProperty(Object value) {
		if (isUndefined(value))
			return false;
		if (isSingleValued()) {
			if (sf instanceof EAttribute)
				return isCompatibleValueAttribute(value);
			if (sf instanceof EReference)
				return isCompatibleValueAssociation(value);
			return false;
		}

		if (isMultiValued()) {
			// TODO: needs implemnetation
			return true;
		}
		return true;
	}

	private boolean isCompatibleValueAttribute(Object v) {

		if (v == null)
			return false;
		// 1. Java primitive types
		if ((instanceType.equals(boolean.class) || instanceType.equals(Boolean.class)) && v instanceof Boolean)
			return true;

		if ((instanceType.equals(byte.class) || instanceType.equals(Byte.class)) && v instanceof Byte)
			return true;

		if ((instanceType.equals(short.class) || instanceType.equals(Short.class)) && v instanceof Short)
			return true;

		if ((instanceType.equals(int.class) || instanceType.equals(Integer.class)) && v instanceof Integer)
			return true;

		if ((instanceType.equals(long.class) || instanceType.equals(Long.class)) && v instanceof Long)
			return true;

		if ((instanceType.equals(float.class) || instanceType.equals(Float.class)) && v instanceof Float)
			return true;

		if ((instanceType.equals(double.class) || instanceType.equals(Double.class)) && v instanceof Double)
			return true;

		if ((instanceType.equals(char.class) || instanceType.equals(Character.class)) && v instanceof Character)
			return true;

		if ((instanceType.equals(String.class)) && v instanceof String)
			return true;

		if ((instanceType.equals(Date.class)) && v instanceof Date)
			return true;

		if ((instanceType.equals(BigInteger.class)) && v instanceof BigInteger)
			return true;

		if ((instanceType.equals(BigDecimal.class)) && v instanceof BigDecimal)
			return true;

		// 2. Java object types
		return isCompatibleValueAssociation(v);
	}

	private boolean isCompatibleValueAssociation(Object v) {
		try {
			return getModel().isOfKind(v, (String) instanceType);
		} catch (EolModelElementTypeNotFoundException e) {
			return false;
		}
	}

	private boolean isJavaType() {
		if (instanceType instanceof Number || instanceType instanceof String || instanceType instanceof Boolean || instanceType instanceof Character)
			return true;
		return false;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean checkSingleAMOConditions(Object oldValue, Object newValue, String action) {
		switch (action) {
		case "byAdd":
			return canBeAddMutationSingle(oldValue, newValue);
		case "byDelete":
			return canBeDeleteMutationSingle(oldValue, newValue);
		case "byReplace":
			return canBeReplaceMutationSingle(oldValue, newValue);
		}
		return false;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean checkMultiAMOConditions(Object oldValue, Object newValue, String action) {
		switch (action) {
		case "byAdd":
			return canBeAddMutationMulti(oldValue, newValue);
		case "byDelete":
			return canBeDeleteMutationMulti(oldValue, newValue);
		case "byReplace":
			return canBeReplaceMutationMulti(oldValue, newValue);
		}
		return false;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getMulitiplicityLowerLimit() {
		return sf.getLowerBound();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getMulitiplicityUpperLimit() {
		return sf.getUpperBound();
	}
}
