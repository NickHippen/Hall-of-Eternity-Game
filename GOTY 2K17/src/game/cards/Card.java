package game.cards;

import java.awt.image.BufferedImage;

import game.sprites.BoundingSprite;
import game.vectors.AxisAlignedBoundingBox;
import game.vectors.Vector2f;

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
