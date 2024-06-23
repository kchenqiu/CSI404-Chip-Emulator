import java.util.Optional;

public class PushNode extends StatementNode{
	
	private MathNode.Operations operation;
	private Optional<RegisterNode> register;
	
	public PushNode(MathNode.Operations operation, Optional<RegisterNode> register) {
		this.operation = operation;
		this.register = register;
	}
	
	public RegisterNode getRegister() {
		return register.get();
	}
	
	public MathNode.Operations getOperation(){
		return operation;
	}
	
	public String toString() {
		return "Operation: " + operation.toString() + ", " + register.toString();
	}
}
