package game.units;

import java.awt.image.BufferedImage;

import game.sprites.BoundingSprite;

public abstract class Unit extends BoundingSprite {
	
	private int damagePerHit;
	protected static float size;
	
	protected Unit(BufferedImage image) {
		super(image);
		this.setSize();
	}

	public int getDamagePerHit() {
		return damagePerHit;
	}
	
	public void setDamagePerHit(int damage) {
		damagePerHit = damage;
	}
	
	public abstract void setSize();
	
	public float getSize(){
		return Unit.size;
	}
}
