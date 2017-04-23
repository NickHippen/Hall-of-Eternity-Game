package edu.unomaha.nknc.game;

public class TileLocation {

	private int x;
	private int y;
	
	public TileLocation(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof TileLocation)) {
			return false;
		}
		TileLocation tileLoc = (TileLocation) obj;
		return tileLoc.x == this.x && tileLoc.y == this.y;
	}
	
}
