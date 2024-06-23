
public class R3 extends RegisterNode{
	private int r1, r2, rd, immediate;
	
	public R3(int r1, int r2, int rd, int immediate) {
		this.r1 = r1;
		this.r2 = r2;
		this.rd = rd;
		this.immediate = immediate;
	}
	
	public int getRegister1() {
		return r1;
	}
	
	public int getRegister2() {
		return r2;
	}
	
	public int getRegisterDirection() {
		return rd;
	}
	
	public int getImmediate() {
		return immediate;
	}
	
	public String toString() {
		return "Immediate: " + immediate + ", Register: " + r1 + ", Register: " + r2 + ", Register Direction: " + rd;
	}
}
