import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.NoSuchFileException;

import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.junit.Before;
import org.junit.Test;

/**
 * JUnit testing class for testing the various aspects of Assignment 1
 */
public class ParsingTestSuite {
	// Please change this to match the base directory on your machine before running
	public static String BASEDIR = "src/";

	String fileContents;
	String fileContents2;
	String fileContents3;
	Parser parse = new Parser(); 
	
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
	// Equivalence test. Checks that the file converter ignores files which are not
	// .java files, returning an empty string
	@Test
	public void testFileConverterNotJavaFile() {
		String directory = BASEDIR + "testFiles/testFile(2).txt"; 
		assertEquals("", new String(parse.fileContentToCharArray(directory)));
	}

	// Boundary test. Checks that the file converter ignores files which do not
	// exist, returning an empty string
	@Test
	public void testFileConverterMissingJavaFile() {
		String directory = BASEDIR + "testFiles/testFile(2).java"; 
		assertEquals("", new String(parse.fileContentToCharArray(directory)));
	}

	// Equivalence test. Checks that the file converter properly converts a File
	// into a char[]
	@Test
	public void testFileConverterJavaFile() {
		String directory = BASEDIR + "testFiles/testFile.java"; 
		assertEquals("", new String(parse.fileContentToCharArray(directory)));
	}

	// Equivalence test. Simple test for creation of an ASTParser.
	@Test (expected = NullPointerException.class) 
	public void testParseFilesParserBuilder() {
		assertNotEquals(null,parse.initAST(null));
	}

	// -------------------------- DeclarationAndReferenceVisitor CLASS TESTS ----------------------
	// Equivalence test. Tests the reference Visitors to correctly ignore type names that are
	// not fully qualified
	@Test
	public void testParseFilesUnqualifiedNameReferences() throws IOException {
		String[] args = {BASEDIR};
		Driver.main(args);
		assertEquals(0, DeclarationAndReferenceVisitor.getReferenceCounter());

	}
	
	// Equivalence test. Tests the declaration Visitors for a qualified name
	@Test
	public void testParseFilesQualifiedNameDeclarations() throws IOException {
		String path = BASEDIR;
		String[] args = {path};
		Driver.main(args);
		
		assertEquals(0, DeclarationAndReferenceVisitor.getDeclarationCounter());
	}
	
	// Boundary test. Tests the declaration Visitors for an incorrect, unqualified name
	@Test
	public void testParseFilesUnqualifiedNameDeclarations() throws IOException {
		String[] args = {BASEDIR};
		Driver.main(args);
		
		assertEquals(0, DeclarationAndReferenceVisitor.getDeclarationCounter());
	}
	
}