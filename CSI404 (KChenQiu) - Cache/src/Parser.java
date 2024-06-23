import java.util.HashMap;
import java.util.LinkedList;
import java.util.Optional;

public class Parser {
	private TokenHandler tokenHandler;	
	private LinkedList<StatementNode> statements = new LinkedList<>();
	private ProgramNode program = new ProgramNode(statements);
	private HashMap<Token.TokenType, MathNode.Operations> mopHash = new HashMap<>();
	private HashMap<Token.TokenType, BranchNode.Operations> bopHash = new HashMap<>();
	
	public Parser(Lexer lex) {
		this.tokenHandler = new TokenHandler(lex);
	}
	
	//accepts any amount of separators
	public boolean AcceptSeparators() {
		boolean separator = false;
		if(tokenHandler.MoreTokens() == true && tokenHandler.Peek(0).get().getTokenType().equals(Token.TokenType.LINESEPARATOR)) {
			separator = true;
			while(tokenHandler.Peek(0).get().getTokenType().equals(Token.TokenType.LINESEPARATOR)) {
				tokenHandler.MatchAndRemove(Token.TokenType.LINESEPARATOR);
			}
		}
		return separator;
	}
	
	public ProgramNode Parse() {
		//sets up the hash data
		MOPHash();
		BOPHash();
		//loops until there are no more tokens
		while(tokenHandler.MoreTokens()) {
			if(ParseStatement(program)) {
				
			}
			else {
				throw new RuntimeException("Unexpected Token: " + tokenHandler.Peek(0));
			}
		}
		
		return program;
	}
	
	
	public boolean ParseStatement(ProgramNode program) {
		//Matches the token to the each instruction
		if(tokenHandler.Peek(0).get().getTokenType().equals(Token.TokenType.MATH)) {
			tokenHandler.MatchAndRemove(Token.TokenType.MATH);
			statements.add(ParseMath().get());
			return true;
		}
		else if(tokenHandler.Peek(0).get().getTokenType().equals(Token.TokenType.BRANCH)) {
			tokenHandler.MatchAndRemove(Token.TokenType.BRANCH);
			statements.add(ParseBranch().get());
			return true;
		}
		else if(tokenHandler.Peek(0).get().getTokenType().equals(Token.TokenType.CALL)) {
			tokenHandler.MatchAndRemove(Token.TokenType.CALL);
			statements.add(ParseCall().get());
			return true;
		}
		else if(tokenHandler.Peek(0).get().getTokenType().equals(Token.TokenType.PUSH)) {
			tokenHandler.MatchAndRemove(Token.TokenType.PUSH);
			statements.add(ParsePush().get());
			return true;
		}
		else if(tokenHandler.Peek(0).get().getTokenType().equals(Token.TokenType.LOAD)) {
			tokenHandler.MatchAndRemove(Token.TokenType.LOAD);
			statements.add(ParseLoad().get());
			return true;
		}
		else if(tokenHandler.Peek(0).get().getTokenType().equals(Token.TokenType.STORE)) {
			tokenHandler.MatchAndRemove(Token.TokenType.STORE);
			statements.add(ParseStore().get());
			return true;
		}
		else if(tokenHandler.Peek(0).get().getTokenType().equals(Token.TokenType.PEEK)) {
			tokenHandler.MatchAndRemove(Token.TokenType.PEEK);
			statements.add(ParsePeek().get());
			return true;
		}
		else if(tokenHandler.Peek(0).get().getTokenType().equals(Token.TokenType.POP)) {
			tokenHandler.MatchAndRemove(Token.TokenType.POP);
			statements.add(ParsePop().get());
			return true;
		}
		else if(tokenHandler.Peek(0).get().getTokenType().equals(Token.TokenType.JUMP)) {
			tokenHandler.MatchAndRemove(Token.TokenType.JUMP);
			statements.add(ParseJump().get());
			return true;
		}
		else if(tokenHandler.Peek(0).get().getTokenType().equals(Token.TokenType.HALT)) {
			tokenHandler.MatchAndRemove(Token.TokenType.HALT);
			statements.add(ParseHalt().get());
			return true;
		}
		else if(tokenHandler.Peek(0).get().getTokenType().equals(Token.TokenType.COPY)) {
			tokenHandler.MatchAndRemove(Token.TokenType.COPY);
			statements.add(ParseCopy().get());
			return true;
		}
		else if(tokenHandler.Peek(0).get().getTokenType().equals(Token.TokenType.RETURN)) {
			tokenHandler.MatchAndRemove(Token.TokenType.RETURN);
			statements.add(ParseReturn().get());
			return true;
		}
		else if(tokenHandler.Peek(0).get().getTokenType().equals(Token.TokenType.INTERRUPT)) {
			tokenHandler.MatchAndRemove(Token.TokenType.INTERRUPT);
			statements.add(ParseInterrupt().get());
			return true;
		}
		return true;
	}
	
