import java.util.Arrays;

public class Processor {
	private Word pc, sp, currentInstruction;
	private Bit halted;
	private Word[] registers = new Word[32];
	private Word opCode, immediate, rs, rs1, rs2, function, rd, rdLocation;
	private ALU alu = new ALU();
	
	public Processor() {
		pc = new Word();
		sp = new Word();
		currentInstruction = new Word();
		sp.setBit(20, new Bit(true));
		halted = new Bit();
	}
	
	public void run() {
		for(int i = 0; i < 32; i++) {
			registers[i] = new Word();
		}
		while(!halted.getValue()) {
			fetch();
			decode();
			execute();
			store();
		}
	}
		
	//gets the "current" instruction based on pc
	public void fetch() {
		currentInstruction.copy(MainMemory.read(pc));
		pc.Increment();
	}
	
	
	public void decode() {
		opCode = new Word();
		int registerLocation;
		//masks the first 27 bits of current instructions, leaving the last 5 for opcode 
		for(int i = 0; i < 27; i++) {
			opCode.setBit(i, currentInstruction.getBit(i).and(new Bit(false)));
		}
		for(int i = 27; i < 32; i++) {
			opCode.setBit(i, currentInstruction.getBit(i).and(new Bit(true)));
		}
		
		//00 (no register)
		if(!opCode.getBit(30).getValue() && !opCode.getBit(31).getValue()) {
			immediate = new Word();
			for(int i = 0; i < 27; i++) {
				immediate.setBit(i, currentInstruction.getBit(i));
			}
		}
		
		//01 (Dest only)
		else if(!opCode.getBit(30).getValue() && opCode.getBit(31).getValue()) {
			immediate = new Word();
			function = new Word();
			rd = new Word();
			rdLocation = new Word();
			
			//first 18 bits are immediate instructions
			immediate = currentInstruction.rightShift(14);
			
			//4 bits after immediate instructions is function
			function = currentInstruction.rightShift(10);
			for(int i = 0; i < 28; i++) {
				function.setBit(i, function.getBit(i).and(new Bit(false)));
			}
			
			//5 bits after function is the rd
			rdLocation = currentInstruction.rightShift(5);
			for(int i = 0; i < 27; i++) {
				rdLocation.setBit(i, rd.getBit(i).and(new Bit(false)));
			}
			
			//gets the register destination in case we need to use it
			registerLocation = getInt(rdLocation);
			rd.copy(registers[registerLocation]);
			
		}
		
		//11 (2 register)
		else if(opCode.getBit(30).getValue() && opCode.getBit(31).getValue()) {
			immediate = new Word();
			rs = new Word();
			function = new Word();
			rd = new Word();
			rdLocation = new Word();
			
			//first 13 bits are immediate instructions
			immediate = currentInstruction.rightShift(19);
			
			//5 bits after immediate instructions is rs
			rs = currentInstruction.rightShift(14);
			for(int i = 0; i < 27; i++) {
				rs.setBit(i, rs.getBit(i).and(new Bit(false)));
			}
			
			registerLocation = getInt(rs);
			rs.copy(registers[registerLocation]);
			
			//4 bits after rs is function
			function = currentInstruction.rightShift(10);
			for(int i = 0; i < 28; i++) {
				function.setBit(i, function.getBit(i).and(new Bit(false)));
			}
			
			//5 bits after function is the rd
			rdLocation = currentInstruction.rightShift(5);
			for(int i = 0; i < 27; i++) {
				rdLocation.setBit(i, rd.getBit(i).and(new Bit(false)));
			}
			
			//gets the register destination in case we need to use it

			registerLocation = getInt(rdLocation);
			rd.copy(registers[registerLocation]);
			
		}
		
		//10 (3 register)
		else if(opCode.getBit(30).getValue() && !opCode.getBit(31).getValue()) {
			immediate = new Word();
			rs1 = new Word();
			rs2 = new Word();
			function = new Word();
			rd = new Word();
			rdLocation = new Word();
			
			//first 8 bits are immediate instructions
			immediate = currentInstruction.rightShift(24);
			
			//5 bits after immediate instructions is rs1
			rs1 = currentInstruction.rightShift(19);
			for(int i = 0; i < 27; i++) {
				rs1.setBit(i, rs1.getBit(i).and(new Bit(false)));
			}
			
			//calculates the location of rs1
			registerLocation = getInt(rs1);
			rs1.copy(registers[registerLocation]);
			
			//5 bits after rs1 is rs2
			rs2 = currentInstruction.rightShift(14);
			for(int i = 0; i < 27; i++) {
				rs2.setBit(i, rs2.getBit(i).and(new Bit(false)));
			}
			
			//calculates the location of rs2
			registerLocation = getInt(rs2);
			rs2.copy(registers[registerLocation]);
			
			//4 bits after rs is funciton
			function = currentInstruction.rightShift(10);
			for(int i = 0; i < 28; i++) {
				function.setBit(i, function.getBit(i).and(new Bit(false)));
			}
			
			//5 bits after function is the rd
			rdLocation = currentInstruction.rightShift(5);
			for(int i = 0; i < 27; i++) {
				rdLocation.setBit(i, rd.getBit(i).and(new Bit(false)));
			}
			
			//gets the register destination in case we need to use it
			registerLocation = getInt(rdLocation);
			rd.copy(registers[registerLocation]);
		}
		
	}
	
