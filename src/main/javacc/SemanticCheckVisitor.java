import java.util.List;
import java.util.LinkedList;

/**
 * SemanticCheckVisitor for parsing the abstract syntax tree and do some 
 * semantic checks
 * All work is my own
 * @author Conor Smyth <conor.smyth39@mail.dcu.ie>
 */
@SuppressWarnings("unchecked")
public class SemanticCheckVisitor implements BasicLAnalyserVisitor {
	private List<STC> symbolTable = new LinkedList<>();

	private final String GLOBAL = "global";
	private final String MAIN = "main";

	private String scope = GLOBAL;

	public SemanticCheckVisitor() {}

	/**
	 * Accept the node
	 * @param node root node for the ndoe
	 * @param data
	 * @return null
	 */
	public Object visit(SimpleNode node, Object data) {
		node.childrenAccept(this, data);

		return null;
	}

	/**
	 * Go through the nodes for this program
	 * @param node root node for the program
	 * @param data
	 * @return null
	 */
	public Object visit(ASTProgram node, Object data) {
		node.childrenAccept(this, data);

		printTable();

		return null;
	}

	/**
	 * Go through the nodes of the Var declaration and do some semantic checks
	 * @param node root node for this var declaration
	 * @param data
	 * @return null
	 */
	public Object visit(ASTVarDeclaration node, Object data) {
		int size = node.jjtGetNumChildren();

		for(int i = 0; i < size; i += 2) {
			/* Get the list of identifiers */
			List<Token> identList = (List<Token>) node.jjtGetChild(i).jjtAccept(this, data);

			/* Get the type of identifiers */
			Token type = (Token) node.jjtGetChild(i + 1).jjtAccept(this, data);

			for (Token identifier : identList) {
				/* Create the STC for this var */
				STC variable = new STC(scope);

				/* Get and set the datatype for this var */
				DataType dataType = getType(type);
				variable.setType(dataType);

				if(!isDeclaredInScope(identifier.image)) {
					variable.setName(identifier.image);

					symbolTable.add(variable);
				} else {
					String whereDeclared = getScope(identifier.image);
					System.out.println(identifier.image + " already declared in scope " + whereDeclared);
				}
			}
		}

		return null;
	}

	/**
	 * Go through the nodes of the const declaration and do some semantic checks
	 * @param node root node for this const declaration
	 * @param data
	 * @return null
	 */
	public Object visit(ASTConstDeclaration node, Object data) {
		/* Get the name */
		Token identifier = (Token) node.jjtGetChild(0).jjtAccept(this, data);

		/* Get the type */
		Token type = (Token) node.jjtGetChild(1).jjtAccept(this, data);

		/* Get the value */
		Token value = (Token) node.jjtGetChild(2).jjtAccept(this, data);

		if(type.kind == BasicLAnalyserConstants.BOOL) {
			if(!isBool(value.kind)) {
				System.out.println("Error: " + identifier.image + " is type " + type.image + ". Cannot assign " + value.image);
			}
		} else {
			if(isBool(value.kind)) {
				System.out.println("Error: " + identifier.image + " is type " + type.image + ". Cannot assign " + value.image);
			}
		}

		STC variable = new STC(DataType.CONST, scope);

		/* Ensure const not already declared */
		if(!isDeclaredInScope(identifier.image)) {
			variable.setName(identifier.image);

			symbolTable.add(variable);
		} else {
			String whereDeclared = getScope(identifier.image);
			System.out.println(identifier.image + " already declared in scope " + whereDeclared);
		}

		return null;
	}

