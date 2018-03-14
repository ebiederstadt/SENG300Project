import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.CompilationUnit;

public class Driver {
	
	private static String javaType;
	
	/**
	 * Mains method, takes in user input for directory
	 * 
	 * @throws IOException
	 *             Thrown when I/O fails or is not interpreted
	 */
	public static void main(String[] args) throws IOException, FileNotFoundException {
		
		Scanner keyboard = new Scanner(System.in);
		String inputDir;
		String stringParse;
		int declerationCounter = 0;
		int referenceCounter = 0;
		
		JavaAST javaAST = new JavaAST();
		ParseFiles parseFiles = new ParseFiles();
		
		// Prompts for user input on directory, checks if directory is valid
		while (true) {
			try {
				System.out.print("Enter Directory: ");
				inputDir = keyboard.next();
				break;
			}

			catch (NullPointerException e) {
				System.err.print("Error: Directory does not exist\n");
				continue;
			}

		}
		
		File directory = new File(inputDir);
		File[] fileList = directory.listFiles();
		
		for (File current:fileList) {
			char[] source = JavaAST.fileConverter(current);
			ASTParser parser = ParseFiles.buildParser(source, current.getName(), inputDir);
			CompilationUnit compilationUnit = (CompilationUnit) parser.createAST(null);
			parseFiles.setRoot(compilationUnit);
			compilationUnit.accept(parseFiles);
		}
		
		// Create an ASTParser and count the number of decelerations and references
		javaType = JavaAST.inputType();
		declerationCounter = ParseFiles.getDeclerationCounter();
		//referenceCounter = ParseFiles.referenceCounter(parser, stringParse);
		
		// Print the results
		System.out.println(javaType + " Declarations found: " + declerationCounter +
				" References found: " + referenceCounter);
		
		keyboard.close();
	}

}
