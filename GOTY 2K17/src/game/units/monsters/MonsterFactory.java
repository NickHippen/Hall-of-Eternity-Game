package game.units.monsters;

import game.TileWorld;

public class MonsterFactory {

	public Monster getMonster(MonsterType monsterType, TileWorld world) {
		switch (monsterType) {
		case ZOMBIE:
			return new Zombie(world);
		case DRAGON:
			return new Dragon(world);
		default:
			return null;
		}
	}
	
}
