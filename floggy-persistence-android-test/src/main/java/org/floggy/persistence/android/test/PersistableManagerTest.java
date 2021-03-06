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
package org.floggy.persistence.android.test;

import org.floggy.persistence.android.Configuration;
import org.floggy.persistence.android.PersistableManager;

import android.test.AndroidTestCase;

/**
* DOCUMENT ME!
*
* @author <a href="mailto:thiago.moreira@floggy.org">Thiago Moreira</a>
* @version $Revision$
 */
public class PersistableManagerTest extends AndroidTestCase {
	/**
	* DOCUMENT ME!
	*/
	public void testGetInstanceNotNullContext() {
		Configuration configuration = new Configuration(getContext(), null);
		PersistableManager manager = PersistableManager.getInstance(configuration);

		assertNotNull(manager);
		assertEquals(PersistableManager.class, manager.getClass());
	}

	/**
	* DOCUMENT ME!
	*/
	public void testGetInstanceNotNullContextSameInstance() {
		Configuration configuration = new Configuration(getContext(), null);
		PersistableManager manager = PersistableManager.getInstance(configuration);

		assertNotNull(manager);
		assertEquals(PersistableManager.class, manager.getClass());

		PersistableManager manager2 = PersistableManager.getInstance(configuration);

		assertNotNull(manager2);
		assertEquals(PersistableManager.class, manager2.getClass());

		assertSame(manager, manager2);
	}

	/**
	* DOCUMENT ME!
	*/
	public void testGetInstanceNullContext() {
		try {
			PersistableManager.getInstance(null);
			fail("It mus throw an IllegalArgumentException");
		} catch (Exception ex) {
			assertEquals(IllegalArgumentException.class, ex.getClass());
		}
	}
}
