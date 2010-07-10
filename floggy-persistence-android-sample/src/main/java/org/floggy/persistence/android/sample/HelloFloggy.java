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
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;

/**
* HelloFloggy is a sample application for the usage of the Maven Android
* Plugin. The code is trivial and not the focus of this example and therefore
* not really documented.
 */
public class HelloFloggy extends Activity {
	Button blackButton;
	Button blueButton;
	Button greenButton;
	Button redButton;
	Button whiteButton;
	TableLayout table;

	/**
	* DOCUMENT ME!
	*
	* @param savedInstanceState DOCUMENT ME!
	*/
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		table = (TableLayout) findViewById(R.id.Table);
		table.setBackgroundColor(Color.WHITE);
		redButton = (Button) findViewById(R.id.ButtonRed);
		greenButton = (Button) findViewById(R.id.ButtonGreen);
		blueButton = (Button) findViewById(R.id.ButtonBlue);
		blackButton = (Button) findViewById(R.id.ButtonBlack);
		whiteButton = (Button) findViewById(R.id.ButtonWhite);

		redButton.setOnClickListener(new Button.OnClickListener() {
				public void onClick(View v) {
					table.setBackgroundColor(Color.RED);
				}
			});

		greenButton.setOnClickListener(new Button.OnClickListener() {
				public void onClick(View v) {
					table.setBackgroundColor(Color.GREEN);
				}
			});

		blueButton.setOnClickListener(new Button.OnClickListener() {
				public void onClick(View v) {
					table.setBackgroundColor(Color.BLUE);
				}
			});

		blackButton.setOnClickListener(new Button.OnClickListener() {
				public void onClick(View v) {
					table.setBackgroundColor(Color.BLACK);
				}
			});

		whiteButton.setOnClickListener(new Button.OnClickListener() {
				public void onClick(View v) {
					Person person = new Person();
					try {
						PersistableManager.getInstance().save(person);
					} catch (Exception e) {
						Log.e(ACTIVITY_SERVICE, e.getMessage(), e);
					}
					table.setBackgroundColor(Color.WHITE);
				}
			});
	}
}