	/**
	 * Go through and do some semantic checks on the function 
	 * @param node root node for this function
	 * @param data
	 * @return null
	 */
	public Object visit(ASTFunction node, Object data) {
		int numChildren = node.jjtGetNumChildren();
		int tracker = 0;

		Token functionType = (Token) node.jjtGetChild(tracker).jjtAccept(this, data);
		Token functionIdentifier = (Token) node.jjtGetChild(++tracker).jjtAccept(this, data);

		scope = functionIdentifier.image;

		List<Token> paramList = (List<Token>) node.jjtGetChild(++tracker).jjtAccept(this, data);

		STC functionStc = new STC(functionIdentifier.image, DataType.FUNC, functionIdentifier.image);

		int argCount = 0;

		for(int i = 0; i < paramList.size(); i += 2) {
			Token identifier = paramList.get(i);
			Token type = paramList.get(i + 1);

			DataType dataType = getType(type);

			STC variable = new STC(identifier.image, dataType, scope);

			if(!isDeclaredInScope(variable.getName())) {
				symbolTable.add(variable);
			} else {
				String whereDeclared = getScope(identifier.image);
				System.out.println(variable.getName() + " already declared in scope " + whereDeclared);
			}

			argCount++;
		}

		/* Set the number of params for later checks */
		functionStc.setArgCount(argCount);

		/* Add th function to the symbol table */
		symbolTable.add(functionStc);

		node.childrenAccept(this, data);

		/* Set the scope to global again */
		scope = GLOBAL;

		return null;
	}

	/**
	 * Get the param list for the node
	 * @param node this node
	 * @param data
	 * @return paramList list of the params
	 */
	public Object visit(ASTParamList node, Object data) {
		List<Token> paramList = ParserUtilities.convertNodeToList(node);

		return paramList;
	}

	/**
	 * Get the value for the type of this node
	 * @param node this node
	 * @param data
	 * @return type of this node 
	 */
	public Object visit(ASTType node, Object data) {
		return node.jjtGetValue();
	}


	/**
	 * Go through the main of the program
	 * @param node the root of the main program branch of the tree
	 * @param data
	 * @return null
	 */
	public Object visit(ASTMainProg node, Object data) {
		/* Set the scope to main */
		scope = MAIN;

		node.childrenAccept(this, data);

		/* Set the scope back to global */
		scope = GLOBAL;

		return null;
	}

	/**
	 * Go through the statement node
	 * @param node root node for the statement
	 * @param data
	 * @return null
	 */
	public Object visit(ASTStatement node, Object data) {
		node.childrenAccept(this, data);

		return null;
	}

	/**
	 * Return the value for this expression
	 * @param node root node for the expression
	 * @param data
	 * @return value for this node 
	 */
	public Object visit(ASTExpression node, Object data) {
		return (Token) node.jjtGetChild(0).jjtAccept(this, data);
	}

	/**
	 * Return the value for this term
	 * @param node root node for the term
	 * @param data
	 * @return value for this node 
	 */
	public Object visit(ASTTerm node, Object data) {
		return (Token) node.jjtGetChild(0).jjtAccept(this, data);
	}

	/**
	 * Return the value for this fragment
	 * @param node root node for the fragment
	 * @param data
	 * @return value for this node 
	 */
	public Object visit(ASTFragment node, Object data) {
		node.childrenAccept(this, data);

		return (Token) node.jjtGetChild(0).jjtAccept(this, data);
	}

	/**
	 * Go through the nodes for this condition
	 * @param node root node for the condition
	 * @param data
	 * @return null
	 */
	public Object visit(ASTCondition node, Object data) {
		node.childrenAccept(this, data);

		return null;
	}

	/**
	 * Go through the Condtional Expression
	 * @param node root node for this Condtional Expression
	 * @param data
	 * @return null
	 */
	public Object visit(ASTConditionalExpression node, Object data) {
		//check type of lhs and assert rhs is the same
		//Token lhs = (Token) node.jjtGetChild(0).jjtAccept(this, data);
		//Token op = (Token) node.jjtGetChild(1).jjtAccept(this, data);
		//Token rhs = (Token) node.jjtGetChild(2).jjtAccept(this, data);

		//return (Token) node.jjtGetChild(0).jjtAccept(this, data);
		node.childrenAccept(this, data);

		return null;
	}

	/**
	 * Parse through and do some checks on the function call
	 * @param node the root node for this fucntion call
	 * @param data
	 * @return null
	 */
	public Object visit(ASTFunctionCall node, Object data) {
		Token functionName = (Token) node.jjtGetChild(0).jjtAccept(this, data);
		List<Token> functionArgs = (List<Token>) node.jjtGetChild(1).jjtAccept(this, data);

		/* Get the functoin for checks */
		STC function = getFunction(functionName.image);

		if(function.getScope().equals("Not found")) {
			System.out.println("Fucntion " + functionName.image + " does not exist");
		}

		if(function.getArgCount() != functionArgs.size()) {
			System.out.println("Wrong number of arguments");
		}

		return null;
	}

