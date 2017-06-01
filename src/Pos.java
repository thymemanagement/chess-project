
public class Pos {
	
	private int row;
	private int col;
	
	public Pos(int row, int col) {
		this.row = row;
		this.col = col;
	}
	
	public int getRow() {
		return row;
	}
	
	public int getCol() {
		return col;
	}
	
	public boolean equals(Object o) {
		Pos other = (Pos) o;
		return this.row == other.row && this.col == other.col;
	}

}
