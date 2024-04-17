import java.util.Optional;

public class Pop_PeekNode extends StatementNode{
	private Optional<RegisterNode> register;
	
	public Pop_PeekNode(Optional<RegisterNode> register) {
		this.register = register;
	}
	
	public RegisterNode getRegister() {
		return register.get();
	}
	
	public String toString() {
		return register.toString();
	}
}
