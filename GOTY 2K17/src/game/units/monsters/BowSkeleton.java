package game.units.monsters;

import java.awt.image.BufferedImage;
import java.net.URL;

import javax.imageio.ImageIO;

import game.TileWorld;

public class BowSkeleton extends Monster {

	private final static int MAX_HEALTH = 100;
	private final static int DAMAGE = 30;
	
	private static BufferedImage spriteSheet;
	
	static {
		try {
			URL url = BowSkeleton.class.getResource("/resources/units/monsters/bow_skeleton.png");
			spriteSheet = ImageIO.read(url);
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
	}
	
	public BowSkeleton(TileWorld world) {
		super(spriteSheet, world, MAX_HEALTH);
		this.maxFrameNum = 12;
		this.setDamage(DAMAGE);
		setRanged(true);
	}
	
	public String getName() {
		return "Bow Skeleton";
	}
}
