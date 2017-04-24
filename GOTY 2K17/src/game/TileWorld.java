package game;

import game.maps.GameMap;
import game.units.Unit;
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
		unit.setLocation(new Vector2f(tileSizeX * location.getX() - (worldWidth / 2F)/* + (tileSizeX / 2F)*/,
										tileSizeY * (tilesY - location.getY() - 1) - (worldHeight / 2F) + tileSizeY));
		tiles[location.getX()][location.getY()].getUnits().add(unit);
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
	
}
