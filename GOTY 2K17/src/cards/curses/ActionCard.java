package edu.unomaha.nknc.game.cards.curses;

import java.awt.image.BufferedImage;

import edu.unomaha.nknc.game.cards.Card;

public abstract class ActionCard extends Card {

	protected ActionCard(BufferedImage image) {
		super(image);
	}
	
	public abstract void performAction();
	
}
