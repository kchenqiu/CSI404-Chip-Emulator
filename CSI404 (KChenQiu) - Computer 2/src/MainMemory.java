
public class MainMemory {
	static Word[] memory = new Word[1024];
	
	
	public static Word read(Word address) {
		Word newWord = new Word();
		
		//checks if the address is not out of bounds
		//checks if the word is not null
		if(address.getUnsigned() <= 1024 && memory[(int)(address.getUnsigned())] != null) {
			newWord.copy(memory[(int)(address.getUnsigned())]);
		}
		else {
			throw new RuntimeException("Invalid word at address");
		}
		return newWord;
	}
	
	public static void write(Word address, Word value) {
		//checks if the address is not out of bounds
		if(address.getUnsigned() <= 1024) {
			memory[(int)(address.getUnsigned())] = new Word();
			//copies values onto the address of the word array
			memory[(int)(address.getUnsigned())].copy(value);
		}
		else {
			throw new RuntimeException("Invalid address");
		}
	}
	
	public static void load(String[] data) {
		//loops for every string
		for(int j = 0; j < data.length; j++) {
			memory[j] = new Word();
			//every string should have 32 1's or 0's so loops through each one
			for(int i = 0; i < 32; i++) {
				//sets the ith bit to false if it is a 0
				if(data[j] != null && data[j].charAt(i) == '0') {
					memory[j].setBit(i, new Bit(false));
				}
				//sets the ith bit to true if it is a 1
				else if(data[j] != null && data[j].charAt(i) == '1') {
					memory[j].setBit(i, new Bit(true));
				}
				//exception in case it is not a 1 or 0 or if it is less than 32 1's and 0's
				else {
					throw new RuntimeException("Invalid character on word " + j + " character " + i);
				}
			}
		}
	}
}
