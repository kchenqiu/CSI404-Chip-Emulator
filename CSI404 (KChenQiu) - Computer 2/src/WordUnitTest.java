import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class WordUnitTest {
	Word word1 = new Word();
	Word word2 = new Word();
	Word word3 = new Word();
	Word word4 = new Word();
	long longNumber = 0;
	int intNumber = -2147483648;
	
	Bit on = new Bit(true);
	Bit off = new Bit(false);
	
	@Test
	void WordTest() {
		//tests to make sure the bits are off by default
		assertEquals(off.getValue(), word1.getBit(0).getValue());
		assertEquals(off.getValue(), word1.getBit(14).getValue());
		assertEquals(off.getValue(), word1.getBit(26).getValue());
		assertEquals(off.getValue(), word1.getBit(31).getValue());
		
		//tests the setBits function
		word1.setBit(0, on);
		assertEquals(on.getValue(), word1.getBit(0).getValue());
		
		word1.setBit(0, off);
		assertEquals(off.getValue(), word1.getBit(0).getValue());
		
		//tests and function
		word1.setBit(0, on);
		word3.setBit(0, on);
		//first bit is on for both
		word2 = word1.and(word3);
		assertTrue(word2.getBit(0).getValue());
		
		//only the first bit is on for word 1
		word2 = word1.and(word4);
		assertFalse(word2.getBit(0).getValue());
		
		//neither of the bits are on
		word2 = word2.and(word4);
		assertFalse(word2.getBit(0).getValue());
		
		//tests or function
		word2 = word1.or(word4);
		//only the first bit is on for word1
		assertTrue(word2.getBit(0).getValue());
		
		//the first bit is on for word 1 and the second bit is on for word 4
		word4.setBit(1, on);
		word2 = word1.or(word4);
		assertTrue(word2.getBit(1).getValue());
		assertFalse(word2.getBit(2).getValue());
		
		//resets word 2 and turns on the third bit for word 1 and word 4
		word1.setBit(2, on);
		word4.setBit(2, on);
		word2.setBit(0, off);
		word2.setBit(1, off);
		
		//tests xor function
		word2 = word1.xor(word4);
		assertTrue(word2.getBit(0).getValue());
		assertTrue(word2.getBit(1).getValue());
		assertFalse(word2.getBit(2).getValue());
		
		//tests not function
		word1 = word2.not();
		assertFalse(word1.getBit(0).getValue());
		assertFalse(word1.getBit(1).getValue());
		for(int i = 2; i < 32; i++) {
			assertTrue(word1.getBit(i).getValue());
		}		
		
		//tests the rightShift function
		word2 = word1.rightShift(10);
		
		for(int i = 0; i < 12; i++) {
			assertFalse(word2.getBit(i).getValue());
		}
		for(int i = 12; i < 32; i++) {
			assertTrue(word2.getBit(i).getValue());
		}
		
		//tests the leftShift function
		word2 = word2.leftShift(10);
		
		for(int i = 2; i < 22; i++) {
			assertTrue(word2.getBit(i).getValue());
		}
		for(int i = 22; i < 32; i++) {
			assertFalse(word2.getBit(i).getValue());
		}
		
		//tests the copy function
		word3.copy(word2);
		
		for(int i = 0; i < 32; i++) {
			assertEquals(word3.getBit(i).getValue(), word2.getBit(i).getValue());
		}
	}
	
	@Test
	void WordNumberTests() {
		//resets all the words
		word1 = word1.rightShift(31);
		word2 = word2.rightShift(31);
		word3 = word3.rightShift(31);
		word4 = word4.rightShift(31);
		
		//tests with no bits on
		assertEquals(longNumber, word2.getUnsigned());	
		assertEquals(intNumber, word2.getSigned());
		
		//00000000000000000000000000000001
		word1.set(-2147483647);
		//tests with only one bit on
		longNumber = 1;
		intNumber++;
		assertEquals(longNumber, word1.getUnsigned());
		assertEquals(intNumber, word1.getSigned());
		
		//tests with all the bits on
		//11111111111111111111111111111111
		word1.set(2147483647);
		longNumber = 4294967295L;
		assertEquals(longNumber, word1.getUnsigned());
		intNumber = 2147483647;
		assertEquals(intNumber, word1.getSigned());
		
		//tests with two bits on
		//00000000000000000000000000000011
		word2.set(-2147483645);
		
		longNumber = 3;		
		assertEquals(longNumber, word2.getUnsigned());
	
		//tests with only one bit on again in a different position
		//10000000000000000000000000000000
		word1 = word1.leftShift(31);
		intNumber = 0;
		assertEquals(intNumber, word1.getSigned());
		
		//tests with random amount of bits on
		//10000010001010000000010000000000
		word1.set(36176896);
		
		longNumber = 2183660544L;
		intNumber = 36176896;		
		assertEquals(longNumber, word1.getUnsigned());		
		assertEquals(intNumber, word1.getSigned());

		
		//00000000000000000010000100010100
		word2 = word1.rightShift(17);		
		longNumber = 16660;
		intNumber = -2147466988;
		assertEquals(longNumber, word2.getUnsigned());
		assertEquals(intNumber, word2.getSigned());
		
		//00000000000000100000000000000000
		word3.set(-2147352576);
		longNumber = 131072;
		intNumber = -2147352576;
		assertEquals(longNumber, word3.getUnsigned());
		assertEquals(intNumber, word3.getSigned());
		
		//00100000010001000100000000000010
		word4.set(-1606139902);
		longNumber = 541343746;
		intNumber = -1606139902;
		assertEquals(longNumber, word4.getUnsigned());
		assertEquals(intNumber, word4.getSigned());
		
		word1.set(20623);
		longNumber = 2147504271L;
		intNumber = 20623;				
		assertEquals(longNumber, word1.getUnsigned());
		assertEquals(intNumber, word1.getSigned());
	}
	
	@Test
	void IncrementTest() {
		//testing with 0
		word1.set(-2147483648);
		word1.Increment();
		
		longNumber = 1;
		intNumber = -2147483647;
		assertEquals(longNumber, word1.getUnsigned());
		assertEquals(intNumber, word1.getSigned());
		
		//testing with 1
		word1.Increment();
		longNumber++;
		intNumber++;
		assertEquals(longNumber, word1.getUnsigned());
		assertEquals(intNumber, word1.getSigned());
		
		//testing with a random number
		word1.set(20623);
		word1.Increment();
		longNumber = 2147504272L;
		intNumber = 20624;				
		assertEquals(longNumber, word1.getUnsigned());
		assertEquals(intNumber, word1.getSigned());
		
		//testing consecutive number
		word1.Increment();
		longNumber = 2147504273L;
		intNumber = 20625;				
		assertEquals(longNumber, word1.getUnsigned());
		assertEquals(intNumber, word1.getSigned());
	}
}
