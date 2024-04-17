import java.util.Optional;

public class CallNode extends StatementNode{

	private BranchNode.Operations operation;
	private Optional<RegisterNode> register;
	
	public CallNode(BranchNode.Operations operation, Optional<RegisterNode> register) {
		this.operation = operation;
		this.register = register;
	}
	
	public RegisterNode getRegister() {
		return register.get();
	}
	
	public BranchNode.Operations getOperation() {
		return operation;
	}
	
	public String toString() {
		return "Operation: " + operation + ", " + register.toString();
	}
}
