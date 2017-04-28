package game;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import game.util.Direction;

public class PathfindingNode {

	private boolean focusMonsters;
	private double qValue;
	private final Map<Direction, Double> actionQMap = new HashMap<>();

	private Set<Direction> directions = new HashSet<>();
	
	public PathfindingNode(boolean focusMonsters) {
		this.focusMonsters = focusMonsters;
	}
	
	/**
	 * Updates the pathfinding directions based on action values
	 * @return whether or not any of the actions changed
	 */
	public boolean updateDirections() {
		Set<Direction> dirs = new HashSet<>();
		for (Direction direction : getActionQMap().keySet()) {
			if (getActionQMap().get(direction).equals(getQValue())) {
				dirs.add(direction);
			}
		}
		boolean same = dirs.equals(getDirections());
		setDirections(dirs);
		return !same;
	}

	public double getQValue() {
		return qValue;
	}

	public void setQValue(double qValue) {
		this.qValue = qValue;
	}

	public Set<Direction> getDirections() {
		return directions;
	}

	public void setDirections(Set<Direction> directions) {
		this.directions = directions;
	}

	public Map<Direction, Double> getActionQMap() {
		return actionQMap;
	}

	public boolean isFocusMonsters() {
		return focusMonsters;
	}

}
