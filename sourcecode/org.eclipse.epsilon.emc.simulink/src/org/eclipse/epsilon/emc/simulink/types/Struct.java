/*********************************************************************
* Copyright (c) 2008 The University of York.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/
package org.eclipse.epsilon.emc.simulink.types;

import java.lang.reflect.Method;
import java.util.Set;

public class Struct {

	protected static final String STRUCT_MATLAB_CLASS = "com.mathworks.matlab.types.Struct";
	
	protected static final String CONTAINS_KEY_METHOD = "containsKey";
	protected static final String CONTAINS_VALUE_METHOD = "containsValue";
	protected static final String EQUALS_METHOD = "equals";
	protected static final String GET_METHOD = "get";
	protected static final String ENTRY_SET_METHOD = "entrySet";
	protected static final String HASH_CODE_METHOD = "hashCode";
	protected static final String IS_EMPTY_METHOD = "isEmpty";
	protected static final String KEY_SET_METHOD = "keySet";
	protected static final String SIZE_METHOD = "size";
	protected static final String VALUES_METHOD = "values";

	private static Class<?> struct_class;

	protected Object struct;

	public static boolean is(Object obj) {
		return (getMatlabClass() == null) ? false : getMatlabClass().isInstance(obj);  
	}
	
	protected static Class<?> getMatlabClass() {
		if (struct_class == null) {
			try {
				struct_class = ClassLoader.getSystemClassLoader().loadClass(STRUCT_MATLAB_CLASS);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
		return struct_class;
	}
	
	
	public Struct(Object struct) {
		if (is(struct)) {
			this.struct = struct;
			init();
		}
	}

	// containsKey(Object key) Returns true if this map contains a mapping for the
	// specified key.
	protected Method containsKeyMethod;

	// containsValue(Object value) Returns true if this map maps one or more keys to
	// the specified value.
	protected Method containsValueMethod;

	// equals(Object o) Compares the specified object with this map for equality.
	protected Method equalsMethod;

	// get(Object key) Returns the value to which the specified key is mapped, or
	// null if this map contains no mapping for the key.
	protected Method getMethod;

	// entrySet() Returns a Set view of the mappings contained in this map.
	protected Method entrySetMethod;

	// hashCode() Returns the hash code value for this map.
	protected Method hashCodeMethod;

	// isEmpty() Returns true if this map contains no key-value mappings.
	protected Method isEmptyMethod;

	// keySet() Returns a Set view of the keys contained in this map.
	protected Method keySetMethod;

	// size() Returns the number of key-value mappings in this map.
	protected Method sizeMethod;

	// values() Returns a Collection view of the values contained in this map.
	protected Method valuesMethod;

	private void init() {
		Class<?> clazz = getMatlabClass();
		try {
			containsKeyMethod = containsKeyMethod == null ? clazz.getDeclaredMethod(CONTAINS_KEY_METHOD, Object.class) : null;
			containsValueMethod = containsValueMethod == null ? clazz.getDeclaredMethod(CONTAINS_VALUE_METHOD, Object.class) : null;
			equalsMethod = equalsMethod == null ? clazz.getDeclaredMethod(EQUALS_METHOD, Object.class) : null;
			getMethod = getMethod == null ? clazz.getDeclaredMethod(GET_METHOD, Object.class) : null;
			entrySetMethod = entrySetMethod == null ? clazz.getDeclaredMethod(ENTRY_SET_METHOD) : null;
			hashCodeMethod = hashCodeMethod == null ? clazz.getDeclaredMethod(HASH_CODE_METHOD) : null;
			isEmptyMethod = isEmptyMethod == null ? clazz.getDeclaredMethod(IS_EMPTY_METHOD) : null;
			keySetMethod = keySetMethod == null ? clazz.getDeclaredMethod(KEY_SET_METHOD) : null;
			sizeMethod = sizeMethod == null ? clazz.getDeclaredMethod(SIZE_METHOD) : null;
			valuesMethod = valuesMethod == null ? clazz.getDeclaredMethod(VALUES_METHOD) : null;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public Boolean containsKey(Object key) {
		try {
			return (Boolean) containsKeyMethod.invoke(struct, key);
		} catch (Exception e) {
			e.printStackTrace();
			throw new IllegalStateException(e.getMessage());
		}
	}
	
	public Boolean containsValue(Object value) {
		try {
			return (Boolean) containsValueMethod.invoke(struct, value);
		} catch (Exception e) {
			e.printStackTrace();
			throw new IllegalStateException(e.getMessage());	
		}
	}
	
	public boolean equals(Object obj) {
		try {
			return (boolean) equalsMethod.invoke(struct, obj);
		} catch (Exception e) {
			e.printStackTrace();
			throw new IllegalStateException(e.getMessage());
		}
	}
	
	public Object get(Object key) {
		try {
			return (Object) getMethod.invoke(struct, key);
		} catch (Exception e) {
			e.printStackTrace();
			throw new IllegalStateException(e.getMessage());
		}
	}
	public Set<?> entrySet() {
		try {
			return (Set<?>) entrySetMethod.invoke(struct);
		} catch (Exception e) {
			e.printStackTrace();
			throw new IllegalStateException(e.getMessage());
		}
	}
	public int hashCode() {
		try {
			return (Integer) hashCodeMethod.invoke(struct);
		} catch (Exception e) {
			e.printStackTrace();
			throw new IllegalStateException(e.getMessage());
		}
	}
	
	public Boolean isEmpty() {
		try {
			return (Boolean) isEmptyMethod.invoke(struct);
		} catch (Exception e) {
			e.printStackTrace();
			throw new IllegalStateException(e.getMessage());
		}		
	}
	public Set<?> keySet() {
		try {
			return (Set<?>) keySetMethod.invoke(struct);
		} catch (Exception e) {
			e.printStackTrace();
			throw new IllegalStateException(e.getMessage());
		}
	}
	public Integer size() {
		try {
			return (Integer) sizeMethod.invoke(struct);
		} catch (Exception e) {
			e.printStackTrace();
			throw new IllegalStateException(e.getMessage());
		}
	}
	public Set<?> values() {
		try {
			return (Set<?>) valuesMethod.invoke(struct);
		} catch (Exception e) {
			e.printStackTrace();
			throw new IllegalStateException(e.getMessage());
		}
	}
	
	@Override
	public String toString() {
		return "Struct: [" +  entrySet().toString() + "]";
	}

}