	//parses a math instruction
	public Optional<StatementNode> ParseMath() {
		MathNode.Operations operation = mopHash.get(tokenHandler.Peek(0).get().getTokenType());
		if(operation != null) {
			tokenHandler.MatchAndRemove(tokenHandler.Peek(0).get().getTokenType());
			Optional<RegisterNode> register = ParseRegister();
			return Optional.of(new MathNode(operation, register));
		}
		else if(tokenHandler.Peek(0).get().getTokenType().equals(Token.TokenType.SHIFT)) {
			tokenHandler.MatchAndRemove(Token.TokenType.SHIFT);
			if(tokenHandler.Peek(0).get().getTokenType().equals(Token.TokenType.LEFT)) {
				tokenHandler.MatchAndRemove(Token.TokenType.LEFT);
				operation = MathNode.Operations.LEFTSHIFT;
				Optional<RegisterNode> register = ParseRegister();
				return Optional.of(new MathNode(operation, register));
			}
			else if(tokenHandler.Peek(0).get().getTokenType().equals(Token.TokenType.RIGHT)) {
				tokenHandler.MatchAndRemove(Token.TokenType.RIGHT);
				operation = MathNode.Operations.RIGHTSHIFT;
				Optional<RegisterNode> register = ParseRegister();
				return Optional.of(new MathNode(operation, register));
			}
		}
		else {
			operation = MathNode.Operations.NONE;
			Optional<RegisterNode> register = ParseRegister();
			return Optional.of(new MathNode(operation, register));
		}
		return null;
	}
	
	//parses a branch instruction
	public Optional<StatementNode> ParseBranch() {
		BranchNode.Operations operation = bopHash.get(tokenHandler.Peek(0).get().getTokenType());
		if(operation != null) {
			tokenHandler.MatchAndRemove(tokenHandler.Peek(0).get().getTokenType());
			Optional<RegisterNode> register = ParseRegister();
			return Optional.of(new BranchNode(operation, register));
		}
		else {
			operation = BranchNode.Operations.NONE;
			Optional<RegisterNode> register = ParseRegister();
			return Optional.of(new BranchNode(operation, register));
		}
	}
	
	//parses a call instruction
	public Optional<StatementNode> ParseCall() {
		BranchNode.Operations operation = bopHash.get(tokenHandler.Peek(0).get().getTokenType());
		if(operation != null) {
			tokenHandler.MatchAndRemove(tokenHandler.Peek(0).get().getTokenType());
			Optional<RegisterNode> register = ParseRegister();
			return Optional.of(new CallNode(operation, register));
		}
		else {
			operation = BranchNode.Operations.NONE;
			Optional<RegisterNode> register = ParseRegister();
			return Optional.of(new CallNode(operation, register));
		}
	}
	
