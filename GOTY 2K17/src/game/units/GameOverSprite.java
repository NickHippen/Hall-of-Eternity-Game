package game.units;

import java.awt.image.BufferedImage;
import java.net.URL;

import javax.imageio.ImageIO;

import game.TileWorld;
import game.sprites.SpriteObject;

public class GameOverSprite extends SpriteObject {

	private static BufferedImage spriteSheet;
	
	static {
		try {
			URL url = GameOverSprite.class.getResource("/resources/menus/YouDied.png");
			spriteSheet = ImageIO.read(url);
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
	}
	
	public GameOverSprite(TileWorld world) {
		super(spriteSheet, 1, 1);
	}
	
}