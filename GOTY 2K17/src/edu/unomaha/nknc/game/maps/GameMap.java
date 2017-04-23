package edu.unomaha.nknc.game.maps;

import java.awt.image.BufferedImage;
import java.util.List;

import edu.unomaha.nknc.game.TileLocation;
import edu.unomaha.nknc.game.sprites.SpriteObject;

public abstract class GameMap extends SpriteObject {
	
	public GameMap(BufferedImage image) {
		super(image);
	}
	
	public abstract List<TileLocation> getInvalidTileLocations();
	
}
