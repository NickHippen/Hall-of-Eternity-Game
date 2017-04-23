package edu.unomaha.nknc.game.cards.monsters;

import java.awt.image.BufferedImage;

import edu.unomaha.nknc.game.TileLocation;
import edu.unomaha.nknc.game.TileWorld;
import edu.unomaha.nknc.game.cards.Card;
import edu.unomaha.nknc.game.units.monsters.Monster;
import edu.unomaha.nknc.game.units.monsters.MonsterFactory;
import edu.unomaha.nknc.game.units.monsters.MonsterType;
import edu.unomaha.nknc.game.vectors.Vector2f;

public abstract class MonsterSpawnCard extends Card {
	
	private static final MonsterFactory MONSTER_FACTORY = new MonsterFactory();
	
	private TileWorld world;

	protected MonsterSpawnCard(BufferedImage image, TileWorld world) {
		super(image);
		this.world = world;
	}
	
	public TileWorld getWorld() {
		return world;
	}
	
	public abstract MonsterType getType();
	
	@Override
	public void performAction(Vector2f pos) {
		System.out.println("Spawning mob at " + pos);
		Monster monster = MONSTER_FACTORY.getMonster(getType());
		TileLocation location = world.getTileLocationAtPosition(pos);
		world.addUnitToTile(location, monster);
	}
	
}
