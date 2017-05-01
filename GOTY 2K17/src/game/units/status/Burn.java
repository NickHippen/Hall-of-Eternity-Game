package game.units.status;

import game.units.LivingUnit;

public class Burn {
	
	private static final float TICK_DELAY = 0.5f;
	
	private boolean isBurned = false;
	private float burnTimer;
	private float burnDuration;
	private float timeSinceTick;
	
	public Burn(float burnDuration) {
		burnTimer = burnDuration;
		this.burnDuration = burnDuration;
	}
	
	public void applyBurn() {
		isBurned = true;
	}
	
	public void updateBurn(LivingUnit livingUnit, float delta) {
		if (isBurned) {
			burnTimer -= delta;
			if (burnTimer < 0) {
				burnTimer = burnDuration;
				isBurned = false;
			} else {
				timeSinceTick += delta;
				if (timeSinceTick >= TICK_DELAY) {
					livingUnit.applyDamage(3);
					timeSinceTick = 0;
				}
			}
		}
	}
	
}
