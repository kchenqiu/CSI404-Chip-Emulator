import java.util.Arrays;

public class ALU {
	public Word op1 = new Word();
	public Word op2 = new Word();
	public Word result = new Word();
	
	public void doOperation(Bit[] operation) {
		int base;
		int shiftAmount;
		Word replacer = new Word();
		Word[] multiplier = new Word[32];
		Word[] adders = new Word[10];
		switch(Arrays.toString(operation)){
		
		//and
		case "[t, f, f, f]":
			replacer = op1.and(op2);
			result.copy(replacer);
			break;
			
		//or
		case "[t, f, f, t]":
			replacer = op1.or(op2);
			result.copy(replacer);
			break;
			
		//xor
		case "[t, f, t, f]":
			replacer = op1.xor(op2);
			result.copy(replacer);
			break;
			
		//not
		case "[t, f, t, t]":
			replacer = op1.not();
			result.copy(replacer);
			break;
			
		//leftshift
		case "[t, t, f, f]":
			//gets the last 5 bits of op2 and converts it to a number to shift
			shiftAmount = getInt(op2);
			replacer = op1.leftShift(shiftAmount);
			result.copy(replacer);
			break;
			
		//rightshift
		case "[t, t, f, t]":
			//gets the last 5 bits of op2 and converts it to a number to shift
			shiftAmount = getInt(op2);
			replacer = op1.rightShift(shiftAmount);
			result.copy(replacer);
			break;
			
		//add calls add2
		case "[t, t, t, f]":
			replacer = Add2(op1, op2);
			result.copy(replacer);
			break;
			
		//subtract inverts op2 using not and adds 1 then calls add2
		case "[t, t, t, t]":
			for(int i = 0; i < 32; i++) {
				op2.setBit(i, op2.getBit(i).not());
			}
			Word negative = new Word();
			negative.setBit(31, new Bit(true));			
			
			op2 = Add2(op2, negative);
			
			replacer = Add2(op1, op2);
			result.copy(replacer);
			break;
			
		//multiply
		case "[f, t, t, t]":
			//initializes multiplier place holder
			for(int j = 31; j >= 0; j--) {
				multiplier[j] = new Word();
				
				//multiplication formula 
				for(int i = 31; i >= 0; i--) {
					multiplier[j].setBit(i, op1.getBit(i).and(op2.getBit(j)));
				}					
				multiplier[j] = multiplier[j].leftShift(31 - j);
			}
			
			//initializing the place holders for multiply adds
			for(int i = 0; i < 10; i++) {
				adders[i] = new Word();
			}

			//first wave of Add4 (8 Add4)
			adders[0] = Add4(multiplier[0], multiplier[1], multiplier[2], multiplier[3]);
			adders[1] = Add4(multiplier[4], multiplier[5], multiplier[6], multiplier[7]);
			adders[2] = Add4(multiplier[8], multiplier[9], multiplier[10], multiplier[11]);
			adders[3] = Add4(multiplier[12], multiplier[13], multiplier[14], multiplier[15]);
			adders[4] = Add4(multiplier[16], multiplier[17], multiplier[18], multiplier[19]);
			adders[5] = Add4(multiplier[20], multiplier[21], multiplier[22], multiplier[23]);
			adders[6] = Add4(multiplier[24], multiplier[25], multiplier[26], multiplier[27]);
			adders[7] = Add4(multiplier[28], multiplier[29], multiplier[30], multiplier[31]);
			
			
			//second wave of Add4 (2 Add4)
			adders[8] = Add4(adders[0], adders[1], adders[2], adders[3]);
			adders[9] = Add4(adders[4], adders[5], adders[6], adders[7]);
			
			//last wave of Add2
			replacer = Add2(adders[8], adders[9]);
			
			result.copy(replacer);
			break;
		}
	}
	
	
	public Word Add2(Word one, Word two) {
		Bit carry = new Bit(); 
		Bit resultBit;
		Word newWord = new Word();
		
		for(int i = 31; i >= 0; i--) {			
			resultBit = new Bit();
			//S = X xor Y xor C
			resultBit = one.getBit(i).xor(two.getBit(i)).xor(carry);
			newWord.setBit(i, resultBit);
			//carry formula C = X and Y or ((X xor Y) and C)
			carry = one.getBit(i).and(two.getBit(i)).or((one.getBit(i).xor(two.getBit(i))).and(carry));			
		}
		
		return newWord;
	}
	
