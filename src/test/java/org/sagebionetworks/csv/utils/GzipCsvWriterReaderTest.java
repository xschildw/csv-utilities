package org.sagebionetworks.csv.utils;

import static org.junit.Assert.assertEquals;

import java.io.InputStream;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class GzipCsvWriterReaderTest {

	private Class<ExampleObject> objectClass;
	private String[] headers;
	private GzipCsvWriter<ExampleObject> writer;
	private GzipCsvReader<ExampleObject> reader;

	@Before
	public void setUp() {
		objectClass = ExampleObject.class;
		headers = new String[]{"aString", "aLong", "aBoolean", "aDouble", "anInteger", "aFloat", "someEnum"};
		
		writer = new GzipCsvWriter<ExampleObject>(objectClass, headers);
		reader = new GzipCsvReader<ExampleObject>(objectClass, headers);
	}

	/**
	 * Test write and read methods
	 * @throws Exception
	 */
	@Test
	public void testRoundTrip() throws Exception{
		// Build up some sample data
		List<ExampleObject> data = ExampleObject.buildExampleObjectList(12);
		// call under test.
		InputStream in = writer.write(data);
		// Can we read the results?
		List<ExampleObject> results = reader.readFromStream(in);
		assertEquals(data, results);
	}


}
