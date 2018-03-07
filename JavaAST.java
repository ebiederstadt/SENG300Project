import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import java.io.File;

import org.eclipse.jdt.core.dom.*;

public class JavaAST {
	
	// Converts all .java files in a directory (including sub-directories) to a string
	public static String fileConverter(String directory) throws FileNotFoundException, IOException {
	    File dirFile = new File(directory);
	    StringBuilder stringBuilder = new StringBuilder();
	    File[] fileList = dirFile.listFiles();
	    
	    for (File file : fileList) {
	        if (file.isFile() && file.getName().endsWith(".java")) {
	            
	            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
	            String line = null;
	           
	            while((line = bufferedReader.readLine())!=null){
	           
	             stringBuilder.append(line).append("\n");
	            }
	            
	        } else if (file.isDirectory()) {
	            fileConverter(file.getAbsolutePath());
	        }
	    }
	    System.out.println(stringBuilder);
	    return stringBuilder.toString();
	}
	
	// Takes user input on directory and checks that directory is valid
	public static void main(String[] args) throws IOException {
			JavaAST parse = new JavaAST();
			Scanner keyboard = new Scanner(System.in);
			String inputDir;
			while(true) {
				try {
					System.out.print("Enter Directory: ");
					inputDir = keyboard.next();
					parse.fileConverter(inputDir);
					break;
				}
				catch (NullPointerException e){
					System.err.print("Error: Directory does not exist\n");
					continue;
				}
			}
			
			// WORK IN PROGRESS, WILL BE IN SEPARATE METHOD
			//@SuppressWarnings("deprecation")
			//ASTParser parser = ASTParser.newParser(AST.JLS8);
			//parser.setSource(directoryContent);
			//parser.setKind(ASTParser.K_COMPILATION_UNIT);
	}
}

