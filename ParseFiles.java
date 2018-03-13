import org.eclipse.jdt.core.dom.*;

public class ParseFiles {

	private static int declerationCounter;
	private static int referenceCounter;

	/**
	 * Resets the counter back to 0 for any future directories.
	 */
	public static void reset() {
		declerationCounter  = 0;
		referenceCounter = 0;
	}

	/**
	 * Builds an ASTParser from the given source.
	 * 
	 * @param directory
	 *            Character Array that is set to be parsed
	 * @return parser Returns a parser ready to be used
	 */
	public static CompilationUnit buildParser(char[] source) {
		ASTParser parser = ASTParser.newParser(AST.JLS8);
		parser.setKind(ASTParser.K_COMPILATION_UNIT);
		parser.setSource(source);
		parser.setResolveBindings(true);
		final CompilationUnit compilationUnit = (CompilationUnit) parser.createAST(null);
		return compilationUnit;
	}

	/**
	 * Crawls through an AST created by a given ASTParser, returning an int count
	 * equal to the number of Type declarations it encounters.
	 * 
	 * @param parser
	 *            Passes a CompilationUnit which is used to move through the rest of the AST
	 *            
	 * @param javaType
	 * 			A fully qualified java type
	 *            
	 * @return integer Count of how many times a node of type javaType was found
	 */
	public static int declarationCounter(CompilationUnit parser, String javaType) {

		parser.accept(new ASTVisitor() {

			public boolean visit(TypeDeclaration node) {
				System.out.println(node);
				/*ITypeBinding binding = node.resolveBinding();
				if (binding.getQualifiedName() != null && binding.getQualifiedName()==javaType) {
					declerationCounter++;
				}*/
				return true;
			}
		});

		return declerationCounter;
	}
	
	/**
	 * Moves through a AST created by an ASTParser, return an integer count
	 * equal to the number of references it encounters
	 * 
	 * @param parser
	 * 			Passes a CompilationUnit which is used to move through the rest of the AST
	 * 
	 * @param javaType
	 * 			A fully qualified java type
	 * 
	 * @return integer count of how many times a the java type was referenced
	 */
	public static int referenceCounter(CompilationUnit parser, String javaType) {
		
		parser.accept(new ASTVisitor() {
			
			public boolean visit(TypeDeclaration node) {
				
				return true;
			}
		});
		
		return referenceCounter;
	}
}
