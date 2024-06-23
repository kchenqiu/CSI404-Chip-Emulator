
public class InstructionCache {
	private static Word startAddress = new Word();
	private static Word[] cache = new Word[8];
	private static ALU alu = new ALU();
	
	
	public static Word read(Word address) {
		int intAddress = CompareAddress(address);
		if(cache[0] == null) {
			FillCache();
		}
		if(intAddress == -1) {
			startAddress.copy(address);
			
			FillCache();
			
			startAddress.copy(L2.getStartAddress(cache));
			
			intAddress = CompareAddress(address);
			
			return cache[intAddress];
		}
		else {
			Processor.currentClockCycle += 10;
			return cache[intAddress];
		}
	}

	private static void FillCache() {
		cache = L2.Read(startAddress);
		Processor.currentClockCycle+=50;
	}
	
	private static int CompareAddress(Word address) {
		alu.op1.copy(address);
		alu.op2.copy(startAddress);
		
		Bit[] operation = new Bit[4];
		
		for(int i = 0; i< 4; i++) {
			operation[i] = new Bit(true);
		}
		
		alu.doOperation(operation);

		Word result = new Word();
		
		result.copy(alu.result);
		
		if(alu.result.getBit(0).getValue()) {
			return -1;
		}
		else {

			alu.op2.copy(alu.result);
			alu.op1.copy(new Word());
			alu.op1.setBit(28, new Bit(true));
			
			alu.doOperation(operation);
			
			if(alu.result.getBit(0).getValue() || alu.result.toString().equals(new Word().toString())) {
				return -1;
			}
			else {
				return CheckAddress(result);
			}
		}
	}
	
	private static int CheckAddress(Word word) {
		if(!word.getBit(29).getValue() && !word.getBit(30).getValue() && !word.getBit(31).getValue()) {
			return 0;
		}
		else if(!word.getBit(29).getValue() && !word.getBit(30).getValue() && word.getBit(31).getValue()) {
			return 1;
		}
		else if(!word.getBit(29).getValue() && word.getBit(30).getValue() && !word.getBit(31).getValue()) {
			return 2;
		}
		else if(!word.getBit(29).getValue() && word.getBit(30).getValue() && word.getBit(31).getValue()) {
			return 3;
		}
		else if(word.getBit(29).getValue() && !word.getBit(30).getValue() && !word.getBit(31).getValue()) {
			return 4;
		}
		else if(word.getBit(29).getValue() && !word.getBit(30).getValue() && word.getBit(31).getValue()) {
			return 5;
		}
		else if(word.getBit(29).getValue() && word.getBit(30).getValue() && !word.getBit(31).getValue()) {
			return 6;
		}
		else if(word.getBit(29).getValue() && word.getBit(30).getValue() && word.getBit(31).getValue()) {
			return 7;
		}
		else{
			return -1;
		}
	}
	
	public static void ClearCache() {
		startAddress = new Word();
		cache = new Word[8];
	}
}
