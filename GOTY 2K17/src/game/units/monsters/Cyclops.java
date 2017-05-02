package game.units.monsters;

import java.awt.image.BufferedImage;
import java.net.URL;

import javax.imageio.ImageIO;

import game.TileWorld;

public class Cyclops extends Monster {

	private final static int MAX_HEALTH = 500;
	private final static int DAMAGE = 25;
	
	private static BufferedImage spriteSheet;
	
	static {
		try {
			URL url = Cyclops.class.getResource("/resources/units/monsters/cyclops.png");
			spriteSheet = ImageIO.read(url);
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
	}
	
	public Cyclops(TileWorld world) {
		super(spriteSheet, world, MAX_HEALTH);
		this.maxFrameNum = 15;
		this.setDamage(DAMAGE);
	}
	
	public String getName(){
		return "Cyclops";
	}
	
}
