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

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;

import org.floggy.persistence.android.Comparator;
import org.floggy.persistence.android.Filter;
import org.floggy.persistence.android.FloggyException;
import org.floggy.persistence.android.ObjectSet;
import org.floggy.persistence.android.Persistable;
import org.floggy.persistence.android.PersistableManager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
* DOCUMENT ME!
*
* @author <a href="mailto:thiago.moreira@floggy.org">Thiago Moreira</a>
* @version $Revision$
 */
public class PersistableManagerAndroid extends PersistableManager {
	/**
	 * DOCUMENT ME!
	 */
	protected Context context;

	/**
	 * DOCUMENT ME!
	 */
	protected HashMap<String, SQLiteOpenHelper> openHelpers;

	/**
	 * Creates a new PersistableManagerAndroid object.
	 *
	 * @param context DOCUMENT ME!
	 */
	public PersistableManagerAndroid(Context context) {
		this.context = context;

		this.openHelpers = new HashMap<String, SQLiteOpenHelper>();
	}

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
		return find(persistableClass, filter, comparator, false);
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
		SQLiteOpenHelper helper = openHelpers.get(persistableClass.getName());

		if (helper == null) {
			helper = new PersonDatabaseHelper(persistableClass, context);
		}

		SQLiteDatabase database = helper.getReadableDatabase();

		Cursor cursor =
			database.query(persistableClass.getSimpleName(), new String[] {"*"}, null, null,
				null, null, null);

