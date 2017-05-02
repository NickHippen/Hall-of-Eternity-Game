package game.units.traps;

import java.awt.image.BufferedImage;

import game.TileWorld;
import game.units.LivingUnit;
import game.units.Unit;

public abstract class Trap extends Unit {

	protected Trap(BufferedImage image, TileWorld world) {
		super(image, world, 1, 1);
		setFrameSize(32);
	}
	
	public abstract void triggerEffect(LivingUnit livingUnit);

}
