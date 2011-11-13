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
package org.floggy.persistence.android;

import junit.framework.TestCase;

/**
* DOCUMENT ME!
*
* @author <a href="mailto:thiago.moreira@floggy.org">Thiago Moreira</a>
* @version $Revision$
 */
public class MetadataManagerTest extends TestCase {
	/**
	* DOCUMENT ME!
	*/
	public void testGetMetadataNotPersistableClass() {
		try {
			MetadataManager.getMetadata(String.class);
			fail("A IllegalArgumentException must be throw!");
		} catch (Exception ex) {
			assertEquals(IllegalArgumentException.class, ex.getClass());
		}
	}

	/**
	* DOCUMENT ME!
	*/
	public void testGetMetadataNullArgument() {
		try {
			MetadataManager.getMetadata(null);
			fail("A IllegalArgumentException must be throw!");
		} catch (Exception ex) {
			assertEquals(IllegalArgumentException.class, ex.getClass());
		}
	}

	/**
	* DOCUMENT ME!
	*/
	public void testGetMetadataPersistableClass() {
		try {
			Metadata metadata = MetadataManager.getMetadata(BeanWithIdField.class);
			assertNotNull(metadata);
			assertSame(BeanWithIdField.class, metadata.getObjectClass());
		} catch (Exception ex) {
			fail(ex.getMessage());
		}
	}
}
