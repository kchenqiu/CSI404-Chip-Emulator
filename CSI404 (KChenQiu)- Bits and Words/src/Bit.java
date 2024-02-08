
public class Bit {
	
	//storage to represent the bit
	private boolean bit = false;
	
	//bit constructor 
	public Bit(boolean bit) {
		this.bit = bit;
	}
	
	//empty constructor
	public Bit() {
		
	}
	
	//sets the value of the bit
	public void set(boolean value) {
		bit = value;
	}
	
	//changes the value of the bit from true to false or false to true
	public void toggle() {
		if(bit) {
			bit = false;
		}
		else {
			bit = true;
		}
	}
	
	//sets the bit to true
	public void set() {
		bit = true;
	}
	
	//sets the bit to false
	public void clear() {
		bit = false;
	}
	
	//returns the current value of the bit
	public boolean getValue() {
		return bit;
	}
	
	//performs and on two bits and returns a new bit set to the result
	public Bit and(Bit other) {
		if(bit == true) {
			if(other.getValue() == true) {
				Bit newBit = new Bit(true);
				return newBit;
			}
			else {
				Bit newBit = new Bit(false);
				return newBit;
			}
		}
		else {
			Bit newBit = new Bit(false);
			return newBit;
		}
	}
	
	//performs or on two bits and returns a new bit set to the result
	public Bit or(Bit other) {
		if(bit == true) {
			Bit newBit = new Bit(true);
			return newBit;
		}
		else if(other.getValue() == true) {
			Bit newBit = new Bit(true);
			return newBit;
		}
		else {
			Bit newBit = new Bit(false);
			return newBit;
		}
		
	}
	
	//performs xor on two bits and returns a new bit set to the result
	public Bit xor(Bit other) {
		if(bit != other.getValue()) {
			Bit newBit = new Bit(true);
			return newBit;
		}
		else {
			Bit newBit = new Bit(false);
			return newBit;
		}
	}
	
	//performs not on the existing bit, returning the result as a new bit
	public Bit not() {
		if(bit == true) {
			Bit newBit = new Bit(false);		
			return newBit;
		}
		else {
			Bit newBit = new Bit(true);
			return newBit;
		}
	}
	
	//returns “t” or “f”
	public String toString() {
		if(bit) {
			return "t";
		}
		else {
			return "f";
		}
	}
}
