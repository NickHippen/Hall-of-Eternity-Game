package game;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import game.units.Unit;
import game.units.monsters.Monster;
import game.util.Direction;

public class Tile {

	private TileLocation location;
	
	private PathfindingNode standardPathfinding;
	private PathfindingNode aggroPathfinding;
	
	private TileWorld world;

	public Tile(TileLocation location, TileWorld world) {
		this.location = location;
		standardPathfinding = new PathfindingNode(false);
		aggroPathfinding = new PathfindingNode(true);
		this.world = world;
	}
	
	public TileLocation getLocation() {
		return location;
	}
	
	public List<Unit> getUnits() {
		return getUnits(null);
	}
	
	public <T extends Unit> List<T> getUnits(Class<T> desiredClass) {
		List<T> units = new ArrayList<>();
		for (Unit unit : getWorld().getUnits()) {
			if (desiredClass != null && !desiredClass.isInstance(unit)) {
				continue;
			}
			Tile tile = unit.getTile();
			if (this.equals(tile)) {
				units.add((T) unit);
			}
		}
		return units;
	}
	
	public TileWorld getWorld() {
		return world;
	}
	
	public PathfindingNode getStandardPathfinding() {
		return standardPathfinding;
	}
	
	public Set<Direction> getStandardDirections() {
		Set<Direction> directions = world.getMap().getHardcodedDirections(location);
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
		for (Unit unit : getUnits()) {
			if (unit instanceof Monster) {
				monsters.add((Monster) unit);
			}
		}
		return monsters;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Tile)) {
			return false;
		}
		return getLocation().equals(((Tile) obj).getLocation());
	}

}
