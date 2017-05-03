package game.menu;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import game.TileWorld;
import game.sprites.BoundingSprite;
import game.units.monsters.Zombie;
import game.vectors.AxisAlignedBoundingBox;
import game.vectors.Vector2f;

public class DrawButton extends BoundingSprite{

	private static BufferedImage spriteSheet;

	private static ArrayList<BufferedImage> buttonList;

	static{
		URL url = Zombie.class.getResource("/resources/menus/buttons/drawButton.png");
		try {
			spriteSheet = (ImageIO.read(url));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public DrawButton(TileWorld world) {
		super(spriteSheet, 1, 1);		
		this.setLocation(new Vector2f(2.485f, -.95f));
	}
}
