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

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashSet;
import java.util.Set;

import org.floggy.persistence.android.core.impl.DatabaseHelper;
import org.floggy.persistence.android.core.impl.ObjectSetImpl;
import org.floggy.persistence.android.core.impl.Utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
* This is the main class of the framework. All persistence operations
* methods (such as loading, saving, deleting and searching for objects) are
* declared in this class.
 */
public class PersistableManager {
	public static final String BATCH_MODE = "BATCH_MODE";

	/** The single instance of PersistableManager. */
	private static PersistableManager instance;
	/**
	* Returns the current instance of PersistableManager.
	*
	* @param context DOCUMENT ME!
	*
	* @return The current instance of PersistableManager.
	*
	* @throws IllegalArgumentException DOCUMENT ME!
	*/
	public static PersistableManager getInstance(Context context) {
		if (context == null) {
			throw new IllegalArgumentException("Context cannot be null");
		}

		if (instance == null) {
			instance = new PersistableManager(context);
		}

		return instance;
	}

	/** DOCUMENT ME! */
	protected DatabaseHelper databaseHelper;
	protected Set<String> tables = new HashSet<String>();

	private PersistableManager(Context context) {
		this.databaseHelper = new DatabaseHelper("Floggy", context);
		this.init();
	}

	protected void init() {
		String sql = "SELECT name FROM sqlite_master WHERE type='table' ORDER BY name;";

		SQLiteDatabase database = databaseHelper.getReadableDatabase();

		Cursor cursor = database.rawQuery(sql, null);

		while (cursor.moveToNext()) {
			tables.add(cursor.getString(0));
		}

		cursor.close();

		Log.v(Context.ACTIVITY_SERVICE, String.valueOf(tables));
	}

