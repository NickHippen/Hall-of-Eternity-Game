package game.units.heroes;

import java.awt.image.BufferedImage;
import java.net.URL;

import javax.imageio.ImageIO;

import game.TileWorld;

public class Rogue extends Hero {
	private final static int MAX_HEALTH = 125;
	private final static int DAMAGE = 25;
	
	private static BufferedImage spriteSheet;
	
	static {
		try {
			URL url = Rogue.class.getResource("/resources/units/heroes/rogue.png");
			spriteSheet = ImageIO.read(url);
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
	}
	
	public Rogue(TileWorld world) {
		super(spriteSheet, world, MAX_HEALTH, HeroClassType.ROGUE);
		this.name="Rogue";		
		this.setDamage(DAMAGE);
		this.setDropAmount(13);
	}
	
	public String getName(){
		return "Rogue";
	}
}
