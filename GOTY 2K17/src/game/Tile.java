package game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import game.units.Unit;
import game.units.monsters.Monster;
import game.util.Direction;

public class Tile {

	private List<Unit> units;
	
	private double qValue;
	private final Map<Direction, Double> actionQMap = new HashMap<>();
	
	private Set<Direction> pathfindingDirections = new HashSet<>(0);

	public Tile() {
		units = new ArrayList<>();
	}
	
	public List<Unit> getUnits() {
		return units;
	}

	public void setUnits(List<Unit> units) {
		this.units = units;
	}
	
	public double getQValue() {
		return qValue;
	}

	public void setQValue(double qValue) {
		this.qValue = qValue;
	}
	
	public Map<Direction, Double> getActionQMap() {
		return actionQMap;
	}
	
	/**
	 * Updates the pathfinding directions based on action values
	 * @return whether or not any of the actions changed
	 */
	public boolean updatePathfindingDirections() {
		Set<Direction> dirs = new HashSet<>();
		for (Direction direction : getActionQMap().keySet()) {
			if (getActionQMap().get(direction).equals(getQValue())) {
				dirs.add(direction);
			}
		}
		boolean same = dirs.equals(getPathfindingDirections());
		pathfindingDirections = dirs;
		return !same;
	}
	
	public Set<Direction> getPathfindingDirections() {
		return pathfindingDirections;
	}
	
	public boolean hasMonster() {
		for (Unit unit : units) {
			if (unit instanceof Monster) {
				return true;
			}
		}
		return false;
	}
	
}
