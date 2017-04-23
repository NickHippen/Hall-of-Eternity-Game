package game.units.status;

import game.units.heroes.Hero;

public class Impair {
	private boolean isImpaired = false;
	private int heroDamage;
	private float impairTimer;
	private float impairDuration;
	
	public Impair(int heroDamage, float impairDuration) {
		this.heroDamage = heroDamage;
		this.impairTimer = impairDuration;
		this.impairDuration = impairDuration;
	}
	
	public void applyImpair() {
		isImpaired = true;
	}
	
	public void updateImpair(Hero hero, float delta) {
		if(isImpaired) {
			hero.setDamagePerHit(0);
			impairTimer -= delta;
			if(impairTimer < 0) {
				hero.setDamagePerHit(heroDamage);
				impairTimer = impairDuration;
				isImpaired = false;
			}
		}
	}
}
