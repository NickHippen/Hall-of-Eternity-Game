package game.cards.curses;

import java.awt.image.BufferedImage;

import game.TileWorld;
import game.units.Unit;

public abstract class UnitSelectCard extends CurseCard {

	protected UnitSelectCard(BufferedImage image, TileWorld world) {
		super(image, world);
	}
	
	public abstract void performAction(Unit unit);

}
