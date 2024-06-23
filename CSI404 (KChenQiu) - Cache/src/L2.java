import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.LinkedList;

public class L2 {
	private static LinkedList<Integer> queue = new LinkedList<Integer>(Arrays.asList(1, 2, 3, 4));
	
	private static Word address1 = new Word();
	private static Word[] cache1 = new Word[8];
	
	private static Word address2 = new Word();
	private static Word[] cache2 = new Word[8];
	
	private static Word address3 = new Word();
	private static Word[] cache3 = new Word[8];
	
	private static Word address4 = new Word();
	private static Word[] cache4 = new Word[8];
	
	private static ALU alu = new ALU();
	
	//for instruction cache
	public static Word[] Read(Word address) {
		if(CheckAddress(address, address1)) {	
			if(cache1[0] == null) {
				Start();
				UpdateCache(address1);
			}
			Processor.currentClockCycle+=50;
			return cache1;
		}
		else if(CheckAddress(address, address2)) {
			if(cache2[0] == null) {
				Start();
				UpdateCache(address2);
			}
			Processor.currentClockCycle+=50;
			return cache2;
		}
		else if(CheckAddress(address, address3)) {
			if(cache3[0] == null) {
				Start();
				UpdateCache(address3);
			}
			Processor.currentClockCycle+=50;
			return cache3;
		}
		else if(CheckAddress(address, address4)) {
			if(cache4[0] == null) {
				Start();
				UpdateCache(address4);
			}
			Processor.currentClockCycle+=50;
			return cache4;
		}
		else {
			int cacheNumber = UpdateCache(address);
			if(cacheNumber == 1) {
				Processor.currentClockCycle+=50;
				return cache1;
			}
			else if(cacheNumber == 2) {
				Processor.currentClockCycle+=50;
				return cache2;
			}
			else if(cacheNumber == 3) {
				Processor.currentClockCycle+=50;
				return cache3;
			}
			else if(cacheNumber == 4) {
				Processor.currentClockCycle+=50;
				return cache4;
			}
			else {
				return null;
			}
		}
	}
	
	//for processor 
	public static Word read(Word address) {
		if(CheckAddress(address, address1)) {	
			Processor.currentClockCycle+=50;
			return cache1[FindAddress(address, address1)];
		}
		else if(CheckAddress(address, address2)) {
			Processor.currentClockCycle+=50;
			return cache2[FindAddress(address, address2)];
		}
		else if(CheckAddress(address, address3)) {
			Processor.currentClockCycle+=50;
			return cache3[FindAddress(address, address3)];
		}
		else if(CheckAddress(address, address4)) {
			Processor.currentClockCycle+=50;
			return cache4[FindAddress(address, address4)];
		}
		else {
			int cacheNumber = UpdateCache(address);
			if(cacheNumber == 1) {
				Processor.currentClockCycle+=50;
				return cache1[0];
			}
			else if(cacheNumber == 2) {
				Processor.currentClockCycle+=50;
				return cache2[0];
			}
			else if(cacheNumber == 3) {
				Processor.currentClockCycle+=50;
				return cache3[0];
			}
			else if(cacheNumber == 4) {
				Processor.currentClockCycle+=50;
				return cache4[0];
			}
			else {
				return null;
			}
		}
	}
	
	public static void write(Word address, Word value) {
		if(CheckAddress(address, address1)) {		
			Processor.currentClockCycle+=50;
			cache1[FindAddress(address, address1)].copy(value);
			
			Word temp = new Word();
			temp.copy(address1);
			for(int i = 0; i< 8; i++) {
				MainMemory.write(temp, cache1[i]);
				temp.Increment();
			}
		}
		else if(CheckAddress(address, address2)) {
			Processor.currentClockCycle+=50;
			cache2[FindAddress(address, address2)].copy(value);
			
			Word temp = new Word();
			temp.copy(address2);
			for(int i = 0; i< 8; i++) {
				MainMemory.write(temp, cache2[i]);
				temp.Increment();
			}
		}
		else if(CheckAddress(address, address3)) {
			Processor.currentClockCycle+=50;
			cache3[FindAddress(address, address3)].copy(value);
			
			Word temp = new Word();
			temp.copy(address3);
			for(int i = 0; i< 8; i++) {
				MainMemory.write(temp, cache3[i]);
				temp.Increment();
			}
		}
		else if(CheckAddress(address, address4)) {
			Processor.currentClockCycle+=50;
			cache4[FindAddress(address, address4)].copy(value);
			
			Word temp = new Word();
			temp.copy(address4);
			for(int i = 0; i< 8; i++) {
				MainMemory.write(temp, cache4[i]);
				temp.Increment();
			}
		}
		else {
			int cacheNumber = UpdateCache(address);
			if(cacheNumber == 1) {
				Processor.currentClockCycle+=50;
				cache1[0].copy(value);
				
				Word temp = new Word();
				temp.copy(address1);
				for(int i = 0; i< 8; i++) {
					MainMemory.write(temp, cache1[i]);
					temp.Increment();
				}
			}
			else if(cacheNumber == 2) {
				Processor.currentClockCycle+=50;
				cache2[0].copy(value);
				
				Word temp = new Word();
				temp.copy(address2);
				for(int i = 0; i< 8; i++) {
					MainMemory.write(temp, cache2[i]);
					temp.Increment();
				}
			}
			else if(cacheNumber == 3) {
				Processor.currentClockCycle+=50;
				cache3[0].copy(value);
				
				Word temp = new Word();
				temp.copy(address3);
				for(int i = 0; i< 8; i++) {
					MainMemory.write(temp, cache3[i]);
					temp.Increment();
				}
			}
			else if(cacheNumber == 4) {
				Processor.currentClockCycle+=50;
				cache4[0].copy(value);
				
				Word temp = new Word();
				temp.copy(address4);
				for(int i = 0; i< 8; i++) {
					MainMemory.write(temp, cache4[i]);
					temp.Increment();
				}
			}
		}
	}
	
