package game.units.heroes;

import java.awt.image.BufferedImage;
import java.net.URL;

import javax.imageio.ImageIO;

import game.TileWorld;

public class Tank extends Hero {
	private final static int MAX_HEALTH = 200;
	private final static int DAMAGE = 10;
	
	private static BufferedImage spriteSheet;
	
	static {
		try {
			URL url = Tank.class.getResource("/resources/units/heroes/tank.png");
			spriteSheet = ImageIO.read(url);
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
	}
	
	public Tank(TileWorld world) {
		super(spriteSheet, world, MAX_HEALTH, HeroClassType.TANK);
		this.name="Tank";
		this.setDamage(DAMAGE);
		this.setDropAmount(70);
	}
	
	public String getName() {
		return "Tank";
	}
	
}
