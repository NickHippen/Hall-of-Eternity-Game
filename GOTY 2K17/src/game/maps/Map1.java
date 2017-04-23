package game.maps;

import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

import javax.imageio.ImageIO;

import game.TileLocation;

public class Map1 extends GameMap {

	private static BufferedImage BASE_IMAGE;

	static {
		try {
			URL url = Map1.class.getResource("/sprites/maps/map01.png");
			BufferedImage spriteSheet = ImageIO.read(url);
			BASE_IMAGE = spriteSheet;
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
	}
	
	public Map1() {
		super(BASE_IMAGE);
	}

	@Override
	public List<TileLocation> getInvalidTileLocations() {
		return Arrays.asList();
	}

}
