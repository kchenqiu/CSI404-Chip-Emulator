import java.math.BigDecimal;
import java.util.LinkedList;

public class Assembler {
	private ProgramNode program;
	private String r1, r2, rd, immediate, function;
	private LinkedList<String> instructions = new LinkedList<>();
	
	//constructor
	public Assembler(ProgramNode program) {
		this.program = program;
	}
	
	public void Assemble() {
		//loops until every statement is parsed
		while(program.getStatements().size() != 0) {
			//splits up the math node and creates the proper instruction
			if(program.getStatements().getFirst() instanceof MathNode) {
				MathNode mathNode = (MathNode)(program.getStatements().getFirst());
				program.getStatements().removeFirst();
				function = getMathFunction(mathNode.getOperation());
				AssembleRegisters(mathNode.getRegister());
				if(mathNode.getRegister() instanceof R3) {
					String instruction = immediate + r1 + r2 + function + rd + "00010";
					instructions.add(instruction);
				}
				else if(mathNode.getRegister() instanceof R2) {
					String instruction = immediate + r1 + function + rd + "00011";
					instructions.add(instruction);
				}
				else if(mathNode.getRegister() instanceof DestOnly) {
					String instruction = immediate + function + rd + "00001";
					instructions.add(instruction);
				}
				else if(mathNode.getRegister() instanceof NoR) {
					String instruction = immediate + "00000";
					instructions.add(instruction);
				}
			}
			//splits up the branch node and creates the proper instruction
			else if(program.getStatements().getFirst() instanceof BranchNode) {
				BranchNode branchNode = (BranchNode)(program.getStatements().getFirst());
				program.getStatements().removeFirst();
				function = getBooleanFunction(branchNode.getOperation());
				AssembleRegisters(branchNode.getRegister());
				if(branchNode.getRegister() instanceof R3) {
					String instruction = immediate + r1 + r2 + function + rd + "00110";
					instructions.add(instruction);
				}
				else if(branchNode.getRegister() instanceof R2) {
					String instruction = immediate + r1 + function + rd + "00111";
					instructions.add(instruction);
				}
				else if(branchNode.getRegister() instanceof DestOnly) {
					String instruction = immediate + function + rd + "00101";
					instructions.add(instruction);
				}
				else if(branchNode.getRegister() instanceof NoR) {
					String instruction = immediate + "00100";
					instructions.add(instruction);
				}
			}
			//splits up the call node and creates the proper instruction
			else if(program.getStatements().getFirst() instanceof CallNode) {
				CallNode callNode = (CallNode)(program.getStatements().getFirst());
				program.getStatements().removeFirst();
				function = getBooleanFunction(callNode.getOperation());
				AssembleRegisters(callNode.getRegister());
				if(callNode.getRegister() instanceof R3) {
					String instruction = immediate + r1 + r2 + function + rd + "01010";
					instructions.add(instruction);
				}
				else if(callNode.getRegister() instanceof R2) {
					String instruction = immediate + r1 + function + rd + "01011";
					instructions.add(instruction);
				}
				else if(callNode.getRegister() instanceof DestOnly) {
					String instruction = immediate + function + rd + "01001";
					instructions.add(instruction);
				}
				else if(callNode.getRegister() instanceof NoR) {
					String instruction = immediate + "01000";
					instructions.add(instruction);
				}
			}
			//splits up the push node and creates the proper instruction
			else if(program.getStatements().getFirst() instanceof PushNode) {
				PushNode pushNode = (PushNode)(program.getStatements().getFirst());
				program.getStatements().removeFirst();
				function = getMathFunction(pushNode.getOperation());
				AssembleRegisters(pushNode.getRegister());
				if(pushNode.getRegister() instanceof R3) {
					String instruction = immediate + r1 + r2 + function + rd + "01110";
					instructions.add(instruction);
				}
				else if(pushNode.getRegister() instanceof R2) {
					String instruction = immediate + r1 + function + rd + "01111";
					instructions.add(instruction);
				}
				else if(pushNode.getRegister() instanceof DestOnly) {
					String instruction = immediate + function + rd + "01101";
					instructions.add(instruction);
				}
				else if(pushNode.getRegister() instanceof NoR) {
					String instruction = immediate + "01100";
					instructions.add(instruction);
				}
			}
			//splits up the load node and creates proper instruction
			else if(program.getStatements().getFirst() instanceof LoadNode) {
				LoadNode loadNode = (LoadNode)(program.getStatements().getFirst());
				program.getStatements().removeFirst();
				AssembleRegisters(loadNode.getRegister());
				if(loadNode.getRegister() instanceof R3) {
					String instruction = immediate + r1 + r2 + "0000" + rd + "10010";
					instructions.add(instruction);
				}
				else if(loadNode.getRegister() instanceof R2) {
					String instruction = immediate + r1 + "0000" + rd + "10011";
					instructions.add(instruction);
				}
				else if(loadNode.getRegister() instanceof DestOnly) {
					String instruction = immediate + "0000" + rd + "10001";
					instructions.add(instruction);
				}
				else if(loadNode.getRegister() instanceof NoR) {
					String instruction = immediate + "10000";
					instructions.add(instruction);
				}
			}
			//splits up the store node and creates proper instruction
			else if(program.getStatements().getFirst() instanceof StoreNode) {
				StoreNode storeNode = (StoreNode)(program.getStatements().getFirst());
				program.getStatements().removeFirst();
				AssembleRegisters(storeNode.getRegister());
				if(storeNode.getRegister() instanceof R3) {
					String instruction = immediate + r1 + r2 + "0000" + rd + "10110";
					instructions.add(instruction);
				}
				else if(storeNode.getRegister() instanceof R2) {
					String instruction = immediate + r1 + "0000" + rd + "10111";
					instructions.add(instruction);
				}
				else if(storeNode.getRegister() instanceof DestOnly) {
					String instruction = immediate + "0000" + rd + "10101";
					instructions.add(instruction);
				}
				else if(storeNode.getRegister() instanceof NoR) {
					String instruction = immediate + "10100";
					instructions.add(instruction);
				}
			}
			//splits up the peek/pop node and creates proper instructions
			else if(program.getStatements().getFirst() instanceof Pop_PeekNode) {
				Pop_PeekNode peek_PopNode = (Pop_PeekNode)(program.getStatements().getFirst());
				program.getStatements().removeFirst();
				AssembleRegisters(peek_PopNode.getRegister());
				if(peek_PopNode.getRegister() instanceof R3) {
					String instruction = immediate + r1 + r2 + "0000" + rd + "11010";
					instructions.add(instruction);
				}
				else if(peek_PopNode.getRegister() instanceof R2) {
					String instruction = immediate + r1 + "0000" + rd + "11011";
					instructions.add(instruction);
				}
				else if(peek_PopNode.getRegister() instanceof DestOnly) {
					String instruction = immediate + "0000" + rd + "11001";
					instructions.add(instruction);
				}
			}
			//creates interrupt instruction
			else if(program.getStatements().getFirst() instanceof InterruptNode) {
				InterruptNode interruptNode = (InterruptNode)(program.getStatements().getFirst());
				program.getStatements().removeFirst();
				AssembleRegisters(interruptNode.getRegister());
				String instruction = immediate + "11000";
				instructions.add(instruction);
			}
		}
	}
	
