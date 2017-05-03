package game.units.monsters;

import java.awt.image.BufferedImage;
import java.net.URL;

import javax.imageio.ImageIO;

import game.TileWorld;

public class Wolf extends Monster {

	private final static int MAX_HEALTH = 200;
	private final static int DAMAGE = 30;
	
	private static BufferedImage spriteSheet;
	
	static {
		try {
			URL url = Wolf.class.getResource("/resources/units/monsters/wolf.png");
			spriteSheet = ImageIO.read(url);
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
	}
	
	public Wolf(TileWorld world) {
		super(spriteSheet, world, MAX_HEALTH);
		this.maxFrameNum = 12;
		this.setDamage(DAMAGE);
		setAttackSpeed(getAttackSpeed() / 2f);
	}
	
	public String getName(){
		return "Wolf";
	}
	
}
