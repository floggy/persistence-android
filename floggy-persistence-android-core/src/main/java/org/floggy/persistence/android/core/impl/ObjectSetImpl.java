/**
 * Copyright (c) 2006-2010 Floggy Open Source Group. All rights reserved.
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

import java.util.Iterator;

import org.floggy.persistence.android.FloggyException;
import org.floggy.persistence.android.ObjectSet;
import org.floggy.persistence.android.Persistable;

import android.database.Cursor;

/**
 * DOCUMENT ME!
 *
 * @author <a href="mailto:thiago.moreira@floggy.org">Thiago Moreira</a>
 * @version $Revision$
  */
public class ObjectSetImpl implements ObjectSet, Iterable<Persistable> {
	/**
	 * DOCUMENT ME!
	 */
	protected Class<Persistable> persistableClass;

	/**
	 * DOCUMENT ME!
	 */
	protected Cursor cursor;

	/**
	 * Creates a new ObjectSetImpl object.
	 *
	 * @param persistableClass DOCUMENT ME!
	 * @param cursor DOCUMENT ME!
	 */
	public ObjectSetImpl(Class<Persistable> persistableClass, Cursor cursor) {
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
	public Persistable get(int index) throws FloggyException {
		if (cursor.moveToNext()) {
			Persistable persistable = null;

			try {
				persistable = (Persistable) persistableClass.newInstance();
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
	* @param index DOCUMENT ME!
	* @param object DOCUMENT ME!
	*
	* @throws FloggyException DOCUMENT ME!
	*/
	public void get(int index, Persistable object) throws FloggyException {
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
	public int getId(int index) throws FloggyException {
		return 0;
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
	* @return DOCUMENT ME!
	*/
	public Iterator<Persistable> iterator() {
		return null;
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
