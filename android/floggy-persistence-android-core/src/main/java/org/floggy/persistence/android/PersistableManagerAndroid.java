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

package org.floggy.persistence.android;

import java.lang.reflect.Field;

import android.util.Log;

/**
 * DOCUMENT ME!
 *
 * @author <a href="mailto:thiago.moreira@floggy.org">Thiago Moreira</a>
 * @version $Revision$
  */
public class PersistableManagerAndroid extends PersistableManager {
	/**
	 * DOCUMENT ME!
	*
	* @param persistable DOCUMENT ME!
	*
	* @throws FloggyException DOCUMENT ME!
	*/
	public void delete(Persistable persistable) throws FloggyException {
	}

	/**
	 * DOCUMENT ME!
	*
	* @throws FloggyException DOCUMENT ME!
	*/
	public void deleteAll() throws FloggyException {
	}

	/**
	 * DOCUMENT ME!
	*
	* @param persistableClass DOCUMENT ME!
	*
	* @throws FloggyException DOCUMENT ME!
	*/
	public void deleteAll(Class persistableClass) throws FloggyException {
	}

	/**
	 * DOCUMENT ME!
	*
	* @param persistableClass DOCUMENT ME!
	* @param filter DOCUMENT ME!
	* @param comparator DOCUMENT ME!
	*
	* @return DOCUMENT ME!
	*
	* @throws FloggyException DOCUMENT ME!
	*/
	public ObjectSet find(Class persistableClass, Filter filter,
		Comparator comparator) throws FloggyException {
		return null;
	}

	/**
	 * DOCUMENT ME!
	*
	* @param persistableClass DOCUMENT ME!
	* @param filter DOCUMENT ME!
	* @param comparator DOCUMENT ME!
	* @param lazy DOCUMENT ME!
	*
	* @return DOCUMENT ME!
	*
	* @throws FloggyException DOCUMENT ME!
	*/
	public ObjectSet find(Class persistableClass, Filter filter,
		Comparator comparator, boolean lazy) throws FloggyException {
		return null;
	}

	/**
	 * DOCUMENT ME!
	*
	* @param persistable DOCUMENT ME!
	*
	* @return DOCUMENT ME!
	*/
	public int getId(Persistable persistable) {
		return 0;
	}

	/**
	 * DOCUMENT ME!
	*
	* @param persistable DOCUMENT ME!
	*
	* @return DOCUMENT ME!
	*/
	public boolean isPersisted(Persistable persistable) {
		return false;
	}

	/**
	 * DOCUMENT ME!
	*
	* @param persistable DOCUMENT ME!
	* @param id DOCUMENT ME!
	*
	* @throws FloggyException DOCUMENT ME!
	*/
	public void load(Persistable persistable, int id) throws FloggyException {
	}

	/**
	 * DOCUMENT ME!
	*
	* @param persistable DOCUMENT ME!
	* @param id DOCUMENT ME!
	* @param lazy DOCUMENT ME!
	*
	* @throws FloggyException DOCUMENT ME!
	*/
	public void load(Persistable persistable, int id, boolean lazy)
		throws FloggyException {
	}

	/**
	 * DOCUMENT ME!
	*
	* @param persistable DOCUMENT ME!
	*
	* @return DOCUMENT ME!
	*
	* @throws FloggyException DOCUMENT ME!
	*/
	public int save(Persistable persistable) throws FloggyException {
		Field[] fields = persistable.getClass().getDeclaredFields();
		Log.i("Floggy", fields.toString());
		
		for (Field field : fields) {
			Log.i("Floggy", field.getName());
		}
		
		return 0;
	}

	/**
	 * DOCUMENT ME!
	*
	* @param name DOCUMENT ME!
	* @param value DOCUMENT ME!
	*/
	public void setProperty(String name, Object value) {
	}

	/**
	 * DOCUMENT ME!
	*
	* @throws FloggyException DOCUMENT ME!
	*/
	public void shutdown() throws FloggyException {
	}
}
