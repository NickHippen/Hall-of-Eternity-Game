package game.cards.curses;

import java.awt.image.BufferedImage;

import game.TileWorld;
import game.cards.Card;

public abstract class CurseCard extends Card {

	protected CurseCard(BufferedImage image, TileWorld world) {
		super(image, world);
	}

}
