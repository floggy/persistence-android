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

import org.floggy.persistence.android.FloggyException;
import org.floggy.persistence.android.ObjectSet;

import android.database.Cursor;

/**
* DOCUMENT ME!
*
* @author <a href="mailto:thiago.moreira@floggy.org">Thiago Moreira</a>
* @version $Revision$
 */
public class ObjectSetImpl implements ObjectSet {
	/** DOCUMENT ME! */
	protected Class persistableClass;

	/** DOCUMENT ME! */
	protected Cursor cursor;

/**
   * Creates a new ObjectSetImpl object.
   *
   * @param persistableClass DOCUMENT ME!
   * @param cursor DOCUMENT ME!
   */
	public ObjectSetImpl(Class persistableClass, Cursor cursor) {
		this.persistableClass = persistableClass;
		this.cursor = cursor;
	}

	/**
	* DOCUMENT ME!
	*
	* @param index DOCUMENT ME!
	*
	* @return DOCUMENT ME!
	*
	* @throws FloggyException DOCUMENT ME!
	*/
	public Object get(int index) throws FloggyException {
		if (cursor.moveToNext()) {
			Object persistable = null;

			try {
				persistable = persistableClass.newInstance();
				PersistableManagerAndroid.setValues(cursor, persistable);
			} catch (Exception e) {
				e.printStackTrace();
			}

			return persistable;
		} else {
			throw new FloggyException("Invalid index");
		}
	}

	/**
	* DOCUMENT ME!
	*
	* @return DOCUMENT ME!
	*/
	public boolean isLazy() {
		return false;
	}

	/**
	* DOCUMENT ME!
	*
	* @param lazy DOCUMENT ME!
	*/
	public void setLazy(boolean lazy) {
	}

	/**
	* DOCUMENT ME!
	*
	* @return DOCUMENT ME!
	*/
	public int size() {
		return cursor.getCount();
	}
}
