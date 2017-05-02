package game.units.heroes;

import java.awt.image.BufferedImage;
import java.net.URL;

import javax.imageio.ImageIO;

import game.TileWorld;

public class Mage extends Hero {
	private final static int MAX_HEALTH = 100;
	private final static int DAMAGE = 100;
	
	private static BufferedImage spriteSheet;
	
	static {
		try {
			URL url = Mage.class.getResource("/resources/units/heroes/mage.png");
			spriteSheet = ImageIO.read(url);
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
	}
	
	public Mage(TileWorld world) {
		super(spriteSheet, world, MAX_HEALTH, HeroClassType.MAGE);
		this.name="Mage";		
		this.setDamage(DAMAGE);
		this.setDropAmount(15);
	}
	
	public String getName(){
		return "Mage";
	}
}
