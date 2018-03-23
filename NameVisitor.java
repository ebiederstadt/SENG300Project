import java.util.ArrayList;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.PrimitiveType;
import org.eclipse.jdt.core.dom.SimpleName;

public class NameVisitor extends ASTVisitor {
	private static ArrayList<String> nodeList = new ArrayList<String>();
	
	public boolean visit(SimpleName node){
		if(!nodeList.contains(node))
			nodeList.add(node.getFullyQualifiedName());
		return true;
	}
	
	public boolean visit(PrimitiveType node){
		if(!nodeList.contains(node))
			nodeList.add(node.getPrimitiveTypeCode().toString());
		return true;
	}
	
	public static ArrayList<String> getNodelist(){
		return new ArrayList<String>(nodeList);
	}
}