	public void execute() {
		Bit[] operation = new Bit[4];
		//000
		if(!opCode.getBit(27).getValue() && !opCode.getBit(28).getValue() && !opCode.getBit(29).getValue()) {
			
			//00 (no registers)
			if(!opCode.getBit(30).getValue() && !opCode.getBit(31).getValue()) {
				halted.set();
			}
	
			//01 (Dest Only)
			else if(!opCode.getBit(30).getValue() && opCode.getBit(31).getValue()) {
				rd.copy(immediate);
			}
			
			//11 (2R)
			else if(opCode.getBit(30).getValue() && opCode.getBit(31).getValue()) {
				alu.op1.copy(rs);
				alu.op2.copy(rd);					
				int j = 0;
				for(int i = 28; i < 32; i++) {
					operation[j] = new Bit(function.getBit(i).getValue());
					j++;
				}
				alu.doOperation(operation);
				rd.copy(alu.result);

			}
			
			//10 (3R)
			else if(opCode.getBit(30).getValue() && !opCode.getBit(31).getValue()) {
				alu.op1.copy(rs1);
				alu.op2.copy(rs2);	
				
				
				int j = 0;
				for(int i = 28; i < 32; i++) {
					operation[j] = new Bit(function.getBit(i).getValue());
					j++;
				}
				
				alu.doOperation(operation);
				rd.copy(alu.result);				
			}
		}
		
		//001
		else if(!opCode.getBit(27).getValue() && !opCode.getBit(28).getValue() && opCode.getBit(29).getValue()) {
			System.out.println("not implemented yet");
		}
		
		//010
		else if(!opCode.getBit(27).getValue() && opCode.getBit(28).getValue() && !opCode.getBit(29).getValue()) {
			System.out.println("not implemented yet");
		}
		
		//011
		else if(!opCode.getBit(27).getValue() && opCode.getBit(28).getValue() && opCode.getBit(29).getValue()) {
			System.out.println("not implemented yet");
		}
		
		//100
		else if(opCode.getBit(27).getValue() && !opCode.getBit(28).getValue() && !opCode.getBit(29).getValue()) {
			System.out.println("not implemented yet");
		}
		
		//101
		else if(opCode.getBit(27).getValue() && !opCode.getBit(28).getValue() && opCode.getBit(29).getValue()) {
			System.out.println("not implemented yet");
		}
		
		//110
		else if(opCode.getBit(27).getValue() && opCode.getBit(28).getValue() && !opCode.getBit(29).getValue()) {
			System.out.println("not implemented yet");
		}
	}
	
	public void store() {
		//000
		if(!opCode.getBit(27).getValue() && !opCode.getBit(28).getValue() && !opCode.getBit(29).getValue()) {
			
			//11,10,01 does not work for 00
			if(opCode.getBit(30).getValue() || opCode.getBit(31).getValue()) {
				int registerLocation;
				registerLocation = getInt(rdLocation);
				
				//checks to make sure we are not changing register 0
				if(registerLocation == 0) {
					
				}
				else {
					registers[registerLocation].copy(rd);
				}
			}
		}
		
		//001
		else if(!opCode.getBit(27).getValue() && !opCode.getBit(28).getValue() && opCode.getBit(29).getValue()) {
			System.out.println("not implemented yet");
		}
		
		//010
		else if(!opCode.getBit(27).getValue() && opCode.getBit(28).getValue() && !opCode.getBit(29).getValue()) {
			System.out.println("not implemented yet");
		}
		
		//011
		else if(!opCode.getBit(27).getValue() && opCode.getBit(28).getValue() && opCode.getBit(29).getValue()) {
			System.out.println("not implemented yet");
		}
		
		//100
		else if(opCode.getBit(27).getValue() && !opCode.getBit(28).getValue() && !opCode.getBit(29).getValue()) {
			System.out.println("not implemented yet");
		}
		
		//101
		else if(opCode.getBit(27).getValue() && !opCode.getBit(28).getValue() && opCode.getBit(29).getValue()) {
			System.out.println("not implemented yet");
		}
		
		//110
		else if(opCode.getBit(27).getValue() && opCode.getBit(28).getValue() && !opCode.getBit(29).getValue()) {
			System.out.println("not implemented yet");
		}
	}
	
	//used for unit testing
	public Word getRegister(int i) {
		return registers[i];
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
