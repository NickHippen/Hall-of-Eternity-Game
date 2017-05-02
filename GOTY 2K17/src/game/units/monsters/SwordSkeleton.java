package game.units.monsters;

import java.awt.image.BufferedImage;
import java.net.URL;

import javax.imageio.ImageIO;

import game.TileWorld;

public class SwordSkeleton extends Monster {

	private final static int MAX_HEALTH = 175;
	private final static int DAMAGE = 30;
	
	private static BufferedImage spriteSheet;
	
	static {
		try {
			URL url = SwordSkeleton.class.getResource("/resources/units/monsters/sword_skeleton.png");
			spriteSheet = ImageIO.read(url);
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
	}
	
	public SwordSkeleton(TileWorld world) {
		super(spriteSheet, world, MAX_HEALTH);
		this.maxFrameNum = 12;
		this.setDamage(DAMAGE);
	}
	
	public String getName(){
		return "Sword Skeleton";
	}
	
}
