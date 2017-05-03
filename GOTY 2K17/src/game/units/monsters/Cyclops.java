package game.units.monsters;

import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import game.Tile;
import game.TileLocation;
import game.TileWorld;
import game.units.LivingUnit;

public class Cyclops extends Monster {

	private final static int MAX_HEALTH = 300;
	private final static int DAMAGE = 80;
	
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
		setRanged(true);
	}
	
	public String getName(){
		return "Cyclops";
	}
	
	@Override
	public void attack(Tile attackLoc, Class<? extends LivingUnit> targetClass) {
		TileLocation loc = getTileLocation();
		int diffX = attackLoc.getLocation().getX() - loc.getX();
		int diffY = attackLoc.getLocation().getY() - loc.getY();
		List<Tile> attackLocs = new ArrayList<>();
		if (diffX != 0 && diffY != 0) {
			// Attacking diagonal
			attackLocs.add(attackLoc);
		} else if (diffX != 0) {
			// Attacking horizontal
			if (diffX < 0) {
				attackLocs.add(getWorld().getTile(new TileLocation(getTileLocation().getX() - 1, getTileLocation().getY())));
				attackLocs.add(getWorld().getTile(new TileLocation(getTileLocation().getX() - 2, getTileLocation().getY())));
			} else {
				attackLocs.add(getWorld().getTile(new TileLocation(getTileLocation().getX() + 1, getTileLocation().getY())));
				attackLocs.add(getWorld().getTile(new TileLocation(getTileLocation().getX() + 2, getTileLocation().getY())));
			}
		} else {
			// Attacking vertical
			if (diffY < 0) {
				attackLocs.add(getWorld().getTile(new TileLocation(getTileLocation().getX(), getTileLocation().getY() - 1)));
				attackLocs.add(getWorld().getTile(new TileLocation(getTileLocation().getX(), getTileLocation().getY() - 2)));
			} else {
				attackLocs.add(getWorld().getTile(new TileLocation(getTileLocation().getX(), getTileLocation().getY() + 1)));
				attackLocs.add(getWorld().getTile(new TileLocation(getTileLocation().getX(), getTileLocation().getY() + 2)));
			}
		}
		for (Tile splashAttackLoc : attackLocs) {
			List<? extends LivingUnit> targets = splashAttackLoc.getUnits(targetClass);
			for (LivingUnit target : targets) {
				this.attack(target);
			}
		}
	}
	
}
