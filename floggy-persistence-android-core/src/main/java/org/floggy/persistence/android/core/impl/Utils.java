/**
 * Copyright (c) 2006-2011 Floggy Open Source Group. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.floggy.persistence.android.core.impl;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import org.floggy.persistence.android.FloggyException;
import org.floggy.persistence.android.Log;
import org.floggy.persistence.android.Persistable;

import android.content.ContentValues;

import android.database.Cursor;

/**
* DOCUMENT ME!
*
* @author <a href="mailto:thiago.moreira@floggy.org">Thiago Moreira</a>
* @version $Revision$
 */
public class Utils {
	private static final String TAG = "floggy.Utils";

	/**
	* DOCUMENT ME!
	*
	* @param objectClass DOCUMENT ME!
	*
	* @return DOCUMENT ME!
	*
	* @throws IllegalArgumentException DOCUMENT ME!
	*/
	public static Field getIdField(Class objectClass) {
		if (objectClass == null) {
			throw new IllegalArgumentException("The class object cannot be null!");
		}

		Field idField = null;

		Field[] fields = objectClass.getDeclaredFields();

		if (Log.isLoggable(TAG, Log.DEBUG)) {
			Log.d(TAG, "Getting Id field from class: " + objectClass.getName());
			Log.d(TAG, "Amount of fields: " + fields.length);
		}

		for (Field field : fields) {
			org.floggy.persistence.android.Field fieldAnnotation =
				field.getAnnotation(org.floggy.persistence.android.Field.class);

			if ((fieldAnnotation != null) && fieldAnnotation.id()) {
				field.setAccessible(true);

				idField = field;

				break;
			}
		}

		if (Log.isLoggable(TAG, Log.DEBUG)) {
			if (idField != null) {
				Log.d(TAG, "Found Id field : " + idField.toGenericString());
			} else {
				Log.d(TAG, "Not found Id field");
			}
		}

		return idField;
	}

	/**
	* DOCUMENT ME!
	*
	* @param object DOCUMENT ME!
	* @param fieldName DOCUMENT ME!
	*
	* @return DOCUMENT ME!
	*
	* @throws SecurityException DOCUMENT ME!
	* @throws NoSuchMethodException DOCUMENT ME!
	* @throws IllegalArgumentException DOCUMENT ME!
	* @throws IllegalAccessException DOCUMENT ME!
	* @throws InvocationTargetException DOCUMENT ME!
	*/
	public static Object getProperty(Object object, String fieldName)
		throws SecurityException, NoSuchMethodException, IllegalArgumentException,
			IllegalAccessException, InvocationTargetException {
		Method method =
			object.getClass().getMethod(getGetterMethodName(fieldName), null);

		return method.invoke(object, null);
	}

	/**
	* DOCUMENT ME!
	*
	* @param objectClass DOCUMENT ME!
	*
	* @return DOCUMENT ME!
	*/
	public static String getTableName(Class objectClass) {
		validatePersistableClassArgument(objectClass);

		Persistable annotation =
			(Persistable) objectClass.getAnnotation(Persistable.class);

		String tableName = annotation.table();

		if ("".equals(tableName)) {
			tableName = objectClass.getSimpleName();
		}

		if (Log.isLoggable(TAG, Log.DEBUG)) {
			Log.d(TAG,
				"Mapping class " + objectClass.getName() + " to table " + tableName);
		}

		return tableName;
	}

	/**
	* DOCUMENT ME!
	*
	* @param object DOCUMENT ME!
	*
	* @return DOCUMENT ME!
	*
	* @throws FloggyException DOCUMENT ME!
	*/
	public static ContentValues getValues(Object object)
		throws FloggyException {
		ContentValues values = new ContentValues();

		Field[] fields = object.getClass().getDeclaredFields();

		for (Field field : fields) {
			try {
				int modifier = field.getModifiers();

				if (!(Modifier.isStatic(modifier) || Modifier.isTransient(modifier))) {
					String fieldName = field.getName();
					Class fieldType = field.getType();

					try {
						Object value = Utils.getProperty(object, fieldName);

						if (fieldType.equals(boolean.class)
							 || fieldType.equals(Boolean.class)) {
							values.put(field.getName(), (Boolean) value);
						} else if (fieldType.equals(byte.class)
							 || fieldType.equals(Byte.class)) {
							values.put(field.getName(), (Byte) value);
						} else if (fieldType.equals(double.class)
							 || fieldType.equals(Double.class)) {
							values.put(field.getName(), (Double) value);
						} else if (fieldType.equals(float.class)
							 || fieldType.equals(Float.class)) {
							values.put(field.getName(), (Float) value);
						} else if (fieldType.equals(int.class)
							 || fieldType.equals(Integer.class)) {
							values.put(field.getName(), (Integer) value);
						} else if (fieldType.equals(long.class)
							 || fieldType.equals(Long.class)) {
							values.put(field.getName(), (Long) value);
						} else if (fieldType.equals(short.class)
							 || fieldType.equals(Short.class)) {
							values.put(field.getName(), (Short) value);
						} else if (fieldType.equals(String.class)) {
							values.put(field.getName(), (String) value);
						}
					} catch (NoSuchMethodException nsmex) {
						continue;
					}
				}
			} catch (Exception ex) {
				throw Utils.handleException(ex);
			}
		}

		return values;
	}

	/**
	* DOCUMENT ME!
	*
	* @param ex DOCUMENT ME!
	*
	* @return DOCUMENT ME!
	*/
	public static FloggyException handleException(Exception ex) {
		if (ex instanceof FloggyException) {
			return (FloggyException) ex;
		}

		String message = ex.getMessage();

		if (message == null) {
			message = ex.getClass().getName();
		}

		return new FloggyException(message, ex);
	}

