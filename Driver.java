import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Set;

import javax.lang.model.SourceVersion;

import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.CompilationUnit;

public class Driver {
	
	static Logger $ = new Logger();
	private static String inputType;
	private static int declerationCounter = 0;
	private static int referenceCounter = 0;
	
	/**
	 * Mains method, takes in user input for directory
	 * 
	 * @param args args[0] being input directory and args[1] being java type 
	 * 
	 * @throws IOException
	 *             Thrown when I/O fails or is not interpreted
	 * @throws FileNotFoundException
	 * 				Thrown when no file is found in the directory
	 */
	public static void main(String[] args) throws IOException, FileNotFoundException {
		// Error check the user input for the input directory and java type
		String inputDir = args[0];
		if (inputDir.equals(null)) {
			System.err.println("Directory does not exist");
			System.exit(0);
		}
		inputType = args[1];
		
		File directory = new File(inputDir);
		File[] fileList = directory.listFiles();
		Set<String> allNodes = GetNodeUtil.nodesToStringArray(fileList);
		$.log(allNodes);
		
		for(String node: allNodes){
			ParseFiles parseFiles = new ParseFiles(node);
			
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
			
			$.log("" + referenceCounter).log("" + declerationCounter);
			
			System.out.println(node + ". Declarations found: " + declerationCounter +
					" References found: " + referenceCounter);

			ParseFiles.setDeclerationCounter(0);
			ParseFiles.setReferenceCounter(0);
		}
	}

}
