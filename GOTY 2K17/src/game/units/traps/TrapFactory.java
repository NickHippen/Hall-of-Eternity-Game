package game.units.traps;

import game.TileWorld;

public class TrapFactory {
	
	public Trap getTrap(TrapType trapType, TileWorld world) {
		switch (trapType) {
		case ICE:
			return new IceTrap(world);
		case STUN:
			return new StunTrap(world);
		case PITFALL:
			return new PitfallTrap(world);
		default:
			return null;
		}
	}
}
