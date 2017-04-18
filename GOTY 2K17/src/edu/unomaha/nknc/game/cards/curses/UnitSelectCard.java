package edu.unomaha.nknc.game.cards.curses;

import java.awt.image.BufferedImage;

import edu.unomaha.nknc.game.units.Unit;

public abstract class UnitSelectCard extends CurseCard {

	protected UnitSelectCard(BufferedImage image) {
		super(image);
	}
	
	public abstract void performAction(Unit unit);

}
