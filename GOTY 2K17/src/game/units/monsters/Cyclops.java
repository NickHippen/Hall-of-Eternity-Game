package game.units.monsters;

import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.List;

import javax.imageio.ImageIO;

import game.Tile;
import game.TileWorld;
import game.units.LivingUnit;

public class Cyclops extends Monster {

	private final static int MAX_HEALTH = 500;
	private final static int DAMAGE = 25;
	
	private static BufferedImage spriteSheet;
	
	static {
		try {
			URL url = Cyclops.class.getResource("/resources/units/monsters/cyclops.png");
			spriteSheet = ImageIO.read(url);
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
	}
	
	public Cyclops(TileWorld world) {
		super(spriteSheet, world, MAX_HEALTH);
		this.maxFrameNum = 15;
		this.setDamage(DAMAGE);
		setOffsetY(-0.1f);
	}
	
	public String getName(){
		return "Cyclops";
	}
	
	@Override
	public void attack(Tile attackLoc, Class<? extends LivingUnit> targetClass) {
		List<? extends LivingUnit> targets = attackLoc.getUnits(targetClass);
		for (LivingUnit target : targets) {
			this.attack(target);
		}
	}
	
}
