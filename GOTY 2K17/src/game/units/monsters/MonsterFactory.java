package game.units.monsters;

import game.TileWorld;

public class MonsterFactory {

	public Monster getMonster(MonsterType monsterType, TileWorld world) {
		switch (monsterType) {
		case ZOMBIE:
			return new Zombie(world);
		case DRAGON:
			return new Dragon(world);
		case WOLF:
			return new Wolf(world);
		case BOW_SKELETON:
			return new BowSkeleton(world);
		case CENTAUR:
			return new Centaur(world);
		case CERBERUS:
			return new Cerberus(world);
		case CYCLOPS:
			return new Cyclops(world);
		case SPIDER:
			return new Spider(world);
		case SWORD_SKELETON:
			return new SwordSkeleton(world);
		case ZOMBIE_DRAGON:
			return new ZombieDragon(world);
		default:
			throw new RuntimeException("Unknown monster type");
		}
	}
	
}