	public static Word getStartAddress(Word[] cache) {
		if(cache1 == cache) {
			return address1;
		}
		else if(cache2 == cache) {
			return address2;
		}
		else if(cache3 == cache) {
			return address3;
		}
		else if(cache4 == cache) {
			return address4;
		}
		else {
			return null;
		}
	}
	
	private static boolean CheckAddress(Word target, Word cacheAddress) {
		alu.op1.copy(target);
		alu.op2.copy(cacheAddress);

		Bit[] operation = new Bit[4];
		
		for(int i = 0; i< 4; i++) {
			operation[i] = new Bit(true);
		}
		
		//checks that the target is larger than the start address
		alu.doOperation(operation);
		
		if(alu.result.getBit(0).getValue()) {
			return false;
		}
		
		else {
			//check that it is within bounds of cache (not greater than 8 from the start cache)
			alu.op2.copy(alu.result);
			alu.op1.copy(new Word());
			alu.op1.setBit(28, new Bit(true));
			
			alu.doOperation(operation);
			
			if(alu.result.getBit(0).getValue() || alu.result.toString().equals(new Word().toString())) {
				return false;
			}
			else {
				return true;
			}
		}
	}
	
	private static int FindAddress(Word target, Word address) {
		alu.op1.copy(target);
		alu.op2.copy(address);
		
		Bit[] operation = new Bit[4];
		
		for(int i = 0; i< 4; i++) {
			operation[i] = new Bit(true);
		}
		
		alu.doOperation(operation);
		
		alu.op2.copy(alu.result);
		alu.op1.copy(new Word());
		alu.op1.setBit(28, new Bit(true));
		
		alu.doOperation(operation);
		
		return GetInt(alu.result);
	}
	
	//updates the cache in order
	private static int UpdateCache(Word startAddress) {
		Word temp = new Word();
		temp.copy(startAddress);;
		if(queue.getFirst() == 1) {
			queue.removeFirst();
			queue.addLast(1);
			address1.copy(startAddress);			
			for(int i = 0; i < 8; i++) {
				cache1[i].copy(MainMemory.read(temp));
				temp.Increment();			
			}
			Processor.currentClockCycle+= 350;
			return 1;
		}	
		else if(queue.getFirst() == 2) {
			queue.removeFirst();
			queue.addLast(2);
			address2.copy(startAddress);			
			for(int i = 0; i < 8; i++) {
				cache2[i].copy(MainMemory.read(temp));
				temp.Increment();
			}
			Processor.currentClockCycle+= 350;
			return 2;
		}	
		else if(queue.getFirst() == 3) {
			queue.removeFirst();
			queue.addLast(3);
			address3.copy(startAddress);
			for(int i = 0; i < 8; i++) {
				cache3[i].copy(MainMemory.read(temp));
				temp.Increment();
			}
			Processor.currentClockCycle+= 350;
			return 3;
		}	
		else if(queue.getFirst() == 4) {
			queue.removeFirst();
			queue.addLast(4);
			address4.copy(startAddress);
			for(int i = 0; i < 8; i++) {
				cache4[i].copy(MainMemory.read(temp));
				temp.Increment();
			}
			Processor.currentClockCycle+= 350;
			return 4;
		}	
		return -1;
	}
	
	private static int GetInt(Word word) {
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
	
	private static void Start() {
		for(int i = 0; i < 8; i++) {
			cache1[i] = new Word();
		}
		for(int i = 0; i < 8; i++) {
			cache2[i] = new Word();
		}
		for(int i = 0; i < 8; i++) {
			cache3[i] = new Word();
		}
		for(int i = 0; i < 8; i++) {
			cache4[i] = new Word();
		}
	}
	
	public static void ClearCache() {
		queue.clear();
		queue.add(1);
		queue.add(2);
		queue.add(3);
		queue.add(4);
		
		address1 = new Word();
		cache1 = new Word[8];
		
		address2 = new Word();
		cache2 = new Word[8];
		
		address3 = new Word();
		cache3 = new Word[8];
		
		address4 = new Word();
		cache4 = new Word[8];
	}
}
