package game.cards.traps;

import java.awt.image.BufferedImage;

import game.TileLocation;
import game.TileWorld;
import game.cards.Card;
import game.units.traps.Trap;
import game.units.traps.TrapFactory;
import game.units.traps.TrapType;
import game.vectors.Vector2f;

public abstract class TrapSpawnCard extends Card {
	
	private static final TrapFactory TRAP_FACTORY = new TrapFactory();
	
	protected TrapSpawnCard(BufferedImage image, TileWorld world) {
		super(image, world);
	}

	public abstract TrapType getType();
	
	@Override
	public boolean performAction(Vector2f pos) {
		Trap trap = TRAP_FACTORY.getTrap(getType(), getWorld());
		TileLocation loc = getWorld().getTileLocationAtPosition(pos);
		if (getWorld().getMap().getInvalidTileLocations().contains(loc)) {
			return false;
		}
		getWorld().addUnitToTile(loc, trap);
		getWorld().getMap().addInvalidTileLocation(loc);
		return true;
	}
	
}
