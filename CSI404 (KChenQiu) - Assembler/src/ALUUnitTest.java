import static org.junit.jupiter.api.Assertions.*;


import org.junit.jupiter.api.Test;

public class ALUUnitTest {
	Word word1 = new Word();
	Word word2 = new Word();
	Word word3 = new Word();
	Word word4 = new Word();
	Word result = new Word();
	Bit[] operation = new Bit[4];
	ALU alu = new ALU();
	long longNumber = 0;
	int intNumber = 0;
	

	
	@Test
	void ALUTest() {	
		for(int i = 0; i < 4; i++) {
			operation[i] = new Bit();
		}
		
		//and 1000
		alu.op1.setBit(31, new Bit(true));
		alu.op1.setBit(29, new Bit(true));
		alu.op2.setBit(31, new Bit(true));
		alu.op2.setBit(30, new Bit(true));
		operation[0].set();
		operation[1].clear();
		operation[2].clear();
		operation[3].clear();
		alu.doOperation(operation);
		assertTrue(alu.result.getBit(31).getValue());
		assertFalse(alu.result.getBit(30).getValue());
		assertFalse(alu.result.getBit(29).getValue());
		
		//or 1001
		operation[0].set();
		operation[1].clear();
		operation[2].clear();
		operation[3].set();
		alu.doOperation(operation);
		assertTrue(alu.result.getBit(31).getValue());
		assertTrue(alu.result.getBit(30).getValue());
		assertTrue(alu.result.getBit(29).getValue());
		assertFalse(alu.result.getBit(28).getValue());
		
		//xor 1010
		alu.op1.set(-2147483643);
		operation[0].set();
		operation[1].clear();
		operation[2].set();
		operation[3].clear();
		alu.doOperation(operation);
		assertFalse(alu.result.getBit(31).getValue());
		assertTrue(alu.result.getBit(30).getValue());
		assertTrue(alu.result.getBit(29).getValue());
		assertFalse(alu.result.getBit(28).getValue());
		
		//not 1011
		alu.op1.set(-2147483647);
		operation[0].set();
		operation[1].clear();
		operation[2].set();
		operation[3].set();
		alu.doOperation(operation);

		word1.set(-2147483647);		
		word1 = word1.not();
		assertEquals(word1.toString(), alu.result.toString());
		
		//leftshift 1100
		operation[0].set();
		operation[1].set();
		operation[2].clear();
		operation[3].clear();
		
		alu.op1.set(21315);
		alu.op2.set(-2147483645);
		word1.set(21315);
		
		word1 = word1.leftShift(3);
		alu.doOperation(operation);
		assertEquals(word1.toString(), alu.result.toString());
		
		//rightshift 1101
		operation[0].set();
		operation[1].set();
		operation[2].clear();
		operation[3].set();
		
		alu.op1.set(21315);
		alu.op2.set(-2147483645);
		word1.set(21315);
		
		word1 = word1.rightShift(3);
		alu.doOperation(operation);
		assertEquals(word1.toString(), alu.result.toString());
		
		//add 1110
		operation[0].set();
		operation[1].set();
		operation[2].set();
		operation[3].clear();
		
		//537
		alu.op1.set(-2147483111);
		//329
		alu.op2.set(-2147483319);
		
		alu.doOperation(operation);
		longNumber = 866;
		assertEquals(longNumber, alu.result.getUnsigned());
		
		
		//subtract 1111
		operation[0].set();
		operation[1].set();
		operation[2].set();
		operation[3].set();
		
		alu.doOperation(operation);
		longNumber = 208;
		assertEquals(longNumber, alu.result.getUnsigned());
		
		
		//multiply 0111
		
		//537
		alu.op1.set(-2147483111);
		//329
		alu.op2.set(-2147483319);
		operation[0].clear();
		operation[1].set();
		operation[2].set();
		operation[3].set();
		
		alu.doOperation(operation);
		longNumber = 176673;
		assertEquals(longNumber, alu.result.getUnsigned());
	}
	
	@Test
	void Add2Test() {
		//add 1 + 1
		word1.rightShift(31);
		word2.rightShift(31);
		
		word1.setBit(31, new Bit(true));
		word2.setBit(31, new Bit(true));
		result = alu.Add2(word1, word2);
		
		longNumber = 2;
		assertEquals(longNumber, result.getUnsigned());
		
		//add 1421 + 8912 
		word1.set(1421);
		word2.set(8912);
		result = alu.Add2(word1, word2);
		
		longNumber = 10333;
		assertEquals(longNumber, result.getUnsigned());
		
		//add 121312523 + 213093
		word1.set(121312523);
		word2.set(213093);
		result = alu.Add2(word1, word2);
		
		longNumber = 121525616;
		assertEquals(longNumber, result.getUnsigned());
		
		//add max + min
		word1.set(2147483647);
		word2.set(-2147483647);
		result = alu.Add2(word1, word2);
		
		longNumber = 0;
		assertEquals(longNumber, result.getUnsigned());
	}
	
	@Test
	void Add4Test() {
		//add 1 + 1 + 1 + 1
		word1.set(1);
		word2.set(1);
		word3.set(1);
		word4.set(1);
		
		result = alu.Add4(word1, word2, word3, word4);
		
		longNumber = 4;
		assertEquals(longNumber, result.getUnsigned());
		
		//add 12312 + 43450 + 123690 + 123943
		word1.set(12312);
		word2.set(43450);
		word3.set(123690);
		word4.set(123943);

		result = alu.Add4(word1, word2, word3, word4);
		
		longNumber = 303395;
		assertEquals(longNumber, result.getUnsigned());
		
		//add 5 + 10 + 2 + 1
		word1.set(5);
		word2.set(10);
		word3.set(2);
		word4.set(1);
		
		result = alu.Add4(word1, word2, word3, word4);
		
		longNumber = 18;
		assertEquals(longNumber, result.getUnsigned());
		
		//add 2131209123 + 1238109523 + (-45867604) + (-74305)
		word1.set(2131209123);
		word2.set(1238109523);
		word3.set(-45867604);
		word4.set(-74305);
		
		result = alu.Add4(word1, word2, word3, word4);
		
		longNumber = 3323376737L;
		assertEquals(longNumber, result.getUnsigned());
	}
}