	/**
	 * Get the list of identifiers
	 * @param node root node for identifier list
	 * @param data
	 * @return identifier list as a List
	 */
	public Object visit(ASTIdentList node, Object data) {
		List<Token> identList = ParserUtilities.convertNodeToList(node);

		return identList;
	}

	/**
	 * Get the arg list for the node
	 * @param node root node for arg list
	 * @param data
	 * @return arg list as a list
	 */
	public Object visit(ASTArgList node, Object data) {
		List<Token> argList = ParserUtilities.convertNodeToList(node);

		return argList;
	}

	/**
	 * Get the value for this identifier node
	 * @param node root node for this identifier
	 * @param data
	 * @return the value for this identifier
	 */
	public Object visit(ASTIdentifier node, Object data) {
		return (Token) node.jjtGetValue();
	}

	/**
	 * Get the value for this number
	 * @param node root node for this number
	 * @param data
	 * @return the value for this number
	 */
	public Object visit(ASTNumber node, Object data) {
		return (Token) node.jjtGetValue();
	}

	/**
	 * Get the value for this bool node
	 * @param node root node for the bool
	 * @param data
	 * @return value for the bool node
	 */
	public Object visit(ASTBool node, Object data) {
		return (Token) node.jjtGetValue();
	}

	/**
	 * Go through the children nodes for this node
	 * @param node the root node for the conditional Operator
	 * @param data
	 * @return null
	 */
	public Object visit(ASTConditionalOperator node, Object data) {
		return (Token) node.childrenAccept(this, data);
	}

	/**
	 * Go through the children nodes for this node
	 * @param node the root node for the add sub operator
	 * @param data
	 * @return null
	 */
	public Object visit(ASTAddSubOperator node, Object data) {
		node.childrenAccept(this, data);

		return null;
	}

	/**
	 * GO through the children nodes for this node
	 * @param node the root node for the mul div operator
	 * @param data
	 * @return null
	 */
	public Object visit(ASTMulDivOperator node, Object data) {
		node.childrenAccept(this, data);

		return null;
	}

	/**
	 * Check if a variable is declared in the scope
	 * @param name the name of the variable to check
	 * @return true if the variable is declared in the scope
	 */
	private boolean isDeclaredInScope(String name) {
		for(STC var : symbolTable) {
			if(var.getScope().equals(scope)) {
				if(var.getName().equals(name)) {
					return true;
				}
			} else if(var.getScope().equals(GLOBAL)) {
				if(var.getName().equals(name)) {
					return true;
				}
			}
		}

		return false;
	}

	/**
	 * Get the scope for the variable with the name supplied
	 * @param name the name of the variable to find
	 * @return the scope of the variable as a string
	 */
	private String getScope(String name) {
		for(STC var : symbolTable) {
			if(var.getName().equals(name)) {
				return var.getScope();
			}
		}

		return "Not found";
	}

	/**
	 * Print out the symbol table
	 */
	public void printTable() {
		for(STC var : symbolTable) {
			System.out.println(var);
		}
	}

	/**
	 * check if the kind passed it a bool
	 * @param kind the kind of the token
	 * @return true if the kind is a bool
	 */
	private boolean isBool(int kind) {
		return kind == BasicLAnalyserConstants.TRUE || kind == BasicLAnalyserConstants.FALSE;
	}

	/**
	 * Get the function with the name
	 * @param name the name of the function to get
	 * @return the function as a STC
	 */
	private STC getFunction(String name) {
		for(STC t : symbolTable) {
			if(t.getName().equals(name)) {
				if(t.getType().equals(DataType.FUNC)) {
					return t;
				}
			}
		}

		System.out.println("Error function not found");
		return new STC("Not found");
	}

	/**
	 * Get the data type for the token
	 * @param type the token to get the type of
	 * @return type of the token as a DataType
	 */
	private DataType getType(Token type) {
		if(type.kind == BasicLAnalyserConstants.BOOL) {
			return DataType.BOOL;
		} else {
			return DataType.INT;
		}
	}
}
