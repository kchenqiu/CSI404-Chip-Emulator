
public class Word {
	Bit[] word = new Bit[32];

	//constructor to create a new word object and fill it with bits
	public Word() {
		for(int i = 0; i < 32; i++) {
			word[i] = new Bit();
		}
	}
	
	//Get a new Bit of the ith Bit saved in the word
	public Bit getBit(int i) {
		if(word[i].getValue() == true) {
			return new Bit(true);
		}
		else {
			return new Bit(false);
		}
	}
	
	//set ith bit to the value given
	public void setBit(int i, Bit value) {
		word[i] = value;
	}
	
	//loops through the words and uses the bit and operator
	public Word and(Word other) {
		Word newWord = new Word();
		for(int i = 0; i < 32; i++) {
			newWord.setBit(i, word[i].and(other.getBit(i)));
		}
		return newWord;
	}
	
	//loops through the words and uses the bit or operator
	public Word or(Word other) {
		Word newWord = new Word();
		for(int i = 0; i < 32; i++) {
			newWord.setBit(i, word[i].or(other.getBit(i)));
		}
		return newWord;
	}
	
	//loops through the words and uses the bit xor operator 
	public Word xor(Word other) {
		Word newWord = new Word();
		for(int i = 0; i < 32; i++) {
			newWord.setBit(i, word[i].xor(other.getBit(i)));
		}
		return newWord;
	}
	
	//loops through the word and uses the bit not operator
	public Word not() {
		Word newWord = new Word();
		for(int i = 0; i < 32; i++) {
			newWord.setBit(i, word[i].not());
		}
		return newWord;
	}
	
	//right shift this word by amount bits, creating a new Word
	public Word rightShift(int amount) {
		Word newWord = new Word();
		for(int i = 0; i < 32 - amount; i++) {
			newWord.setBit(i + amount, word[i]);
		}
		return newWord;
	}
	
	//left shift this word by amount bits, creating a new Word
	public Word leftShift(int amount) {
		Word newWord = new Word();
		for(int i = 31; i >= amount; i--) {
			newWord.setBit(i - amount , word[i]);
		}
		return newWord;
	}
	
	//returns a comma separated string t’s and f’s
	public String toString() {
		String toString = "";
		for(int i = 0; i < 32; i++) {
			if(i < 31) {
				toString = toString + word[i].toString() + ", ";
			}
			else {
				toString = toString + word[i].toString();
			}
		}
		return toString;
	}
	
	//returns the value of this word as a long
	//checks each bit and adds 2^i based on the position of the bit
	public long getUnsigned() {
		long number = 0;
		long base = 1;
		for(int i = 0; i < 32; i++) {
			if(word[31 - i].getValue()) {
				number += base;
			}
			base *= 2;
		}
		return number;
	}
	
	//returns the value of this word as an int
	//same as getUnsigned() but starts off with the lowest int value
	public int getSigned() {
		int number = -2147483648;
		int base = 1;
		for(int i = 0; i < 32; i++) {
			if(word[31 - i].getValue()) {
				number += base;

			}				
			base *= 2;
		}
		return number;
	}
	
	//copies the values of the bits from another Word into this one
	public void copy(Word other) {
		for(int i = 0; i < 32; i++) {
			word[i].set(other.getBit(i).getValue());
		}
	}
	
	//set the value of the bits of this Word (used for tests)
	//if base is greater or equal to the value given, subtract from value and turns on the bit
	public void set(int value) {
		long base = 4294967296L;
		long longValue = value + 2147483648L;

		for(int i = 0; i < 32; i++) {			
			base = base / 2;
			if(longValue >= base) {
				word[i].set();
				longValue -= base;		
			}				
			else {
				word[i].clear();
			}
		}
	}
	
	public void Increment() {
		Word carry = new Word();
		carry.setBit(31, new Bit(true));
		for(int i = 31; i >= 0; i--) {
			if(i-1 >= 0) {
				carry.setBit(i-1, word[i].and(carry.getBit(i)));
			}			
			word[i].set(word[i].xor(carry.getBit(i)).getValue());
		}
	}
	
	public void Decrement() {
		Word carry = new Word();
		Word negate = new Word();
		for(int i = 0; i < 32; i++) {
			negate.setBit(i, new Bit(true));
		}
		for(int i = 31; i >= 0; i--) {
			if(i-1 >= 0) {
				carry.setBit(i-1, (word[i].and(negate.getBit(i)).or((word[i].xor(negate.getBit(i))).and(carry.getBit(i)))));
			}			
			word[i].set((word[i].xor(negate.getBit(i))).xor(carry.getBit(i)).getValue());
		}
	}
}
