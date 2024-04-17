import java.util.Optional;

public class StoreNode extends StatementNode{
	private Optional<RegisterNode> register;
	
	public StoreNode(Optional<RegisterNode> register) {
		this.register = register;
	}
	
	public RegisterNode getRegister() {
		return register.get();
	}
	
	public String toString() {
		return register.toString();
	}
}
