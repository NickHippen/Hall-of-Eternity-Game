package edu.unomaha.nknc.game.units.status;

import edu.unomaha.nknc.game.units.heroes.Hero;

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
	
	public float updateChill(Hero hero, float delta) {
		if(isChilled) {
			chillTimer -= delta;
			if(chillTimer < 0) {
				chillTimer = chillDuration;
				isChilled = false;
			}
			return delta /= 2f;
		}
		else return delta;
	}
}