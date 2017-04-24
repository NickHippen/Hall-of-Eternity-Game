package game.units.monsters;

import game.TileWorld;

public class MonsterFactory {

	public Monster getMonster(MonsterType monsterType, TileWorld world) {
		switch (monsterType) {
		case ZOMBIE:
			return new Zombie(world);
		default:
			return null;
		}
	}
	
}
