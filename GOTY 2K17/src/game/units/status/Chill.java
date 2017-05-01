package game.units.status;

import game.units.LivingUnit;

public class Chill {
	private boolean isChilled = false;
	private float chillTimer;
	private float chillDuration;
	
	public Chill(float chillDuration) {
		chillTimer = chillDuration;
		this.chillDuration = chillDuration;
	}
	
	public void applyChill() {
		isChilled = true;
	}
	
	public void updateChill(LivingUnit livingUnit, float delta) {
		if(isChilled) {
			chillTimer -= delta;
			if(chillTimer < 0) {
				chillTimer = chillDuration;
				isChilled = false;
			}
		}
	}
	
	public boolean isChilled() {
		return isChilled;
	}
	
}
