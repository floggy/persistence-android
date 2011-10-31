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

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.floggy.persistence.android.FloggyException;

/**
* DOCUMENT ME!
*
* @author <a href="mailto:thiago.moreira@floggy.org">Thiago Moreira</a>
* @version $Revision$
 */
public class Utils {
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
	*/
	public static void setProperty(Object object, String fieldName,
		Class fieldType, Object value)
		throws SecurityException, NoSuchMethodException, IllegalArgumentException,
			IllegalAccessException, InvocationTargetException {
		Method method =
			object.getClass().getMethod(getSetterMethodName(fieldName), fieldType);

		method.invoke(object, value);
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