	//parses a push instruction
	public Optional<StatementNode> ParsePush() {
		MathNode.Operations operation = mopHash.get(tokenHandler.Peek(0).get().getTokenType());
		if(operation != null) {
			tokenHandler.MatchAndRemove(tokenHandler.Peek(0).get().getTokenType());
			Optional<RegisterNode> register = ParseRegister();
			return Optional.of(new PushNode(operation, register));
		}
		else if(tokenHandler.Peek(0).get().getTokenType().equals(Token.TokenType.SHIFT)) {
			tokenHandler.MatchAndRemove(Token.TokenType.SHIFT);
			if(tokenHandler.Peek(0).get().getTokenType().equals(Token.TokenType.LEFT)) {
				tokenHandler.MatchAndRemove(Token.TokenType.LEFT);
				operation = MathNode.Operations.LEFTSHIFT;
				Optional<RegisterNode> register = ParseRegister();
				return Optional.of(new PushNode(operation, register));
			}
			else if(tokenHandler.Peek(0).get().getTokenType().equals(Token.TokenType.RIGHT)) {
				tokenHandler.MatchAndRemove(Token.TokenType.RIGHT);
				operation = MathNode.Operations.RIGHTSHIFT;
				Optional<RegisterNode> register = ParseRegister();
				return Optional.of(new PushNode(operation, register));
			}
		}
		else {
			operation = MathNode.Operations.NONE;
			Optional<RegisterNode> register = ParseRegister();
			return Optional.of(new PushNode(operation, register));
		}
		return null;
	}
	
	//parses a load instruction
	public Optional<StatementNode> ParseLoad() {
		Optional<RegisterNode> register = ParseRegister();
		return Optional.of(new LoadNode(register));
	}
	
	//parses a store instruction
	public Optional<StatementNode> ParseStore() {
		Optional<RegisterNode> register = ParseRegister();
		return Optional.of(new StoreNode(register));
	}
	
	//parses a peek instruction
	public Optional<StatementNode> ParsePeek() {
		Optional<RegisterNode> register = ParseRegister();
		return Optional.of(new Pop_PeekNode(register));
	}
	
	//parses a pop instruction
	public Optional<StatementNode> ParsePop() {
		Optional<RegisterNode> register = ParseRegister();
		return Optional.of(new Pop_PeekNode(register));
	}
	
	//parses a jump instruction
	public Optional<StatementNode> ParseJump() {
		BranchNode.Operations operation = BranchNode.Operations.NONE;
		Optional<RegisterNode> register = ParseRegister();
		return Optional.of(new BranchNode(operation, register));
	}
	
	//parses a halt instruction
	public Optional<StatementNode> ParseHalt() {
		return Optional.of(new MathNode(MathNode.Operations.NONE, Optional.of(new NoR(0))));
	}
	
	//parses a copy instruction
	public Optional<StatementNode> ParseCopy() {
		Optional<RegisterNode> register = ParseRegister();
		return Optional.of(new MathNode(MathNode.Operations.NONE, register));
	}
	
	//parses a return instruction
	public Optional<StatementNode> ParseReturn() {
		return Optional.of(new LoadNode(Optional.of(new NoR(0))));
	}
	
	//parses a interrupt instruction
	public Optional<StatementNode> ParseInterrupt() {
		Optional<RegisterNode> register = ParseRegister();
		return Optional.of(new InterruptNode(register));
	}
	
