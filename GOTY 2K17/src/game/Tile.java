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

	
	private TileLocation location;
	private List<Unit> units;
	
	private double qValue;
	private final Map<Direction, Double> actionQMap = new HashMap<>();
	
	private Set<Direction> pathfindingDirections = new HashSet<>(0);

	public Tile(TileLocation location) {
		this.location = location;
		units = new ArrayList<>();
	}
	
	public TileLocation getLocation() {
		return location;
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((actionQMap == null) ? 0 : actionQMap.hashCode());
		result = prime * result + ((pathfindingDirections == null) ? 0 : pathfindingDirections.hashCode());
		long temp;
		temp = Double.doubleToLongBits(qValue);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((units == null) ? 0 : units.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Tile other = (Tile) obj;
		if (actionQMap == null) {
			if (other.actionQMap != null)
				return false;
		} else if (!actionQMap.equals(other.actionQMap))
			return false;
		if (pathfindingDirections == null) {
			if (other.pathfindingDirections != null)
				return false;
		} else if (!pathfindingDirections.equals(other.pathfindingDirections))
			return false;
		if (Double.doubleToLongBits(qValue) != Double.doubleToLongBits(other.qValue))
			return false;
		if (units == null) {
			if (other.units != null)
				return false;
		} else if (!units.equals(other.units))
			return false;
		return true;
	}
	
}
