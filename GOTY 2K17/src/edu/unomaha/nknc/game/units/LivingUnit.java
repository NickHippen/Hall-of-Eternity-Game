package edu.unomaha.nknc.game.units;

import java.awt.image.BufferedImage;

public class LivingUnit extends Unit {

	private int maxHealth;
	private int health;

	protected LivingUnit(BufferedImage image) {
		super(image);
	}

	public int getMaxHealth() {
		return maxHealth;
	}

	public void setMaxHealth(int maxHealth) {
		this.maxHealth = maxHealth;
	}

	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = health;
	}

}
