package game.cards.curses;

import java.awt.image.BufferedImage;

import game.TileWorld;
import game.cards.Card;

public abstract class ActionCard extends Card {

	protected ActionCard(BufferedImage image, TileWorld world) {
		super(image, world);
	}
	
	public abstract void performAction();
	
}
