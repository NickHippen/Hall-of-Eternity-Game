package game.units.status;

import game.units.LivingUnit;
import game.units.heroes.Hero;

public class Poison {
	private boolean isPoisoned = false;
	
	public Poison() {
		
	}
	
	public void applyPoison() {
		isPoisoned = true;
	}
	
	public void updatePoison(LivingUnit livingUnit) {
		if(isPoisoned) {
			livingUnit.applyDamage(1);;
		}
	}
}
