package game.units.monsters;

import java.awt.image.BufferedImage;
import java.net.URL;

import javax.imageio.ImageIO;

import game.TileFramework;
import game.TileWorld;
import game.vectors.AxisAlignedBoundingBox;
import game.vectors.Vector2f;

public class Spider extends Monster {

	private final static int MAX_HEALTH = 150;
	private final static int DAMAGE = 20;
	
	private static BufferedImage spriteSheet;
	
	static {
		try {
			URL url = Spider.class.getResource("/resources/units/monsters/spider.png");
			spriteSheet = ImageIO.read(url);
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
	}
	
	public Spider(TileWorld world) {
		super(spriteSheet, world, MAX_HEALTH);
		this.maxFrameNum = 12;	

		setOuterBound(new AxisAlignedBoundingBox(
				new Vector2f(-TileFramework.TILE_SIZE_X / 2F, TileFramework.TILE_SIZE_Y / 2F),
				new Vector2f(TileFramework.TILE_SIZE_X / 2F, -TileFramework.TILE_SIZE_Y / 2F)));
		
		this.setDamage(DAMAGE);
	}
	
	public String getName(){
		return "Spider";
	}
	
}
