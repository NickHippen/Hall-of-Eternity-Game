package game.cards.curses;

import java.awt.image.BufferedImage;

import game.TileWorld;

public abstract class AreaCard extends CurseCard {

	protected AreaCard(BufferedImage image, TileWorld world) {
		super(image, world);
	}
	
	public abstract void performAction(); // TODO Pass something in to define the area to perform the action

}
