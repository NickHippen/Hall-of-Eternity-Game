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

public class Button extends BoundingSprite{

	private static BufferedImage spriteSheet;
	
	private static ArrayList<BufferedImage> buttonList;

	static{
		URL url = Zombie.class.getResource("/resources/menus/buttons/buttonDone.png");
		try {
			spriteSheet = (ImageIO.read(url));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public Button(TileWorld world) {
		super(spriteSheet, 1, 1);		
		URL url;
		buttonList = new ArrayList<BufferedImage>();
		try {
			url = Zombie.class.getResource("/resources/menus/buttons/buttonDone.png");
			buttonList.add(ImageIO.read(url));
			url = Zombie.class.getResource("/resources/menus/buttons/buttonDone_selected.png");
			buttonList.add(ImageIO.read(url));
			url = Zombie.class.getResource("/resources/menus/buttons/buttonQuit.png");
			buttonList.add(ImageIO.read(url));
			url = Zombie.class.getResource("/resources/menus/buttons/buttonQuit_selected.png");
			buttonList.add(ImageIO.read(url));
			url = Zombie.class.getResource("/resources/menus/buttons/buttonMonster.png");
			buttonList.add(ImageIO.read(url));
			url = Zombie.class.getResource("/resources/menus/buttons/buttonMonster_selected.png");
			buttonList.add(ImageIO.read(url));
			url = Zombie.class.getResource("/resources/menus/buttons/buttonCurse.png");
			buttonList.add(ImageIO.read(url));
			url = Zombie.class.getResource("/resources/menus/buttons/buttonCurse_selected.png");
			buttonList.add(ImageIO.read(url));
			url = Zombie.class.getResource("/resources/menus/buttons/buttonTrap.png");
			buttonList.add(ImageIO.read(url));
			url = Zombie.class.getResource("/resources/menus/buttons/buttonTrap_selected.png");
			buttonList.add(ImageIO.read(url));
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
		setOuterBound(new AxisAlignedBoundingBox(
				new Vector2f(-0.55f, 0.21f),
				new Vector2f(0.55f, -.22f)));
	}
	
	public Button(BufferedImage image){
		super(image, 1, 1);
	}
	
	public void selectButton(int i){
		this.setRenderedImage(buttonList.get(i));
	}
}
