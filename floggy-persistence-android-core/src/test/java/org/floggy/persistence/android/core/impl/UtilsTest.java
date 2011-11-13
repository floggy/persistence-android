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

import org.floggy.persistence.android.BeanWithIdField;
import org.floggy.persistence.android.BeanWithoutIdField;

import junit.framework.TestCase;

/**
* DOCUMENT ME!
*
* @author <a href="mailto:thiago.moreira@floggy.org">Thiago Moreira</a>
* @version $Revision$
 */
public class UtilsTest extends TestCase {
	/**
	* DOCUMENT ME!
	*/
	public void testGetIdFieldNotNull() {
		try {
			Field idField = Utils.getIdField(BeanWithIdField.class);
			assertNotNull(idField);
		} catch (Exception ex) {
			ex.printStackTrace();
			fail(ex.getMessage());
		}
	}

	/**
	* DOCUMENT ME!
	*/
	public void testGetIdFieldNull() {
		try {
			Field idField = Utils.getIdField(BeanWithoutIdField.class);
			assertNull(idField);
		} catch (Exception ex) {
			fail(ex.getMessage());
		}
	}

	/**
	* DOCUMENT ME!
	*/
	public void testGetIdFieldNullArgument() {
		try {
			Utils.getIdField(null);
			fail("A IllegalArgumentException must be throw!");
		} catch (Exception ex) {
			assertEquals(IllegalArgumentException.class, ex.getClass());
		}
	}
}