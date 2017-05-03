package game.cards;

import java.awt.image.BufferedImage;

import game.TileWorld;
import game.sprites.BoundingSprite;
import game.vectors.AxisAlignedBoundingBox;
import game.vectors.Vector2f;

public abstract class Card extends BoundingSprite {

	protected TileWorld world;
	
	protected Card(BufferedImage image, TileWorld world) {
		super(image, 1, 1);
		setScale(0.95);
		setOuterBound(new AxisAlignedBoundingBox(
				new Vector2f(-0.024f, 0.026f),
				new Vector2f(0.895f, 1.175f)));
		this.world = world;
	}
	
	public TileWorld getWorld() {
		return world;
	}
	
	public abstract boolean performAction(Vector2f pos);
	
	public abstract String getName();

	public abstract Card copy (Card card);
}
