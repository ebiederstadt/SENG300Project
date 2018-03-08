import org.eclipse.jdt.core.dom.*;

public class parseFiles {
	
	int classCounter;
	
	/**
	 * Builds a parser
	 * 
	 * @param directory Character Array that is set to be parsed
	 * @return parser Returns a parser ready to be used
	 */
	public static ASTParser buildParser(char[] directory) {
		ASTParser parser = ASTParser.newParser(AST.JLS8);
		parser.setKind(ASTParser.K_COMPILATION_UNIT);
		parser.setSource(directory);
		parser.setResolveBindings(true);
		return parser;
	}
	
	/**
	 * 
	 * @param parser Passes a parser to begin creating an AST
	 * @return int Count of how many times a node of TypeDeclaration was found
	 */
	public int classDeclarationCounter(ASTParser parser) {
		final CompilationUnit compilationUnit = (CompilationUnit) parser.createAST(null);
		
		compilationUnit.accept(new ASTVisitor() {
			
			public boolean visit(TypeDeclaration node) {
				classCounter++;
				return true;
			}
		});
		
		return classCounter;
	
	}
}
	
