package edu.unomaha.nknc.game.cards;

import java.awt.image.BufferedImage;

import edu.unomaha.nknc.game.sprites.BoundingSprite;
import edu.unomaha.nknc.game.vectors.AxisAlignedBoundingBox;
import edu.unomaha.nknc.game.vectors.Vector2f;

public abstract class Card extends BoundingSprite {

	protected Card(BufferedImage image) {
		super(image);
		setScale(0.95);
		setOuterBound(new AxisAlignedBoundingBox(
				new Vector2f(-0.02f, 0.02f),
				new Vector2f(0.89f, 1.151f)));
	}
	
	public abstract void performAction(Vector2f pos);

}
