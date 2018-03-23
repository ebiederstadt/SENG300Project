import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.AnnotationTypeDeclaration;
import org.eclipse.jdt.core.dom.EnumDeclaration;
import org.eclipse.jdt.core.dom.PrimitiveType;
import org.eclipse.jdt.core.dom.SimpleName;
import org.eclipse.jdt.core.dom.TypeDeclaration;

public class DeclarationAndReferenceVisitor extends ASTVisitor {
			
		final Logger $ = new Logger();
		private static int declarationCounter;
		private static int referenceAndDeclarationCounter;
		private String declarationType;
		
		
		/**
		 * Getters and Setters 
		 */
		public static void setDeclarationCounter(int counter) {
			declarationCounter = counter; 
		}
		
		public static int getDeclarationCounter() {
			return declarationCounter; 
		}
		
		public static void setReferenceCounter(int counter) {
			referenceAndDeclarationCounter = counter; 
		}
		
		public static int getReferenceCounter() {
			return referenceAndDeclarationCounter; 
		}
		
		
		public DeclarationAndReferenceVisitor(String declarationType){
			this.declarationType = declarationType;
		}

		public boolean visit(EnumDeclaration node) throws NullPointerException {
			String strBinding = node.resolveBinding().getQualifiedName();
			if (strBinding.equals(declarationType)) {
				declarationCounter++;
			}
			return true;
		}
				
		/**
		 * Visit a node in the AST of type TypeDeclaration
		 * @param node
		 * 		node in the AST
		 * @return true to end the visit
		 */
		public boolean visit(TypeDeclaration node) {
			String strBinding = node.resolveBinding().getQualifiedName();
			if (strBinding.equals(declarationType)) {
				declarationCounter++;
			}
			return true;
		}
		
		/**
		 * Visit a node in the AST of type AnnotationtypeDeclaration
		 * @param node
		 * 		node in the AST
		 * @return true to end the visit
		 */
		public boolean visit(AnnotationTypeDeclaration node) throws NullPointerException {
			String strBinding = node.resolveBinding().getQualifiedName();
			if (strBinding.equals(declarationType)) {
				declarationCounter++;
			}
			return true;
		}
		
		/**
		 * finds Simple Names and adds to
		 * referenceAndDeclarationCounter if
		 * the node string matches the specified
		 * declaration type
		 */
		public boolean visit(SimpleName node){
			String nodeString = node.getFullyQualifiedName();
			if(declarationType.equals(nodeString)){
				referenceAndDeclarationCounter++;
			}
			$.log(nodeString);
			return true;
		}

		/**
		 * visits primitive nodes
		 */
		public boolean visit(PrimitiveType node){
			String nodeString = node.getPrimitiveTypeCode().toString();
			if(declarationType.equals(nodeString)){
				referenceAndDeclarationCounter++;
			}
			$.log(nodeString);
			return true;
		}
	

	/**
	 * gets the count of declarations and references from a visit
	 * and resets the counters for a different visit
	 * @return declaration counter and reference and declaration counter
	 */
	public static int[] getCounters(){
		int[] results = {declarationCounter,
						referenceAndDeclarationCounter - declarationCounter};
		
		// resets the counters for a different visit
		declarationCounter = referenceAndDeclarationCounter = 0;
		return results;
	}
}