	/**
	* DOCUMENT ME!
	*
	* @param object DOCUMENT ME!
	* @param fieldName DOCUMENT ME!
	* @param fieldType DOCUMENT ME!
	* @param value DOCUMENT ME!
	*
	* @throws SecurityException DOCUMENT ME!
	* @throws NoSuchMethodException DOCUMENT ME!
	* @throws IllegalArgumentException DOCUMENT ME!
	* @throws IllegalAccessException DOCUMENT ME!
	* @throws InvocationTargetException DOCUMENT ME!
	* @throws IllegalStateException DOCUMENT ME!
	*/
	public static void setProperty(Object object, String fieldName,
		Class fieldType, Object value)
		throws SecurityException, NoSuchMethodException, IllegalArgumentException,
			IllegalAccessException, InvocationTargetException {
		Method method =
			object.getClass().getMethod(getSetterMethodName(fieldName), fieldType);

		if (fieldType.equals(boolean.class) || fieldType.equals(Boolean.class)) {
			Integer temp = (Integer) value;

			if (temp.intValue() == 0) {
				value = Boolean.FALSE;
			} else if (temp.intValue() == 1) {
				value = Boolean.TRUE;
			} else {
				throw new IllegalStateException();
			}
		} else if (fieldType.equals(byte.class) || fieldType.equals(Byte.class)) {
			Integer temp = (Integer) value;

			value = new Byte(temp.byteValue());
		}

		Log.v(TAG, fieldName + " " + fieldType + " " + value);
		method.invoke(object, value);
	}

	/**
	* DOCUMENT ME!
	*
	* @param cursor DOCUMENT ME!
	* @param object DOCUMENT ME!
	*
	* @throws FloggyException DOCUMENT ME!
	*/
	public static void setValues(Cursor cursor, Object object)
		throws FloggyException {
		Field[] fields = object.getClass().getDeclaredFields();

		for (Field field : fields) {
			try {
				int modifier = field.getModifiers();

				if (!(Modifier.isStatic(modifier) || Modifier.isTransient(modifier))) {
					String fieldName = field.getName();
					Class fieldType = field.getType();

					try {
						int columnIndex = cursor.getColumnIndex(fieldName);

						if (fieldType.equals(boolean.class)
							 || fieldType.equals(Boolean.class)) {
							Utils.setProperty(object, fieldName, fieldType,
								cursor.getInt(columnIndex));
						} else if (fieldType.equals(byte.class)
							 || fieldType.equals(Byte.class)) {
							Utils.setProperty(object, fieldName, fieldType,
								cursor.getInt(columnIndex));
						} else if (fieldType.equals(double.class)
							 || fieldType.equals(Double.class)) {
							Utils.setProperty(object, fieldName, fieldType,
								cursor.getDouble(columnIndex));
						} else if (fieldType.equals(float.class)
							 || fieldType.equals(Float.class)) {
							Utils.setProperty(object, fieldName, fieldType,
								cursor.getFloat(columnIndex));
						} else if (fieldType.equals(int.class)
							 || fieldType.equals(Integer.class)) {
							Utils.setProperty(object, fieldName, fieldType,
								cursor.getInt(columnIndex));
						} else if (fieldType.equals(long.class)
							 || fieldType.equals(Long.class)) {
							Utils.setProperty(object, fieldName, fieldType,
								cursor.getLong(columnIndex));
						} else if (fieldType.equals(short.class)
							 || fieldType.equals(Short.class)) {
							Utils.setProperty(object, fieldName, fieldType,
								cursor.getShort(columnIndex));
						} else if (fieldType.equals(String.class)) {
							Utils.setProperty(object, fieldName, fieldType,
								cursor.getString(columnIndex));
						}
					} catch (NoSuchMethodException nsmex) {
						continue;
					}
				}
			} catch (Exception ex) {
				throw Utils.handleException(ex);
			}
		}
	}

	/**
	* DOCUMENT ME!
	*
	* @param objectClass DOCUMENT ME!
	*
	* @throws IllegalArgumentException DOCUMENT ME!
	*/
	public static void validatePersistableClassArgument(Class objectClass) {
		if (objectClass == null) {
			throw new IllegalArgumentException("The class object cannot be null!");
		}

		Persistable annotation =
			(Persistable) objectClass.getAnnotation(Persistable.class);

		if (annotation == null) {
			throw new IllegalArgumentException(objectClass
				+ " is not a valid Persistable class.");
		}
	}

	/**
	* DOCUMENT ME!
	*
	* @param fieldName DOCUMENT ME!
	*
	* @return DOCUMENT ME!
	*/
	protected static String capitalizeFieldName(String fieldName) {
		char ch = fieldName.charAt(0);
		ch = Character.toUpperCase(ch);

		return ch + fieldName.substring(1);
	}

	/**
	* DOCUMENT ME!
	*
	* @param fieldName DOCUMENT ME!
	*
	* @return DOCUMENT ME!
	*/
	protected static String getGetterMethodName(String fieldName) {
		fieldName = capitalizeFieldName(fieldName);

		return "get" + fieldName;
	}

	/**
	* DOCUMENT ME!
	*
	* @param fieldName DOCUMENT ME!
	*
	* @return DOCUMENT ME!
	*/
	protected static String getSetterMethodName(String fieldName) {
		fieldName = capitalizeFieldName(fieldName);

		return "set" + fieldName;
	}
}
