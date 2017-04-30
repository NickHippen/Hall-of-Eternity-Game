package game.cards.curses;

import java.awt.image.BufferedImage;

import game.TileWorld;
import game.cards.Card;
import game.vectors.Vector2f;

public abstract class CurseCard extends Card {

	protected CurseCard(BufferedImage image, TileWorld world) {
		super(image, world);
	}

	@Override
	public boolean performAction(Vector2f location) {
		return false;
	}
}
