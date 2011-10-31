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
package org.floggy.persistence.android.test;

import java.lang.reflect.Field;

import org.floggy.persistence.android.Persistable;

import android.content.ContentValues;

import android.database.Cursor;

import android.util.Log;

/**
* DOCUMENT ME!
*
* @author <a href="mailto:thiago.moreira@floggy.org">Thiago Moreira</a>
* @version $Revision$
 */

@Persistable(table="Person")
public class Person {
	/** DOCUMENT ME! */
	protected String name;

	/** DOCUMENT ME! */
	protected int age;

	/**
	* DOCUMENT ME!
	*
	* @return DOCUMENT ME!
	*/
	public int getAge() {
		return age;
	}

	/**
	* DOCUMENT ME!
	*
	* @return DOCUMENT ME!
	*/
	public String getName() {
		return name;
	}

	/**
	 * DOCUMENT ME!
	*
	* @return DOCUMENT ME!
	*/
	public ContentValues getValues() {
		ContentValues values = new ContentValues();

		Log.i("Floggy", toString());

		Field[] fields = getClass().getDeclaredFields();

		for (Field field : fields) {
			Log.i("Floggy", field.getName());

			try {
				values.put(field.getName(), field.get(this).toString());
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}

		return values;
	}

	/**
	* DOCUMENT ME!
	*
	* @param age DOCUMENT ME!
	*/
	public void setAge(int age) {
		this.age = age;
	}

	/**
	* DOCUMENT ME!
	*
	* @param name DOCUMENT ME!
	*/
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * DOCUMENT ME!
	*
	* @param cursor DOCUMENT ME!
	*/
	public void setValues(Cursor cursor) {
		this.setName(cursor.getString(0));
		this.setAge(cursor.getInt(1));
	}

	/**
	 * DOCUMENT ME!
	*
	* @return DOCUMENT ME!
	*/
	@Override
	public String toString() {
		return "Person [name=" + name + ", age=" + age + "]";
	}
}
