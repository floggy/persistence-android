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

import org.floggy.persistence.android.core.impl.PersistableManagerAndroid;

import android.content.Context;

/**
* This is the main class of the framework. All persistence operations
* methods (such as loading, saving, deleting and searching for objects) are
* declared in this class.
*
 */
public abstract class PersistableManager {
	public static final String BATCH_MODE = "BATCH_MODE";

	/** The single instance of PersistableManager. */
	private static PersistableManager instance;

	/**
	* Returns the current instance of PersistableManager.
	*
	* @param context DOCUMENT ME!
	*
	* @return The current instance of PersistableManager.
	*/
	public static PersistableManager getInstance(Context context) {
		if (context == null) {
			throw new IllegalArgumentException("Context cannot be null");
		}
		if (instance == null) {
			instance = new PersistableManagerAndroid(context);
		}

		return instance;
	}

	/**
	* Removes an object from the repository. If the object is not stored in
	* the repository then a <code>FloggyException</code> will be thrown.
	*
	* @param object Object to be removed.
	*
	* @throws FloggyException Exception thrown if an error occurs while removing
	* 				the object.
	*/
	public abstract void delete(Object object)
		throws FloggyException;

	/**
	* Removes all objects from the repository.
	*
	* @throws FloggyException Exception thrown if an error occurs while removing
	* 				the objects.
	*/
	public abstract void deleteAll() throws FloggyException;

	/**
	* Removes all objects that belongs to the class passed as parameter
	* from the repository.
	*
	* @param objectClass The object class to search the objects.
	*
	* @throws FloggyException Exception thrown if an error occurs while removing
	* 				the objects.
	*/
	public abstract void deleteAll(Class objectClass)
		throws FloggyException;

	/**
	* Searches objects of an especific object class from the
	* repository. <br>
	* <br>
	* An optional application-defined search criteria can be  defined using a <code>Filter</code>.<br>
	* <br>
	* An optional application-defined sort order can be defined using a
	* <code>Comparator</code>.
	*
	* @param objectClass The object class to search the objects.
	* @param filter An optional application-defined criteria for searching
	* 			 objects.
	* @param comparator An optional application-defined criteria for sorting
	* 			 objects.
	*
	* @return List of objects that matches the defined criteria.
	*
	* @throws FloggyException DOCUMENT ME!
	*/
	public abstract ObjectSet find(Class objectClass, Filter filter,
		Comparator comparator) throws FloggyException;

	/**
	* Searches objects of an especific object class from the
	* repository. <br>
	* <br>
	* An optional application-defined search criteria can be  defined using a <code>Filter</code>.<br>
	* <br>
	* An optional application-defined sort order can be defined using a
	* <code>Comparator</code>.
	*
	* @param objectClass The object class to search the objects.
	* @param filter An optional application-defined criteria for searching
	* 			 objects.
	* @param comparator An optional application-defined criteria for sorting
	* 			 objects.
	* @param lazy A flag indicating to load or not all composite relationships.
	*
	* @return List of objects that matches the defined criteria.
	*
	* @throws FloggyException DOCUMENT ME!
	*/
	public abstract ObjectSet find(Class objectClass, Filter filter,
		Comparator comparator, boolean lazy) throws FloggyException;

	/**
	* Gets the id under the object is stored. <br>
	*
	* @param object Object to be retrieved the id.
	*
	* @return the id under the object is stored
	*/
	public abstract int getId(Object object);

	/**
	* Check if the object is already persisted. <br>
	* <b>WARNING</b> The method only checks if the underline system has an entry
	* for the  given object object. The method doesn't checks if the
	* fields have changed.
	*
	* @param object Object to be checked the object state.
	*
	* @return true if the object is already persisted in the underline system,
	* 				false otherwise.
	*/
	public abstract boolean isPersisted(Object object);

	/**
	* Load an previously stored object from the repository using the object ID.<br>
	* The object ID is the result of a save operation or you can obtain it
	* executing a search.
	*
	* @param object An instance where the object data will be loaded into.
	* 			 Cannot be <code>null</code>.
	* @param id The ID of the object to be loaded from the repository.
	*
	* @throws FloggyException Exception thrown if an error occurs while loading
	* 				the object.
	*
	* @see #save(Persistable)
	*/
	public abstract void load(Object object, long id)
		throws FloggyException;

	/**
	* Load an previously stored object from the repository using the object ID.<br>
	* The object ID is the result of a save operation or you can obtain it
	* executing a search.
	*
	* @param object An instance where the object data will be loaded into.
	* 			 Cannot be <code>null</code>.
	* @param id The ID of the object to be loaded from the repository.
	* @param lazy A flag indicating to load or not all composite relationships.
	*
	* @throws FloggyException Exception thrown if an error occurs while loading
	* 				the object.
	*
	* @see #save(Persistable)
	*/
	public abstract void load(Object object, long id, boolean lazy)
		throws FloggyException;

	/**
	* Store an object in the repository. If the object is already in the
	* repository, the object data will be overwritten.<br>
	* The object ID obtained from this operation can be used in the load
	* operations.
	*
	* @param object Object to be stored.
	*
	* @return The ID of the object.
	*
	* @throws FloggyException Exception thrown if an error occurs while storing
	* 				the object.
	*
	* @see #load(Persistable, int)
	*/
	public abstract long save(Object object) throws FloggyException;

	/**
	* Set a property
	*
	* @param name the property's name
	* @param value the property's value
	*/
	public abstract void setProperty(String name, Object value);

	/**
	* Shutdown the PersistableManager.
	*
	* @throws FloggyException DOCUMENT ME!
	*/
	public abstract void shutdown() throws FloggyException;
}
