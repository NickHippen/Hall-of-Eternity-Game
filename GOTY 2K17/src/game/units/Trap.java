package game.units;

import java.awt.image.BufferedImage;

public abstract class Trap extends Unit {

	protected Trap(BufferedImage image) {
		super(image);
	}
	
	public abstract void triggerEffect();

}
