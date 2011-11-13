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

import java.lang.reflect.Field;

import org.floggy.persistence.android.core.impl.Utils;

/**
* DOCUMENT ME!
*
* @author <a href="mailto:thiago.moreira@floggy.org">Thiago Moreira</a>
* @version $Revision$
 */
public class Metadata {
	/** DOCUMENT ME! */
	protected Class objectClass;

	/** DOCUMENT ME! */
	protected Field idField;

	/** DOCUMENT ME! */
	protected String tableName;

/**
   * Creates a new Metadata object.
   *
   * @param objectClass DOCUMENT ME!
   */
	public Metadata(Class objectClass) {
		super();

		Utils.validatePersistableClassArgument(objectClass);

		this.objectClass = objectClass;
		this.tableName = Utils.getTableName(objectClass);
		this.idField = Utils.getIdField(objectClass);
	}

	/**
	* DOCUMENT ME!
	*
	* @return DOCUMENT ME!
	*/
	public Field getIdField() {
		return idField;
	}

	/**
	* DOCUMENT ME!
	*
	* @return DOCUMENT ME!
	*/
	public Class getObjectClass() {
		return objectClass;
	}

	/**
	* DOCUMENT ME!
	*
	* @return DOCUMENT ME!
	*/
	public String getTableName() {
		return tableName;
	}
}
