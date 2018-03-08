import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import java.io.File;

import org.eclipse.jdt.core.dom.*;

public class JavaAST {
	
	/**
	 * Searches for all .java files in a directory and converts them to a single string
	 * 
	 * @param directory Passes directory to be sorted through
	 * @return String Returns string consisting of all .java files
	 * @throws IOException Thrown when I/O fails or is not interpreted
	 */
	public static String fileConverter(String directory) throws IOException {
	    
		File dirFile = new File(directory);
	    StringBuilder fileContents = new StringBuilder();
	    File[] fileList = dirFile.listFiles();
	    
	    // Searches given directory for .java files
	    for (File file : fileList) {
	        if (file.isFile() && file.getName().endsWith(".java")) {
	            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
	            String line = null;
	           
	            while((line = bufferedReader.readLine()) != null){
	            	fileContents.append(line).append("\n");
	            }
	        } 
	    }
	    return fileContents.toString();
	}
	
	/**
	 * Mains method, takes in user input for directory
	 * 
	 * @throws IOException Thrown when I/O fails or is not interpreted
	 */
	public static void main(String[] args) throws IOException {
			
			JavaAST parse = new JavaAST();
			Scanner keyboard = new Scanner(System.in);
			String inputDir;
			String stringParse;
			
			// Prompts for user input on directory, checks if directory is valid
			while(true) {
				try {
					System.out.print("Enter Directory: ");
					inputDir = keyboard.next();
					stringParse = parse.fileConverter(inputDir);
					break;
				}
				
				catch (NullPointerException e){
					System.err.print("Error: Directory does not exist\n");
					continue;
				}
		
		}
		
		char[] astSource = stringParse.toCharArray();
		parseFiles counter = new parseFiles();
		ASTParser parser = counter.buildParser(astSource);
		int classCount = counter.classDeclarationCounter(parser);
		System.out.println("Class declarations found: " + classCount);
		
	}

}