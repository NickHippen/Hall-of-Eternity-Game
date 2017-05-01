package game.units.heroes;

public enum HeroClassType {

	FREELANCER(HeroDamageType.MELEE),
	FIGHTER(HeroDamageType.MELEE),
	ARCHER(HeroDamageType.RANGED),
	TANK(HeroDamageType.MELEE),
	HEALER(HeroDamageType.MELEE),
	ROGUE(HeroDamageType.MELEE),
	BARD(HeroDamageType.MELEE),
	MAGE(HeroDamageType.CASTER);
	
	private HeroDamageType damageType;
	
	private HeroClassType(HeroDamageType damageType) {
		this.damageType = damageType;
	}
	
	public HeroDamageType getDamageType() {
		return damageType;
	}
	
}
