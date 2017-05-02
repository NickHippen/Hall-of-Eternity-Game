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
		case IRON_MAIDEN:
			return new IronMaidenTrap(world);
		case FIRE:
			return new FireTrap(world);
		case POISON:
			return new PoisonTrap(world);
		case BLIND:
			return new BlindTrap(world);
		default:
			return null;
		}
	}
}
