package game.units.status;

import game.units.LivingUnit;
import game.units.heroes.Hero;
import game.units.heroes.HeroDamageType;

public class StatusEffects {
	
	private final float POISON_DURATION = 15;
	private final float BURN_DURATION = 5;
	private final float CHILL_DURATION = 10;
	private final float STUN_DURATION = 2;
	private final float IMPAIR_DURATION = 5;
	private final float VULN_DURATION = 3;
	private final float SPEED_DURATION = 0.5f;
	
	private Poison poison;
	private Burn burn;
	private Chill chill; // currently reduces speed by 50%
	private Impair impair;
	private Stun stun;
	private Vulnerability vuln;
	private SpeedUp speedUp;
	
	private LivingUnit livingUnit;
	
	public StatusEffects(LivingUnit livingUnit) {
		this.livingUnit = livingUnit;
		poison = new Poison(POISON_DURATION);
		burn = new Burn(BURN_DURATION);
		chill = new Chill(CHILL_DURATION);
		stun = new Stun(STUN_DURATION);
		impair = new Impair(livingUnit.getDamage(), IMPAIR_DURATION);
		vuln = new Vulnerability(VULN_DURATION);
		speedUp = new SpeedUp(SPEED_DURATION);
	}
	
	public void processStatus(float delta) {
		poison.updatePoison(livingUnit, delta);
		burn.updateBurn(livingUnit, delta);
		impair.updateImpair(livingUnit, delta);
		vuln.updateVulnerability(livingUnit, delta);
		
		// status that affects movement
		speedUp.updateSpeedup(livingUnit, delta);
		chill.updateChill(livingUnit, delta);
		stun.updateStun(livingUnit, delta);
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
	
	public void applySpeedUp() {
		speedUp.applySpeedup();
	}
	
//	public void applySilenceStatus() {
//		if(hero.getHeroType() == HeroDamageType.CASTER) {
//			impair.applyImpair();
//		}
//	}
//	
	public void applyBlindStatus() {
		if (!(livingUnit instanceof Hero)) {
			return;
		}
		Hero hero = (Hero) livingUnit;
		if(hero.getClassType().getDamageType() == HeroDamageType.MELEE || hero.getClassType().getDamageType() == HeroDamageType.RANGED) {
			impair.applyImpair();
		}
	}
	
	public void applyVulnerableStatus() {
		vuln.applyVulnerability();
	}
	
	public boolean isVulnerable() {
		return vuln.isVulnerable();
	}
	
	public boolean isChilled() {
		return chill.isChilled();
	}
	
	public boolean isStunned() {
		return stun.isStunned();
	}
	
	public boolean isPoisoned() {
		return poison.isPoisoned();
	}
	
	public boolean isBurning() {
		return burn.isBurning();
	}
	
	public boolean isSpedUp() {
		return speedUp.isSpedUp();
	}
	
}
