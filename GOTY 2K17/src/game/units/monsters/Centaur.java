package game.units.monsters;

import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import game.Tile;
import game.TileWorld;
import game.units.LivingUnit;

public class Centaur extends Monster {

	private final static int MAX_HEALTH = 700;
	private final static int DAMAGE = 25;
	
	private static BufferedImage spriteSheet;
	
	static {
		try {
			URL url = Centaur.class.getResource("/resources/units/monsters/centaur.png");
			spriteSheet = ImageIO.read(url);
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
	}
	
	public Centaur(TileWorld world) {
		super(spriteSheet, world, MAX_HEALTH);
		this.maxFrameNum = 12;
		this.setDamage(DAMAGE);
		setOffsetY(-0.1f);
	}
	
	public String getName() {
		return "Centaur";
	}
	
	@Override
	public void attack(Tile attackLoc, Class<? extends LivingUnit> targetClass) {
		ArrayList<Tile> targetTiles = getWorld().getSurroundingTilesDiag(getTileLocation(), 1);
		for (Tile tile : targetTiles) {
			List<? extends LivingUnit> targets = tile.getUnits(targetClass);
			for (LivingUnit target : targets) {
				this.attack(target);
			}
		}
	}
	
}
