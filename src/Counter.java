
public class Counter {

	private int value;
	
	public void setValue(int amount) {
		value = amount;
	}
	
	public void addValue(int amount) {
		value += amount;
	}
	
	public void count() {
		value++;
	}
	
	public int getValue() {
		return value;
	}
}
