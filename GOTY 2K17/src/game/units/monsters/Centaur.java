package game.units.monsters;

import java.awt.image.BufferedImage;
import java.net.URL;

import javax.imageio.ImageIO;

import game.TileWorld;

public class Centaur extends Monster {

	private final static int MAX_HEALTH = 300;
	private final static int DAMAGE = 20;
	
	private static BufferedImage spriteSheet;
	
	static {
		try {
			URL url = Centaur.class.getResource("/resources/units/monsters/centaur.png");
			spriteSheet = ImageIO.read(url);
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
	}
	
	public Centaur(TileWorld world) {
		super(spriteSheet, world, MAX_HEALTH);
		this.maxFrameNum = 12;
		this.setDamage(DAMAGE);
	}
	
	public String getName(){
		return "Centaur";
	}
}
