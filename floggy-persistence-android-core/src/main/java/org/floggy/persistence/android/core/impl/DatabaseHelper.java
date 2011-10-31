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

import android.content.Context;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
* DOCUMENT ME!
*
* @author <a href="mailto:thiago.moreira@floggy.org">Thiago Moreira</a>
* @version $Revision$
 */
public class DatabaseHelper extends SQLiteOpenHelper {
/**
   * Creates a new PersonDatabaseHelper object.
   *
   * @param context DOCUMENT ME!
   */
	public DatabaseHelper(String databaseName, Context context) {
		super(context, databaseName, null, 1);
	}

	/**
	* DOCUMENT ME!
	*
	* @param db DOCUMENT ME!
	*/
	@Override
	public void onCreate(SQLiteDatabase db) {
	}

	/**
	* DOCUMENT ME!
	*
	* @param db DOCUMENT ME!
	*/
	@Override
	public void onOpen(SQLiteDatabase db) {
		super.onOpen(db);
	}

	/**
	* DOCUMENT ME!
	*
	* @param db DOCUMENT ME!
	* @param oldVersion DOCUMENT ME!
	* @param newVersion DOCUMENT ME!
	*/
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	}
}
