package game.units.status;

import game.units.LivingUnit;

public class Stun {
	private boolean isStuned = false;
	private float stunTimer;
	private float stunDuration;
	
	public Stun(float stunDuration) {
		stunTimer = stunDuration;
		this.stunDuration = stunDuration;
	}
	
	public void applyStun() {
		isStuned = true;
	}
	
	public void updateStun(LivingUnit livingUnit, float delta) {
		if(isStuned) {
			stunTimer -= delta;
			if(stunTimer < 0) {
				stunTimer = stunDuration;
				isStuned = false;
			}
		}
	}
	
	public boolean isStunned() {
		return isStuned;
	}
	
}
