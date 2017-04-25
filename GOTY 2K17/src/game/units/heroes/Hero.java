package game.units.heroes;

import java.awt.image.BufferedImage;

import game.TileWorld;
import game.units.LivingUnit;
import game.units.status.StatusEffects;

public class Hero extends LivingUnit {
	
	private final float MOVE_TIMER = 5; // Time before moving to next tile
	
	private int dropAmount;
	private StatusEffects status;
	private HeroDamageType heroDamageType;
	private float movementTimer = MOVE_TIMER;
	
	protected Hero(BufferedImage image, TileWorld world, int maxHealth, HeroDamageType type) {
		super(image, world, maxHealth, 1, 1);
		heroDamageType = type;
		status = new StatusEffects(this, heroDamageType);
		this.setHorizontalFrameNum(1);
		this.setVerticalFrameNum(1);
	}
	
	protected void updateHero(float delta) {
		// status effects determine update speed
		delta = status.processStatus(delta);
	}

	
	public void applyDamage(int damage) {
		if(status.isVulnerable()) {
			this.setHealth(this.getHealth() - damage*2);
		}
		else this.setHealth(this.getHealth() - damage);
	}

	public int getDropAmount() {
		return dropAmount;
	}
	
	public void setDropAmount(int amount) {
		dropAmount = amount;
	}
	
	// can use this to apply status effects
	// Ex. hero.getStatusEffects().applyPoison();
	public StatusEffects getStatusEffects() {
		return status;
	}
	
	public HeroDamageType getHeroType() {
		return heroDamageType;
	}
	
	public int getFrameSize(){
		return 0; //not implemented
	}
}
