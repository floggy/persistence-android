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
import java.lang.reflect.Modifier;

import org.floggy.persistence.android.Comparator;
import org.floggy.persistence.android.Filter;
import org.floggy.persistence.android.FloggyException;
import org.floggy.persistence.android.ObjectSet;
import org.floggy.persistence.android.PersistableManager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
* DOCUMENT ME!
*
* @author <a href="mailto:thiago.moreira@floggy.org">Thiago Moreira</a>
* @version $Revision$
 */
public class PersistableManagerAndroid extends PersistableManager {
	/** DOCUMENT ME! */
	protected DatabaseHelper databaseHelper;

/**
   * Creates a new PersistableManagerAndroid object.
   *
   * @param context DOCUMENT ME!
   */
	public PersistableManagerAndroid(Context context) {
		this.databaseHelper = new DatabaseHelper("Floggy", context);
	}

	/**
	* DOCUMENT ME!
	*
	* @param object DOCUMENT ME!
	*
	* @throws FloggyException DOCUMENT ME!
	*/
	public void delete(Object object) throws FloggyException {
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
	* @param objectClass DOCUMENT ME!
	*
	* @throws FloggyException DOCUMENT ME!
	*/
	public void deleteAll(Class objectClass) throws FloggyException {
	}

	/**
	* DOCUMENT ME!
	*
	* @param objectClass DOCUMENT ME!
	* @param filter DOCUMENT ME!
	* @param comparator DOCUMENT ME!
	*
	* @return DOCUMENT ME!
	*
	* @throws FloggyException DOCUMENT ME!
	*/
	public ObjectSet find(Class objectClass, Filter filter, Comparator comparator)
		throws FloggyException {
		return find(objectClass, filter, comparator, false);
	}

	/**
	* DOCUMENT ME!
	*
	* @param objectClass DOCUMENT ME!
	* @param filter DOCUMENT ME!
	* @param comparator DOCUMENT ME!
	* @param lazy DOCUMENT ME!
	*
	* @return DOCUMENT ME!
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
	* DOCUMENT ME!
	*
	* @param object DOCUMENT ME!
	*
	* @return DOCUMENT ME!
	*/
	public int getId(Object object) {
		return 0;
	}

	/**
	* DOCUMENT ME!
	*
	* @param object DOCUMENT ME!
	*
	* @return DOCUMENT ME!
	*/
	public boolean isPersisted(Object object) {
		return false;
	}

	/**
	* DOCUMENT ME!
	*
	* @param object DOCUMENT ME!
	* @param id DOCUMENT ME!
	*
	* @throws FloggyException DOCUMENT ME!
	*/
	public void load(Object object, long id) throws FloggyException {
		load(object, id, false);
	}

	/**
	* DOCUMENT ME!
	*
	* @param object DOCUMENT ME!
	* @param id DOCUMENT ME!
	* @param lazy DOCUMENT ME!
	*
	* @throws FloggyException DOCUMENT ME!
	*/
	public void load(Object object, long id, boolean lazy)
		throws FloggyException {

		SQLiteDatabase database = databaseHelper.getReadableDatabase();

		Cursor cursor =
			database.query(object.getClass().getSimpleName(), new String[] { "*" },
				"_id=" + id, null, null, null, null);

		if (cursor != null) {
			cursor.moveToFirst();
			setValues(cursor, object);
		}
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
	* @param cursor DOCUMENT ME!
	* @param object DOCUMENT ME!
	*
	* @throws FloggyException DOCUMENT ME!
	*/
	public static void setValues(Cursor cursor, Object object)
		throws FloggyException {
		Field[] fields = object.getClass().getDeclaredFields();

		for (Field field : fields) {
			try {
				int modifier = field.getModifiers();

				if (!(Modifier.isStatic(modifier) || Modifier.isTransient(modifier))) {
					String fieldName = field.getName();
					Class fieldType = field.getType();

					int columnIndex = cursor.getColumnIndex(fieldName);

					if (fieldType.equals(boolean.class)
						 || fieldType.equals(Boolean.class)) {
					} else if (fieldType.equals(byte.class)
						 || fieldType.equals(Byte.class)) {
					} else if (fieldType.equals(double.class)
						 || fieldType.equals(Double.class)) {
						Utils.setProperty(object, fieldName, fieldType,
							cursor.getDouble(columnIndex));
					} else if (fieldType.equals(float.class)
						 || fieldType.equals(Float.class)) {
						Utils.setProperty(object, fieldName, fieldType,
							cursor.getFloat(columnIndex));
					} else if (fieldType.equals(int.class)
						 || fieldType.equals(Integer.class)) {
						Utils.setProperty(object, fieldName, fieldType,
							cursor.getInt(columnIndex));
					} else if (fieldType.equals(long.class)
						 || fieldType.equals(Long.class)) {
						Utils.setProperty(object, fieldName, fieldType,
							cursor.getLong(columnIndex));
					} else if (fieldType.equals(short.class)
						 || fieldType.equals(Short.class)) {
						Utils.setProperty(object, fieldName, fieldType,
							cursor.getShort(columnIndex));
					} else if (fieldType.equals(String.class)) {
						Utils.setProperty(object, fieldName, fieldType,
							cursor.getString(columnIndex));
					}
				}
			} catch (Exception ex) {
				throw Utils.handleException(ex);
			}
		}
	}

	/**
	* DOCUMENT ME!
	*
	* @throws FloggyException DOCUMENT ME!
	*/
	public void shutdown() throws FloggyException {
	}

	/**
	* DOCUMENT ME!
	*
	* @param objectClass DOCUMENT ME!
	* @param database DOCUMENT ME!
	*/
	protected void createTable(Class objectClass, SQLiteDatabase database) {
		StringBuilder builder = new StringBuilder();

		builder.append("create table ");
		builder.append(objectClass.getSimpleName());
		builder.append(" (_id integer primary key autoincrement");

		Field[] fields = objectClass.getDeclaredFields();

		for (Field field : fields) {
			try {
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
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}

		builder.append(")");

		System.out.println(builder);

		database.execSQL(builder.toString());
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
}
