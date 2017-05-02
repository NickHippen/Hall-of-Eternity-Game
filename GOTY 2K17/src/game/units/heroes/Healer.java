package game.units.heroes;

import java.awt.image.BufferedImage;
import java.net.URL;

import javax.imageio.ImageIO;

import game.TileWorld;

public class Healer extends Hero {
	private final static int MAX_HEALTH = 150;
	private final static int DAMAGE = -10;
	
	private static BufferedImage spriteSheet;
	
	static {
		try {
			URL url = Healer.class.getResource("/resources/units/heroes/healer.png");
			spriteSheet = ImageIO.read(url);
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
	}
	
	public Healer(TileWorld world) {
		super(spriteSheet, world, MAX_HEALTH, HeroClassType.HEALER);
		this.name="Healer";		
		this.setDamage(DAMAGE);
		this.setDropAmount(13);
	}
	
	public String getName(){
		return "Healer";
	}
}
