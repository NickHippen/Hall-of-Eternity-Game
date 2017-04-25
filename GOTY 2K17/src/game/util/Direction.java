package game.util;

public enum Direction {

	UP(-1, 0), DOWN(1, 0), LEFT(0, -1), RIGHT(0, 1);
	
	private int rowDif;
	private int colDif;
	
	private Direction(int rowDif, int colDif) {
		this.rowDif = rowDif;
		this.colDif = colDif;
	}

	public int getRowDif() {
		return rowDif;
	}

	public int getColDif() {
		return colDif;
	}

}
