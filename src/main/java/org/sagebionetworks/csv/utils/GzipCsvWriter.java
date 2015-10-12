package org.sagebionetworks.csv.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.util.List;
import java.util.zip.GZIPOutputStream;

/**
 * A writer that writes csv.gz object to stream.
 * 
 * @param <T>
 *            For object of this type provide, a row will be written to a CSV
 *            according to the provided header mapping.
 */
public class GzipCsvWriter<T> {

	private Class<T> objectClass;
	private String[] headers;

	/**
	 * Create a new write for each object type to write.
	 * 
	 * @param awsS3Client
	 *            A configured S3 client.
	 * @param objectClass
	 *            The type of Objects to be written to the CSV.
	 * @param headers
	 *            Maps the fields of the given objectClass to columns of the
	 *            resulting CSV file.
	 */
	public GzipCsvWriter(Class<T> objectClass, String[] headers) {
		super();
		this.objectClass = objectClass;
		this.headers = headers;
	}

	/**
	 * Write a batch of object to a csv.gz stream.
	 * 
	 * @param batch
	 * @throws IOException
	 */
	public InputStream write(List<T> batch)
			throws IOException {
		// Write the data to a gzip
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		GZIPOutputStream zipOut = new GZIPOutputStream(out);
		OutputStreamWriter osw = new OutputStreamWriter(zipOut);
		ObjectCSVWriter<T> writer = new ObjectCSVWriter<T>(osw, objectClass,
				headers);
		// Write all of the data
		for (T ar : batch) {
			writer.append(ar);
		}
		writer.close();
		// Create an input stream
		byte[] bytes = out.toByteArray();
		return new ByteArrayInputStream(bytes);
	}
}
