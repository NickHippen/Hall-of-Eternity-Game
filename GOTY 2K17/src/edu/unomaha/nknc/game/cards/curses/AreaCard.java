package edu.unomaha.nknc.game.cards.curses;

import java.awt.image.BufferedImage;

public abstract class AreaCard extends CurseCard {

	protected AreaCard(BufferedImage image) {
		super(image);
	}
	
	public abstract void performAction(); // TODO Pass something in to define the area to perform the action

}