	//sets the global register values in binary string (ex. 10 = "001010", 16 = "10000", 31 = "11111")
	private void AssembleRegisters(RegisterNode register) {
		if(register instanceof R3) {
			R3 r3 = (R3)(register);
			r1 = ConvertRegister(r3.getRegister1());
			r2 = ConvertRegister(r3.getRegister2());
			rd = ConvertRegister(r3.getRegisterDirection());
			immediate = ConvertInt(r3.getImmediate());
			//checks to make sure the immediate is the correct length 
			//adds "0" to the front if it is not long enough and removes the first digit if it is too long
			while(immediate.length() < 8) {
				immediate = "0".concat(immediate);
			}
			if(immediate.length() > 8) {
				immediate = immediate.substring(immediate.length() - 8);
			}
		}
		else if(register instanceof R2) {
			R2 r2 = (R2)(register);
			r1 = ConvertRegister(r2.getRegister());
			rd = ConvertRegister(r2.getRegisterDirection());
			immediate = ConvertInt(r2.getImmediate());
			//checks to make sure the immediate is the correct length
			//adds "0" to the front if it is not long enough and removes the first digit if it is too long
			while(immediate.length() < 13) {
				immediate = "0".concat(immediate);
			}
			if(immediate.length() > 13) {
				immediate = immediate.substring(immediate.length() - 13);
			}
		}
		else if(register instanceof DestOnly) {
			DestOnly r1 = (DestOnly)(register);
			rd = ConvertRegister(r1.getRegisterDirection());
			immediate = ConvertInt(r1.getImmediate());
			//checks to make sure the immediate is the correct length
			//adds "0" to the front if it is not long enough and removes the first digit if it is too long
			while(immediate.length() < 18) {
				immediate = "0".concat(immediate);
			}
			if(immediate.length() > 18) {
				immediate = immediate.substring(immediate.length() - 18);
			}
		}
		else if(register instanceof NoR) {
			NoR r0 = (NoR)(register);
			immediate = ConvertInt(r0.getImmediate());
			//checks to make sure the immediate is the correct length
			//adds "0" to the front if it is not long enough and removes the first digit if it is too long
			while(immediate.length() < 27) {
				immediate = "0".concat(immediate);
			}
			if(immediate.length() > 27) {
				immediate = immediate.substring(immediate.length() - 27);
			}
		}
	}
	
