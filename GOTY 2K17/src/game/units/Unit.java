package game.units;

import java.awt.image.BufferedImage;

import game.TileWorld;
import game.sprites.BoundingSprite;

public class Unit extends BoundingSprite {
	
	private TileWorld world;
	
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
	
}
