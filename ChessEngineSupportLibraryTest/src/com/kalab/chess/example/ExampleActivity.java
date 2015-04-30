package com.kalab.chess.example;

import android.app.Activity;
import android.os.Bundle;

public class ExampleActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			int result = extras.getInt("licenseCheckTestResult", Activity.RESULT_OK);
			setResult(result);
		}
		finish();
	}

}
