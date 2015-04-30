package com.kalab.chess.example;

import android.app.Activity;
import android.content.Intent;

public class ActivityForResult extends Activity {
	private int resultCode;

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		this.resultCode = resultCode;
	}

	public int getResultCode() {
		return resultCode;
	}

}
