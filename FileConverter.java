import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

import javax.lang.model.SourceVersion;

import java.io.File;

public class FileConverter {

	/**
	 * Searches for all .java files in a directory and converts them to a single
	 * string
	 * 
	 * @param file
	 *            File that will be converted to a character array
	 * @return String Returns string consisting of all .java files
	 * @throws IOException
	 *             Thrown when I/O fails or is not interpreted
	 */
	public static char[] fileConverter(File file) throws IOException {
		// For containing all the javacode
		StringBuilder fileContents = new StringBuilder();
		BufferedReader bufferedReader;

		// Searches given directory for .java files
		if (file.isFile() && file.getName().endsWith(".java")) {
			bufferedReader = new BufferedReader(new FileReader(file));
			String line = null;

			// Append each line of java source code onto the string
			while ((line = bufferedReader.readLine()) != null) {
				fileContents.append(line).append("\n");
			}

			bufferedReader.close();
		}
		String source = fileContents.toString();
		return source.toCharArray();
	}

	
}