package game.cards.curses;

import java.awt.image.BufferedImage;

import game.cards.Card;

public abstract class ActionCard extends Card {

	protected ActionCard(BufferedImage image) {
		super(image);
	}
	
	public abstract void performAction();
	
}
