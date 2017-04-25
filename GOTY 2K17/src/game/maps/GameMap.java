package game.maps;

import java.awt.image.BufferedImage;
import java.util.List;

import game.TileLocation;
import game.sprites.SpriteObject;

public abstract class GameMap extends SpriteObject {
	
	public GameMap(BufferedImage image) {
		super(image, 1, 1);
	}
	
	public boolean isTileLocationValid(TileLocation loc) {
		return !getInvalidTileLocations().contains(loc);
	}
	
	public abstract List<TileLocation> getInvalidTileLocations();
	
	public abstract List<TileLocation> getGoalLocations();
	
}
