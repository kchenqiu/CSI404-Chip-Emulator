
public class NoR extends RegisterNode{
	private int immediate;
	
	public NoR(int immediate) {
		this.immediate = immediate;
	}
	
	public int getImmediate() {
		return immediate;
	}
	
	public String toString() {
		return "Immediate: " + immediate;
	}
}
