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
package org.floggy.persistence.android.test.primitive;

import org.floggy.persistence.android.test.AbstractTest;

/**
* DOCUMENT ME!
*
* @author <a href="mailto:thiago.moreira@floggy.org">Thiago Moreira</a>
* @version $Revision$
 */
public class FloggyByteTest extends AbstractTest {
	/**
	* DOCUMENT ME!
	*
	* @return DOCUMENT ME!
	*/
	public Object getDefaultValue() {
		return Byte.valueOf((byte) 0);
	}

	/**
	* DOCUMENT ME!
	*
	* @return DOCUMENT ME!
	*/
	@Override
	public Object getNewValueForSetMethod() {
		return Byte.valueOf((byte) -90);
	}

	/**
	* DOCUMENT ME!
	*
	* @return DOCUMENT ME!
	*/
	@Override
	public Object getValueForSetMethod() {
		return Byte.valueOf((byte) 45);
	}

	/**
	* DOCUMENT ME!
	*
	* @return DOCUMENT ME!
	*/
	@Override
	public Object newInstance() {
		return new FloggyByte();
	}

	/**
	* DOCUMENT ME!
	*
	* @throws Exception DOCUMENT ME!
	*/
	public void testNullAttribute() throws Exception {
		Object object = newInstance();
		long id = manager.save(object);
		assertTrue("Deveria ser diferente de -1!", id != -1);
		object = newInstance();
		manager.load(object, id);
		assertEquals("Deveria ser igual (valor default)!", getDefaultValue(),
			getX(object));
		manager.delete(object.getClass(), id);
	}

	/**
	* DOCUMENT ME!
	*
	* @return DOCUMENT ME!
	*/
	@Override
	protected Class getParameterType() {
		return byte.class;
	}
}
