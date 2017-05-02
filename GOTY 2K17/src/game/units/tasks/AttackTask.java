package game.units.tasks;

import java.util.List;

import game.Tile;
import game.units.LivingUnit;

public class AttackTask implements Task {

	private Tile attackLoc;
	private Class<? extends LivingUnit> targetClass;
	
	public AttackTask(Tile attackLoc, Class<? extends LivingUnit> targetClass) {
		this.attackLoc = attackLoc;
		this.targetClass = targetClass;
	}

	@Override
	public boolean contributeTask(LivingUnit unit, float delta) {
		if (unit.isReadyToAttack()) {
			unit.attack(attackLoc, targetClass);
			List<? extends LivingUnit> targets = attackLoc.getUnits(targetClass);
			for (LivingUnit target : targets) {
				if (target.isAlive()) {
					return false;
				}
			}
			return true;
		}
		return false;
	}
	
}
