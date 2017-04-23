package game.units.status;

import game.units.heroes.Hero;
import game.units.heroes.HeroDamageType;

public class StatusEffects {
	private final float BURN_DURATION = 200;
	private final float CHILL_DURATION = 200;
	private final float STUN_DURATION = 50;
	private final float IMPAIR_DURATION = 100;
	private final float VULN_DURATION = 200;
	
	private Poison poison;
	private Burn burn;
	private Chill chill; // currently reduces speed by 50%
	private Impair impair;
	private Stun stun;
	private Vulnerability vuln;
	
	private Hero hero;
	
	public StatusEffects(Hero hero, HeroDamageType type) {
		this.hero = hero;
		poison = new Poison();
		burn = new Burn(BURN_DURATION);
		chill = new Chill(CHILL_DURATION);
		stun = new Stun(STUN_DURATION);
		impair = new Impair(hero.getDamagePerHit(), IMPAIR_DURATION);
		vuln = new Vulnerability(VULN_DURATION);
	}
	
	public float processStatus(float delta) {
		poison.updatePoison(hero);
		burn.updateBurn(hero, delta);
		impair.updateImpair(hero, delta);
		vuln.updateVulnerability(hero, delta);
		
		// status that affects movement
		delta = chill.updateChill(hero, delta);
		delta = stun.updateStun(hero, delta);
		return delta;
	}
	
	public void applyPoisonStatus() {
		poison.applyPoison();
	}

	public void applyBurnStatus() {
		burn.applyBurn();
	}
	
	public void applyChillStatus() {
		chill.applyChill();
	}
	
	public void applyStunStatus() {
		stun.applyStun();
	}
	
	public void applySilenceStatus() {
		if(hero.getHeroType() == HeroDamageType.CASTER) {
			impair.applyImpair();
		}
	}
	
	public void applyBlindStatus() {
		if(hero.getHeroType() == HeroDamageType.MELEE || hero.getHeroType() == HeroDamageType.RANGED) {
			impair.applyImpair();
		}
	}
	
	public void applyVulnerableStatus() {
		vuln.applyVulnerability();
	}
	
	public boolean isVulnerable() {
		return vuln.isVulnerable();
	}
}
