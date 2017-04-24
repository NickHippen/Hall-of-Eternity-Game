package game.cards.monsters;

import java.awt.image.BufferedImage;

import game.TileLocation;
import game.TileWorld;
import game.cards.Card;
import game.units.monsters.Monster;
import game.units.monsters.MonsterFactory;
import game.units.monsters.MonsterType;
import game.vectors.Vector2f;

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
	public boolean performAction(Vector2f pos) {
		Monster monster = MONSTER_FACTORY.getMonster(getType(), getWorld());
		TileLocation location = world.getTileLocationAtPosition(pos);
		if (world.getMap().getInvalidTileLocations().contains(location)) {
			return false;
		}
		world.addUnitToTile(location, monster);
		return true;
	}
	
}
