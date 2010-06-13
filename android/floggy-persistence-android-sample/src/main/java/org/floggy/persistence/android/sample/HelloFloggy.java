package org.floggy.persistence.android.sample;

import org.floggy.persistence.android.sample.R;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;

/**
 * HelloFloggy is a sample application for the usage of the Maven Android
 * Plugin. The code is trivial and not the focus of this example and therefore
 * not really documented.
 */
public class HelloFloggy extends Activity {

	TableLayout table;
	Button redButton;
	Button greenButton;
	Button blueButton;
	Button blackButton;
	Button whiteButton;

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
				table.setBackgroundColor(Color.WHITE);
			}

		});
	}
}