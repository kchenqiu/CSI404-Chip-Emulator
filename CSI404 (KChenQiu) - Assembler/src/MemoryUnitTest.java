
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class MemoryUnitTest {
	
	@Test
	public void Memorytest() {
		Word word1 = new Word();
		Word address = new Word();
		String[] string = new String[6];
		String[] string2 = new String[5];
		string[0] = "00000000000000000000000000000000";
		string[1] = "00000000000000000000000000000001";
		string[2] = "00000100000000101000000100010010";
		string[3] = "10000000000000000000000000000000";
		string[4] = "00000010001000000100000000100010";
		string[5] = "01000000000000000000000000010000";
		MainMemory.load(string);
		
		//checks first index, both should contain all f bits
		assertEquals(MainMemory.read(address).toString(), word1.toString());
		
		//checks the second index, both should contain only a t bit at the 31
		address.setBit(31, new Bit(true));
		word1.setBit(31, new Bit(true));
		assertEquals(MainMemory.read(address).toString(), word1.toString());
		
		//1 index
		word1.set(32053);
		address.set(-2147483648);
		MainMemory.write(address , word1);
		assertEquals(MainMemory.read(address).toString(), word1.toString());
		
		//1024 index
		word1.set(-89413);
		address.set(-2147482625);
		MainMemory.write(address, word1);
		assertEquals(MainMemory.read(address).toString(), word1.toString());
		
		//503 index
		word1.set(-89413);
		address.set(-2147482625);
		MainMemory.write(address, word1);
		assertEquals(MainMemory.read(address).toString(), word1.toString());
		
		string2[0] = "000000000000010000000011000000000";
		string2[1] = "00000000011000000010000000000001";
		string2[2] = "00000100100000101000000100011110";
		string2[3] = "10000000001101101000000001010001";
		string2[4] = "11111111111111111111111111111111";
		

		MainMemory.load(string2);
		
		//1 index
		word1.set(-2147220736);
		address.set(-2147483648);
		assertEquals(MainMemory.read(address).toString(), word1.toString());
		
		//5 index
		word1.set(2147483647);
		address.set(-2147483644);
		assertEquals(MainMemory.read(address).toString(), word1.toString());
	}

}
