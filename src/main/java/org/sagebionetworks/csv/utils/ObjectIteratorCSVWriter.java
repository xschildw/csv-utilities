package org.sagebionetworks.csv.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;

public class ObjectIteratorCSVWriter {

	public static final String SUFFIX = ".csv";

	/**
	 * Read each record from the iterator and write it to a file
	 * 
	 * @param it - the iterator
	 * @param fileName
	 * @return the file that contains all records. The caller of this method is
	 * responsible to close the file after using it.
	 * @throws IOException 
	 */
	public static <T> File write(Iterator<T> it, String fileName, String[] headers, Class<T> clazz) throws IOException {
		File file = File.createTempFile(fileName, SUFFIX);
		FileWriter fw = new FileWriter(file.getAbsoluteFile());
		BufferedWriter bw = new BufferedWriter(fw);
		ObjectCSVWriter<T> writer = new ObjectCSVWriter<T>(bw, clazz, headers);
		while (it.hasNext()) {
			T record = it.next();
			writer.append(record);
		}
		writer.close();
		bw.close();
		return file;
	}
}
