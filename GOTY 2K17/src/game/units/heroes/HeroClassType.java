package game.units.heroes;

public enum HeroClassType {

	FREELANCER(HeroDamageType.MELEE),
	ARCHER(HeroDamageType.RANGED),
	TANK(HeroDamageType.MELEE),
	ROGUE(HeroDamageType.MELEE),
	BARD(HeroDamageType.MELEE);
	
	private HeroDamageType damageType;
	
	private HeroClassType(HeroDamageType damageType) {
		this.damageType = damageType;
	}
	
	public HeroDamageType getDamageType() {
		return damageType;
	}
	
}
