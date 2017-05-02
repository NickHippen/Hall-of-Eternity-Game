package game.units.monsters;

import java.awt.image.BufferedImage;
import java.net.URL;

import javax.imageio.ImageIO;

import game.TileWorld;

public class Cerberus extends Monster {

	private final static int MAX_HEALTH = 200;
	private final static int DAMAGE = 45;
	
	private static BufferedImage spriteSheet;
	
	static {
		try {
			URL url = Cerberus.class.getResource("/resources/units/monsters/cerberus.png");
			spriteSheet = ImageIO.read(url);
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
	}
	
	public Cerberus(TileWorld world) {
		super(spriteSheet, world, MAX_HEALTH);
		this.maxFrameNum = 13;
		this.setDamage(DAMAGE);
	}
	
	public String getName(){
		return "Cerberus";
	}
	
}
