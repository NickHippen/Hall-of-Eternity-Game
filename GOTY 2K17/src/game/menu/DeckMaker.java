package game.menu;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

import game.TileFramework;
import game.TileWorld;
import game.sprites.BoundingSprite;
import game.units.monsters.Zombie;
import game.util.Matrix3x3f;
import game.vectors.AxisAlignedBoundingBox;
import game.vectors.Vector2f;

public class DeckMaker extends BoundingSprite{

	private static BufferedImage spriteSheet;
	
	public Button monsterButton;
	public Button curseButton;
	public Button trapButton;
	public Button doneButton;
	
	static {
		try {
			URL url = Zombie.class.getResource("/resources/menus/deckMaker.png");
			spriteSheet = ImageIO.read(url);
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
	}
	
	public DeckMaker(TileWorld world) {
		super(spriteSheet, 1, 1);		
		
		setOuterBound(new AxisAlignedBoundingBox(
				new Vector2f(-TileFramework.TILE_SIZE_X / 2F, TileFramework.TILE_SIZE_Y / 2F),
				new Vector2f(TileFramework.TILE_SIZE_X / 2F, -TileFramework.TILE_SIZE_Y / 2F)));
		
		monsterButton = new Button(world);
		curseButton = new Button(world);
		trapButton = new Button(world);
		doneButton = new Button(world);
		
		monsterButton.selectButton(4);
		curseButton.selectButton(6);
		trapButton.selectButton(8);
		doneButton.selectButton(0);
		
		doneButton.setLocation(doneButton.getLocation().add(new Vector2f(0,100)));
	}
	
	public Button getMonsterButton(){
		return monsterButton;
	}
	
	public Button getCurseButton(){
		return curseButton;
	}
	
	public Button getTrapButton(){
		return trapButton;
	}
	
	public Button getDoneButton(){
		return doneButton;
	}
}
