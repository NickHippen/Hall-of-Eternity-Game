package game.units;

import java.awt.image.BufferedImage;
import java.util.List;

import game.Tile;
import game.TileLocation;
import game.TileWorld;
import game.units.monsters.Monster;


public abstract class LivingUnit extends Unit {
	private final int ATTACK_SPEED = 1; // attack rate adjustments to all living units
	
	private int maxHealth;
	private int health;
	private float attackSpeed;
	private float timeSinceLastAttack;
	private int damage;

	protected String name;
	
	protected LivingUnit(BufferedImage image, TileWorld world, int maxHealth, int horizontalFrameNum, int verticalFrameNum) {
		super(image, world, horizontalFrameNum, verticalFrameNum);
		
		this.setMaxHealth(maxHealth);
		this.setHealth(maxHealth);
		this.setAttackSpeed(ATTACK_SPEED);
	}

	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = health;
	}
	
	public float getAttackSpeed() {
		return attackSpeed;
	}
	
	public void setAttackSpeed(float attackSpeed) {
		this.attackSpeed = attackSpeed;
	}

	public int getMaxHealth() {
		return maxHealth;
	}

	public void setMaxHealth(int maxHealth) {
		this.maxHealth = maxHealth;
	}

	public int getDamage() {
		return damage;
	}

	public void setDamage(int damage) {
		this.damage = damage;
	}

	public boolean isReadyToAttack() {
		if (timeSinceLastAttack >= attackSpeed) {
			return true;
		}
		return false;
	}
	
	public void attack(List<? extends LivingUnit> targets) {
		for (LivingUnit target : targets) {
			target.applyDamage(getDamage());
		}
	}
	
	public void applyDamage(int amount) {
		setHealth(getHealth() - amount);
	}
	
	public boolean isAlive() {
		return getHealth() > 0;
	}
	
	@Override
	public void update(float delta) {
		super.update(delta);

		if(!isAlive()){
			for(Tile[] tileRow : getWorld().getTiles()){
				for(Tile tile : tileRow){
					if (tile.getUnits().contains(this)){
						tile.removeUnit(this);
						getWorld().getMap().removeInvalidTileLocation(tile.getLocation());
					}
				}
			}
		}
		
		timeSinceLastAttack += delta;
	}
	
}
