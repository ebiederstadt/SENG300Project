import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;

import org.eclipse.jdt.core.dom.CompilationUnit;

public class TypeFinder extends Parser {
	
	final Logger $ = new Logger();
	private ArrayList<CompilationUnit> trees = new ArrayList<>();
	
	/**
	 * Initializes the type finder by creating ASTs
	 * for each file in directory and subdirectory
	 * while creating a list of all nodes in the
	 * directory
	 * @param dir directory or jar file specified
	 * @param flag set to false if dir is a directory, true if dir is a jar file
	 */
	public TypeFinder(String dir, boolean flag){
		if (!flag) {
			for(File f: getJavaFileList(dir)){
				CompilationUnit unit = initAST(f);
				
				trees.add(unit);				    // adds to list of compilation units
				unit.accept(new NameVisitor());		// finds all unique names the files
			}
		}
		else {
			// Need to convert to .java file
			
		}
	}
	
	/**
	 * Visits compilation unit and finds declarations and
	 * references to specified node
	 * @param node
	 * @return
	 */
	public int[] getDeclarationsAndReferences(String node) {
		for(CompilationUnit unit: trees)
			unit.accept(new DeclarationAndReferenceVisitor(node));
		return DeclarationAndReferenceVisitor.getCounters();	// gets declarations and references from visitor
	}
	
	/**
	 * Cals a recursive method to get a list of all java files
	 * @param inputDir
	 * @return list of all java files in inputDir and subdirectories
	 */
	private ArrayList<File> getJavaFileList(String inputDir){
		return subdirectoriesToFiles(inputDir, new ArrayList<File>());
	}
	
	/**
	 * Recursive method that searches for files in a directory and its sub-directories
	 * 
	 * @author Jason
	 * @param inputDir String of the command line argument for the directory
	 * @throws FileNotFoundException
	 * 				Thrown when no file is found in the directory
	 */
	private ArrayList<File> subdirectoriesToFiles(String inputDir, ArrayList<File> fullFileList) {
		ArrayList<File> currentList =
				new ArrayList<File>(Arrays.asList(new File(inputDir).listFiles()));
		
		for (File file: currentList) {
			if (isJavaFile(file)) {
				fullFileList.add(file);
			}
			else if (file.isDirectory()) {
				subdirectoriesToFiles(file.getAbsolutePath(), fullFileList);
			}
		}
		return fullFileList;
	}
	
	/**
	 * Checks if file exists and is a java file
	 * @param file
	 * @return true or false
	 */
	private boolean isJavaFile(File file){
		if(file.isFile()){
			if(file.getName().toLowerCase().endsWith(".java"))
				return true;
		}
		return false;
	}
}
