import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

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
	 * @throws IOException 
	 */
	public TypeFinder(String dir, boolean flag) throws IOException{
		if (!flag) {
			for(File f: getJavaFileList(dir)){
				CompilationUnit unit = initAST(f);
				
				trees.add(unit);				    // adds to list of compilation units
				unit.accept(new NameVisitor());		// finds all unique names the files
			}
		}
		else {
			for (char[] c: jarToCharArray(dir)) {
				CompilationUnit unit = initAST(c);
				
				trees.add(unit);
				unit.accept(new NameVisitor());
			}
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
	 * Calls a recursive method to get a list of all java files
	 * @param inputDir
	 * @return list of all java files in inputDir and subdirectories
	 */
	private ArrayList<File> getJavaFileList(String inputDir){
		try {
			return subdirectoriesToFiles(inputDir, new ArrayList<File>());
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * Recursive method that searches for files in a directory and its sub-directories
	 * 
	 * @author Jason
	 * @param inputDir String of the command line argument for the directory
	 * @throws IOException 
	 * @throws FileNotFoundException
	 * 				Thrown when no file is found in the directory
	 */
	private ArrayList<File> subdirectoriesToFiles(String inputDir, ArrayList<File> fullFileList) throws IOException {
		ArrayList<File> currentList =
				new ArrayList<File>(Arrays.asList(new File(inputDir).listFiles()));
		
		for (File file: currentList) {
			if (isJavaFile(file)) 
				fullFileList.add(file);
			
			else if (file.isDirectory())
				subdirectoriesToFiles(file.getPath(), fullFileList);
			
			else if (isJarFile(file))
<<<<<<< HEAD
				subdirectoriesToFiles(jarToFile(file), fullFileList);
=======
				subdirectoriesToFiles(convertJarFile(file.getPath()), fullFileList);
>>>>>>> fbf8bf9ca7aadbb5b25298eb0654cd59c50525d6
		}
		return fullFileList;
	}

	/**
	 * Recursive method that searches for files in a directory and its sub-directories
	 * 
	 * @author Jason
	 * @param inputDir String of the command line argument for the directory
	 * @throws IOException 
	 * @throws FileNotFoundException
	 * 				Thrown when no file is found in the directory
	 */
	private ArrayList<File> subdirectoriesToFiles(ArrayList<File> currentList, ArrayList<File> fullFileList) throws IOException {		
		for (File file: currentList) {
			if (isJavaFile(file)) 
				fullFileList.add(file);
			
<<<<<<< HEAD
			else if (file.isDirectory()){
				$.log(file.toString());
				subdirectoriesToFiles(file.getAbsolutePath(), fullFileList);
=======
			else if (file.isDirectory())
				subdirectoriesToFiles(file.getPath(), fullFileList);
			
			else if (isJarFile(file)) {
				subdirectoriesToFiles(convertJarFile(file.getPath()), fullFileList);
>>>>>>> fbf8bf9ca7aadbb5b25298eb0654cd59c50525d6
			}
			else if (isJarFile(file))
				subdirectoriesToFiles(jarToFile(file), fullFileList);
		}
		return fullFileList;
	}
	
	/**
	 * Convert a jar file to a(possibly empty) list of java files
	 * 
	 * @param file - jar file 
	 * @return list of length at least zero of all java files found in the jar file
	 * @throws IOException
	 */
	protected ArrayList<File> jarToFile(File file) throws IOException {
		JarFile jarFile = new JarFile(file);
		Enumeration<JarEntry> entries = jarFile.entries();
		ArrayList<File> fileList = new ArrayList<File>();
		JarEntry curentry = null;
		
		while (entries.hasMoreElements()){
			curentry = entries.nextElement();
			if (isJavaFile(curentry)) {
				fileList.add(new File(curentry.getName().toString()));
			}
			else if (curentry.getName().endsWith(".class") || curentry.isDirectory()) {
				continue;
			}
		}
		jarFile.close();
		return fileList;
	}
	
	/**
	 * Checks if file exists and is a java file
	 * @param file
	 * @return true or false
	 */
	private boolean isJavaFile(File file){
		if(file.isFile())
			return file.getName().toLowerCase().endsWith(".java");
		return false;
	}
	
	/**
	 * Checks if file exists and is a java file
	 * @param file
	 * @return true or false
	 */
	private boolean isJavaFile(JarEntry file){
		return file.getName().toLowerCase().endsWith(".java");
	}	
	/**
	 * Check if a file exists and is a jar file
	 * @param file
	 * @return true or false
	 */
	private boolean isJarFile(File file) {
		if(file.isFile())
			return file.getName().toLowerCase().endsWith(".jar");
		return false;
	}
}