	//check each possible register number and returns the appropriate binary
	private String ConvertRegister(int registerNumber) {
		if(registerNumber == 0) {
			return "00000";
		}
		else if(registerNumber == 1) {
			return "00001";
		}
		else if(registerNumber == 2) {
			return "00010";
		}
		else if(registerNumber == 3) {
			return "00011";
		}
		else if(registerNumber == 4) {
			return "00100";
		}
		else if(registerNumber == 5) {
			return "00101";
		}
		else if(registerNumber == 6) {
			return "00110";
		}
		else if(registerNumber == 7) {
			return "00111";
		}
		else if(registerNumber == 8) {
			return "01000";
		}
		else if(registerNumber == 9) {
			return "01001";
		}
		else if(registerNumber == 10) {
			return "01010";
		}
		else if(registerNumber == 11) {
			return "01011";
		}
		else if(registerNumber == 12) {
			return "01100";
		}
		else if(registerNumber == 13) {
			return "01101";
		}
		else if(registerNumber == 14) {
			return "01110";
		}
		else if(registerNumber == 15) {
			return "01111";
		}
		else if(registerNumber == 16) {
			return "10000";
		}
		else if(registerNumber == 17) {
			return "10001";
		}
		else if(registerNumber == 18) {
			return "10010";
		}
		else if(registerNumber == 19) {
			return "10011";
		}
		else if(registerNumber == 20) {
			return "10100";
		}
		else if(registerNumber == 21) {
			return "10101";
		}
		else if(registerNumber == 22) {
			return "10110";
		}
		else if(registerNumber == 23) {
			return "10111";
		}
		else if(registerNumber == 24) {
			return "11000";
		}
		else if(registerNumber == 25) {
			return "11001";
		}
		else if(registerNumber == 26) {
			return "11010";
		}
		else if(registerNumber == 27) {
			return "11011";
		}
		else if(registerNumber == 28) {
			return "11100";
		}
		else if(registerNumber == 29) {
			return "11101";
		}
		else if(registerNumber == 30) {
			return "11110";
		}
		else if(registerNumber == 31) {
			return "11111";
		}
		return null;
	}
	
	//creates a string of 1 and 0 by converting immediate to binary
	private String ConvertInt(int integer) {
		int base = 67108864;
		String binaryInt = "";
		for(int i = 27; i > 0; i--) {
			if(integer >= base) {
				integer -= base;
				binaryInt = binaryInt.concat("1");
			}
			else {
				binaryInt = binaryInt.concat("0");
			}
			base = base / 2;
		}
		return binaryInt;
	}
	
	//checks each possible math function
	private String getMathFunction(MathNode.Operations operation) {
		if(operation.equals(MathNode.Operations.AND)) {
			return "1000";
		}
		else if(operation.equals(MathNode.Operations.OR)) {
			return "1001";
		}
		else if(operation.equals(MathNode.Operations.XOR)) {
			return "1010";
		}
		else if(operation.equals(MathNode.Operations.NOT)) {
			return "1011";
		}
		else if(operation.equals(MathNode.Operations.LEFTSHIFT)) {
			return "1100";
		}
		else if(operation.equals(MathNode.Operations.RIGHTSHIFT)) {
			return "1101";
		}
		else if(operation.equals(MathNode.Operations.ADD)) {
			return "1110";
		}
		else if(operation.equals(MathNode.Operations.SUBTRACT)) {
			return "1111";
		}
		else if(operation.equals(MathNode.Operations.MULTIPLY)) {
			return "0111";
		}
		return "0000";
	}
	
	//checks each possible boolean function
	private String getBooleanFunction(BranchNode.Operations operation) {
		if(operation.equals(BranchNode.Operations.EQUAL)) {
			return "0000";
		}
		else if(operation.equals(BranchNode.Operations.NOTEQUAL)) {
			return "0001";
		}
		else if(operation.equals(BranchNode.Operations.LESS)) {
			return "0010";
		}
		else if(operation.equals(BranchNode.Operations.LESSOREQUAL)) {
			return "0101";
		}
		else if(operation.equals(BranchNode.Operations.GREATER)) {
			return "0100";
		}
		else if(operation.equals(BranchNode.Operations.GREATEROREQUAL)) {
			return "0011";
		}
		return "1111";
	}
	
	//returns instruction for testing/use
	public LinkedList<String> getInstructions(){
		return instructions;
	}
}
