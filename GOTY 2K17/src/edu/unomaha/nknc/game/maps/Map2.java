package edu.unomaha.nknc.game.maps;

import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import edu.unomaha.nknc.game.TileLocation;

public class Map2 extends GameMap {

	private static final List<TileLocation> INVALID_TILE_LOCATIONS = new ArrayList<>();
	
	private static BufferedImage BASE_IMAGE;

	static {
		// Invalid Locations
		for (int x = 0; x < 30; x++) {
			for (int y = 0; y < 2; y++) {
				INVALID_TILE_LOCATIONS.add(new TileLocation(x, y));
			}
		}
		
		// Image
		try {
			URL url = Map2.class.getResource("/sprites/maps/map02.png");
			BufferedImage spriteSheet = ImageIO.read(url);
			BASE_IMAGE = spriteSheet;
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
	}
	
	public Map2() {
		super(BASE_IMAGE);
	}

	@Override
	public List<TileLocation> getInvalidTileLocations() {
		return INVALID_TILE_LOCATIONS;
	}

}
