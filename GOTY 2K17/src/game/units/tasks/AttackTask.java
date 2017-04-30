package game.units.tasks;

import java.util.List;

import game.Tile;
import game.units.LivingUnit;
import game.units.monsters.Monster;

public class AttackTask implements Task {

	private Tile attackLoc;
	
	public AttackTask(Tile attackLoc) {
		this.attackLoc = attackLoc;
	}
	
	@Override
	public boolean contributeTask(LivingUnit unit, float delta) {
		List<Monster> monsters = attackLoc.getMonsters();
		if (unit.isReadyToAttack()) {
			unit.attack(monsters);
		}
		for (Monster monster : monsters) {
			if (monster.isAlive()) {
				return false;
			}
		}
		return true;
	}

}
