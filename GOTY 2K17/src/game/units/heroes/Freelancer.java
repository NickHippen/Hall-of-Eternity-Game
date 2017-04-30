package game.units.heroes;

import java.awt.image.BufferedImage;
import java.net.URL;

import javax.imageio.ImageIO;

import game.TileWorld;

public class Freelancer extends Hero {
	private final static int MAX_HEALTH = 75;
	private final static int DAMAGE = 20;
	
	private static BufferedImage BASE_IMAGE;
	
	static {
		try {
			URL url = Freelancer.class.getResource("/resources/units/heroes/freelancer.png");
			BufferedImage spriteSheet = ImageIO.read(url);
			BASE_IMAGE = spriteSheet.getSubimage(0, 0, 64, 64);
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
	}
	
	public Freelancer(TileWorld world) {
		super(BASE_IMAGE, world, MAX_HEALTH, HeroDamageType.MELEE);
		this.name="Freelancer";		
		this.setDamage(DAMAGE);
	}
	
	public String getName(){
		return "Freelancer";
	}
}
