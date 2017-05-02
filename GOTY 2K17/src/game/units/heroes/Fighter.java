package game.units.heroes;

import java.awt.image.BufferedImage;
import java.net.URL;

import javax.imageio.ImageIO;

import game.TileWorld;

public class Fighter extends Hero {
	private final static int MAX_HEALTH = 150;
	private final static int DAMAGE = 25;
	
	private static BufferedImage spriteSheet;
	
	static {
		try {
			URL url = Fighter.class.getResource("/resources/units/heroes/fighter.png");
			spriteSheet = ImageIO.read(url);
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
	}
	
	public Fighter(TileWorld world) {
		super(spriteSheet, world, MAX_HEALTH, HeroClassType.FIGHTER);
		this.name="Fighter";		
		this.setDamage(DAMAGE);
		this.setDropAmount(10);
	}
	
	public String getName(){
		return "Fighter";
	}
}
