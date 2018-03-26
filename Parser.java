import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Map;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.CompilationUnit;

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
		ASTParser parser = initASTParser();
		parser.setSource(fileToCharArray(file.getPath()));
		parser.setUnitName(file.getName());
		
		String[] classPath = new String[] {file.getParent()};
		parser.setEnvironment(classPath, classPath, new String[] {"UTF-8"}, true);
		
		return (CompilationUnit) parser.createAST(null);	
	}
	
	 protected CompilationUnit initAST(char[] content){
		ASTParser parser = initASTParser();
		parser.setSource(content);
		
		return (CompilationUnit) parser.createAST(null);		 
	 }
	 
	 protected CompilationUnit initAST(){
		return initAST("".toCharArray());
	 }
	 
	 private ASTParser initASTParser(){
		 ASTParser parser = ASTParser.newParser(AST.JLS8);
		 parser.setKind(ASTParser.K_COMPILATION_UNIT);

		Map<String, String> map = JavaCore.getOptions();
			JavaCore.setComplianceOptions(JavaCore.VERSION_1_8, map);
			parser.setCompilerOptions(map);
	 		
		parser.setResolveBindings(true);
		parser.setBindingsRecovery(true);
		return parser;
	 }

	/**
	 * Outputs contents of a file as a string
	 * @param path -- path of the file
	 * @return contents of the file in a string
	 * @throws IOException 
	 */
	public char[] fileToCharArray(String directory){
		$.log("reading file from " + directory);
		try{
			return new String(Files.readAllBytes(Paths.get(directory))).toCharArray();
		} 
		catch(IOException e){
				e.printStackTrace();
				return null;
		} 
	}	

	protected ArrayList<char[]> jarToCharArray(String fileName) throws IOException {
		File file = new File(fileName);
		
		JarFile jarFile = new JarFile(file);
		Enumeration<JarEntry> entries = jarFile.entries();
		JarEntry curentry = null;
		ArrayList<char[]> result = new ArrayList<char[]>();
		
		while (entries.hasMoreElements()){
			curentry = entries.nextElement();
			if (curentry.getName().toLowerCase().endsWith(".java")) {
				BufferedReader rdr = new BufferedReader(new InputStreamReader(jarFile.getInputStream(curentry)));
		        StringBuilder sb = new StringBuilder();
		        for (int c = 0; (c = rdr.read()) != -1;) {
		            sb.append((char) c);
		        }
		        result.add(sb.toString().toCharArray());
			}
			else if (curentry.getName().endsWith(".class") || curentry.isDirectory()) {
				continue;
			}
		}
		jarFile.close();
		return result;
	}

	public HashSet<String> getJavaNodesAsSet(){
		return new HashSet<String>(NameVisitor.getNodelist());
	}
}
