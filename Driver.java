import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

import javax.lang.model.SourceVersion;

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
		
		String inputDir = args[0];
		javaType = args[1];
		if (inputDir.equals(null)) {
			System.err.println("Directory does not exist");
			System.exit(0);
		}
		if (! SourceVersion.isName(javaType)) {
			System.err.println("Not a java type!");
			System.exit(0);
		}
		
		Scanner keyboard = new Scanner(System.in);
		String stringParse;
		int declerationCounter = 0;
		int referenceCounter = 0;
		
		File directory = new File(inputDir);
		File[] fileList = directory.listFiles();
		
		ParseFiles parseFiles = new ParseFiles(javaType);
		
		
		for (File current:fileList) {
			char[] source = JavaAST.fileConverter(current);
			ASTParser parser = ParseFiles.buildParser(source, current.getName(), inputDir);
			CompilationUnit compilationUnit = (CompilationUnit) parser.createAST(null);
			parseFiles.setRoot(compilationUnit);
			compilationUnit.accept(parseFiles);
		}
		
		// Create an ASTParser and count the number of decelerations and references
		declerationCounter = ParseFiles.getDeclerationCounter();
		referenceCounter = ParseFiles.getReferenceCounter();
		
		// Print the results
		System.out.println(javaType + " Declarations found: " + declerationCounter +
				" References found: " + referenceCounter);
		
		keyboard.close();
	}

}
