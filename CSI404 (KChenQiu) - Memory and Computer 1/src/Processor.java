
public class Processor {
	private Word pc, sp, currentInstruction;
	Bit halted;
	
	
	public Processor() {
		pc = new Word();
		sp = new Word();
		currentInstruction = new Word();
		sp.setBit(20, new Bit(true));
		halted = new Bit();
	}
	
	public void run() {
		while(!halted.getValue()) {
			fetch();
			decode();
			execute();
			store();
		}
	}
		
	public void fetch() {
		currentInstruction.copy(MainMemory.read(pc));
		pc.Increment();
	}
	
	public void decode() {
		
	}
	
	public void execute() {
		
	}
	
	public void store() {
		
	}
}
