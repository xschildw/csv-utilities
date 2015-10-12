package org.sagebionetworks.csv.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;
import java.util.zip.GZIPInputStream;

/**
 * A reader that reads csv.gz object files from stream.
 * 
 * @param <T>
 *            For each row in the CSV file, an object of this type will be built
 *            according to the provided header mapping.
 */
public class GzipCsvReader<T> {

	private Class<T> objectClass;
	private String[] headers;

	/**
	 * Create a new reader for each object type to read.
	 * 
	 * @param awsS3Client
	 * @param objectClass
	 *            The type of object that will be created for row found in the
	 *            read CSV.
	 * @param headers
	 *            Maps the column of the read CSV to fields of the objectClass.
	 */
	public GzipCsvReader(Class<T> objectClass, String[] headers) {
		super();
		this.objectClass = objectClass;
		this.headers = headers;
	}

	/**
	 * Read the objects from the stream
	 * 
	 * @param input
	 * @return
	 * @throws IOException
	 */
	public List<T> readFromStream(InputStream input) throws IOException {
		try {
			// Read the data
			GZIPInputStream zipIn = new GZIPInputStream(input);
			InputStreamReader isr = new InputStreamReader(zipIn);
			ObjectCSVReader<T> reader = new ObjectCSVReader<T>(isr,
					objectClass, headers);
			List<T> results = new LinkedList<T>();
			T record = null;
			while ((record = reader.next()) != null) {
				results.add(record);
			}
			reader.close();
			return results;
		} finally {
			if (input != null) {
				input.close();
			}
		}
	}
}
