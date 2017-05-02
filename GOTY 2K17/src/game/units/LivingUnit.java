package game.units;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import game.Tile;
import game.TileLocation;
import game.TileWorld;
import game.units.heroes.Hero;
import game.units.monsters.Monster;
import game.units.status.StatusEffects;
import game.units.tasks.Task;
import game.vectors.Vector2f;


public abstract class LivingUnit extends Unit {
	
	private static final int ATTACK_SPEED = 2; // attack rate adjustments to all living units
	
	private int maxHealth;
	private boolean attacking;
	private int health;
	private float attackSpeed;
	private float timeSinceLastAttack;
	private int damage;
	private StatusEffects status;
	private Task task;

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
		if (this.health > this.maxHealth) {
			this.health = this.maxHealth;
		}
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

	public boolean isAttacking() {
		return attacking;
	}

	public void setAttacking(boolean attacking) {
		this.attacking = attacking;
	}

	public Task getTask() {
		return task;
	}

	public void setTask(Task task) {
		this.task = task;
	}

	public boolean isReadyToAttack() {
		if (timeSinceLastAttack >= attackSpeed) {
			return true;
		}
		return false;
	}
	
	public void attack(LivingUnit target) {
		target.applyDamage(getDamage());
		timeSinceLastAttack = 0;
	}
	
	public void applyDamage(int amount) {
		if (getStatusEffects().isVulnerable()) {
			amount *= 2;
		}
		setHealth(getHealth() - amount);
	}
	
	public boolean isAlive() {
		return getHealth() > 0;
	}
	
	public void kill() {
		setHealth(0);
		getWorld().getUnits().remove(this);
		TileLocation loc = getWorld().getTileLocationAtPosition(getLocation());
		getWorld().getMap().removeInvalidTileLocation(loc);
		getWorld().policyIteration(Tile::getAggroPathfinding);
	}
	
	@Override
	public void update(float delta) {
		super.update(delta);
		timeSinceLastAttack += delta;
		if (getTask() != null) {
			if(this instanceof Monster) this.setAttacking(true);
			boolean taskComplete = getTask().contributeTask(this, delta);
			if (taskComplete) {
				setTask(null);
				this.setAttacking(false);
			}
		}
		getStatusEffects().processStatus(delta);
		if(!isAlive()){
			kill();
		}
	}
	
	public float getTimeSinceLastAttack() {
		return timeSinceLastAttack;
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
