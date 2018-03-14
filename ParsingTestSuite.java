import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;

import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.junit.Before;
import org.junit.Test;

/**
 * JUnit testing class for testing the various aspects of Assignment 1
 */
public class ParsingTestSuite {
	// Please change this to match the base directory on your machine before running
	public static String BASEDIR = "";

	String fileContents;
	String fileContents2;
	String fileContents3;

	@Before
	public void setup() {
		fileContents = "package testFiles;\n" + "// To be used for testing purposes only.\n"
				+ "public class testFile {\n" + "}\n";
		fileContents2 = "package testFiles;\n" + "// To be used for testing purposes only.\n"
				+ "public class testFile2 {\n" + "	String ref = new String();\n" + "}";
		fileContents3 = "package testFiles;\n" + 
				"// To be used for testing purposes only.\n" + 
				"public class testFile3 {\n" + 
				"	public class A{}\n" + 
				"	A a;\n" + 
				"}";
		
		ParseFiles.setDeclerationCounter(0);
		ParseFiles.setReferenceCounter(0);
	}

	// --------------------- Driver CLASS TESTS ------------------------------------
	// Equivalence test. Checks if the Driver correctly identifies and restricts a
	// nonexistent invalid directory
	@Test(expected = NullPointerException.class)
	public void testDriverInvalidDirectory() {
		try {
			String[] args = { "invalidDirectory", "java.lang.String" };
			Driver.main(args);
		} catch (IOException e) {
			System.out.println("IO Error while performing testDriverInvalidDirectory\n\n");
			e.printStackTrace();
		}
	}

	// Equivalence test. Checks if the Driver accepts a proper directory
	@Test
	public void testDriverValidDirectory() {
		try {
			String[] args = { BASEDIR, "java.lang.String" };
			Driver.main(args);
		} catch (IOException e) {
			System.out.println("IO Error while performing testDriverValidDirectory\n\n");
			e.printStackTrace();
		}
	}

	// -------------------------- FileConverter CLASS TESTS ----------------------
	// Equivalence test. Checks that the file converter ignores files which are not
	// .java files, returning an empty string
	@Test
	public void testFileConverterNotJavaFile() {
		File testFile = new File(BASEDIR + "testFiles/testFile(2).txt");
		try {
			assertEquals("", new String(FileConverter.fileConverter(testFile)));
		} catch (IOException e) {
			System.out.println("IO Error while performing testFileConverterNotJavaFile\n\n");
			e.printStackTrace();
		}
	}

	// Boundary test. Checks that the file converter ignores files which do not
	// exist, returning an empty string
	@Test
	public void testFileConverterMissingJavaFile() {
		File testFile = new File(BASEDIR + "testFiles/testFile(2).java");
		try {
			assertEquals("", new String(FileConverter.fileConverter(testFile)));
		} catch (IOException e) {
			System.out.println("IO Error while performing testFileConverterMissingJavaFile\n\n");
			e.printStackTrace();
		}
	}

	// Equivalence test. Checks that the file converter properly converts a File
	// into a char[]
	@Test
	public void testFileConverterJavaFile() {
		File testFile = new File(BASEDIR + "testFiles/testFile.java");
		try {
			assertEquals(fileContents, new String(FileConverter.fileConverter(testFile)));
		} catch (IOException e) {
			System.out.println("IO Error while performing testFileConverterNotJavaFile\n\n");
			e.printStackTrace();
		}
	}

	// ---------------------------------- ParseFiles CLASS TESTS --------------
	// Equivalence test. Simple test for creation of an ASTParser.
	@Test
	public void testParseFilesParserBuilder() {
		assertNotEquals(null,
				ParseFiles.buildParser(fileContents.toCharArray(), "testFile.java", BASEDIR + "testFiles"));
	}

	// Equivalence test. Tests the reference Visitors to correctly ignore type names that are
	// not fully qualified
	@Test
	public void testParseFilesUnqualifiedNameReferences() {
		ParseFiles p = new ParseFiles("String");
		ASTParser parser = ParseFiles.buildParser(fileContents2.toCharArray(), "testFile2.java",
				(BASEDIR + "testFiles/"));
		CompilationUnit compilationUnit = (CompilationUnit) parser.createAST(null);
		p.setRoot(compilationUnit);
		compilationUnit.accept(p);
		
		assertEquals(0, ParseFiles.getReferenceCounter());

	}
	
	// Equivalence test. Tests the declaration Visitors for a qualified name
	@Test
	public void testParseFilesQualifiedNameDeclarations() {
		ParseFiles p = new ParseFiles("testFiles.testFile3.A");
		ASTParser parser = ParseFiles.buildParser(fileContents3.toCharArray(), "testFile3.java",
				(BASEDIR + "testFiles/"));
		CompilationUnit compilationUnit = (CompilationUnit) parser.createAST(null);
		p.setRoot(compilationUnit);
		compilationUnit.accept(p);
		
		assertEquals(1, ParseFiles.getDeclerationCounter());
	}
	
	// Boundary test. Tests the declaration Visitors for an incorrect, unqualified name
	@Test
	public void testParseFilesUnqualifiedNameDeclarations() {
		ParseFiles p = new ParseFiles("A");
		ASTParser parser = ParseFiles.buildParser(fileContents3.toCharArray(), "testFile3.java",
				(BASEDIR + "testFiles/"));
		CompilationUnit compilationUnit = (CompilationUnit) parser.createAST(null);
		p.setRoot(compilationUnit);
		compilationUnit.accept(p);
		
		assertEquals(0, ParseFiles.getDeclerationCounter());
	}
	
	// Boundary test. Tests the ParseFiles to differentiate correctly between a declaration and a reference
	@Test
	public void testParseFilesDeclarationAgainstReference() {
		ParseFiles p = new ParseFiles("testFiles.testFile3.A");
		ASTParser parser = ParseFiles.buildParser(fileContents3.toCharArray(), "testFile3.java",
				(BASEDIR + "testFiles/"));
		CompilationUnit compilationUnit = (CompilationUnit) parser.createAST(null);
		p.setRoot(compilationUnit);
		compilationUnit.accept(p);
		
		assertEquals(1, ParseFiles.getReferenceCounter());
	}
}