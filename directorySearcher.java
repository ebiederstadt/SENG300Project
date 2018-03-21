import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class directorySearcher {
	
	/**
	 * Recursive method that searches for files in a directory and its sub-directories
	 * 
	 * @author Jason
	 * 
	 * @param inputDir String of the command line argument for the directory
	 * 
	 * @throws FileNotFoundException
	 * 				Thrown when no file is found in the directory
	 */
	
	private static List<File> fullFileList = new ArrayList<>();
	
	public List<File> searchSubdirectories(String inputDir) throws FileNotFoundException {
		
		File directory = new File(inputDir);
		File[] fileList = directory.listFiles();
		
		for (File file: fileList) {
			if (file.isFile()) {
				fullFileList.add(file);
			}
			else if (file.isDirectory()) {
				searchSubdirectories(file.getAbsolutePath());
			}
		}
		return fullFileList;
	}
	
}