	//parses the registers (used by most of the parsing instruction calls)
	public Optional<RegisterNode> ParseRegister(){
		int r1, r2, r3, immediate;
		Token token;
		//keeps checking for register/number (up to 3 registers and 1 number)
		//returns the proper register node (NoR for only immediate, DestOnly for 1 register, R2 for 2 registers, R3 for 3 registers)
		//defaults immediate to 0 if there is no number value
		if(tokenHandler.Peek(0) != null && tokenHandler.Peek(0).get().getTokenType().equals(Token.TokenType.REGISTER)) {
			token = tokenHandler.MatchAndRemove(Token.TokenType.REGISTER).get();
			r1 = Integer.valueOf(token.getString());
			
			if(tokenHandler.Peek(0) != null && tokenHandler.Peek(0).get().getTokenType().equals(Token.TokenType.REGISTER)) {
				token = tokenHandler.MatchAndRemove(Token.TokenType.REGISTER).get();
				r2 = Integer.valueOf(token.getString());
				
				if(tokenHandler.Peek(0) != null && tokenHandler.Peek(0).get().getTokenType().equals(Token.TokenType.REGISTER)) {
					token = tokenHandler.MatchAndRemove(Token.TokenType.REGISTER).get();
					r3 = Integer.valueOf(token.getString());
					
					if(tokenHandler.Peek(0) != null && tokenHandler.Peek(0).get().getTokenType().equals(Token.TokenType.NUMBER)) {
						token = tokenHandler.MatchAndRemove(Token.TokenType.NUMBER).get();
						immediate = Integer.valueOf(token.getString());
						return Optional.of(new R3(r1, r2, r3, immediate));
					}
					else {
						return Optional.of(new R3(r1, r2, r3, 0));
					}
				}
				else if(tokenHandler.Peek(0) != null && tokenHandler.Peek(0).get().getTokenType().equals(Token.TokenType.NUMBER)) {
					token = tokenHandler.MatchAndRemove(Token.TokenType.NUMBER).get();
					immediate = Integer.valueOf(token.getString());
					return Optional.of(new R2(r1, r2, immediate));
				}
				else {
					return Optional.of(new R2(r1, r2, 0));
				}
			}
			else if(tokenHandler.Peek(0) != null && tokenHandler.Peek(0).get().getTokenType().equals(Token.TokenType.NUMBER)) {
				token = tokenHandler.MatchAndRemove(Token.TokenType.NUMBER).get();
				immediate = Integer.valueOf(token.getString());
				return Optional.of(new DestOnly(r1, immediate));
			}
			else {
				return Optional.of(new DestOnly(r1, 0));
			}
		}
		else if(tokenHandler.Peek(0) != null && tokenHandler.Peek(0).get().getTokenType().equals(Token.TokenType.NUMBER)) {
			token = tokenHandler.MatchAndRemove(Token.TokenType.NUMBER).get();
			immediate = Integer.valueOf(token.getString());
			return Optional.of(new NoR(immediate));
		}
		else {
			return Optional.of(new NoR(0));
		}
	}
	
	//set up for math operation checks
	private void MOPHash() {
		mopHash.put(Token.TokenType.AND, MathNode.Operations.AND);
		mopHash.put(Token.TokenType.OR, MathNode.Operations.OR);
		mopHash.put(Token.TokenType.XOR, MathNode.Operations.XOR);
		mopHash.put(Token.TokenType.NOT, MathNode.Operations.NOT);
		mopHash.put(Token.TokenType.ADD, MathNode.Operations.ADD);
		mopHash.put(Token.TokenType.SUBTRACT, MathNode.Operations.SUBTRACT);
		mopHash.put(Token.TokenType.MULTIPLY, MathNode.Operations.MULTIPLY);
	}
	
	//set up for boolean operation checks
	private void BOPHash() {
		bopHash.put(Token.TokenType.EQUAL, BranchNode.Operations.EQUAL);
		bopHash.put(Token.TokenType.NOTEQUAL, BranchNode.Operations.NOTEQUAL);
		bopHash.put(Token.TokenType.LESS, BranchNode.Operations.LESS);
		bopHash.put(Token.TokenType.GREATER, BranchNode.Operations.GREATER);
		bopHash.put(Token.TokenType.LESSOREQUAL, BranchNode.Operations.LESSOREQUAL);
		bopHash.put(Token.TokenType.GREATEROREQUAL, BranchNode.Operations.GREATEROREQUAL);
	}
}
