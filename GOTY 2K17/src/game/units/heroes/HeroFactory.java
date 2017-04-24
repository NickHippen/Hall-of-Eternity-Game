package game.units.heroes;

import game.TileWorld;

public class HeroFactory {
	
	public Hero getHero(HeroClassType heroType, TileWorld world) {
		switch (heroType) {
		case FREELANCER:
			return new Freelancer(world);
		default:
			return null;
		}
	}
	
}
