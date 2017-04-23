package edu.unomaha.nknc.game.units;

import java.awt.image.BufferedImage;


public class LivingUnit extends Unit {
	
	private int maxHealth;
	private int health;
	private int attackSpeed;

	protected LivingUnit(BufferedImage image, int maxHealth) {
		super(image);
		this.maxHealth = maxHealth;
		this.health = maxHealth;
	}

	public int getHealth() {
		return health;
	}

	protected void setHealth(int health) {
		this.health = health;
	}
	
	protected int getAttackSpeed() {
		return attackSpeed;
	}
	
	protected void setAttackSpeed(int attackSpeed) {
		this.attackSpeed = attackSpeed;
	}
}
