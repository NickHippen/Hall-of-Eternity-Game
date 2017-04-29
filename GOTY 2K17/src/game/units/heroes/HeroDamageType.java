package game.units.heroes;

public enum HeroDamageType {
	
	MELEE(1),
	RANGED(2),
	CASTER(2);
	
	private int range;
	
	private HeroDamageType(int range) {
		this.range = range;
	}
	
	public int getRange() {
		return range;
	}
	
}
