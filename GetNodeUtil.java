import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.PrimitiveType;
import org.eclipse.jdt.core.dom.SimpleName;

public class GetNodeUtil {
	
	static ArrayList<String> nodeList = new ArrayList<String>();

	public static Set<String> nodesToStringArray(File[] fileList) throws IOException {
		for(File file: fileList){
			char[] contents = FileConverter.fileConverter(file);
			parse(contents).accept(GetNodeUtil.nameGetter());
		}
		Set<String> set = new HashSet<String>(nodeList);
		return set;
	}
	
	private static CompilationUnit parse(char[] contents){
		ASTParser parser = ASTParser.newParser(AST.JLS8);
		parser.setSource(contents);
		parser.setKind(ASTParser.K_COMPILATION_UNIT);
		
		Map<String, String> options = JavaCore.getOptions();
		options.put(JavaCore.COMPILER_COMPLIANCE, JavaCore.VERSION_1_5);
		options.put(JavaCore.COMPILER_CODEGEN_TARGET_PLATFORM, JavaCore.VERSION_1_5);
		options.put(JavaCore.COMPILER_SOURCE, JavaCore.VERSION_1_5);
		parser.setCompilerOptions(options);

		return (CompilationUnit) parser.createAST(null);
	}
	
	public static ASTVisitor nameGetter(){
			return new ASTVisitor(){
				
				public boolean visit(SimpleName node){
					if(!nodeList.contains(node))nodeList.add(node.getFullyQualifiedName());
					return true;
				}
				
				public boolean visit(PrimitiveType node){
					if(!nodeList.contains(node)) nodeList.add(node.getPrimitiveTypeCode().toString());
					return true;
				}
			};
	}
}