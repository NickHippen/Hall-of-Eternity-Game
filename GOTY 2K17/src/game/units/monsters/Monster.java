package game.units.monsters;

import java.awt.image.BufferedImage;

import game.units.LivingUnit;

public abstract class Monster extends LivingUnit {

	protected Monster(BufferedImage image, int maxHealth) {
		super(image, maxHealth);
	}

}
