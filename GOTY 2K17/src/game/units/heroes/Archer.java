package game.units.heroes;

import java.awt.image.BufferedImage;
import java.net.URL;

import javax.imageio.ImageIO;

import game.TileWorld;

public class Archer extends Hero {
	private final static int MAX_HEALTH = 75;
	private final static int DAMAGE = 20;
	
	private static BufferedImage spriteSheet;
	
	static {
		try {
			URL url = Archer.class.getResource("/resources/units/heroes/archer.png");
			spriteSheet = ImageIO.read(url);
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
	}
	
	public Archer(TileWorld world) {
		super(spriteSheet, world, MAX_HEALTH, HeroClassType.ARCHER);
		this.name="Archer";
		this.setDamage(DAMAGE);
		this.setDropAmount(30);
	}
	
	public String getName(){
		return "Archer";
	}
}
