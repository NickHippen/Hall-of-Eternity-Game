package game.units.heroes;

public enum HeroClassType {

	FREELANCER(HeroDamageType.MELEE, 0.4f),
	ARCHER(HeroDamageType.RANGED, 0.15f),
	TANK(HeroDamageType.MELEE, 0.15f),
	ROGUE(HeroDamageType.MELEE, 0.15f),
	BARD(HeroDamageType.MELEE, 0.15f);
	
	private HeroDamageType damageType;
	private float weight;
	
	private HeroClassType(HeroDamageType damageType, float weight) {
		this.damageType = damageType;
		this.weight = weight;
	}
	
	public HeroDamageType getDamageType() {
		return damageType;
	}
	
	public float getWeight() {
		return weight;
	}
	
}
