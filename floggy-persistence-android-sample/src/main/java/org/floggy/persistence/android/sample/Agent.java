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
package org.floggy.persistence.android.sample;

import java.util.UUID;

import org.floggy.persistence.android.Persistable;

/**
* DOCUMENT ME!
*
* @author <a href="mailto:thiago.moreira@floggy.org">Thiago Moreira</a>
* @version $Revision$
 */
@Persistable(table = "Agent")
public class Agent {
	/** DOCUMENT ME! */
	protected String uid;

	/** DOCUMENT ME! */
	protected long valid;

/**
   * Creates a new Agent object.
   */
	public Agent() {
		uid = UUID.randomUUID().toString();
		valid = System.currentTimeMillis();
	}

	/**
	* DOCUMENT ME!
	*
	* @return DOCUMENT ME!
	*/
	public String getUid() {
		return uid;
	}

	/**
	* DOCUMENT ME!
	*
	* @return DOCUMENT ME!
	*/
	public long getValid() {
		return valid;
	}

	/**
	* DOCUMENT ME!
	*
	* @param uid DOCUMENT ME!
	*/
	public void setUid(String uid) {
		this.uid = uid;
	}

	/**
	* DOCUMENT ME!
	*
	* @param valid DOCUMENT ME!
	*/
	public void setValid(long valid) {
		this.valid = valid;
	}

	/**
	* DOCUMENT ME!
	*
	* @return DOCUMENT ME!
	*/
	@Override
	public String toString() {
		return "Agent [uid=" + uid + ", valid=" + valid + "]";
	}
}
