package game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import game.units.Unit;
import game.units.monsters.Monster;
import game.util.Direction;

public class Tile {

	private List<Unit> units;
	
	public double U;
	public Direction D;
	public double value;
	public Map<Direction, Double> Q = new HashMap<>();

	public Tile() {
		units = new ArrayList<>();
	}
	
	public List<Unit> getUnits() {
		return units;
	}

	public void setUnits(List<Unit> units) {
		this.units = units;
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
