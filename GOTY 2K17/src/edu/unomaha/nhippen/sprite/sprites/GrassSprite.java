package edu.unomaha.nhippen.sprite.sprites;

import java.awt.image.BufferedImage;
import java.net.URL;

import javax.imageio.ImageIO;

public class GrassSprite extends SpriteObject {

	private static BufferedImage BASE_IMAGE;
	
	static {
		try {
			URL url = GrassSprite.class.getResource("/sprites/Tiles.png");
			BufferedImage spriteSheet = ImageIO.read(url);
			BASE_IMAGE = spriteSheet.getSubimage(0, 0, 48, 48);
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
	}
	
	public GrassSprite() {
		super(BASE_IMAGE);
	}

}
