import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

/**
 * JUnit testing class for testing the various aspects of the JavaAST class.
 */
public class JavaASTTest {
	
	String fileContents;
	// Please change this to match the base directory on your machine before running 
	String baseDirectory = "C:\\Users\\biede\\eclipse-workspace\\SENG300Project\\src\\";
	
	@Before
	public void setup() {
		fileContents = "package testFiles;\n"
				+ "// To be used for testing purposes only.\n"
				+ "public class testFile {\n"
				+ "}\n";
	}

	// Checks to see if fileConverter correctly identifies an nonexistent, invalid
	// directory
	@Test(expected = NullPointerException.class)
	public void testFileConverterInvalidGivenDirectory() {
		try {
			JavaAST.fileConverter("invalidFileDirectory");
		} catch (IOException e) {
			System.out.println("IO Error while performing testFileConverterInvalidGivenDirectory\n\n");
			e.printStackTrace();
		}
	}
	
	// Tests to see if fileConveter correctly returns the contents of a Java file as a String
	@Test
	public void testFileConverterJavaFile() {
		
	try {
		assertEquals(fileContents, JavaAST.fileConverter(baseDirectory + "testFiles"));
	} catch (IOException e) {
		System.out.println("IO Error while performing testFileConverterInvalidGivenDirectory\n\n");
		e.printStackTrace();
	}
	}
	
	// Tests that the correct number of class declarations are found by fileParser
	@Test
	public void testFileParserClassDeclarations() {
		assertEquals("Class declarations found: 1", JavaAST.fileParser(fileContents));
	}
	

}
