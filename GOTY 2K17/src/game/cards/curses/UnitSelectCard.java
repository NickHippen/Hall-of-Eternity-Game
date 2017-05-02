package game.cards.curses;

import java.awt.image.BufferedImage;
import java.util.List;

import game.TileWorld;
import game.units.Unit;
import game.vectors.Vector2f;

public abstract class UnitSelectCard extends CurseCard {

	protected UnitSelectCard(BufferedImage image, TileWorld world) {
		super(image, world);
	}
	
	@Override
	public boolean performAction(Vector2f pos) {
		List<Unit> units = getWorld().getTileAtPosition(pos).getUnits();
		if (!units.isEmpty()) {
			performAction(units.get(0));
		}
		return true;
	}
	
	public abstract void performAction(Unit unit);

}
