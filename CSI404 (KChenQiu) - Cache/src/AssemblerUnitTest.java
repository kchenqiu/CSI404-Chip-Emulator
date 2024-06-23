import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class AssemblerUnitTest {
	private Lexer lexer;
	private Parser parser;
	private Assembler assembler;
	
	@Test
	public void AssemblerUnitTest() {
		//testing math
		lexer = new Lexer("math add R1 R2 R3");
		parser = new Parser(lexer);
		assembler = new Assembler(parser.Parse());
		assembler.Assemble();
		
		assertEquals("00000000000010001011100001100010", assembler.getInstructions().getFirst());
		
		//testing copy
		lexer = new Lexer("copy R1 123");
		parser = new Parser(lexer);
		assembler = new Assembler(parser.Parse());
		assembler.Assemble();
	
		assertEquals("00000000000111101100000000100001", assembler.getInstructions().getFirst());
		
		//testing halt
		lexer = new Lexer("halt");
		parser = new Parser(lexer);
		assembler = new Assembler(parser.Parse());
		assembler.Assemble();
	
		assertEquals("00000000000000000000000000000000", assembler.getInstructions().getFirst());
		
		//testing load
		lexer = new Lexer("load 234");
		parser = new Parser(lexer);
		assembler = new Assembler(parser.Parse());
		assembler.Assemble();
		
		assertEquals("00000000000000000001110101010000", assembler.getInstructions().getFirst());
		
		//testing store
		lexer = new Lexer("store R10 543");
		parser = new Parser(lexer);
		assembler = new Assembler(parser.Parse());
		assembler.Assemble();
		
		assertEquals("00000000100001111100000101010101", assembler.getInstructions().getFirst());
		
		//testing branch
		lexer = new Lexer("branch equal R20 R1 R31");
		parser = new Parser(lexer);
		assembler = new Assembler(parser.Parse());
		assembler.Assemble();
		
		assertEquals("00000000101000000100001111100110", assembler.getInstructions().getFirst());
		
		//testing push
		lexer = new Lexer("push shift left R1 R16 R0");
		parser = new Parser(lexer);
		assembler = new Assembler(parser.Parse());
		assembler.Assemble();
		
		assertEquals("00000000000011000011000000001110", assembler.getInstructions().getFirst());

		//testing jump
		lexer = new Lexer("jump 10");
		parser = new Parser(lexer);
		assembler = new Assembler(parser.Parse());
		assembler.Assemble();
		
		assertEquals("00000000000000000000000101000100", assembler.getInstructions().getFirst());
		
		//testing return
		lexer = new Lexer("return");
		parser = new Parser(lexer);
		assembler = new Assembler(parser.Parse());
		assembler.Assemble();
		
		assertEquals("00000000000000000000000000010000", assembler.getInstructions().getFirst());
		
		//testing peek
		lexer = new Lexer("peek R0 R16");
		parser = new Parser(lexer);
		assembler = new Assembler(parser.Parse());
		assembler.Assemble();
		
		assertEquals("00000000000000000000001000011011", assembler.getInstructions().getFirst());
	}
	
	//3R = immediate + r1 + r2 + function + rd + opCode; ( immediate = "00000000" )
	//2R = immediate + r1 + function + rd + opCode; ( immediate = "0000000000000"; )
	//Dest Only = immediate + function + rd + opCode; ( immediate = "000000000000000000"; )
	//No R = immediate + opCode; ( immediate = "000000000000000000000000000"; )
}
