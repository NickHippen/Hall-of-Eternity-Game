package game.units.status;

import game.units.LivingUnit;

public class Poison {
	private static final float TICK_DELAY = 1f;
	
	private boolean isPoisoned = false;
	private float poisonTimer;
	private float poisonDuration;
	private float timeSinceTick;
	
	public Poison(float poisonDuration) {
		poisonTimer = poisonDuration;
		this.poisonDuration = poisonDuration;
	}
	
	public void applyPoison() {
		isPoisoned = true;
	}
	
	public void updatePoison(LivingUnit livingUnit, float delta) {
		if (isPoisoned) {
			poisonTimer -= delta;
			if (poisonTimer < 0) {
				poisonTimer = poisonDuration;
				isPoisoned = false;
			} else {
				timeSinceTick += delta;
				if (timeSinceTick >= TICK_DELAY) {
					livingUnit.applyDamage(1);
					timeSinceTick = 0;
				}
			}
		}
	}

	public boolean isPoisoned() {
		return isPoisoned;
	}
}
