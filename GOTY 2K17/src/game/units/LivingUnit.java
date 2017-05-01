package game.units;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.List;

import game.Tile;
import game.TileWorld;
import game.units.heroes.Hero;
import game.units.status.StatusEffects;
import game.vectors.Vector2f;


public abstract class LivingUnit extends Unit {
	
	private static final int ATTACK_SPEED = 2; // attack rate adjustments to all living units
	
	private int maxHealth;
	private int health;
	private float attackSpeed;
	private float timeSinceLastAttack;
	private int damage;
	private StatusEffects status;

	protected String name;
	
	protected LivingUnit(BufferedImage image, TileWorld world, int maxHealth, int horizontalFrameNum, int verticalFrameNum) {
		super(image, world, horizontalFrameNum, verticalFrameNum);
		
		status = new StatusEffects(this);
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
	
	// can use this to apply status effects
	// Ex. hero.getStatusEffects().applyPoison();
	public StatusEffects getStatusEffects() {
		return status;
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
		timeSinceLastAttack = 0;
	}
	
	public void applyDamage(int amount) {
		if (getStatusEffects().isVulnerable()) {
			amount /= 2;
		}
		setHealth(getHealth() - amount);
	}
	
	public boolean isAlive() {
		return getHealth() > 0;
	}
	
	public void kill() {
		setHealth(0);
		for(Tile[] tileRow : getWorld().getTiles()){
			for(Tile tile : tileRow){
				if (tile.getUnits().contains(this)){
					tile.removeUnit(this);
					getWorld().getMap().removeInvalidTileLocation(tile.getLocation());
				}
			}
		}
		getWorld().policyIteration(Tile::getAggroPathfinding);
	}
	
	@Override
	public void update(float delta) {
		super.update(delta);
		getStatusEffects().processStatus(delta);
		if(!isAlive()){
			kill();
		}
		
		timeSinceLastAttack += delta;
	}
	
	@Override //Draws the healthbar of monster
	public void draw(Graphics2D g) {
		super.draw(g);
		Vector2f adjustedLoc = adjustPoint(getLocation());
		g.setStroke(new BasicStroke(2));
		g.setColor(Color.RED);
		double width = 35;
		if (this instanceof Hero) g.drawLine((int)adjustedLoc.x - 15, (int) adjustedLoc.y - 18, (int) adjustedLoc.x - 15 + (int) width, (int) adjustedLoc.y - 18);
		else g.drawLine((int)adjustedLoc.x - 15, (int) adjustedLoc.y - 10, (int) adjustedLoc.x - 15 + (int) width, (int) adjustedLoc.y - 10);

		width *= (double) getHealth() / (double) getMaxHealth();
		if (width > 0) {
			g.setColor(Color.GREEN);
			if (this instanceof Hero) g.drawLine((int)adjustedLoc.x - 15, (int) adjustedLoc.y - 18, (int) adjustedLoc.x - 15 + (int) width, (int) adjustedLoc.y - 18);
			else g.drawLine((int)adjustedLoc.x - 15, (int) adjustedLoc.y - 10, (int) adjustedLoc.x - 15 + (int) width, (int) adjustedLoc.y - 10);
		}
	}
	
}
