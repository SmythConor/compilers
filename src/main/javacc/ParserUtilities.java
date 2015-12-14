import java.util.Hashtable;
import java.util.Arrays;
import java.util.List;

import java.io.FileInputStream;

import java.io.FileNotFoundException;

/**
 * Utility class to hide all the try/catch stuff
 * All work is my own
 * @author Conor Smyth <conor.smyth39@mail.dcu.ie>
 */
class ParserUtilities {

	/**
	 * Get the file to parse specified by the parameter
	 * @param file name of the file as a string
	 * @return BasicLAnalyser with file
	 */
	public static BasicLAnalyser getFile(String file) {
		try {
			return new BasicLAnalyser(new FileInputStream(file));
		} catch(FileNotFoundException e) {
			System.err.println("File " + file + " not found");

			return null;
		}
	}

	/**
	 * Get the AST for the parser supplied
	 * @param parser the parser to generate the AST from
	 * @return AST representation of the parsed file
	 */
	public static SimpleNode getAst(BasicLAnalyser parser) {
		try {
			return parser.Program();
		} catch(ParseException e) {
			System.out.println(e.getMessage());
			System.out.println("Parser: Encountered errors during parse.");

			return null;
		}
	}

	/**
	 * Perform type checks on the AST
	 * @param program AST representation of the program
	 */
	public static void typeCheckProgram(SimpleNode program) {
		System.out.println("Type Checking:");

		SemanticCheckVisitor tc = new SemanticCheckVisitor();

		program.jjtAccept(tc, null); 
	}

	public static List<Token> convertNodeToList(SimpleNode node) {
		int size = node.jjtGetNumChildren();
		Token[] nodeArray = new Token[size];

		for(int i = 0; i < size; i++) {
			SimpleNode temp = (SimpleNode) node.jjtGetChild(i);
			Token child = (Token) temp.jjtGetValue();

			nodeArray[i] = child;
		}

		return Arrays.asList(nodeArray);
	}
}
