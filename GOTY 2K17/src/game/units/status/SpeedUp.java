package game.units.status;

import game.units.LivingUnit;

public class SpeedUp {
	private boolean isSpedUp = false;
	private float speedTimer;
	private float speedDuration;
	
	public SpeedUp(float speedDuration) {
		speedTimer = speedDuration;
		this.speedDuration = speedDuration;
	}
	
	public void applySpeedup() {
		isSpedUp = true;
	}
	
	public void updateSpeedup(LivingUnit livingUnit, float delta) {
		if(isSpedUp) {
			speedTimer -= delta;
			if(speedTimer < 0) {
				speedTimer = speedDuration;
				isSpedUp = false;
			}
		}
	}
	
	public boolean isSpedUp() {
		return isSpedUp;
	}
	
}
