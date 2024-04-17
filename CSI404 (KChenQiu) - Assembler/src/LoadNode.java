import java.util.Optional;

public class LoadNode extends StatementNode{
	private Optional<RegisterNode> register;
	
	public LoadNode(Optional<RegisterNode> register) {
		this.register = register;
	}
	
	public RegisterNode getRegister() {
		return register.get();
	}
	
	public String toString() {
		return register.toString();
	}
}
