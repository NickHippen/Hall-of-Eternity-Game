package edu.unomaha.nhippen.sprite.sprites;

import java.awt.image.BufferedImage;
import java.net.URL;

import javax.imageio.ImageIO;

import edu.unomaha.nhippen.sprite.game.TileFramework;
import edu.unomaha.nhippen.sprite.vectors.AxisAlignedBoundingBox;
import edu.unomaha.nhippen.sprite.vectors.Vector2f;

public class BlastSprite extends BoundingSprite {

	private static BufferedImage BASE_IMAGE;
	
	static {
		try {
			URL url = BlastSprite.class.getResource("/sprites/blast.png");
			BufferedImage spriteSheet = ImageIO.read(url);
			BASE_IMAGE = spriteSheet;
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
	}
	
	public BlastSprite() {
		super(BASE_IMAGE);
		setOuterBound(new AxisAlignedBoundingBox(
				new Vector2f(-TileFramework.TILE_SIZE_X / 2F, TileFramework.TILE_SIZE_Y / 2F),
				new Vector2f(TileFramework.TILE_SIZE_X / 2F, -TileFramework.TILE_SIZE_Y / 2F)));
		setScale(0.95);
	}

}
