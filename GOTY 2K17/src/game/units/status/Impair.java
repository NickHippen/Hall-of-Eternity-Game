package game.units.status;

import game.units.LivingUnit;

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
	
	public void updateImpair(LivingUnit livingUnit, float delta) {
		if(isImpaired) {
			livingUnit.setDamage(0);
			impairTimer -= delta;
			if(impairTimer < 0) {
				livingUnit.setDamage(heroDamage);
				impairTimer = impairDuration;
				isImpaired = false;
			}
		}
	}
}
