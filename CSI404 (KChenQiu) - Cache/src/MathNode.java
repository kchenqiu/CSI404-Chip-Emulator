import java.util.Optional;

public class MathNode extends StatementNode{
	enum Operations{
		AND, OR, XOR, NOT, LEFTSHIFT, RIGHTSHIFT, ADD, SUBTRACT, MULTIPLY, NONE
	}
	
	private Operations operation;
	private Optional<RegisterNode> register;
	
	public MathNode(Operations operation, Optional<RegisterNode> register) {
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
		return "Operation: " + operation.toString() + ", " + register.toString();
	}
}
