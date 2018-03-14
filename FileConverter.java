import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.File;

public class FileConverter {

	/**
	 * Converts a given file's contents into a String, if it is a .java file.
	 * 
	 * @param file
	 *            File that will be converted to a character array
	 * @return char[] Returns a character array consisting of all the content within
	 *         a given .java file
	 * @throws IOException
	 *             Thrown when I/O fails or is not interpreted
	 */
	public static char[] fileConverter(File file) throws IOException {
		// For containing all the javacode
		StringBuilder fileContents = new StringBuilder();
		BufferedReader bufferedReader;

		// Checks to see if a valid .java file is given before converting
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