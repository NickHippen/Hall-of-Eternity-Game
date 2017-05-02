package game.units.traps;

import game.TileWorld;

public class TrapFactory {
	
	public Trap getTrap(TrapType trapType, TileWorld world) {
		switch (trapType) {
		case ICE:
			return new IceTrap(world);
		default:
			return null;
		}
	}
}
