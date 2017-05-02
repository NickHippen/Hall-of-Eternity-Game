package game;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import game.maps.GameMap;
import game.units.Unit;
import game.units.monsters.Boss;
import game.units.monsters.Monster;
import game.units.traps.Trap;
import game.util.Direction;
import game.vectors.Vector2f;

public class TileWorld {

	private Tile[][] tiles;
	
	private GameMap map;
	private List<Unit> units;
	private int tilesX;
	private int tilesY;
	private float tileSizeX;
	private float tileSizeY;
	private float worldWidth;
	private float worldHeight;
	
	private int waveNum;
	private int boneNum;
	
	protected TileWorld(GameMap map, int tilesX, int tilesY, float tileSizeX, float tileSizeY, float worldWidth, float worldHeight) {
		this.map = map;
		this.units = new ArrayList<>();
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
				 tiles[i][j] = new Tile(new TileLocation(i, j), this);
			 }
		 }
	}
	
	public Tile[][] getTiles() {
		return tiles;
	}
	
	public GameMap getMap() {
		return map;
	}
	
	public List<Unit> getUnits(){
		return units;
	}
	
	public void addUnitToTile(TileLocation location, Unit unit) {
		//Get the tile it was placed. The middle of the sprite spawns at 0,0 (upper left) of tile.
		//Sprite must be shifted 24 pixels to the right to line up with the center
		//It must also be shifted frameSize/2 - 48 pixels up/down for the bottom to line up with the tile
		unit.setLocation(new Vector2f(tileSizeX * location.getX() - (worldWidth / 2F) + (tileSizeX/48) * 24,
				tileSizeY * (tilesY - location.getY() - 1) - (worldHeight / 2F) + tileSizeY + (unit.getFrameSize()/2 - 48) * (tileSizeY/48)));
		if (unit instanceof Trap) System.out.println(unit.getFrameSize());
		if(unit instanceof Boss) unit.setLocation(unit.getLocation().add(new Vector2f(.11f, -.06f)));
//		tiles[location.getX()][location.getY()].getUnits().add(unit);
		units.add(unit);

		//Hard code dragon to take up 1x3 tiles
//		if(unit instanceof Dragon){
//			tiles[location.getX()-1][location.getY()].getUnits().add(unit);
//		}
	
		if (unit instanceof Monster) {
			policyIteration(Tile::getAggroPathfinding);
		}
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
	
	public Tile getTileAtPosition(Vector2f pos) {
		TileLocation tileLoc = getTileLocationAtPosition(pos);
		return getTile(tileLoc);
	}
	
	public Tile getTile(TileLocation loc) {
		return tiles[loc.getX()][loc.getY()];
	}
	
	//Returns a list of the 9 tiles in a square
	public ArrayList<Tile> getSurroundingTilesDiag(TileLocation loc, int distance){
		ArrayList<Tile> tiles = new ArrayList<>();
		int thisX = loc.getX();
		int thisY = loc.getY();
		for(int i = thisX - distance; i <= thisX + distance; i++){
			for(int j = thisY - distance; j <= thisY + distance; j++){
				if(i >= 0 && i <= 29 && j >= 0 && i <= 19) tiles.add(getTile(new TileLocation(i,j))); //Don't add out of bounds tiles to selection
			}
		}
		return tiles;
	}
	
	public Set<Tile> getSurroundingTiles(TileLocation loc, int distance) {
		return getSurroundingTiles(loc, distance, null);
	}
	
	public Set<Tile> getSurroundingTiles(TileLocation loc, int distance, Direction exclude) {
		Set<Tile> tiles = new HashSet<>();
		if (distance == 0) {
			return tiles;
		}
		for (TileLocation neighbor : getNeighborLocations(loc, exclude)) {
			tiles.add(getTile(neighbor));
			tiles.addAll(getSurroundingTiles(neighbor, distance - 1, exclude));
		}
		return tiles;
	}
	
	public Set<TileLocation> getNeighborLocations(TileLocation loc, Direction exclude) {
		Set<TileLocation> neighbors = new HashSet<>();
		for (Direction dir : Direction.values()) {
			if (dir == exclude) {
				continue;
			}
			TileLocation neighborLoc = new TileLocation(loc.getX() + dir.getDx(), loc.getY() + dir.getDy());
			if (!isOutOfBounds(neighborLoc.getX(), neighborLoc.getY())) {
				neighbors.add(neighborLoc);
			}
		}
		return neighbors;
	}
	
	public void policyIteration(PathfindingNodeGetter nodeGetter) {
		boolean changed;
		do {
			changed = calculateActionValues(nodeGetter);
		} while (changed);
	}
	
	private boolean calculateActionValues(PathfindingNodeGetter nodeGetter) {
		boolean changed = false;
		double newValues[][] = new double[tilesX][tilesY];
		for (int x = 0; x < tilesX; x++) {
			for (int y = 0; y < tilesY - 8; y++) {
				Tile tile = tiles[x][y];
				PathfindingNode node = nodeGetter.getNode(tile); 
				node.getActionQMap().put(Direction.values()[0], calculateValue(x, y, Direction.values()[0], nodeGetter));
				newValues[x][y] = node.getActionQMap().get(Direction.values()[0]);
				for (int i = 0; i < Direction.values().length; i++) {
					Direction action = Direction.values()[i];
					node.getActionQMap().put(action, calculateValue(x, y, action, nodeGetter));
					if (node.getActionQMap().get(action) > newValues[x][y]) {
						newValues[x][y] = node.getActionQMap().get(action);
					}
					if (node.updateDirections()) {
						changed = true;
					}
				}
			}
		}
		// Apply new values
		for (int x = 0; x < tilesX; x++) {
			for (int y = 0; y < tilesY; y++) {
				nodeGetter.getNode(tiles[x][y]).setQValue(newValues[x][y]);
			}
		}
		return changed;
	}
	
	private double calculateValue(int x, int y, Direction action, PathfindingNodeGetter nodeGetter) {
		for (TileLocation goalLoc : getMap().getGoalLocations()) {
			if (new TileLocation(x, y).equals(goalLoc)) {
				return 100;
			}
		}
		if (nodeGetter.getNode(tiles[x][y]).isFocusMonsters() && tiles[x][y].hasMonster()) {
			return 100;
		}
		double newQ = 0;
		for (Direction dir : Direction.values()) {
			double contrib = contribution(x, y, dir, nodeGetter);
			if (dir == action) {
				newQ += contrib;
			}
		}
		
		if (!getMap().isTileLocationValid(new TileLocation(x, y))) {
			newQ += -1000;
		}
		return newQ;
	}
	
	private double contribution(int x, int y, Direction dir, PathfindingNodeGetter nodeGetter) {
		switch (dir) {
		case DOWN:
			if (isOutOfBounds(x, y + 1)) {
				return 0.9 * nodeGetter.getNode(tiles[x][y]).getQValue();
			}
			return 0.9 * nodeGetter.getNode(tiles[x][y + 1]).getQValue();
		case LEFT:
			if (isOutOfBounds(x - 1, y)) {
				return 0.9 * nodeGetter.getNode(tiles[x][y]).getQValue();
			}
			return 0.9 * nodeGetter.getNode(tiles[x - 1][y]).getQValue();
		case RIGHT:
			if (isOutOfBounds(x + 1, y)) {
				return 0.9 * nodeGetter.getNode(tiles[x][y]).getQValue();
			}
			return 0.9 * nodeGetter.getNode(tiles[x + 1][y]).getQValue();
		case UP:
			if (isOutOfBounds(x, y - 1)) {
				return 0.9 * nodeGetter.getNode(tiles[x][y]).getQValue();
			}
			return 0.9 * nodeGetter.getNode(tiles[x][y - 1]).getQValue();
		}
		return 0;
	}
	
	public boolean isOutOfBounds(int x, int y) {
		if (y < 0 || x < 0) {
			return true;
		}
		if (x > tilesX -1 || y > tilesY - 1) {
			return true;
		}
		return false;
	}
	
	public int getTilesX() {
		return tilesX;
	}

	public int getTilesY() {
		return tilesY;
	}

	public float getTileSizeX() {
		return tileSizeX;
	}

	public float getTileSizeY() {
		return tileSizeY;
	}

	public float getWorldWidth() {
		return worldWidth;
	}

	public float getWorldHeight() {
		return worldHeight;
	}
	
	public int getWaveNum() {
		return waveNum;
	}

	public void setWaveNum(int waveNum) {
		this.waveNum = waveNum;
	}

	public int getBoneNum() {
		return boneNum;
	}

	public void setBoneNum(int boneNum) {
		this.boneNum = boneNum;
	}
	
	public void addBones(int amount) {
		this.setBoneNum(this.getBoneNum() + amount);
	}

	@FunctionalInterface
	public static interface PathfindingNodeGetter {
		public PathfindingNode getNode(Tile tile);
	}
	
}
