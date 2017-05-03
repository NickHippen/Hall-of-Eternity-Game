package game.maps;

import java.awt.image.BufferedImage;
import java.util.List;
import java.util.Set;

import game.TileLocation;
import game.TileWorld;
import game.sound.PlayerControl;
import game.sprites.SpriteObject;
import game.units.monsters.Boss;
import game.util.Direction;

public abstract class GameMap extends SpriteObject {

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
	
	public abstract void getSoung(PlayerControl bg);
	
	public void addInvalidTileLocation(TileLocation location) {
		getInvalidTileLocations().add(location);
	}
	
	public void removeInvalidTileLocation(TileLocation location) {
		getInvalidTileLocations().remove(location);
	}
}
