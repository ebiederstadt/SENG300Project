import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Map;

import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.dom.*;

public class Parser {
	
	final Logger $ = new Logger();
	
	/**
	 * Initializes the Compilation Unit while setting the
	 * typeDeclaration
	 * @param file -- file to parse
	 * @param declarationType -- declarationType to search as node
	 * @return compilation unit for the file
	 * @throws IOException 
	 */
	protected CompilationUnit initAST(File file){
		ASTParser parser = ASTParser.newParser(AST.JLS8);
		parser.setSource(fileContentToCharArray(file.getPath()));
		parser.setKind(ASTParser.K_COMPILATION_UNIT);

		parser.setUnitName(file.getName());
		
		Map<String, String> map = JavaCore.getOptions();
			JavaCore.setComplianceOptions(JavaCore.VERSION_1_8, map);
			parser.setCompilerOptions(map);
		
		String[] classPath = new String[] {file.getParent()};
		parser.setEnvironment(classPath, classPath, new String[] {"UTF-8"}, true);
 		
		parser.setResolveBindings(true);
		parser.setBindingsRecovery(true);
		
		return (CompilationUnit) parser.createAST(null);	
	}
	
	/**
	 * Outputs contents of a file as a string
	 * @param path -- path of the file
	 * @return contents of the file in a string
	 * @throws IOException 
	 */
	public char[] fileContentToCharArray(String path){
		$.log("reading file from " + path);
		try{
			return new String(Files.readAllBytes(Paths.get(path))).toCharArray();
		} 
		catch(IOException e){
			$.log("File Content To Char Array got IOEException", true);
			e.printStackTrace();
			return "".toCharArray();
		}
	}
		
	
	public HashSet<String> getJavaNodesAsSet(){
		return new HashSet<String>(NameVisitor.getNodelist());
	}
}
