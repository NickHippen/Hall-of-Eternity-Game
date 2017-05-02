package game.units.heroes;

import java.awt.image.BufferedImage;
import java.net.URL;

import javax.imageio.ImageIO;

import game.TileWorld;

public class Freelancer extends Hero {
	private final static int MAX_HEALTH = 75;
	private final static int DAMAGE = 20;
	
	private static BufferedImage spriteSheet;
	
	static {
		try {
			URL url = Freelancer.class.getResource("/resources/units/heroes/freelancer.png");
			spriteSheet = ImageIO.read(url);
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
	}
	
	public Freelancer(TileWorld world) {
		super(spriteSheet, world, MAX_HEALTH, HeroClassType.FREELANCER);
		this.name="Freelancer";		
		this.setDamage(DAMAGE);
		this.setDropAmount(7);
	}
	
	public String getName(){
		return "Freelancer";
	}
}
