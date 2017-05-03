package game.units.monsters;

import java.awt.image.BufferedImage;
import java.net.URL;

import javax.imageio.ImageIO;

import game.TileWorld;

public class Zombie extends Monster {

	private final static int MAX_HEALTH = 200;
	private final static int DAMAGE = 30;
	
	private static BufferedImage spriteSheet;
	
	static {
		try {
			URL url = Zombie.class.getResource("/resources/units/monsters/zombie.png");
			spriteSheet = ImageIO.read(url);
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
	}
	
	public Zombie(TileWorld world) {
		super(spriteSheet, world, MAX_HEALTH);
		this.maxFrameNum = 14;
		this.setDamage(DAMAGE);
	}
	
	public String getName(){
		return "Zombie";
	}
	
	@Override
	public void update(float delta) {
		super.update(delta);
		System.out.println("ZOMBIE: " + getLocation());
	}
	
}
