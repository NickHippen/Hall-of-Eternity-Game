package game.units.heroes;

import game.TileWorld;

public class HeroFactory {
	
	public Hero getHero(HeroClassType heroType, TileWorld world) {
		switch (heroType) {
		case FREELANCER:
			return new Freelancer(world);
		case ARCHER:
			return new Archer(world);
		case TANK:
			return new Tank(world);
		case ROGUE:
			return new Rogue(world);
		case BARD:
			return new Bard(world);
		default:
			throw new RuntimeException("Unknown hero type");
		}
	}
	
}
