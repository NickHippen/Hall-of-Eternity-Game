package edu.unomaha.nknc.game.units;

import java.awt.image.BufferedImage;

import edu.unomaha.nknc.game.units.heroes.HeroType;
import edu.unomaha.nknc.game.units.status.StatusEffects;

public class Hero extends LivingUnit {
	
	private final float MOVE_TIMER = 5; // Time before moving to next tile
	
	private int dropAmount;
	private StatusEffects status;
	private float movementTimer = MOVE_TIMER;
	private HeroType heroType;
	
	protected Hero(BufferedImage image, int maxHealth, HeroType type) {
		super(image, maxHealth);
		heroType = type;
		status = new StatusEffects(this, heroType);
	}
	
	protected void updateHero(float delta) {
		// status effects determine update speed
		delta = status.processStatus(delta);
	}

	public int getDropAmount() {
		return dropAmount;
	}
	
	public void applyDamage(int damage) {
		if(status.isVulnerable()) {
			this.setHealth(this.getHealth() - damage*2);
		}
		else this.setHealth(this.getHealth() - damage);
	}
	
	public void setDropAmount(int amount) {
		dropAmount = amount;
	}
	
	// can use this to apply status effects
	// Ex. hero.getStatusEffects().applyPoison();
	public StatusEffects getStatusEffects() {
		return status;
	}
	
	public HeroType getHeroType() {
		return heroType;
	}
	
}
