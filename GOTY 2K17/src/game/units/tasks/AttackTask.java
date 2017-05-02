package game.units.tasks;

import java.util.List;

import game.Tile;
import game.units.LivingUnit;
import game.units.Unit;

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
			List<? extends LivingUnit> targets = attackLoc.getUnits(targetClass);
			boolean complete = true;
			for (Unit target : targets) {
				LivingUnit livingTarget = (LivingUnit) target;
				unit.attack(livingTarget);
				if (livingTarget.isAlive()) {
					complete = false;
				}
			}
			return complete;
		}
		return false;
	}
	
}
