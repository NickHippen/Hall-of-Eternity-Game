package game.units.monsters;

import java.awt.image.BufferedImage;
import java.net.URL;

import javax.imageio.ImageIO;

import game.TileWorld;
import game.units.LivingUnit;
import game.units.heroes.Hero;

public class Cerberus extends Monster {

	private final static int MAX_HEALTH = 275;
	private final static int DAMAGE = 65;
	
	private static BufferedImage spriteSheet;
	
	static {
		try {
			URL url = Cerberus.class.getResource("/resources/units/monsters/cerberus.png");
			spriteSheet = ImageIO.read(url);
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
	}
	
	public Cerberus(TileWorld world) {
		super(spriteSheet, world, MAX_HEALTH);
		this.maxFrameNum = 13;
		this.setDamage(DAMAGE);
		setOffsetY(-0.1f);
	}
	
	public String getName(){
		return "Cerberus";
	}
	
	@Override
	public void attack(LivingUnit target) {
		super.attack(target);
		if (target.getTile().getUnits(Hero.class).size() == 1) {
			super.attack(target); // Attack twice on solo units
		}
	}
	
}
