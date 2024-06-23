import java.util.Optional;

public class BranchNode extends StatementNode{
	enum Operations{
		EQUAL, NOTEQUAL, LESS, GREATER, LESSOREQUAL, GREATEROREQUAL, NONE
	}
	
	private Operations operation;
	private Optional<RegisterNode> register;
	
	public BranchNode(Operations operation, Optional<RegisterNode> register) {
		this.operation = operation;
		this.register = register;
	}
	
	public RegisterNode getRegister() {
		return register.get();
	}
	
	public Operations getOperation() {
		return operation;
	}
	
	public String toString() {
		return "Operation: " + operation + ", " + register.toString();
	}
}
