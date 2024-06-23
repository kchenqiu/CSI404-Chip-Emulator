import java.util.LinkedList;

public class ProgramNode extends Node{
	private LinkedList<StatementNode> statements;

	public ProgramNode(LinkedList<StatementNode> statements) {
		this.statements = statements;
	}
	
	public LinkedList<StatementNode> getStatements(){
		return statements;
	}
	
	
	public String toString() {
		return statements.toString();
	}
}