	public Word Add4(Word one, Word two, Word three, Word four) {
		Bit resultBit;
		Bit trueBit = new Bit(true);
		Word carry = new Word();
		Word newWord = new Word();
		
		for(int i = 31; i >= 0; i--) {
			resultBit = new Bit();
			//S = 1 xor 2 xor 3 xor 4 xor C
			resultBit = one.getBit(i).xor(two.getBit(i)).xor(three.getBit(i)).xor(four.getBit(i)).xor(carry.getBit(i));

			newWord.setBit(i, resultBit);

			//carry expression
			//checks for 2 or 3 true bits adding together
			//returns false for 1, 4 or 5 true bits adding together
			if(i-1 >= 0) {
				//checks if there is already a carry
				if(carry.getBit(i-1).getValue()) {
					carry.setBit(i-1, carry.getBit(i-1).
							xor((one.getBit(i).and(two.getBit(i)).xor(three.getBit(i).and(four.getBit(i)))).
							or((one.getBit(i).and(three.getBit(i))).xor(two.getBit(i).and(four.getBit(i)))).
							or((one.getBit(i).and(four.getBit(i))).xor(two.getBit(i).and(three.getBit(i)))).
							or((one.getBit(i).and(carry.getBit(i))).xor(two.getBit(i).and(three.getBit(i)))).
							or((one.getBit(i).and(carry.getBit(i))).xor(two.getBit(i).and(four.getBit(i)))).
							or((one.getBit(i).and(carry.getBit(i))).xor(three.getBit(i).and(four.getBit(i)))).
							or((carry.getBit(i).and(two.getBit(i))).xor(three.getBit(i).and(four.getBit(i)))).							
							or((carry.getBit(i).and(two.getBit(i))).xor(one.getBit(i).and(three.getBit(i)))).
							or((carry.getBit(i).and(two.getBit(i))).xor(one.getBit(i).and(four.getBit(i)))).							
							or((carry.getBit(i).and(three.getBit(i))).xor(one.getBit(i).and(two.getBit(i)))).							
							or((carry.getBit(i).and(three.getBit(i))).xor(one.getBit(i).and(four.getBit(i)))).
							or((carry.getBit(i).and(three.getBit(i))).xor(two.getBit(i).and(four.getBit(i)))).							
							or((carry.getBit(i).and(four.getBit(i))).xor(one.getBit(i).and(two.getBit(i)))).
							or((carry.getBit(i).and(four.getBit(i))).xor(one.getBit(i).and(three.getBit(i)))).
							or((carry.getBit(i).and(four.getBit(i))).xor(two.getBit(i).and(three.getBit(i)))).
							and((trueBit.xor((one.getBit(i).and(two.getBit(i)).and(three.getBit(i)).and(four.getBit(i))).
									or(carry.getBit(i).and(two.getBit(i)).and(three.getBit(i)).and(four.getBit(i))).
									or(carry.getBit(i).and(one.getBit(i)).and(three.getBit(i)).and(four.getBit(i))).
									or(carry.getBit(i).and(one.getBit(i)).and(two.getBit(i)).and(four.getBit(i))).
									or(carry.getBit(i).and(one.getBit(i)).and(two.getBit(i)).and(three.getBit(i))))))));
					
					if(i-2 >= 0 && !carry.getBit(i-1).getValue()) {
						carry.setBit(i-2, trueBit);
					}
				}
				else {
					carry.setBit(i-1, carry.getBit(i-1).
							xor(((one.getBit(i).and(two.getBit(i))).xor(three.getBit(i).and(four.getBit(i)))).
							or((one.getBit(i).and(three.getBit(i))).xor(two.getBit(i).and(four.getBit(i)))).
							or((one.getBit(i).and(four.getBit(i))).xor(two.getBit(i).and(three.getBit(i)))).
							or((one.getBit(i).and(carry.getBit(i))).xor(two.getBit(i).and(three.getBit(i)))).
							or((one.getBit(i).and(carry.getBit(i))).xor(two.getBit(i).and(four.getBit(i)))).
							or((one.getBit(i).and(carry.getBit(i))).xor(three.getBit(i).and(four.getBit(i)))).
							or((carry.getBit(i).and(two.getBit(i))).xor(three.getBit(i).and(four.getBit(i)))).							
							or((carry.getBit(i).and(two.getBit(i))).xor(one.getBit(i).and(three.getBit(i)))).
							or((carry.getBit(i).and(two.getBit(i))).xor(one.getBit(i).and(four.getBit(i)))).							
							or((carry.getBit(i).and(three.getBit(i))).xor(one.getBit(i).and(two.getBit(i)))).							
							or((carry.getBit(i).and(three.getBit(i))).xor(one.getBit(i).and(four.getBit(i)))).
							or((carry.getBit(i).and(three.getBit(i))).xor(two.getBit(i).and(four.getBit(i)))).							
							or((carry.getBit(i).and(four.getBit(i))).xor(one.getBit(i).and(two.getBit(i)))).
							or((carry.getBit(i).and(four.getBit(i))).xor(one.getBit(i).and(three.getBit(i)))).
							or((carry.getBit(i).and(four.getBit(i))).xor(two.getBit(i).and(three.getBit(i)))).
							and((trueBit.xor((one.getBit(i).and(two.getBit(i)).and(three.getBit(i)).and(four.getBit(i))).
							or(carry.getBit(i).and(two.getBit(i)).and(three.getBit(i)).and(four.getBit(i))).
							or(carry.getBit(i).and(one.getBit(i)).and(three.getBit(i)).and(four.getBit(i))).
							or(carry.getBit(i).and(one.getBit(i)).and(two.getBit(i)).and(four.getBit(i))).
							or(carry.getBit(i).and(one.getBit(i)).and(two.getBit(i)).and(three.getBit(i))))))));
				}
				
			}
			
			//double carry expression
			//checks for 4 or 5 true bits adding together 
			if(i-2 >= 0) {	
				if(carry.getBit(i-2).getValue()) {
					carry.setBit(i-2, carry.getBit(i-2).
							xor((one.getBit(i).and(two.getBit(i)).and(three.getBit(i)).and(four.getBit(i))).
							or(carry.getBit(i).and(two.getBit(i)).and(three.getBit(i)).and(four.getBit(i))).
							or(carry.getBit(i).and(one.getBit(i)).and(three.getBit(i)).and(four.getBit(i))).
							or(carry.getBit(i).and(one.getBit(i)).and(two.getBit(i)).and(four.getBit(i))).
							or(carry.getBit(i).and(one.getBit(i)).and(two.getBit(i)).and(three.getBit(i)))));
					if(i-3 >= 0 && !carry.getBit(i-2).getValue()) {
						carry.setBit(i-3, trueBit);
					}
				}
				else {
					carry.setBit(i-2, carry.getBit(i-2).
							xor((one.getBit(i).and(two.getBit(i)).and(three.getBit(i)).and(four.getBit(i))).
							or(carry.getBit(i).and(two.getBit(i)).and(three.getBit(i)).and(four.getBit(i))).
							or(carry.getBit(i).and(one.getBit(i)).and(three.getBit(i)).and(four.getBit(i))).
							or(carry.getBit(i).and(one.getBit(i)).and(two.getBit(i)).and(four.getBit(i))).
							or(carry.getBit(i).and(one.getBit(i)).and(two.getBit(i)).and(three.getBit(i)))));
				}

			}

		}
		
		return newWord;
	}
	
