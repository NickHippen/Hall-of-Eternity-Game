package game;

import java.util.Arrays;

import game.maps.GameMap;
import game.units.Unit;
import game.util.Direction;
import game.vectors.Vector2f;

public class TileWorld {

	private Tile[][] tiles;
	
	private GameMap map;
	private int tilesX;
	private int tilesY;
	private float tileSizeX;
	private float tileSizeY;
	private float worldWidth;
	private float worldHeight;
	
	protected TileWorld(GameMap map, int tilesX, int tilesY, float tileSizeX, float tileSizeY, float worldWidth, float worldHeight) {
		this.map = map;
		this.tilesX = tilesX;
		this.tilesY = tilesY;
		this.tileSizeX = tileSizeX;
		this.tileSizeY = tileSizeY;
		this.worldWidth = worldWidth;
		this.worldHeight = worldHeight;
		
		tiles = new Tile[tilesX][];
		 for (int i = 0; i < tiles.length; i++) {
			 tiles[i] = new Tile[tilesY];
			 for (int j = 0; j < tiles[i].length; j++) {
				 tiles[i][j] = new Tile();
			 }
		 }
	}
	
	public Tile[][] getTiles() {
		return tiles;
	}
	
	public GameMap getMap() {
		return map;
	}
	
	public void addUnitToTile(TileLocation location, Unit unit) {
		System.out.println(tileSizeX * location.getX());
		unit.setLocation(new Vector2f(tileSizeX * location.getX() - (worldWidth / 2F) + (worldWidth/unit.getFrameSize()),
				tileSizeY * (tilesY - location.getY() - 1) - (worldHeight / 2F) + tileSizeY - (worldHeight/unit.getFrameSize())));
		tiles[location.getX()][location.getY()].getUnits().add(unit);
		System.out.println(unit.getLocation().x + " " + unit.getLocation().y);
	}
	
	public TileLocation getTileLocationAtPosition(Vector2f pos) {
		int x = (int) (tilesX * ((pos.x + (worldWidth / 2)) / worldWidth));
		int y = tilesY - 1 - (int) (tilesY * ((pos.y + (worldHeight / 2)) / worldHeight));
		if (x >= tilesX) {
			x = tilesX - 1;
		}
		if (y >= tilesY) {
			y = tilesY - 1;
		}
		return new TileLocation(x, y);
	}
	
	public void valueIteration() {
		double prevDelta = 0;
		do {
			double delta = dostep();
			if (Math.abs(delta - prevDelta) <= 0) {
				break;
			}
			prevDelta = delta;
		} while (true);
	}
	
	private double discount = 0.9;
	
	public double dostep() {
		double delta = 0;
		double newvalues[][] = new double[tilesX][tilesY];
		for (int x = 0; x < tilesX; x++) {
			for (int y = 0; y < tilesY; y++) {
				double d = calculateValues(x, y, Direction.values()[0]);
				if (d > delta) {
					delta = d;
				}
				tiles[x][y].Q.put(Direction.values()[0], d);
				newvalues[x][y] = tiles[x][y].Q.get(Direction.values()[0]);
//				for (Direction action : Direction.values()) {
				for (int i = 0; i < Direction.values().length; i++) {
					Direction action = Direction.values()[i];
					d = calculateValues(x, y, action);
					if (d > delta) {
						delta = d;
					}
					tiles[x][y].Q.put(action, d);
					if (tiles[x][y].Q.get(action) > newvalues[x][y]) {
						newvalues[x][y] = tiles[x][y].Q.get(action);
					}
				}
			}
		}
		// Apply new values
		for (int x = 0; x < tilesX; x++) {
			for (int y = 0; y < tilesY; y++) {
				tiles[x][y].value = newvalues[x][y];
			}
		}
		return delta;
	}
	
	public double calculateValues(int x, int y, Direction action) {
		if (x == 28 && (y == 6 || y == 7)) {
			return 100;
		}
		if (tiles[x][y].hasMonster()) {
			return 20;
		}
		double newQ = 0;
		for (Direction dir : Direction.values()) {
			double contrib = contribution(x, y, dir);
			if (dir == action) {
				newQ += contrib;
			}
		}
		
		if (!getMap().isTileLocationValid(new TileLocation(x, y))) {
			newQ += -100;
		}
		return newQ;
	}
	
	public double contribution(int x, int y, Direction dir) {
		switch (dir) {
		case DOWN:
			if (isOutOfBounds(x, y + 1)) {
				return /*-1 + */discount * tiles[x][y].value;
			}
			return discount * tiles[x][y + 1].value;
		case LEFT:
			if (isOutOfBounds(x - 1, y)) {
				return /*-1 + */discount * tiles[x][y].value;
			}
			return discount * tiles[x - 1][y].value;
		case RIGHT:
			if (isOutOfBounds(x + 1, y)) {
				return /*-1 + */discount * tiles[x][y].value;
			}
			return discount * tiles[x + 1][y].value;
		case UP:
			if (isOutOfBounds(x, y - 1)) {
				return /*-1 + */discount * tiles[x][y].value;
			}
			return discount * tiles[x][y - 1].value;
		}
		return 0;
	}
	
//	public double calculateValues(boolean applyBest, int k) {
//		double delta = 0;
//		for (int i = 0; i < k; i++) {
//			for (int y = 0; y < tilesY; y++) {
//				for (int x = tilesX - 1; x >= 0; x--) {
//					if ((y == 6 || y == 7) && x == 28) { // Goal
//						continue;
//					}
//					double d = calculateValue(x, y, applyBest);
//					if (d > delta) {
//						delta = d;
//					}
//				}
//			}
//		}
//		return delta;
//	}
//	
//	public double calculateValue(int x, int y, boolean applyBest) {
//		double maxValue = Double.NEGATIVE_INFINITY;
//		Direction bestDir = null;
//		for (Direction primaryDir : Direction.values()) {
//			double value = 0;
//			if (!isOutOfBounds(x + primaryDir.getColDif(), y + primaryDir.getRowDif())) {
//				value += tiles[x + primaryDir.getColDif()][y + primaryDir.getRowDif()].U;
//			} else {
//				// Apply reward function for staying in place
//				value += tiles[x][y].U;
//			}
//			if (value > maxValue) {
//				maxValue = value;
//				bestDir = primaryDir;
//			}
//		}
//		double oldValue = tiles[x][y].U;
//		if (getMap().isTileLocationValid(new TileLocation(x, y))) {
//			if (tiles[x][y].hasMonster()) {
//				tiles[x][y].U = maxValue + 1000;
//			} else {
//				tiles[x][y].U = maxValue - 400;
//			}
//		} else {
//			tiles[x][y].U = maxValue - 1000;
//		}
//		if (applyBest) {
//			tiles[x][y].D = bestDir;
//		}
//		return Math.abs(oldValue - tiles[x][y].U);
//	}
	
	public boolean isOutOfBounds(int x, int y) {
		if (y < 0 || x < 0) {
			return true;
		}
		if (x > tilesX -1 || y > tilesY - 1) {
			return true;
		}
		return false;
	}
	
}
