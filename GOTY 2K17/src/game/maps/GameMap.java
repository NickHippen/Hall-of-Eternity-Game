package game.maps;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import game.TileLocation;
import game.TileWorld;
import game.sprites.SpriteObject;
import game.units.monsters.Boss;
import game.util.Direction;

public abstract class GameMap extends SpriteObject {
	protected static final List<TileLocation> INVALID_TILE_LOCATIONS = new ArrayList<>();

	public GameMap(BufferedImage image) {
		super(image, 1, 1);
	}
	
	public boolean isTileLocationValid(TileLocation loc) {
		return !getInvalidTileLocations().contains(loc);
	}
	
	public abstract List<TileLocation> getInvalidTileLocations();
	
	public abstract List<TileLocation> getGoalLocations();
	
	public abstract Set<Direction> getHardcodedDirections(TileLocation loc);

	public abstract void addBoss(TileWorld world, Boss boss);
	
	
	public void addInvalidTileLocation(int x, int y) {
		INVALID_TILE_LOCATIONS.add(new TileLocation(x,y));
	}
	
	public void removeInvalidTileLocation(int x, int y) {
		INVALID_TILE_LOCATIONS.remove(new TileLocation(x,y));
	}
}
