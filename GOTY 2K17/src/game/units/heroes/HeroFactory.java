package game.units.heroes;

public class HeroFactory {
	public Hero getHero(HeroClassType heroType) {
		switch (heroType) {
		case FREELANCER:
			return new Freelancer();
		default:
			return null;
		}
	}
}
