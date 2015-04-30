package com.kalab.chess.enginesupport.test;

import java.util.ArrayList;

import android.app.Activity;
import android.app.Instrumentation.ActivityMonitor;
import android.os.Bundle;
import android.test.ActivityInstrumentationTestCase2;

import com.kalab.chess.enginesupport.ChessEngine;
import com.kalab.chess.enginesupport.ChessEngineResolver;
import com.kalab.chess.example.ActivityForResult;
import com.kalab.chess.example.ExampleActivity;

public class LicenseCheckTest extends
		ActivityInstrumentationTestCase2<ActivityForResult> {

	private ArrayList<ChessEngine> engines;
	private ChessEngineResolver resolver;
	private ActivityMonitor activityMonitor;

	public LicenseCheckTest() {
		super(ActivityForResult.class);
	}

	public void testLicenseCheckFails() {
		givenAnEngineResolver();
		givenResolvedEngines();
		whenCheckingLicenseWithResult(Activity.RESULT_CANCELED);
		thenLicenseCheckShouldHaveReturnedWithResult(Activity.RESULT_CANCELED);
	}

	public void testLicenseCheckIsSuccessful() {
		givenAnEngineResolver();
		givenResolvedEngines();
		whenCheckingLicenseWithResult(Activity.RESULT_OK);
		thenLicenseCheckShouldHaveReturnedWithResult(Activity.RESULT_OK);
	}

	private void givenAnEngineResolver() {
		this.resolver = new ChessEngineResolver(this.getActivity());
	}

	private void givenResolvedEngines() {
		this.engines = new ArrayList<ChessEngine>();
		for (ChessEngine engine : this.resolver.resolveEngines()) {
			if (engine.getAuthority().equals(
					"com.kalab.chess.example.ExampleEngineProvider")) {
				this.engines.add(engine);
			}
		}
	}

	private void whenCheckingLicenseWithResult(int licenseCheckResult) {
		this.activityMonitor = getInstrumentation().addMonitor(
				ExampleActivity.class.getName(), null, false);

		Bundle extras = new Bundle();
		extras.putInt("licenseCheckTestResult", licenseCheckResult);
		this.engines.get(0).checkLicense(getActivity(), 1, extras);

	}

	private void thenLicenseCheckShouldHaveReturnedWithResult(int expectedResult) {
		getInstrumentation().waitForIdleSync();
		Activity result = getInstrumentation().waitForMonitorWithTimeout(
				activityMonitor, 5);
		assertNotNull(result);
		assertEquals(expectedResult, getActivity().getResultCode());
	}
}
