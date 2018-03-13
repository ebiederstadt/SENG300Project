import org.eclipse.jdt.core.dom.*;

public class ParseFiles {

	private static int classCounter;

	/**
	 * Resets the counter back to 0 for any future directories.
	 */
	public static void reset() {
		classCounter = 0;
	}

	/**
	 * Builds an ASTParser from the given source.
	 * 
	 * @param directory
	 *            Character Array that is set to be parsed
	 * @return parser Returns a parser ready to be used
	 */
	public static ASTParser buildParser(char[] source) {
		ASTParser parser = ASTParser.newParser(AST.JLS8);
		parser.setKind(ASTParser.K_COMPILATION_UNIT);
		parser.setSource(source);
		parser.setResolveBindings(true);
		return parser;
	}

	/**
	 * Crawls through an AST created by a given ASTParser, returning an int count
	 * equal to the number of Type declarations it encounters.
	 * 
	 * @param parser
	 *            Passes a parser to begin creating an AST
	 * @return int Count of how many times a node of TypeDeclaration was found
	 */
	public static int classDeclarationCounter(ASTParser parser) {
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
