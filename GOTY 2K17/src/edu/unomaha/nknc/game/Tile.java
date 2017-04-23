package edu.unomaha.nknc.game;

import java.util.ArrayList;
import java.util.List;

import edu.unomaha.nknc.game.units.Unit;

public class Tile {

	private List<Unit> units;

	public Tile() {
		units = new ArrayList<>();
	}
	
	public List<Unit> getUnits() {
		return units;
	}

	public void setUnits(List<Unit> units) {
		this.units = units;
	}
	
}
