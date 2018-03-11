import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

import javax.lang.model.SourceVersion;

import java.io.File;

import org.eclipse.jdt.core.dom.*;

public class JavaAST {
	// Base directory from which the given directory is relative to
	private static final String BASEDIR = "";

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
	public static String fileConverter(String directory) throws IOException {
		// Directory to parse
		File dirFile = new File(BASEDIR + directory);
		// For containing all the javacode
		StringBuilder fileContents = new StringBuilder();
		File[] fileList = dirFile.listFiles();
		BufferedReader bufferedReader;

		// Searches given directory for .java files
		for (File file : fileList) {
			if (file.isFile() && file.getName().endsWith(".java")) {
				bufferedReader = new BufferedReader(new FileReader(file));
				String line = null;

				// Append each line of java source code onto the string
				while ((line = bufferedReader.readLine()) != null) {
					fileContents.append(line).append("\n");
				}

				bufferedReader.close();
			}
		}
		return fileContents.toString();
	}
	
	/**
	 * Given a String that represents the contents of a java file,
	 * parser the string to a char array and use ASTParser to count 
	 * the number of class declarations 
	 */
	public static String fileParser(String parsedString) {
		// Prepare and use the ParseFiles class to parse the code from the given directory
		char[] astSource = parsedString.toCharArray();
		ParseFiles.reset();
		ASTParser parser = ParseFiles.buildParser(astSource);
		int classCount = ParseFiles.classDeclarationCounter(parser);
		
		return("Class declarations found: " + classCount);
	}

	/**
	 * Mains method, takes in user input for directory
	 * 
	 * @throws IOException
	 *             Thrown when I/O fails or is not interpreted
	 */
	public static void main(String[] args) throws IOException {
		Scanner keyboard = new Scanner(System.in);
		String inputDir;
		String javaType;
		String stringParse;

		// Prompts for user input on directory, checks if directory is valid
		while (true) {
			try {
				System.out.print("Enter Directory: ");
				inputDir = keyboard.next();
				stringParse = JavaAST.fileConverter(inputDir);
				break;
			}

			catch (NullPointerException e) {
				System.err.print("Error: Directory does not exist\n");
				continue;
			}

		}
		
		// Prompts the user for input for qualified java type, checks if type is valid
		System.out.print("Enter qualified Java type: ");
		while (true) {
			javaType = keyboard.next();
			if (SourceVersion.isName(javaType)) {
				break;
			}
			else {
				System.out.println("That is not a valid type name\n");
				continue;
			}
		}
		
		System.out.println(fileParser(stringParse));
		
		keyboard.close();
	}
}