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
package org.floggy.persistence.android.sample;

import org.floggy.persistence.android.PersistableManager;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class HelloFloggy extends Activity {

	protected EditText nameEditText;
	protected EditText ageEditText;
	protected Button submitButton;

	/**
	 * DOCUMENT ME!
	 * 
	 * @param savedInstanceState
	 *            DOCUMENT ME!
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
				try {
					PersistableManager.getInstance().save(person);
				} catch (Exception e) {
					Log.e(ACTIVITY_SERVICE, e.getMessage(), e);
				}
			}
		});
	}
}
