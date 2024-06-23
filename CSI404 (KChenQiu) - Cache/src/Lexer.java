import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class Lexer
{
	//initializes the private variables
    private StringHandler stringHandler;
    private int lineNumber;
    private int charPosition;
    private List<Token> tokens;
    private HashMap<String, Token.TokenType> keywords = new HashMap<>();
    
    //constructor to take in the document and initializes the string handler and position
    public Lexer(String input) {
    	this.stringHandler = new StringHandler(input);
    	this.lineNumber = 1;
    	this.charPosition = 1;
    	this.tokens = new LinkedList<>();
    }
    
    //main method that uses the string handler to break down and assign tokens to strings and numbers
    public List<Token> Lex(){
    	KeyWordHelper(); 		
    	while(stringHandler.IsDone() == false) {
    		char nextChar = stringHandler.peek(0);
    		String string = String.valueOf(nextChar); 		
    		//leads to ProcessWord method if the next character is a letter 
    		if(Character.isLetter(nextChar) == true ) {
    			tokens.add(ProcessWord());
    		}
    		
    		//leads to ProcessDigit method if the next character is a number
    		else if(Character.isDigit(nextChar) == true) {
    			tokens.add(ProcessDigit());
    		}
    		
    		//moves onto the next character if input is a tab or space
    		else if(nextChar == ' ' || nextChar == '\t') {
    			stringHandler.GetChar();
    			charPosition++;
    		}
    		
    		//creates a token for a line separator if there is a new line
    		else if(nextChar == '\n') {
    			tokens.add(new Token (Token.TokenType.LINESEPARATOR, lineNumber, charPosition));
    			stringHandler.GetChar();
    			lineNumber++;
    			charPosition = 1;
    		}
    		
    		//does nothing if it is carriage return
    		else if(nextChar == '\r') {
    			stringHandler.GetChar();
    		}
    		
    		//throws an exception if there is a unrecognized character
    		else {
    			throw new RuntimeException("Unrecognized character at line " + lineNumber + ", position " + charPosition);
    		}
    		
    	}
    	
    	//returns and prints out the tokens in a format
    	return tokens;
    }
    
    //Creates the string and assigns a WORD token to it
    private Token ProcessWord() {
    	StringBuilder builder = new StringBuilder();

    	//continues until it reaches a character that is not a letter, number or _
    	while(Character.isLetter(stringHandler.peek(0))) {
    		builder.append(stringHandler.GetChar());
    		charPosition++;
    	}
    	String word = builder.toString();
    	
    	//checks if it is a keyword
    	if(keywords.containsKey(word)) {
    		return new Token(keywords.get(word), lineNumber, charPosition - word.length());
    	}
    	
    	//checks if it is a register
    	if(word.equals("R")) {
    		builder = new StringBuilder();
    		while(Character.isDigit(stringHandler.peek(0))) {
    			builder.append(stringHandler.GetChar());
    			charPosition++;
    		}
    		word = builder.toString();    	
    	}
    	return new Token(Token.TokenType.REGISTER, word, lineNumber, charPosition - word.length());
    }
    
    //Creates a string of digits and assigns a NUMBER token to it
    private Token ProcessDigit() {
    	StringBuilder builder = new StringBuilder();
    	boolean decimal = false;
    	
    	//checks if it starts off with a decimal
    	if(stringHandler.peek(0) == '.'){
    		decimal = true;
    		builder.append(stringHandler.GetChar());
    	}
    	
    	//continues until it reaches a character that is not a decimal or number
    	while(Character.isDigit(stringHandler.peek(0)) || stringHandler.peek(0) == '.'  && stringHandler.peek(0)!=' ') {
    		char nextChar = stringHandler.GetChar();
    		charPosition++;
    		//throws an exception if two decimals were used
    		if(nextChar == '.' && decimal == true) {
    			throw new RuntimeException("Unrecognized input at line " + lineNumber + ", position " + charPosition);
    		}
    		else if(nextChar == '.' && decimal == false) {
    			decimal = true;
    		}
    		
    		builder.append(nextChar);
    	}
    	String number = builder.toString();
    	return new Token(Token.TokenType.NUMBER, number, lineNumber, charPosition - number.length());
    }
    
    //math, add, subtract, multiply, and, or, not, xor, copy, halt, branch, jump, call, push, load, 
    //return, store, peek, pop, interrupt, equal, unequal, greater, less, greaterOrEqual, lessOrEqual, 
    //shift, left, right
    
    //helper method to input all the keywords into a hashmap
    private void KeyWordHelper(){
    	keywords.put("math", Token.TokenType.MATH);
    	keywords.put("add", Token.TokenType.ADD);
    	keywords.put("subtract", Token.TokenType.SUBTRACT);
    	keywords.put("multiply", Token.TokenType.MULTIPLY);
    	keywords.put("and", Token.TokenType.AND);
    	keywords.put("or", Token.TokenType.OR);
    	keywords.put("not", Token.TokenType.NOT);
    	keywords.put("xor", Token.TokenType.XOR);
    	keywords.put("copy", Token.TokenType.COPY);
    	keywords.put("halt", Token.TokenType.HALT);
    	keywords.put("branch", Token.TokenType.BRANCH);
    	keywords.put("jump", Token.TokenType.JUMP);
    	keywords.put("call", Token.TokenType.CALL);
    	keywords.put("push", Token.TokenType.PUSH);
    	keywords.put("load", Token.TokenType.LOAD);
    	keywords.put("return", Token.TokenType.RETURN);
    	keywords.put("store", Token.TokenType.STORE);
    	keywords.put("peek", Token.TokenType.PEEK);
    	keywords.put("pop", Token.TokenType.POP);
    	keywords.put("interrupt", Token.TokenType.INTERRUPT);
    	keywords.put("equal", Token.TokenType.EQUAL);
    	keywords.put("notequal", Token.TokenType.NOTEQUAL);
    	keywords.put("greater", Token.TokenType.GREATER);
    	keywords.put("less", Token.TokenType.LESS);
    	keywords.put("greaterOrEqual", Token.TokenType.GREATEROREQUAL);
    	keywords.put("lessOrEqual", Token.TokenType.LESSOREQUAL);
    	keywords.put("shift", Token.TokenType.SHIFT);
    	keywords.put("left", Token.TokenType.LEFT);
    	keywords.put("right", Token.TokenType.RIGHT);
    }

}