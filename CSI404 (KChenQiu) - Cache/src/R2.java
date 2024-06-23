
public class R2 extends RegisterNode{
	private int rs, rd, immediate;
	
	public R2(int rs, int rd, int immediate) {
		this.rs = rs;
		this.rd = rd;
		this.immediate = immediate;
	}
	
	public int getRegister() {
		return rs;
	}
	
	public int getRegisterDirection() {
		return rd;
	}
	
	public int getImmediate() {
		return immediate;
	}
	
	public String toString() {
		return "Immediate: " + immediate + ", Register: " + rs + ", Register Direction: " + rd;
	}
}
