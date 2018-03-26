import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.io.File;
import java.io.IOException;
import org.junit.Before;
import org.junit.Test;

/**
 * JUnit testing class for testing the various aspects of Assignment 1
 */
public class ParsingTestSuite {
	// Please change this to match the base directory on your machine before running
	public static String BASEDIR = new File("src/SENG300Project").getAbsolutePath() + "\\";

	private String fileContents;
	private String fileContents2;
	private String fileContents3;
	private String fileContents4;
	Parser testParser = new Parser(); 
	
	@Before
	public void setup() throws IOException {
		this.fileContents = "package testFiles;public class testFile {}";
		this.fileContents2 = "package testFiles;\n" + "// To be used for testing purposes only.\n"
				+ "public class testFile2 {\n" + "	String ref = new String();\n" + "}";
		this.fileContents3 = "package testFiles;\n" + 
				"// To be used for testing purposes only.\n" + 
				"public class testFile3 {\n" + 
				"	public class A{}\n" + 
				"	A a;\n" + 
				"}";
		this.fileContents4 = "This file is for testing purposes only. If the implementation is correct then this is expected to be ignored. ";
		
 		DeclarationAndReferenceVisitor.setDeclarationCounter(0);
		DeclarationAndReferenceVisitor.setReferenceCounter(0);
		
	}
	
	// --------------------- Driver CLASS TESTS ------------------------------------
	// Equivalence test. Checks if the Driver correctly identifies and restricts a
	// nonexistent invalid directory
	@Test(expected = NullPointerException.class)
	public void testDriverInvalidDirectory() throws IOException {
		String[] args = { "invalidDirectory"};
		Driver.main(args);
	}

	// Equivalence test. Checks if the Driver accepts a proper directory
	@Test
	public void testDriverValidDirectory() throws IOException {
		String[] args = {BASEDIR};
		Driver.main(args);
	}

	// -------------------------- Parser CLASS TESTS ----------------------
	// Equivalence test. Checks that the file converter treats files that are not
	// .java files just like any other file and returns the content of the file
	@Test
	public void testFileConverterNotJavaFile() {
		String directory = BASEDIR + "testFiles\\testFile(2).txt"; 
		assertEquals(fileContents4, new String(testParser.fileToCharArray(directory)));
	}

	// Boundary test. Checks that the file converter ignores files which do not exist,
	// resulting in a null pointer exception
	@Test (expected = NullPointerException.class)
	public void testFileConverterMissingJavaFile() {
		String directory = BASEDIR + "testFiles\\testFile(2).java"; 
		assertEquals("", new String(testParser.fileToCharArray(directory)));
	}

	// Equivalence test. Checks that the file converter properly converts a File
	// into a char[]
	@Test
	public void testFileConverterJavaFile() {
		String directory = BASEDIR + "testFiles\\testFile.java"; 
		assertEquals(fileContents, new String(testParser.fileToCharArray(directory)));
	}

	// Equivalence test. Simple test for creation of an ASTParser.
	@Test
	public void testParseFilesParserBuilder() {
		assertNotEquals(null,testParser.initAST());
	}

	// -------------------------- DeclarationAndReferenceVisitor CLASS TESTS ----------------------
	// Equivalence test. Tests the declaration Visitors for a qualified name
	@Test
	public void testParseFilesqualifiedNameDeclarations() throws IOException {
		String[] args = {BASEDIR};
		Driver.main(args);
		assertEquals(0, DeclarationAndReferenceVisitor.getDeclarationCounter());

	}
	// Equivalence test. Tests the reference Visitors to correctly ignore type names that are
	// not fully qualified
	@Test
	public void testParseFilesUnqualifiedNameReferences() throws IOException {
		String[] args = {BASEDIR};
		Driver.main(args);
		assertEquals(0, DeclarationAndReferenceVisitor.getReferenceCounter());

	}
	
	// Boundary test. Tests the declaration Visitors for an incorrect, unqualified name
	@Test
	public void testParseFilesUnqualifiedNameDeclarations() throws IOException {
		String[] args = {BASEDIR};
		Driver.main(args);
		
		assertEquals(0, DeclarationAndReferenceVisitor.getDeclarationCounter());
	}
	
	// Equivalence test. Tests the declaration visitors for a jar file that has java files
	@Test
	public void testParseFilesJarFileNoJavaFilesDeclaration() throws IOException {
		String[] args = {BASEDIR + "testFiles\\jarTest2.jar"};
		Driver.main(args);
		
		assertEquals(0, DeclarationAndReferenceVisitor.getDeclarationCounter());
	}
	
	// Equivalence test. Tests the reference visitors for a jar file that has not java files
	@Test
	public void testParseFilesJarFileNoJavaFilesReference() throws IOException {
		String[] args = {BASEDIR + "testFiles\\jarTest2.jar"};
		Driver.main(args);
		
		assertEquals(0, DeclarationAndReferenceVisitor.getReferenceCounter());
	}
	
	// Equivalence test. Test the declaration visitors for a jar file that has one java file
	// which contains one type declaration
	@Test
	public void testParseFilesJarFileOneJavaFileDeclaration() throws IOException {
		String dir = BASEDIR + "testFiles\\jarTest.jar";
		TypeFinder tf = new TypeFinder(dir, true);
		int[] dr = tf.getDeclarationsAndReferences("TestClass");
		assertEquals(1, dr[0]);
		}
	
	// Equivalence test. Test the reference visitors for a jar file that has one java file 
	// which contains one type reference to java.lang.String
	@Test
	public void testParseFilesJarFileOneJavaFileReference() throws IOException {
		String dir = BASEDIR +  "testFiles\\jarTest.jar";
		TypeFinder tf = new TypeFinder(dir, true);
		int[] dr = tf.getDeclarationsAndReferences("String");
		assertEquals(1, dr[1]);
	}
}