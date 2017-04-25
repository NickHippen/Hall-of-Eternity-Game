package game.units;

import java.awt.image.BufferedImage;

import game.TileWorld;
import game.sprites.BoundingSprite;
import game.util.Direction;

public class Unit extends BoundingSprite {
	
	private TileWorld world;
	private Direction moving;
	
	private int damagePerHit;
	
	protected Unit(BufferedImage image, TileWorld world) {
		super(image);
	}

	public int getDamagePerHit() {
		return damagePerHit;
	}
	
	public void setDamagePerHit(int damage) {
		damagePerHit = damage;
	}
	
	public TileWorld getWorld() {
		return world;
	}
	
	public void setWorld(TileWorld world) {
		this.world = world;
	}

	public Direction getMoving() {
		return moving;
	}

	public void setMoving(Direction moving) {
		this.moving = moving;
	}
	
}
