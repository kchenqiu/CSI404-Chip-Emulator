
public class DestOnly extends RegisterNode{
	private int rd, immediate;
	
	public DestOnly(int rd, int immediate) {
		this.rd = rd;
		this.immediate = immediate;
	}
	
	public int getRegisterDirection() {
		return rd;
	}
	
	public int getImmediate() {
		return immediate;
	}
	
	public String toString() {
		return "Immediate: " + immediate + ", Register Direction: " + rd;
	}
}
