package game.sprites;

import java.awt.image.BufferedImage;
import java.net.URL;

import javax.imageio.ImageIO;

import game.TileFramework;
import game.units.Unit;
import game.vectors.AxisAlignedBoundingBox;
import game.vectors.Vector2f;

public class RockSprite extends Unit {

	private static BufferedImage BASE_IMAGE;
	
	static {
		try {
			URL url = RockSprite.class.getResource("/sprites/Tiles.png");
			BufferedImage spriteSheet = ImageIO.read(url);
			BASE_IMAGE = spriteSheet.getSubimage(0, 48, 48, 48);
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
	}
	
	public RockSprite() {
		super(BASE_IMAGE);
		setOuterBound(new AxisAlignedBoundingBox(
				new Vector2f(-TileFramework.TILE_SIZE_X / 2F, TileFramework.TILE_SIZE_Y / 2F),
				new Vector2f(TileFramework.TILE_SIZE_X / 2F, -TileFramework.TILE_SIZE_Y / 2F)));
	}

	@Override
	public void setSize() {
		// TODO Auto-generated method stub
		
	}

}