	/**
	* DOCUMENT ME!
	*
	* @param objectClass DOCUMENT ME!
	* @param database DOCUMENT ME!
	*/
	protected void createTable(Class objectClass, SQLiteDatabase database) throws Exception {
		Persistable annotation = (Persistable)objectClass.getAnnotation(Persistable.class);

		if (annotation != null) {
			String tableName = annotation.table();
			if ("".equals(tableName)) {
				tableName = objectClass.getSimpleName();
			}
			if (!tables.contains(tableName)) {
				StringBuilder builder = new StringBuilder();

				builder.append("create table ");
				builder.append(tableName);
				builder.append(" (_id integer primary key autoincrement");

				Field[] fields = objectClass.getDeclaredFields();

				for (Field field : fields) {
					int modifier = field.getModifiers();

					if (!(Modifier.isStatic(modifier) || Modifier.isTransient(modifier))) {
						builder.append(", ");

						String fieldName = field.getName();
						Class fieldType = field.getType();

						if (fieldType.equals(boolean.class)
							 || fieldType.equals(Boolean.class)) {
						} else if (fieldType.equals(byte.class)
							 || fieldType.equals(Byte.class)) {
						} else if (fieldType.equals(double.class)
							 || fieldType.equals(Double.class)) {
						} else if (fieldType.equals(float.class)
							 || fieldType.equals(Float.class)) {
						} else if (fieldType.equals(int.class)
							 || fieldType.equals(Integer.class)) {
							builder.append(fieldName);
							builder.append(" integer");
						} else if (fieldType.equals(long.class)
							 || fieldType.equals(Long.class)) {
							builder.append(fieldName);
							builder.append(" long");
						} else if (fieldType.equals(short.class)
							 || fieldType.equals(Short.class)) {
						} else if (fieldType.equals(String.class)) {
							builder.append(fieldName);
							builder.append(" text");
						}
					}
				}

				builder.append(")");

				Log.v(Context.ACTIVITY_SERVICE, builder.toString());

				database.execSQL(builder.toString());

				tables.add(tableName);
			}
		} else {
			throw new IllegalArgumentException(objectClass + " is not a valid Persistable class.");
		}
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
	public void delete(Object object) throws FloggyException {
	}

	/**
	* Removes all objects from the repository.
	*
	* @throws FloggyException Exception thrown if an error occurs while removing
	* 				the objects.
	*/
	public void deleteAll() throws FloggyException {
	}

	/**
	* Removes all objects that belongs to the class passed as parameter
	* from the repository.
	*
	* @param objectClass The object class to search the objects.
	*
	* @throws FloggyException Exception thrown if an error occurs while removing
	* 				the objects.
	*/
	public void deleteAll(Class objectClass) throws FloggyException {
	}

	/**
	* Searches objects of an especific object class from the repository. <br>
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
	public ObjectSet find(Class objectClass, Filter filter,
		Comparator comparator) throws FloggyException {
		return find(objectClass, filter, comparator, false);
	}

	/**
	* Searches objects of an especific object class from the repository. <br>
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
	public ObjectSet find(Class objectClass, Filter filter,
		Comparator comparator, boolean lazy) throws FloggyException {
		SQLiteDatabase database = databaseHelper.getReadableDatabase();

		Cursor cursor =
			database.query(objectClass.getSimpleName(), new String[] { "*" }, null,
				null, null, null, null);

		return new ObjectSetImpl(objectClass, cursor);
	}

	/**
	* Gets the id under the object is stored. <br>
	*
	* @param object Object to be retrieved the id.
	*
	* @return the id under the object is stored
	*/
	public long getId(Object object) {
		return -1;
	}

	/**
	* DOCUMENT ME!
	*
	* @param object DOCUMENT ME!
	*
	* @return DOCUMENT ME!
	*
	* @throws FloggyException DOCUMENT ME!
	*/
	protected ContentValues getValues(Object object) throws FloggyException {
		ContentValues values = new ContentValues();

		Field[] fields = object.getClass().getDeclaredFields();

		for (Field field : fields) {
			try {
				int modifier = field.getModifiers();

				if (!(Modifier.isStatic(modifier) || Modifier.isTransient(modifier))) {
					String fieldName = field.getName();
					Class fieldType = field.getType();

					Object value = Utils.getProperty(object, fieldName);

					System.out.println(value);

					if (fieldType.equals(boolean.class)
						 || fieldType.equals(Boolean.class)) {
						values.put(field.getName(), (Boolean) value);
					} else if (fieldType.equals(byte.class)
						 || fieldType.equals(Byte.class)) {
						values.put(field.getName(), (Byte) value);
					} else if (fieldType.equals(double.class)
						 || fieldType.equals(Double.class)) {
						values.put(field.getName(), (Double) value);
					} else if (fieldType.equals(float.class)
						 || fieldType.equals(Float.class)) {
						values.put(field.getName(), (Float) value);
					} else if (fieldType.equals(int.class)
						 || fieldType.equals(Integer.class)) {
						values.put(field.getName(), (Integer) value);
					} else if (fieldType.equals(long.class)
						 || fieldType.equals(Long.class)) {
						values.put(field.getName(), (Long) value);
					} else if (fieldType.equals(short.class)
						 || fieldType.equals(Short.class)) {
						values.put(field.getName(), (Short) value);
					} else if (fieldType.equals(String.class)) {
						values.put(field.getName(), (String) value);
					}
				}
			} catch (Exception ex) {
				throw Utils.handleException(ex);
			}
		}

		System.out.println(values.toString());

		return values;
	}

	/**
	* Check if the object is already persisted. <br>
	* <b>WARNING</b> The method only checks if the underline system has an entry
	* for the  given object object. The method doesn't checks if the fields
	* have changed.
	*
	* @param object Object to be checked the object state.
	*
	* @return true if the object is already persisted in the underline system,
	* 				false otherwise.
	*/
	public boolean isPersisted(Object object) {
		return false;
	}

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
	public void load(Object object, long id) throws FloggyException {
		load(object, id, false);
	}

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
	public void load(Object object, long id, boolean lazy)
		throws FloggyException {
		SQLiteDatabase database = databaseHelper.getReadableDatabase();

		Cursor cursor =
			database.query(object.getClass().getSimpleName(), new String[] { "*" },
				"_id=" + id, null, null, null, null);

		if (cursor != null) {
			cursor.moveToFirst();
			Utils.setValues(cursor, object);
		}

	}

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
	public long save(Object object) throws FloggyException {
		Class objectClass = object.getClass();

		SQLiteDatabase database = databaseHelper.getWritableDatabase();

		try {
			createTable(objectClass, database);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return database.insert(objectClass.getSimpleName(), null, getValues(object));
	}

	/**
	* Set a property
	*
	* @param name the property's name
	* @param value the property's value
	*/
	public void setProperty(String name, Object value) {
	}

	/**
	* Shutdown the PersistableManager.
	*
	* @throws FloggyException DOCUMENT ME!
	*/
	public void shutdown() throws FloggyException {
	}
}
