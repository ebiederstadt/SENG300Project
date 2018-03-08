import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;

/**
 * JUnit testing class for testing the various aspects of the JavaAST class.
 * 
 * @author Wesley
 *
 */
public class JavaASTTest {

	// Checks to see if fileConverter correctly identifies an nonexistant, invalid
	// directory
	@Test(expected = NullPointerException.class)
	public void testFileConverterInvalidGivenDirectory() {
		try {
			JavaAST.fileConverter("invalidfiledirectory");
		} catch (IOException e) {
			System.out.println("IO Error while performing testFileConverterInvalidGivenDirectory\n\n");
			e.printStackTrace();
		}
	}

}
