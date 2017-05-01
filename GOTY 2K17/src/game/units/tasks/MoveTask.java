package game.units.tasks;

import game.units.LivingUnit;
import game.util.Direction;

public class MoveTask implements Task {

	private Direction direction;
	private float distance;
	private float speed;
	private float distanceCovered;
	
	public MoveTask(Direction direction, float distance, float speed) {
		this.direction = direction;
		this.distance = distance;
		this.speed = speed;
		this.distanceCovered = 0;
	}

	public Direction getDirection() {
		return direction;
	}

	public void setDirection(Direction direction) {
		this.direction = direction;
	}

	public float getDistance() {
		return distance;
	}

	public void setDistance(float distance) {
		this.distance = distance;
	}
	
	public float getSpeed() {
		return speed;
	}
	
	public void setSpeed(float speed) {
		this.speed = speed;
	}
	
	public void increaseDistanceCovered(float speed, float delta) {
		this.distanceCovered += speed * delta;
	}

	@Override
	public boolean contributeTask(LivingUnit unit, float delta) {
		float speed = getSpeed();
		if (unit.getStatusEffects().isChilled()) {
			speed /= 2;
		}
		increaseDistanceCovered(speed, delta);
		boolean taskComplete;
		if (distanceCovered >= distance) {
			speed = (distanceCovered - distance) / delta; // Only move to the goal distance
			taskComplete = true;
		} else {
			taskComplete = false;
		}
		unit.move(getDirection(), speed, delta);
		return taskComplete;
	}
	
}
