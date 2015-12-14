/**
 * STC for holding variable information
 * All work is my own
 * @author Conor Smyth <conor.smyth39@mail.dcu.ie>
 */
public class STC extends Object
{
	private String name;
	private DataType type;
	private String scope;
	private int argCount;

	public STC(String scope) {
		this.scope = scope;	
	}

	public STC(String name, String scope) {
		this.scope = scope;
	}

	public STC(DataType type, String scope) {
		this.type = type;
		this.scope = scope;
	}

	public STC(String name, DataType type, String scope) {
		this.name = name;
		this.type = type;
		this.scope = scope;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setType(DataType type) {
		this.type = type;
	}

	public DataType getType() {
		return type;
	}

	public String getScope() {
		return scope;
	}

	public void setArgCount(int argCount) {
		this.argCount = argCount;
	}

	public int getArgCount() {
		return argCount;
	}

	public String toString() {
		StringBuilder builder = new StringBuilder()
			.append("SimpleNode: ")
			.append("[")
			.append("Name: ")
			.append(name)
			.append(", ")
			.append("Type: ")
			.append(type)
			.append(", ")
			.append("Scope: ")
			.append(scope)
			.append("]");

		return builder.toString();
	}
}

//For scope
//We use global for global scope
//we use function name for function scope
//if increment one
//if++
//	if++
//	else
//  --
//--
//if++
//
//--
//
//
//
//
//add all possible scopes
