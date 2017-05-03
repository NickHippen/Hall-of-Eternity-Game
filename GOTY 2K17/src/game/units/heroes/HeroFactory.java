package game.units.heroes;

import game.TileWorld;

public class HeroFactory {
	
	public Hero getHero(HeroClassType heroType, TileWorld world) {
		switch (heroType) {
		case FREELANCER:
			return new Freelancer(world);
		case FIGHTER:
			return new Fighter(world);
		case ARCHER:
			return new Archer(world);
		case TANK:
			return new Tank(world);
		case HEALER:
			return new Healer(world);
		case ROGUE:
			return new Rogue(world);
		case BARD:
			return new Bard(world);
		case MAGE:
			return new Mage(world);
		default:
			return null;
		}
	}
	
}
