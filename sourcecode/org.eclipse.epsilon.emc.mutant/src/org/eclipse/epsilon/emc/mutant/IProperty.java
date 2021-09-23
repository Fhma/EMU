package org.eclipse.epsilon.emc.mutant;

public interface IProperty {

	/**
	 * Returns the name of this <code>IProperty</code>.
	 * 
	 * @return name of target property
	 */
	public String getName();

	/**
	 * Returns the <code>IMutant</code> model that contains this <code>IProperty</code> instance object.
	 * 
	 * @return the <code>IMutant</code> model
	 */
	public IMutant getModel();

	/**
	 * Returns the value of this <code>IProperty</code> obtained from the containing <code>IMutant</code> model
	 * <code>getModel()</code>. It requires an instance of a containing object by calling method
	 * <code>getCurrentContainerInstance()</code> to return a valid value.
	 * 
	 * @return return a valid object value; or <code>null</code> if the containing model or the instance object are not
	 *         defined.
	 */
	public Object getValue();

	/**
	 * Returns the current instance of this <code>IProperty</code>.
	 * 
	 * @return an instance model object
	 */
	public Object getCurrentContainerInstance();

	/**
	 * Returns the type name of the instance object <code>getCurrentContainerInstance()</code>.
	 * 
	 * @return the name of model object type's name
	 */
	public String getCurrentContainerTypeName();

	/**
	 * Returns the type of the instance object <code>getCurrentContainerInstance()</code>.
	 * 
	 * @return the type of the model object instance
	 */
	public Object getCurrentContainerTypeObject();

	/**
	 * Checks if the <code>IProperty</code> instance is a single-valued or not.
	 * 
	 * @return true if singled-valued property; or false otherwise
	 */
	public boolean isSingleValued();

	/**
	 * Checks if the <code>IProperty</code> instance is a multi-valued or not.
	 * 
	 * @return true if multi-valued valued property; or false otherwise
	 */
	public boolean isMultiValued();

	/**
	 * Initialize and update this <code>IProperty</code> internal instance member values, namely instance model object and
	 * <code>IMutant</code> model.
	 * 
	 */
	public void setContainerInstance(IMutant model, Object instance);

	/**
	 * Checks the coniditons related to single-valued properties. This is called to check conditions/constraints with the
	 * property value before approving the execution of the mutation block.
	 * <p>
	 * This method is a modeling-technology dependent. That means the users of the method must implement the conditions to
	 * which the mutation makes a valid mutant according to their definitions of valid and tailored to the target modeling
	 * technology.
	 * 
	 * @param oldValue
	 * @param newValue
	 * @param action
	 * @return
	 */
	public boolean checkSingleAMOConditions(Object oldValue, Object newValue, String action);

	/**
	 * Checks the coniditons related to multi-valued properties. This is called to check conditions/constraints with the
	 * property value before approving the execution of the mutation block.
	 * <p>
	 * This method is a modeling-technology dependent. That means the users of the method must implement the conditions to
	 * which the mutation makes a valid mutant according to their definitions of valid and tailored to the target modeling
	 * technology.
	 * 
	 * @param oldValue
	 * @param newValue
	 * @param action
	 * @return
	 */
	public boolean checkMultiAMOConditions(Object oldValue, Object newValue, String action);

	/**
	 * A constraint method that checks if the given value of property is defined or valid. This method is a
	 * modeling-technology dependent. That means the users of the method must implement the "definition of a value" that
	 * also tailored to the target modeling technology.
	 * 
	 * @param value
	 * @return true if the given value is defined/valid.
	 */
	public boolean isDefined(Object value);

	/**
	 * A constraint method that checks if the given value of property is undefined or invalid. See method
	 * <code>isDefined()</code>.
	 * 
	 * @param value
	 * @return true if the given value is undefined/invalid.
	 */
	public boolean isUndefined(Object value);

	/**
	 * A constraint method that checks if the given two values are equal. This method is a modeling-technology dependent and
	 * user specification.. That means the users of the method must implement the "equality definition" that also tailored
	 * to the target modeling technology.
	 * 
	 * @param value1
	 * @param value2
	 * @return true if the given values <code>value1</code> and <code>value2</code> are equal.
	 */
	public boolean areEqual(Object value1, Object value2);

	/**
	 * A constraint method that checks if the given the value is compatible with this <code>IPropperty</code> instance. This
	 * method is a modeling-technology dependent and user specification. That means the users of the method must implement
	 * the "compatibility definition" that also tailored to the target modeling technology.
	 * 
	 * @param value
	 * @return true if the given values <code>value1</code> and <code>value2</code> are equal.
	 */
	public boolean isCompatibleToProperty(Object value);

	/**
	 * Returns the lower limit of the multiplicity of this <code>IProperty</code>.
	 * 
	 * @return
	 */
	public int getMulitiplicityLowerLimit();

	/**
	 * Returns the upper limit of the multiplicity of this <code>IProperty</code>.
	 * 
	 * @return
	 */
	public int getMulitiplicityUpperLimit();

}
