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

import android.content.Context;

/**
* DOCUMENT ME!
*
* @author <a href="mailto:thiago.moreira@floggy.org">Thiago Moreira</a>
* @version $Revision$
 */
public class Configuration {
	/** DOCUMENT ME! */
	protected Context context;

	/** DOCUMENT ME! */
	protected String databaseName;

/**
   * Creates a new Configuration object.
   *
   * @param context DOCUMENT ME!
   * @param databaseName DOCUMENT ME!
   */
	public Configuration(Context context, String databaseName) {
		if (context == null) {
			throw new IllegalArgumentException("Context cannot be null");
		}

		this.context = context;
		this.databaseName = databaseName;
	}

	/**
	* DOCUMENT ME!
	*
	* @return DOCUMENT ME!
	*/
	public Context getContext() {
		return context;
	}

	/**
	* DOCUMENT ME!
	*
	* @return DOCUMENT ME!
	*/
	public String getDatabaseName() {
		return databaseName;
	}
}
