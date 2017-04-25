package game.units;

import java.awt.image.BufferedImage;

import game.TileWorld;

public abstract class Trap extends Unit {

	protected Trap(BufferedImage image, TileWorld world) {
		super(image, world, 1, 1);
	}
	
	public abstract void triggerEffect();

}
