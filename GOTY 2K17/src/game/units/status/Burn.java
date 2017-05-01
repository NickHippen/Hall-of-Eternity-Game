package game.units.status;

import game.units.LivingUnit;
import game.units.heroes.Hero;

public class Burn {
	private boolean isBurned = false;
	private float burnTimer;
	private float burnDuration;
	
	public Burn(float burnDuration) {
		burnTimer = burnDuration;
		this.burnDuration = burnDuration;
	}
	
	public void applyBurn() {
		isBurned = true;
	}
	
	public void updateBurn(LivingUnit livingUnit, float delta) {
		if(isBurned) {
			burnTimer -= delta;
			if(burnTimer < 0) {
				burnTimer = burnDuration;
				isBurned = false;
			}
			livingUnit.applyDamage(3);
		}
	}
}
