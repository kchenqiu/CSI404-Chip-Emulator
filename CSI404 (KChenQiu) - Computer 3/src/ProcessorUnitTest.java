import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class ProcessorUnitTest {
	String[] mathInstructions = new String[14];
	String[] functionInstructions = new String[16];
	Processor processor;
	
	@Test
	public void MathTest() {
		long longNumber = 0;		
		Word word1, word2, word3;
		MathInstructions();
		MainMemory.load(mathInstructions);
		processor = new Processor();		
		

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
	
	@Test
	public void FunctionTest() {
		//decrement unit test added in WordUnitTest
		long longNumber = 0;		
		Word word1, word2, word3;
		FunctionInstructions();
		MainMemory.load(functionInstructions);
		processor = new Processor();	

		processor.run();
		
		word1 = new Word();
		word2 = new Word();
		word3 = new Word();
		//first instruction stores 2185 in r8
		longNumber = 2185;
		assertEquals(longNumber, processor.getRegister(8).getUnsigned());
		
		//second instruction stores 148616 in r1
		longNumber = 148616;
		assertEquals(longNumber, processor.getRegister(1).getUnsigned());
		
		//third instruction stores 11 in r16
		longNumber = 11;
		assertEquals(longNumber, processor.getRegister(16).getUnsigned());
		
		//fourth instruction skips the fifth instruction
		
		//fifth instruction halts the processor
		
		//sixth instruction storing r8 into MainMemory[112]
		word1.set(-2147483536);
		longNumber = 2185;
		assertEquals(longNumber, MainMemory.read(word1).getUnsigned());
		
		//seventh instruction store instruction 8 in register 2
		word2.setBit(28, new Bit(true));
		assertEquals(processor.getRegister(2).toString(), MainMemory.read(word2).toString());
		
		//ninth instruction stores 2740 in MainMemory[5]
		longNumber = 2740;
		word3.setBit(29, new Bit(true));
		assertEquals(longNumber, MainMemory.read(word3).getUnsigned());
		
		//tenth instruction stores MainMemory[5] into register 4
		assertEquals(longNumber, processor.getRegister(4).getUnsigned());		
		
		//eleventh instruction skips to the fourteenth instruction which leads to the fifth instruction
		
		//twelth instruction stores 2080 in MainMemory[0]
		longNumber = 2080;
		assertEquals(longNumber, MainMemory.read(word1).getUnsigned());
		
		//thirteenth instruction to end the loop at 5th instruction
		longNumber = 873;
		assertEquals(longNumber, processor.getRegister(9).getUnsigned());
	}
	
	public void MathInstructions() {
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
		mathInstructions[0] = immediate + function + rd + opCode;

		//store 148616 in rd 1
		immediate = "100100010010001000";
		rd = "00001";
		mathInstructions[1] = immediate + function + rd + opCode;
		
		//store 11 in rd 16
		immediate = "000000000000001011";
		rd = "10000";
		mathInstructions[2]  = immediate + function + rd + opCode;
		
		//Math add r1 r16 -> r10
		opCode = "00010";
		immediate = "00000000";
		function = "1110";
		r1 = "00001";
		r2 = "10000";
		rd = "01010";
		mathInstructions[3] = immediate + r1 + r2 + function + rd + opCode;
		
		//Math multiply r10 r16 -> r9
		r1 = "01010";
		r2 = "10000";
		rd = "01001";
		function = "0111";
		mathInstructions[4] = immediate + r1 + r2 + function + rd + opCode;
		
		//Math subtract r9 r8 -> r31
		r1 = "01001";
		r2 = "01000";
		rd = "11111";
		function = "1111";
		mathInstructions[5] = immediate + r1 + r2 + function + rd + opCode;
		
		//Math not r9 -> r12
		immediate = "0000000000000";
		r1 = "01001";
		rd = "01100";
		function = "1011";
		opCode = "00011";
		mathInstructions[6] = immediate + r1 + function + rd + opCode;
		
		//Math and r1 r10 -> r11
		r1 = "00001";
		r2 = "01010";
		rd = "01011";
		function = "1000";
		opCode = "00010";		
		immediate = "00000000";
		mathInstructions[7] = immediate + r1 + r2 + function + rd + opCode;
		
		//Math xor r9 r1 -> r3
		r1 = "01001";
		r2 = "00001";
		rd = "00011";
		function = "1010";
		mathInstructions[8] = immediate + r1 + r2 + function + rd + opCode;
		
		//Math or r3 r31 -> r30
		r1 = "00011";
		r2 = "11111";
		rd = "11110";
		function = "1001";
		mathInstructions[9] = immediate + r1 + r2 + function + rd + opCode;
		
		//Math leftShift r1 r16 -> r5
		r1 = "00001";
		r2 = "10000";
		rd = "00101";
		function = "1100";
		mathInstructions[10] = immediate + r1 + r2 + function + rd + opCode;
		
		//Math rightShift r9 r16 -> r4
		r1 = "01001";
		r2 = "10000";
		rd = "00100";
		function = "1101";
		mathInstructions[11] = immediate + r1 + r2 + function + rd + opCode;
		
		//Math multiply r12 r5 -> r14
		r1 = "01100";
		r2 = "00101";
		rd = "00000";
		function = "0111";
		mathInstructions[12] = immediate + r1 + r2 + function + rd + opCode;
		
		//halt
		immediate = "000000000000000000000000000";
		opCode = "00000";
		mathInstructions[13] = immediate + opCode;
	}
	
	public void FunctionInstructions() {
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
		functionInstructions[0] = immediate + function + rd + opCode;

		//store 148616 in rd 1
		immediate = "100100010010001000";
		rd = "00001";
		functionInstructions[1] = immediate + function + rd + opCode;
		
		//store 11 in rd 16
		immediate = "000000000000001011";
		rd = "10000";
		functionInstructions[2]  = immediate + function + rd + opCode;
				
		//jump pc <= pc + 1 (Skip instruction 4)
		immediate = "000000000000000001";
		opCode = "00101";
		functionInstructions[3] = immediate + function + rd + opCode;
		
		//halt
		immediate = "000000000000000000000000000";
		opCode = "00000";
		functionInstructions[4] = immediate + opCode;
		
		//pc <= r1 BOP rd? push pc; pc+immediate
		immediate = "0000000000111";
		r1 = "01001";
		rd = "10000";
		function = "0010";
		opCode = "01011";
		functionInstructions[5] = immediate + r1 + function + rd + opCode;
		
		//MainMemory[rd+ immediate] <= r1 (store 2185 in MainMemory[112])
		immediate = "0000001110000";
		r1 = "01000";
		rd = "00000";
		function = "0000";
		opCode = "10111";
		functionInstructions[6] = immediate + r1 + function + rd + opCode;
		
		//rd <= MainMemory[immediate + r1] (store instruction 8 in register 2)
		immediate = "0000000001000";
		r1 = "00000";
		rd = "00010";
		opCode = "10011";
		functionInstructions[7] = immediate + r1 + function + rd + opCode;
		
		//do nothing(used to check previous instruction)
		immediate = "000000000000000000000000000";
		opCode = "10100";
		functionInstructions[8] = immediate + opCode;
		
		//MainMemory[5] <= rd MOP immediate (store 2185 + 555 (2740) in 4th instruction of main memory)
		immediate = "000000001000101011";
		function = "1110";
		rd = "01000";
		opCode = "01101";
		functionInstructions[9] = immediate + function + rd + opCode;
		
		//rd <= MainMemory[sp-(r1 + immediate)] (5 - (0 + 1)) (stores instruction 4 (2740) in register 4)
		immediate = "0000000000001";
		r1 = "00000";
		rd = "00100";
		opCode = "11011";
		functionInstructions[10] = immediate + r1 + function + rd + opCode;
		
		//pc <= r8 > r16? pc + immediate (go to instruction 15)
		immediate = "00000011";
		r1 = "01000";
		r2 = "10000";
		rd = "00000";
		function = "0100";
		opCode = "00110";
		functionInstructions[11] = immediate + r1 + r2 + function + rd + opCode;
		
		//store 2080 in MainMemory[Register(0)] 
		immediate = "000000100000100000";
		function = "0000";
		rd = "00000";
		opCode = "10101";
		functionInstructions[12] = immediate + function + rd + opCode;
		
		//store 873 in register 9 for instruction 5
		immediate = "000000001101101001";
		rd = "01001";
		opCode = "00001";
		functionInstructions[13] = immediate + function + rd + opCode;
		
		//jump back to instruction 5
		immediate = "000000000000000000000000000";
		opCode = "10000";
		functionInstructions[14] = immediate + opCode;
		
		//jump to instruction 4
		immediate = "000000000000000000000000100";
		opCode = "00100";
		functionInstructions[15] = immediate + opCode;
	}
}