		return new ObjectSetImpl(persistableClass, cursor);
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
	public void load(Persistable persistable, long id) throws FloggyException {
		load(persistable, id, false);
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
	public void load(Persistable persistable, long id, boolean lazy)
		throws FloggyException {
		SQLiteOpenHelper helper = openHelpers.get(persistable.getClass().getName());

		if (helper == null) {
			helper = new PersonDatabaseHelper(persistable.getClass(), context);
		}

		SQLiteDatabase database = helper.getReadableDatabase();

//		createTable(persistable.getClass(), database);

		Cursor cursor =
			database.query(persistable.getClass().getSimpleName(), new String[] {"*"}, "_id=" + id,
				null, null, null, null);

		if (cursor != null) {
			cursor.moveToFirst();
			setValues(cursor, persistable);
		}
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
	public long save(Persistable persistable) throws FloggyException {
		Class persistableClass = persistable.getClass();
		SQLiteOpenHelper helper = openHelpers.get(persistableClass.getName());

		if (helper == null) {
			helper = new PersonDatabaseHelper(persistableClass, context);
			openHelpers.put(persistableClass.getName(), helper);
		}

		SQLiteDatabase database = helper.getWritableDatabase();

		try {
			createTable(persistableClass, database);
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}

		return database.insert(persistableClass.getSimpleName(), null, getValues(persistable));
	}

	//TODO build this create statement at compile time
	protected void createTable(Class persistableClass, SQLiteDatabase database) {
		StringBuilder builder = new StringBuilder();
		
		builder.append("create table ");
		builder.append(persistableClass.getSimpleName());
		builder.append(" (_id integer primary key autoincrement");
		
		Field[] fields = persistableClass.getDeclaredFields();

		for (Field field : fields) {
			try {
				int modifier = field.getModifiers();
				
				if (!(Modifier.isStatic(modifier) || Modifier.isTransient(modifier))) {

					builder.append(", ");

					String fieldName = field.getName();
					Class fieldType = field.getType();

					if (fieldType.equals(boolean.class) || fieldType.equals(Boolean.class)) {
//						values.put(field.getName(), (Boolean)value);
					} else if (fieldType.equals(byte.class) || fieldType.equals(Byte.class)) {
//						values.put(field.getName(), (Byte)value);
					} else if (fieldType.equals(double.class) || fieldType.equals(Double.class)) {
//						values.put(field.getName(), (Double)value);
					} else if (fieldType.equals(float.class) || fieldType.equals(Float.class)) {
//						values.put(field.getName(), (Float)value);
					} else if (fieldType.equals(int.class) || fieldType.equals(Integer.class)) {
						builder.append(fieldName);
						builder.append(" integer");
					} else if (fieldType.equals(long.class) || fieldType.equals(Long.class)) {
						builder.append(fieldName);
						builder.append(" long");
					} else if (fieldType.equals(short.class) || fieldType.equals(Short.class)) {
//						values.put(field.getName(), (Short)value);
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
	
	/**
	 * DOCUMENT ME!
	*
	* @return DOCUMENT ME!
	*/
	protected ContentValues getValues(Persistable persistable) throws FloggyException {
		ContentValues values = new ContentValues();

		Field[] fields = persistable.getClass().getDeclaredFields();

		for (Field field : fields) {
			try {
				int modifier = field.getModifiers();
				
				if (!(Modifier.isStatic(modifier) || Modifier.isTransient(modifier))) {
					
					String fieldName = field.getName();
					Class fieldType = field.getType();

					Object value = Utils.getProperty(persistable, fieldName);

					System.out.println(value);
					
					if (fieldType.equals(boolean.class) || fieldType.equals(Boolean.class)) {
						values.put(field.getName(), (Boolean)value);
					} else if (fieldType.equals(byte.class) || fieldType.equals(Byte.class)) {
						values.put(field.getName(), (Byte)value);
					} else if (fieldType.equals(double.class) || fieldType.equals(Double.class)) {
						values.put(field.getName(), (Double)value);
					} else if (fieldType.equals(float.class) || fieldType.equals(Float.class)) {
						values.put(field.getName(), (Float)value);
					} else if (fieldType.equals(int.class) || fieldType.equals(Integer.class)) {
						values.put(field.getName(), (Integer)value);
					} else if (fieldType.equals(long.class) || fieldType.equals(Long.class)) {
						values.put(field.getName(), (Long)value);
					} else if (fieldType.equals(short.class) || fieldType.equals(Short.class)) {
						values.put(field.getName(), (Short)value);
					} else if (fieldType.equals(String.class)) {
						values.put(field.getName(), (String)value);
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
	 * DOCUMENT ME!
	*
	* @param cursor DOCUMENT ME!
	*/
	public static void setValues(Cursor cursor, Persistable persistable) throws FloggyException {
		
		Field[] fields = persistable.getClass().getDeclaredFields();

		for (Field field : fields) {
			try {
				int modifier = field.getModifiers();
				
				if (!(Modifier.isStatic(modifier) || Modifier.isTransient(modifier))) {
					
					String fieldName = field.getName();
					Class fieldType = field.getType();

					int columnIndex = cursor.getColumnIndex(fieldName);
					
					if (fieldType.equals(boolean.class) || fieldType.equals(Boolean.class)) {
						//Utils.setProperty(persistable, fieldName, cursor.get)
					} else if (fieldType.equals(byte.class) || fieldType.equals(Byte.class)) {
						//Utils.setProperty(persistable, fieldName, cursor.get)
					} else if (fieldType.equals(double.class) || fieldType.equals(Double.class)) {
						Utils.setProperty(persistable, fieldName, fieldType, cursor.getDouble(columnIndex));
					} else if (fieldType.equals(float.class) || fieldType.equals(Float.class)) {
						Utils.setProperty(persistable, fieldName, fieldType, cursor.getFloat(columnIndex));
					} else if (fieldType.equals(int.class) || fieldType.equals(Integer.class)) {
						Utils.setProperty(persistable, fieldName, fieldType, cursor.getInt(columnIndex));
					} else if (fieldType.equals(long.class) || fieldType.equals(Long.class)) {
						Utils.setProperty(persistable, fieldName, fieldType, cursor.getLong(columnIndex));
					} else if (fieldType.equals(short.class) || fieldType.equals(Short.class)) {
						Utils.setProperty(persistable, fieldName, fieldType, cursor.getShort(columnIndex));
					} else if (fieldType.equals(String.class)) {
						Utils.setProperty(persistable, fieldName, fieldType, cursor.getString(columnIndex));
					}
					
				}
				

			} catch (Exception ex) {
				throw Utils.handleException(ex);
			}
		}
	}


}
