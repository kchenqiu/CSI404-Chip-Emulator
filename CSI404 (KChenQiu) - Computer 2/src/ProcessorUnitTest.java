import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class ProcessorUnitTest {
	String[] instructions = new String[14];
	
	@Test
	public void MathTest() {
		long longNumber = 0;		
		Word word1, word2, word3;
		instructions();
		MainMemory.load(instructions);
		Processor processor = new Processor();		
		

		processor.run();
		
		//first instruction stores 2185 in r8
		longNumber = 2185;
		assertEquals(longNumber, processor.getRegister(8).getUnsigned());
		
		//second instruction stores 148616 in r1
		longNumber = 148616;
		assertEquals(longNumber, processor.getRegister(1).getUnsigned());
		
		//third instruction stores 11 in r16
		longNumber = 11;
		assertEquals(longNumber, processor.getRegister(16).getUnsigned());
		
		//fourth instruction does r1 + r16 -> r10
		longNumber = 148627;
		assertEquals(longNumber, processor.getRegister(10).getUnsigned());
		
		//fifth instruction does r10 * r16 -> r9
		longNumber = 1634897;
		assertEquals(longNumber, processor.getRegister(9).getUnsigned());
		
		//sixth instruction does r9 - r8 -> r31
		longNumber = 1632712;
		assertEquals(longNumber, processor.getRegister(31).getUnsigned());
		
		//seventh instruction does not r9 -> r12
		word1 = processor.getRegister(9).not();
		assertEquals(word1.toString(), processor.getRegister(12).toString());
		
		//eighth instruction does and r1 and r10 -> r11
		word1 = processor.getRegister(1);
		word2 = processor.getRegister(10);
		word3 = word1.and(word2);
		assertEquals(word3.toString(), processor.getRegister(11).toString());
		
		//ninth instruction does r9 xor r1 -> r3
		word1 = processor.getRegister(9);
		word2 = processor.getRegister(1);
		word3 = word1.xor(word2);
		assertEquals(word3.toString(), processor.getRegister(3).toString());
		
		//tenth instruction does r3 or r31 -> r30
		word1 = processor.getRegister(3);
		word2 = processor.getRegister(31);
		word3 = word1.or(word2);
		assertEquals(word3.toString(), processor.getRegister(30).toString());
		
		//eleventh instruction does leftshift r1 by r16 -> r5
		word1 = processor.getRegister(1);
		word2 = word1.leftShift((int)(processor.getRegister(16).getUnsigned()));
		assertEquals(word2.toString(), processor.getRegister(5).toString());
		
		//twelfth instruction does rightshift r9 by r16 -> r4
		word1 = processor.getRegister(9);
		word2 = word1.rightShift((int)(processor.getRegister(16).getUnsigned()));
		assertEquals(word2.toString(), processor.getRegister(4).toString());
		
		//thirteenth instruction checks that r0 can not be changed
		longNumber = 0;
		assertEquals(longNumber, processor.getRegister(0).getUnsigned());
		
		//fourteenth instruction halts the processor
	}
	
	public void instructions() {
		String opCode, function, r1, r2, rd, immediate;
		
		//3R = immediate + r1 + r2 + function + rd + opCode; ( immediate = "00000000" )
		//2R = immediate + r1 + function + rd + opCode; ( immediate = "0000000000000"; )
		//Dest Only = immediate + function + rd + opCode; ( immediate = "000000000000000000"; )
		//No R = immediate + opCode; ( immediate = "000000000000000000000000000"; )
		
		//store 2185 in rd 8
		opCode = "00001";
		immediate = "000000100010001001";
		rd = "01000";
		function = "0000";
		instructions[0] = immediate + function + rd + opCode;

		//store 148616 in rd 1
		immediate = "100100010010001000";
		rd = "00001";
		instructions[1] = immediate + function + rd + opCode;
		
		//store 11 in rd 16
		immediate = "000000000000001011";
		rd = "10000";
		instructions[2]  = immediate + function + rd + opCode;
		
		//Math add r1 r16 -> r10
		opCode = "00010";
		immediate = "00000000";
		function = "1110";
		r1 = "00001";
		r2 = "10000";
		rd = "01010";
		instructions[3] = immediate + r1 + r2 + function + rd + opCode;
		
		//Math multiply r10 r16 -> r9
		r1 = "01010";
		r2 = "10000";
		rd = "01001";
		function = "0111";
		instructions[4] = immediate + r1 + r2 + function + rd + opCode;
		
		//Math subtract r9 r8 -> r31
		r1 = "01001";
		r2 = "01000";
		rd = "11111";
		function = "1111";
		instructions[5] = immediate + r1 + r2 + function + rd + opCode;
		
		//Math not r9 -> r12
		immediate = "0000000000000";
		r1 = "01001";
		rd = "01100";
		function = "1011";
		opCode = "00011";
		instructions[6] = immediate + r1 + function + rd + opCode;
		
		//Math and r1 r10 -> r11
		r1 = "00001";
		r2 = "01010";
		rd = "01011";
		function = "1000";
		opCode = "00010";		
		immediate = "00000000";
		instructions[7] = immediate + r1 + r2 + function + rd + opCode;
		
		//Math xor r9 r1 -> r3
		r1 = "01001";
		r2 = "00001";
		rd = "00011";
		function = "1010";
		instructions[8] = immediate + r1 + r2 + function + rd + opCode;
		
		//Math or r3 r31 -> r30
		r1 = "00011";
		r2 = "11111";
		rd = "11110";
		function = "1001";
		instructions[9] = immediate + r1 + r2 + function + rd + opCode;
		
		//Math leftShift r1 r16 -> r5
		r1 = "00001";
		r2 = "10000";
		rd = "00101";
		function = "1100";
		instructions[10] = immediate + r1 + r2 + function + rd + opCode;
		
		//Math rightShift r9 r16 -> r4
		r1 = "01001";
		r2 = "10000";
		rd = "00100";
		function = "1101";
		instructions[11] = immediate + r1 + r2 + function + rd + opCode;
		
		//Math multiply r12 r5 -> r14
		r1 = "01100";
		r2 = "00101";
		rd = "00000";
		function = "0111";
		instructions[12] = immediate + r1 + r2 + function + rd + opCode;
		
		//halt
		immediate = "000000000000000000000000000";
		opCode = "00000";
		instructions[13] = immediate + opCode;
	}
}
