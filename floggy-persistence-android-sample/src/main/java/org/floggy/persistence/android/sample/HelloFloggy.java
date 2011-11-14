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

import org.floggy.persistence.android.Configuration;
import org.floggy.persistence.android.ObjectSet;
import org.floggy.persistence.android.PersistableManager;

import android.app.Activity;

import android.os.Bundle;

import android.util.Log;

import android.view.View;

import android.widget.Button;
import android.widget.EditText;

/**
* DOCUMENT ME!
*
* @author <a href="mailto:thiago.moreira@floggy.org">Thiago Moreira</a>
* @version $Revision$
 */
public class HelloFloggy extends Activity {
	/** DOCUMENT ME! */
	protected Button submitButton;

	/** DOCUMENT ME! */
	protected EditText ageEditText;

	/** DOCUMENT ME! */
	protected EditText nameEditText;

	/** DOCUMENT ME! */
	protected PersistableManager manager;

	/**
	* DOCUMENT ME!
	*
	* @param savedInstanceState DOCUMENT ME!
	*/
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		nameEditText = (EditText) findViewById(R.id.NameEditText);
		ageEditText = (EditText) findViewById(R.id.AgeEditText);
		submitButton = (Button) findViewById(R.id.SubmitButton);

		submitButton.setOnClickListener(new Button.OnClickListener() {
				public void onClick(View v) {
					Person person = new Person();
					person.setName(nameEditText.getEditableText().toString());
					person.setAge(Integer.parseInt(ageEditText.getEditableText().toString()));

					try {
						long id = manager.save(person);
						Log.v(ACTIVITY_SERVICE, String.valueOf(id));

						person = new Person();

						manager.load(person, id, false);

						Log.v(ACTIVITY_SERVICE, String.valueOf(person));

						manager.save(new Agent());
					} catch (Exception e) {
						Log.e(ACTIVITY_SERVICE, e.getMessage(), e);
					}

					try {
						ObjectSet os = manager.find(Person.class, null, null);
						int size = os.size();

						for (int i = 0; i < size; i++) {
							Log.v(ACTIVITY_SERVICE, String.valueOf(os.get(i)));
						}

						os.close();
					} catch (Exception e) {
						e.printStackTrace();
					}

					System.out.println();

					try {
						ObjectSet os = manager.find(Agent.class, null, null);
						int size = os.size();

						for (int i = 0; i < size; i++) {
							Log.v(ACTIVITY_SERVICE, String.valueOf(os.get(i)));
						}

						os.close();
					} catch (Exception e) {
						Log.e(ACTIVITY_SERVICE, e.getMessage(), e);
					}
				}
			});

		Configuration configuration = new Configuration(this, "HelloFloggy");
		manager = PersistableManager.getInstance(configuration);
	}
}
