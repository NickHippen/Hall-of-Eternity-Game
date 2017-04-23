package game.units.monsters;

public class MonsterFactory {

	public Monster getMonster(MonsterType monsterType) {
		switch (monsterType) {
		case ZOMBIE:
			return new Zombie();
		default:
			return null;
		}
	}
	
}
