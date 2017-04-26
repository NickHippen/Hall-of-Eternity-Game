package game.units;

import game.util.Direction;

public class MovementTask {

	private Direction direction;
	private float distance;
	private float speed;
	private float distanceCovered;
	
	public MovementTask(Direction direction, float distance, float speed) {
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
	
	public void increaseDistanceCovered(float delta) {
		this.distanceCovered += getSpeed() * delta;
	}

	public boolean contributeTask(Unit unit, float delta) {
		increaseDistanceCovered(delta);
		float speed;
		boolean taskComplete;
		if (distanceCovered >= distance) {
			speed = (distanceCovered - distance) / delta; // Only move to the goal distance
			taskComplete = true;
		} else {
			speed = getSpeed();
			taskComplete = false;
		}
		unit.move(getDirection(), speed, delta);
		return taskComplete;
	}
	
}
