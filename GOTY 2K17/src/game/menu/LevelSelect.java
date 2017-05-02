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

public class LevelSelect extends BoundingSprite {

	private static BufferedImage spriteSheet;
	private static BufferedImage spriteSheet2;
	private static BufferedImage spriteSheet3;
	private static BufferedImage spriteSheet4;
	private static BufferedImage spriteSheet5;

	static {
		try {
			URL url = Zombie.class.getResource("/resources/menus/level_select.png");
			spriteSheet = ImageIO.read(url);
			url = Zombie.class.getResource("/resources/menus/level_select_ruins.png");
			spriteSheet2 = ImageIO.read(url);

			url = Zombie.class.getResource("/resources/menus/level_select_snow.png");
			spriteSheet3 = ImageIO.read(url);

			url = Zombie.class.getResource("/resources/menus/level_select_town.png");
			spriteSheet4 = ImageIO.read(url);

			url = Zombie.class.getResource("/resources/menus/level_select_cards.png");
			spriteSheet5 = ImageIO.read(url);
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
	}

	public LevelSelect(TileWorld world) {
		super(spriteSheet, 1, 1);

		setOuterBound(new AxisAlignedBoundingBox(
				new Vector2f(-TileFramework.TILE_SIZE_X / 2F, TileFramework.TILE_SIZE_Y / 2F),
				new Vector2f(TileFramework.TILE_SIZE_X / 2F, -TileFramework.TILE_SIZE_Y / 2F)));
	}

	public void selectButton(int i) {
		switch (i) {
		case 0:
			this.setRenderedImage(spriteSheet);
			break;
		case 1:
			this.setRenderedImage(spriteSheet2);
			break;
		case 2:
			this.setRenderedImage(spriteSheet3);
			break;
		case 3:
			this.setRenderedImage(spriteSheet4);
			break;
		case 4:
			this.setRenderedImage(spriteSheet5);
			break;
		}
	}

	public void unselectButton() {
		this.setRenderedImage(spriteSheet);
	}

}
