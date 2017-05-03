package game.menu;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

import game.TileFramework;
import game.TileWorld;
import game.sprites.BoundingSprite;
import game.units.monsters.Zombie;
import game.vectors.AxisAlignedBoundingBox;
import game.vectors.Vector2f;

public class TitleScreen extends BoundingSprite{

	private static BufferedImage spriteSheet;
	private static BufferedImage spriteSheet2;
	static {
		try {
			URL url = Zombie.class.getResource("/resources/menus/title.png");
			spriteSheet = ImageIO.read(url);
			url = Zombie.class.getResource("/resources/menus/select_title.png");
			spriteSheet2 = ImageIO.read(url);
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
	}
	
	public TitleScreen(TileWorld world) {
		super(spriteSheet, 1, 1);		
		
		setOuterBound(new AxisAlignedBoundingBox(
				new Vector2f(-TileFramework.TILE_SIZE_X / 2F, TileFramework.TILE_SIZE_Y / 2F),
				new Vector2f(TileFramework.TILE_SIZE_X / 2F, -TileFramework.TILE_SIZE_Y / 2F)));
	}
	
	public void selectButton(){
		this.setRenderedImage(spriteSheet2);
	}
	
	public void unselectButton() {
		this.setRenderedImage(spriteSheet);
	}
}
