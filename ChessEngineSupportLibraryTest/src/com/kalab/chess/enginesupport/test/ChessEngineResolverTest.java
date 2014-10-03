package com.kalab.chess.enginesupport.test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import android.test.ActivityInstrumentationTestCase2;

import com.kalab.chess.enginesupport.ChessEngine;
import com.kalab.chess.enginesupport.ChessEngineResolver;
import com.kalab.chess.example.ExampleActivity;

public class ChessEngineResolverTest extends
		ActivityInstrumentationTestCase2<ExampleActivity> {

	private ChessEngineResolver resolver;
	private List<ChessEngine> engines;

	public ChessEngineResolverTest() {
		super(ExampleActivity.class);
	}

	protected void setUp() throws Exception {
		super.setUp();
		givenAnEngineResolver();
	}

	public void testResolverReturnsEngine() {
		whenResolvingEngines();
		thenEnginesAreResolved();
	}

	public void testNameForTargetIsRetrieved_WhenSpecifyingX86() {
		whenResolvingEnginesForTarget("x86");
		thenResolvedEngineNamesStringIs("Third Engine");
	}

	public void testResolverReturnsX86TargetEngines_WhenSpecifyingX86() {
		whenResolvingEnginesForTarget("x86");
		thenResolvedEnginesStringIs("example_engine_x86.jet");
	}

	public void testResolverReturnsAllArmTargetEngines_WhenSpecifyingArm() {
		whenResolvingEnginesForTarget("armeabi");
		thenResolvedEnginesStringIs("example_engine_arm.jet lib_engine.so missing_engine.jet");
	}

	public void testResolverReturnsAllArmTargetEngines_WhenSpecifyingArmV6l() {
		whenResolvingEnginesForTarget("armeabi-v6l");
		thenResolvedEnginesStringIs("example_engine_arm.jet lib_engine.so missing_engine.jet");
	}

	public void testResolverReturnsAllArmV7TargetEngines_WhenSpecifyingArmV7() {
		whenResolvingEnginesForTarget("armeabi-v7a");
		thenResolvedEnginesStringIs("example_engine_armv7.jet example_engine_arm.jet");
	}

	public void testFileOfFirstEngineCanBeRetrieved()
			throws FileNotFoundException, IOException {
		whenResolvingEngines();
		thenFileOfFirstEngineCanBeRetrieved();
	}

	public void testFileOfFirstEngineIsExecutable()
			throws FileNotFoundException, IOException {
		whenResolvingEngines();
		thenFileOfFirstEngineIsExecutable();
	}

	public void testFileOfSecondEngineCannotBeRetrievedIfFileIsMissing()
			throws FileNotFoundException, IOException {
		whenResolvingEnginesForTarget("armeabi");
		thenRetrievingOfMissingFileThrowsFileNotFoundException();
	}

	public void testFileIsRetrievedFromLibraryPathIfMissingInAssetsFolder()
			throws FileNotFoundException, IOException {
		whenResolvingEnginesForTarget("armeabi");
		thenRetrievingOfLibraryFilesSucceeds();
	}

	public void testPackageNameIsProvidedInResolvedEngine() {
		whenResolvingEngines();
		thenFirstEngineProvidesPackageName("com.kalab.chess.enginesupport.test");
	}

	private void givenAnEngineResolver() {
		this.resolver = new ChessEngineResolver(this.getActivity());
	}

	private void whenResolvingEngines() {
		this.engines = this.resolver.resolveEngines();
	}

	private void whenResolvingEnginesForTarget(String target) {
		this.resolver.setTarget(target);
		this.engines = this.resolver.resolveEngines();
	}

	private void thenEnginesAreResolved() {
		assertTrue(this.engines.size() > 0);
	}

	private void thenNameOfFirstEngineIs(String expected) {
		ChessEngine firstEngine = this.engines.get(0);
		assertEquals(expected, firstEngine.getName());
	}

	private void thenFileNameOfFirstEngineIs(String expected) {
		ChessEngine firstEngine = this.engines.get(0);
		assertEquals(expected, firstEngine.getFileName());
	}

	private void thenFileOfFirstEngineCanBeRetrieved()
			throws FileNotFoundException, IOException {
		ChessEngine firstEngine = this.engines.get(0);
		File copiedEngine = copyEngine(firstEngine);
		assertTrue(copiedEngine.exists());
	}

	private void thenFirstEngineProvidesPackageName(String expected) {
		ChessEngine firstEngine = this.engines.get(0);
		assertEquals(expected, firstEngine.getPackageName());
	}

	private void thenFileOfFirstEngineIsExecutable()
			throws FileNotFoundException, IOException {
		ChessEngine firstEngine = this.engines.get(0);
		File copiedEngine = copyEngine(firstEngine);
		assertTrue(copiedEngine.canExecute());
	}

	private void thenRetrievingOfLibraryFilesSucceeds()
			throws FileNotFoundException, IOException {
		ChessEngine testEngine = null;
		for (ChessEngine engine : engines) {
			if (engine.getName().equals("Engine in lib folder"))
				testEngine = engine;
		}
		File copiedEngine = copyEngine(testEngine);
		assertTrue(copiedEngine.exists());
	}

	private File copyEngine(ChessEngine engine) throws FileNotFoundException,
			IOException {
		File copiedEngine = engine.copyToFiles(this.getActivity()
				.getContentResolver(), this.getActivity().getFilesDir());
		return copiedEngine;
	}

	private void thenRetrievingOfMissingFileThrowsFileNotFoundException()
			throws IOException {
		ChessEngine missingEngine = this.engines.get(this.engines.size() - 1);
		boolean thrown = false;
		try {
			missingEngine.copyToFiles(this.getActivity().getContentResolver(),
					this.getActivity().getFilesDir());
		} catch (FileNotFoundException e) {
			thrown = true;
		}
		assertTrue("should have thrown FileNotFoundException", thrown);
	}

	private void thenResolvedEnginesStringIs(String expected) {
		String engines = "";
		for (ChessEngine engine : this.engines) {
			engines += engine.getFileName() + " ";
		}
		assertEquals(expected, engines.trim());
	}

	private void thenResolvedEngineNamesStringIs(String expected) {
		String names = "";
		for (ChessEngine engine : this.engines) {
			names += engine.getName() + " ";
		}
		assertEquals(expected, names.trim());
	}
}
