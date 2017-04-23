package game.units.status;

import game.units.heroes.Hero;

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
	
	public float updateStun(Hero hero, float delta) {
		if(isStuned) {
			stunTimer -= delta;
			if(stunTimer < 0) {
				stunTimer = stunDuration;
				isStuned = false;
			}
			return 0;
		}
		else return delta;
	}
}
