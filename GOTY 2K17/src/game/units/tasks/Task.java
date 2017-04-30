package game.units.tasks;

import game.units.LivingUnit;

public interface Task {
	
	/**
	 * Progresses the task
	 * @param unit the unit to perform the task on
	 * @param delta
	 * @return whether or not the task has been fully completed
	 */
	public boolean contributeTask(LivingUnit unit, float delta);

}
