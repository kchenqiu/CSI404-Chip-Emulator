import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class BitUnitTest {
	
	@Test
	void BitTest() {
		Bit bit1 = new Bit(true);
		Bit bit2 = new Bit(true);
		Bit bit3 = new Bit(false);
		Bit bit4 = new Bit(true);
		
		//tests get value
		assertTrue(bit1.getValue());
		
		//tests clear
		bit1.clear();
		assertFalse(bit1.getValue());
		
		//tests set
		bit1.set();
		assertTrue(bit1.getValue());
		
		//tests toggle
		bit1.toggle();
		assertFalse(bit1.getValue());
		
		bit1.toggle();
		assertTrue(bit1.getValue());
		
		//tests and
		bit3 = bit1.and(bit2);
		assertTrue(bit3.getValue());
		
		bit1.clear();
		bit3 = bit1.and(bit2);
		assertFalse(bit3.getValue());
		
		//tests or
		bit3 = bit1.or(bit2);
		assertTrue(bit3.getValue());
		
		bit2.clear();
		bit3 = bit1.or(bit2);
		assertFalse(bit3.getValue());
		
		//tests xor
		bit3.set();
		bit3 = bit1.xor(bit2);
		assertFalse(bit3.getValue());
		
		bit1.set();
		bit3 = bit1.xor(bit2);
		assertTrue(bit3.getValue());
		
		bit2.set();
		bit3 = bit1.xor(bit2);
		assertFalse(bit3.getValue());
		
		//tests not
		bit4 = bit1.not();
		assertFalse(bit4.getValue());
		
		//tests toString
		assertEquals("t", bit1.toString());
		assertEquals("t", bit2.toString());
		assertEquals("f", bit3.toString());
		assertEquals("f", bit4.toString());
	}
}
