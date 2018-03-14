import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

import javax.lang.model.SourceVersion;

import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.CompilationUnit;

public class Driver {
	
	private static String inputType;
	private static int declerationCounter = 0;
	private static int referenceCounter = 0;
	
	/**
	 * Mains method, takes in user input for directory
	 * 
	 * @throws IOException
	 *             Thrown when I/O fails or is not interpreted
	 * @throws FileNotFoundException
	 * 				Thrown when no file is found in the directory
	 */
	public static void main(String[] args) throws IOException, FileNotFoundException {
		String inputDir = args[0];
		if (inputDir.equals(null)) {
			System.err.println("Directory does not exist");
			System.exit(0);
		}
		inputType = args[1];
		if (! SourceVersion.isName(inputType)) {
			System.err.println("Not a java type");
			System.exit(0);                                                                              
		}
		
		File directory = new File(inputDir);
		File[] fileList = directory.listFiles();
		ParseFiles parseFiles = new ParseFiles(inputType);
		
		// For every file in the directory use a AST visitor to count the number of decelerations and references 
		for (File current:fileList) {
			char[] source = FileConverter.fileConverter(current);
			ASTParser parser = ParseFiles.buildParser(source, current.getName(), inputDir);
			CompilationUnit compilationUnit = (CompilationUnit) parser.createAST(null);
			parseFiles.setRoot(compilationUnit);
			compilationUnit.accept(parseFiles);
		}
		
		declerationCounter = ParseFiles.getDeclerationCounter();
		referenceCounter = ParseFiles.getReferenceCounter();
		
		System.out.println(inputType + ". Declarations found: " + declerationCounter +
				" References found: " + referenceCounter);
	}

}
