package game;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import game.maps.GameMap;
import game.units.Unit;
import game.units.monsters.Monster;
import game.util.Direction;

public class Tile {

	private TileLocation location;
	private List<Unit> units;
	
	private PathfindingNode standardPathfinding;
	private PathfindingNode aggroPathfinding;
	
	private GameMap map;

	public Tile(TileLocation location, GameMap map) {
		this.location = location;
		units = new ArrayList<>();
		standardPathfinding = new PathfindingNode(false);
		aggroPathfinding = new PathfindingNode(true);
		this.map = map;
	}
	
	public TileLocation getLocation() {
		return location;
	}
	
	public List<Unit> getUnits() {
		return units;
	}
	
	public void removeUnit(Unit removedUnit){
		this.units.remove(removedUnit);
	}

	public void setUnits(List<Unit> units) {
		this.units = units;
	}
	
	public PathfindingNode getStandardPathfinding() {
		return standardPathfinding;
	}
	
	public Set<Direction> getStandardDirections() {
		Set<Direction> directions = map.getHardcodedDirections(location);
		if (directions == null) {
			return getStandardPathfinding().getDirections();
		}
		return directions;
	}

	public PathfindingNode getAggroPathfinding() {
		return aggroPathfinding;
	}

	public boolean hasMonster() {
		return !getMonsters().isEmpty();
	}
	
	public List<Monster> getMonsters() {
		List<Monster> monsters = new ArrayList<>();
		for (Unit unit : units) {
			if (unit instanceof Monster) {
				monsters.add((Monster) unit);
			}
		}
		return monsters;
	}

}
