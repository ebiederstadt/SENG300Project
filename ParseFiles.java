import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.dom.*;

import java.util.Map;

import javax.lang.model.type.*;

public class ParseFiles extends ASTVisitor {

	private static String javaType;
	private static int declerationCounter;
	private static int referenceCounter;
	private ASTNode rootNode;

	/**
	 * Resets the counter back to 0 for any future directories.
	 */
	public static void reset() {
		declerationCounter  = 0;
		referenceCounter = 0;
	}
	
	public void setDeclerationCounter(int declerationCounter) {
		ParseFiles.declerationCounter = declerationCounter;
	}
	
	public static int getDeclerationCounter() {
		return declerationCounter;
	}

	public void setJavatype(String javaType) {
		ParseFiles.javaType = javaType;
	}
	
	public String getJavaType() {
		return ParseFiles.javaType;
	}
	
	public void setRoot(ASTNode rootNode) {
		this.rootNode = rootNode;
	}
	
	public ASTNode getRootNode() {
		return this.rootNode;
	}

	/**
	 * Builds an ASTParser from the given source.
	 * 
	 * @param directory
	 *            Character Array that is set to be parsed
	 * @return parser Returns a parser ready to be used
	 */
	public static ASTParser buildParser(char[] source, String fileName, String inputDir) {
		ASTParser parser = ASTParser.newParser(AST.JLS8);
		parser.setKind(ASTParser.K_COMPILATION_UNIT);
		
		parser.setUnitName(fileName);
		
		Map map = JavaCore.getOptions();
		JavaCore.setComplianceOptions(JavaCore.VERSION_1_8, map);
		parser.setCompilerOptions(map);
		
		String[] classPath = new String[] {inputDir};
		parser.setEnvironment(classPath, classPath, new String[] {"UTF-8"}, true);
 		
		parser.setResolveBindings(true);
		parser.setBindingsRecovery(true);
		
		parser.setSource(source);
		
		return parser;
	}

	public boolean visit(EnumDeclaration node) throws NullPointerException {
		String strBinding = node.resolveBinding().getQualifiedName();
		if (strBinding.equals(javaType)) {
			ParseFiles.declerationCounter++;
		}
		return true;
	}
	
	public boolean visit(TypeDeclaration node) throws NullPointerException {
		String strBinding = node.resolveBinding().getQualifiedName();
		if (strBinding.equals(javaType)) {
			ParseFiles.declerationCounter++;
		}
		return true;
	}
	
	public boolean visit(AnnotationTypeDeclaration node) throws NullPointerException {
		String strBinding = node.resolveBinding().getQualifiedName();
		if (strBinding.equals(javaType)) {
			ParseFiles.declerationCounter++;
		}
		return true;
	}
	
	
}
