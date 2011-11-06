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
package org.floggy.persistence.android.test.primitive;

import org.floggy.persistence.android.Field;
import org.floggy.persistence.android.Persistable;

/**
* DOCUMENT ME!
*
* @author <a href="mailto:thiago.moreira@floggy.org">Thiago Moreira</a>
* @version $Revision$
 */
@Persistable
public class FloggyBoolean {
	/** DOCUMENT ME! */
	protected boolean x;

	/** DOCUMENT ME! */
	@Field(id = true)
	protected long id = -1;

	/**
	* DOCUMENT ME!
	*
	* @param obj DOCUMENT ME!
	*
	* @return DOCUMENT ME!
	*/
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;

		if (obj == null)
			return false;

		if (getClass() != obj.getClass())
			return false;

		FloggyBoolean other = (FloggyBoolean) obj;

		if (x != other.x)
			return false;

		return true;
	}

	/**
	* DOCUMENT ME!
	*
	* @return DOCUMENT ME!
	*/
	public boolean getX() {
		return x;
	}

	/**
	* DOCUMENT ME!
	*
	* @return DOCUMENT ME!
	*/
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = (prime * result) + (x ? 1231 : 1237);

		return result;
	}

	/**
	* DOCUMENT ME!
	*
	* @param x DOCUMENT ME!
	*/
	public void setX(boolean x) {
		this.x = x;
	}

	/**
	* DOCUMENT ME!
	*
	* @return DOCUMENT ME!
	*/
	@Override
	public String toString() {
		return "FloggyBoolean [x=" + x + ", id=" + id + "]";
	}
}
