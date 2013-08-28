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
	}

	public void testResolverReturnsEngine() {
		givenAnEngineResolver();
		whenResolvingEngines();
		thenEnginesAreResolved();
	}

	public void testNameOfFirstEngine() {
		givenAnEngineResolver();
		whenResolvingEngines();
		thenNameOfFirstEngineIs("Example Engine v1.0");
	}

	public void testFileNameOfFirstEngine() {
		givenAnEngineResolver();
		whenResolvingEngines();
		thenFileNameOfFirstEngineIs("example_engine.jet");
	}

	public void testFileOfFirstEngineCanBeRetrieved()
			throws FileNotFoundException, IOException {
		givenAnEngineResolver();
		whenResolvingEngines();
		thenFileOfFirstEngineCanBeRetrieved();
	}

	public void testFileOfFirstEngineIsExecutable()
			throws FileNotFoundException, IOException {
		givenAnEngineResolver();
		whenResolvingEngines();
		thenFileOfFirstEngineIsExecutable();
	}

	public void testFileOfSecondEngineCannotBeRetrievedIfFileIsMissing()
			throws FileNotFoundException, IOException {
		givenAnEngineResolver();
		whenResolvingEngines();
		thenRetrievingOfMissingFileThrowsFileNotFoundException();
	}

	private void givenAnEngineResolver() {
		this.resolver = new ChessEngineResolver(this.getActivity());
	}

	private void whenResolvingEngines() {
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
		File copiedEngine = firstEngine.copyToFiles(this.getActivity()
				.getContentResolver(), this.getActivity().getFilesDir());
		assertTrue(copiedEngine.exists());
	}

	private void thenFileOfFirstEngineIsExecutable()
			throws FileNotFoundException, IOException {
		ChessEngine firstEngine = this.engines.get(0);
		File copiedEngine = firstEngine.copyToFiles(this.getActivity()
				.getContentResolver(), this.getActivity().getFilesDir());
		assertTrue(copiedEngine.canExecute());
	}

	private void thenRetrievingOfMissingFileThrowsFileNotFoundException()
			throws IOException {
		ChessEngine secondEngine = this.engines.get(1);
		boolean thrown = false;
		try {
			secondEngine.copyToFiles(this.getActivity().getContentResolver(),
					this.getActivity().getFilesDir());
		} catch (FileNotFoundException e) {
			thrown = true;
		}
		assertTrue("should have thrown FileNotFoundException", thrown);
	}
}