	private int getInt(Word word) {
		if(!word.getBit(27).getValue() && !word.getBit(28).getValue() && !word.getBit(29).getValue() && !word.getBit(30).getValue() && !word.getBit(31).getValue()) {
			return 0;
		}
		else if(!word.getBit(27).getValue() && !word.getBit(28).getValue() && !word.getBit(29).getValue() && !word.getBit(30).getValue() && word.getBit(31).getValue()){
			return 1;
		}
		else if(!word.getBit(27).getValue() && !word.getBit(28).getValue() && !word.getBit(29).getValue() && word.getBit(30).getValue() && !word.getBit(31).getValue()){
			return 2;
		}
		else if(!word.getBit(27).getValue() && !word.getBit(28).getValue() && !word.getBit(29).getValue() && word.getBit(30).getValue() && word.getBit(31).getValue()){
			return 3;
		}
		else if(!word.getBit(27).getValue() && !word.getBit(28).getValue() && word.getBit(29).getValue() && !word.getBit(30).getValue() && !word.getBit(31).getValue()){
			return 4;
		}
		else if(!word.getBit(27).getValue() && !word.getBit(28).getValue() && word.getBit(29).getValue() && !word.getBit(30).getValue() && word.getBit(31).getValue()){
			return 5;
		}
		else if(!word.getBit(27).getValue() && !word.getBit(28).getValue() && word.getBit(29).getValue() && word.getBit(30).getValue() && !word.getBit(31).getValue()){
			return 6;
		}
		else if(!word.getBit(27).getValue() && !word.getBit(28).getValue() && word.getBit(29).getValue() && word.getBit(30).getValue() && word.getBit(31).getValue()){
			return 7;
		}
		else if(!word.getBit(27).getValue() && word.getBit(28).getValue() && !word.getBit(29).getValue() && !word.getBit(30).getValue() && !word.getBit(31).getValue()){
			return 8;
		}
		else if(!word.getBit(27).getValue() && word.getBit(28).getValue() && !word.getBit(29).getValue() && !word.getBit(30).getValue() && word.getBit(31).getValue()){
			return 9;
		}
		else if(!word.getBit(27).getValue() && word.getBit(28).getValue() && !word.getBit(29).getValue() && word.getBit(30).getValue() && !word.getBit(31).getValue()){
			return 10;
		}
		else if(!word.getBit(27).getValue() && word.getBit(28).getValue() && !word.getBit(29).getValue() && word.getBit(30).getValue() && word.getBit(31).getValue()){
			return 11;
		}
		else if(!word.getBit(27).getValue() && word.getBit(28).getValue() && word.getBit(29).getValue() && !word.getBit(30).getValue() && !word.getBit(31).getValue()){
			return 12;
		}
		else if(!word.getBit(27).getValue() && word.getBit(28).getValue() && word.getBit(29).getValue() && !word.getBit(30).getValue() && word.getBit(31).getValue()){
			return 13;
		}
		else if(!word.getBit(27).getValue() && word.getBit(28).getValue() && word.getBit(29).getValue() && word.getBit(30).getValue() && !word.getBit(31).getValue()){
			return 14;
		}
		else if(!word.getBit(27).getValue() && word.getBit(28).getValue() && word.getBit(29).getValue() && word.getBit(30).getValue() && word.getBit(31).getValue()){
			return 15;
		}
		else if(word.getBit(27).getValue() && !word.getBit(28).getValue() && !word.getBit(29).getValue() && !word.getBit(30).getValue() && !word.getBit(31).getValue()){
			return 16;
		}
		else if(word.getBit(27).getValue() && !word.getBit(28).getValue() && !word.getBit(29).getValue() && !word.getBit(30).getValue() && word.getBit(31).getValue()){
			return 17;
		}
		else if(word.getBit(27).getValue() && !word.getBit(28).getValue() && !word.getBit(29).getValue() && word.getBit(30).getValue() && !word.getBit(31).getValue()){
			return 18;
		}
		else if(word.getBit(27).getValue() && !word.getBit(28).getValue() && !word.getBit(29).getValue() && word.getBit(30).getValue() && word.getBit(31).getValue()){
			return 19;
		}
		else if(word.getBit(27).getValue() && !word.getBit(28).getValue() && word.getBit(29).getValue() && !word.getBit(30).getValue() && !word.getBit(31).getValue()){
			return 20;
		}
		else if(word.getBit(27).getValue() && !word.getBit(28).getValue() && word.getBit(29).getValue() && !word.getBit(30).getValue() && word.getBit(31).getValue()){
			return 21;
		}
		else if(word.getBit(27).getValue() && !word.getBit(28).getValue() && word.getBit(29).getValue() && word.getBit(30).getValue() && !word.getBit(31).getValue()){
			return 22;
		}
		else if(word.getBit(27).getValue() && !word.getBit(28).getValue() && word.getBit(29).getValue() && word.getBit(30).getValue() && word.getBit(31).getValue()){
			return 23;
		}
		else if(word.getBit(27).getValue() && word.getBit(28).getValue() && !word.getBit(29).getValue() && !word.getBit(30).getValue() && !word.getBit(31).getValue()){
			return 24;
		}
		else if(word.getBit(27).getValue() && word.getBit(28).getValue() && !word.getBit(29).getValue() && !word.getBit(30).getValue() && word.getBit(31).getValue()){
			return 25;
		}
		else if(word.getBit(27).getValue() && word.getBit(28).getValue() && !word.getBit(29).getValue() && word.getBit(30).getValue() && !word.getBit(31).getValue()){
			return 26;
		}
		else if(word.getBit(27).getValue() && word.getBit(28).getValue() && !word.getBit(29).getValue() && word.getBit(30).getValue() && word.getBit(31).getValue()){
			return 27;
		}
		else if(word.getBit(27).getValue() && word.getBit(28).getValue() && word.getBit(29).getValue() && !word.getBit(30).getValue() && !word.getBit(31).getValue()){
			return 28;
		}
		else if(word.getBit(27).getValue() && word.getBit(28).getValue() && word.getBit(29).getValue() && !word.getBit(30).getValue() && word.getBit(31).getValue()){
			return 29;
		}
		else if(word.getBit(27).getValue() && word.getBit(28).getValue() && word.getBit(29).getValue() && word.getBit(30).getValue() && !word.getBit(31).getValue()){
			return 30;
		}
		else if(word.getBit(27).getValue() && word.getBit(28).getValue() && word.getBit(29).getValue() && word.getBit(30).getValue() && word.getBit(31).getValue()){
			return 31;
		}
		return -1;
	}
}
