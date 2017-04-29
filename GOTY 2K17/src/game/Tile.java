package game;

import java.util.ArrayList;
import java.util.List;

import game.units.Unit;
import game.units.monsters.Monster;

public class Tile {

	
	private TileLocation location;
	private List<Unit> units;
	
	private PathfindingNode standardPathfinding;
	private PathfindingNode aggroPathfinding;

	public Tile(TileLocation location) {
		this.location = location;
		units = new ArrayList<>();
		standardPathfinding = new PathfindingNode(false);
		aggroPathfinding = new PathfindingNode(true);
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
	
	public PathfindingNode getStandardPathfinding() {
		return standardPathfinding;
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
