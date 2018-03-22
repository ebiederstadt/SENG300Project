import java.io.FileNotFoundException;
import java.io.IOException;

public class Driver {
	
	final static Logger $ = new Logger();
	private static boolean flag = false;
	
	/**
	 * Mains method, takes in user input for directory
	 * s
	 * @param args args[0] being input directory and args[1] being java type 
	 * 
	 * @throws IOException
	 *             Thrown when I/O fails or is not interpreted
	 * @throws FileNotFoundException
	 * 				Thrown when no file is found in the directory
	 */
	public static void main(String[] args) throws IOException {
		// Error check the user input for the input directory and java type
		String inputDir = args[0];
		$.log(inputDir);
		
		if (inputDir.equals(null)) {
			System.err.println("Directory does not exist");
			System.exit(0);
		}
		
		if (inputDir.endsWith(".jar")) {
			flag = true;
			$.log(" + flag");
		}
		
		// Gets all appropriate nodes from a directory and prints
		// Declarations and references
		TypeFinder tf = new TypeFinder(inputDir, flag);
		for(String node: tf.getJavaNodesAsSet()){
			int[] dr = tf.getDeclarationsAndReferences(node);
			$.log(node + ". Declarations: " + dr[0] + " References: " + dr[1], true);
		}
	}

}