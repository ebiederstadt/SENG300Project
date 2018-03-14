import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.dom.*;

import java.util.Map;

public class ParseFiles extends ASTVisitor {

	private static String javaType;
	private static int declerationCounter = 0;
	private static int referenceCounter = 0;
	private ASTNode rootNode;
	
	public ParseFiles(String javaType) {
		this.javaType = javaType;
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
	
	public static int getReferenceCounter() {
		return referenceCounter;
	}

	public static void setReferenceCounter(int referenceCounter) {
		ParseFiles.referenceCounter = referenceCounter;
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
		if (strBinding.equals(ParseFiles.javaType)) {
			ParseFiles.declerationCounter++;
		}
		return true;
	}
	
	public boolean visit(TypeDeclaration node) throws NullPointerException {
		String strBinding = node.resolveBinding().getQualifiedName();
		if (strBinding.equals(ParseFiles.javaType)) {
			ParseFiles.declerationCounter++;
		}
		return true;
	}
	
	public boolean visit(AnnotationTypeDeclaration node) throws NullPointerException {
		String strBinding = node.resolveBinding().getQualifiedName();
		if (strBinding.equals(ParseFiles.javaType)) {
			ParseFiles.declerationCounter++;
		}
		return true;
	}
	
	public boolean visit(FieldDeclaration node) throws NullPointerException {
		String strBinding = node.getType().resolveBinding().getQualifiedName();
		if (strBinding.equals(ParseFiles.javaType)) {
			ParseFiles.referenceCounter++;
		}
		return true;
	}
	
	public boolean visit(VariableDeclarationStatement node) throws NullPointerException {
		String strBinding = node.getType().resolveBinding().getQualifiedName();
		if (strBinding.equals(ParseFiles.javaType)) {
			ParseFiles.referenceCounter++;
		}
		return true;
	}
	
	public boolean visit(ClassInstanceCreation node) throws NullPointerException {
		String strBinding = node.getType().resolveBinding().getQualifiedName();
		if (strBinding.equals(ParseFiles.javaType)) {
			ParseFiles.referenceCounter++;
		}
		return true;
	}
	
}
