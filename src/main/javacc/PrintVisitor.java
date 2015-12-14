//public class PrintVisitor implements BasicLAnalyserVisitor
//{
//	public Object visit(SimpleNode node, Object data)
//	{
//		throw new RuntimeException("Visit SimpleNode");
//	}
//
//	public Object visit(ASTProgram node, Object data) {
//		node.jjtGetChild(0).jjtAccept(this, data);
//		System.out.println(";");
//
//		return(data);
//	}
//
//	public Object visit(ASTDecl node, Object data) {
//		System.out.print(node.value + " ");
//		node.jjtGetChild(0).jjtAccept(this, data);
//
//		return data;
//	}
//
//	public Object visit(ASTVarDecl node, Object data) {
//		System.out.print(node.value + " ");
//		node.jjtGetChild(0).jjtAccept(this, data);
//
//		return data;
//	}
//
//	public Object visit(ASTConstDecl node, Object data) {
//		System.out.print(node.value + " ");
//		node.jjtGetChild(0).jjtAccept(this, data);
//
//		return data;
//	}
//
//	public Object visit(ASTFunction node, Object data) {
//		System.out.println(node.value);
//
//		return data;
//	}
//
//	public Object visit(ASTParamList node, Object data) {
//		System.out.println(node.value);
//
//		return data;
//	}
//
//	public Object visit(ASTType node, Object data) {
//		System.out.println(node.value);
//
//		return data;
//	}
//
//	public Object visit(ASTMainProg node, Object data) {
//		System.out.print(node.value + " ");
//		node.jjtGetChild(0).jjtAccept(this, data);
//
//		return data;
//	}
//
//	public Object visit(ASTStatement node, Object data)
//	{
//		node.jjtGetChild(0).jjtAccept(this, data);
//		System.out.println(";");
//		node.jjtGetChild(1).jjtAccept(this, data);
//
//		return data;
//	}
//
//	public Object visit(ASTExpression node, Object data) {
//		System.out.print("(");
//		node.jjtGetChild(0).jjtAccept(this, data);
//		System.out.print(")");
//
//		return data;
//	}
//
//	public Object visit(ASTTerm node, Object data) {
//		System.out.print(node.value);
//
//		return data;
//	}
//
//	public Object visit(ASTFragment node, Object data) {
//		System.out.print(node.value);
//
//		return data;
//	}
//
//	public Object visit(ASTCondition node, Object data) {
//		System.out.print(node.value);
//
//		return data;
//	}
//
//	public Object visit(ASTConditionalExpression node, Object data) {
//		System.out.print(node.value);
//
//		return data;
//	}
//
//	public Object visit(ASTFunctionCall node, Object data) {
//		System.out.print(node.value);
//
//		return data;
//	}
//
//	public Object visit(ASTIdentList node, Object data) {
//		System.out.print(node.value);
//
//		return data;
//	}
//
//	public Object visit(ASTArgList node, Object data) {
//		System.out.print(node.value);
//
//		return data;
//	}
//
//	public Object visit(ASTIdentifier node, Object data) {
//		System.out.print(node.value);
//
//		return data;
//	}
//
//	public Object visit(ASTNumber node, Object data) {
//		System.out.print(node.value);
//
//		return data;
//	}
//
//	public Object visit(ASTConditionalOperator node, Object data) {
//		System.out.print(node.value);
//
//		return data;
//	}
//
//	public Object visit(ASTOperator node, Object data) {
//		System.out.print(node.value);
//
//		return data;
//	}
//}
