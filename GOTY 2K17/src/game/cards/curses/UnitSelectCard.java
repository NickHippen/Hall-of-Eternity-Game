package game.cards.curses;

import java.awt.image.BufferedImage;

import game.TileWorld;
import game.units.Unit;
import game.vectors.Vector2f;

public abstract class UnitSelectCard extends CurseCard {

	protected UnitSelectCard(BufferedImage image, TileWorld world) {
		super(image, world);
	}
	
	@Override
	public boolean performAction(Vector2f pos) {
		Unit unit = getWorld().getTileAtPosition(pos).getUnits().get(0);
		System.out.println(unit);
		if (unit != null) {
			performAction(unit);
		}
		return true;
	}
	
	public abstract void performAction(Unit unit);

}
