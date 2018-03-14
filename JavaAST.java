import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

import javax.lang.model.SourceVersion;

import java.io.File;

public class JavaAST {
	public static String javaType;

	/**
	 * Searches for all .java files in a directory and converts them to a single
	 * string
	 * 
	 * @param directory
	 *            Passes directory to be sorted through
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

	
	/**
	 * Prompts user for input on what Java type they would like to check
	 * 
	 * @return A String based on specific desired Java Type
	 */
	public static String inputType() {
		// Prompts the user for input for qualified java type, checks if type is valid
		Scanner keyboard = new Scanner(System.in);
		System.out.print("Enter qualified Java type: ");
		while (true) {
			javaType = keyboard.next();
			if (SourceVersion.isName(javaType)) {
				keyboard.close();
				return javaType;	
			}
			else {
				System.out.println("That is not a valid type name\n");
				continue;
				}
			}
		}

	
}