import java.util.Optional;

public class InterruptNode extends StatementNode{
	private Optional<RegisterNode> register;
	
	public InterruptNode(Optional<RegisterNode> register) {
		this.register = register;
	}
	
	public RegisterNode getRegister() {
		return register.get();
	}
	
	public String toString() {
		return register.toString();
	}
}
