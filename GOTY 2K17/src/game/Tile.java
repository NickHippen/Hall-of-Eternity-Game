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
	
	private double qValue;
	private final Map<Direction, Double> actionQMap = new HashMap<>();

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
	
	public boolean hasMonster() {
		for (Unit unit : units) {
			if (unit instanceof Monster) {
				return true;
			}
		}
		return false;
	}
	
}
