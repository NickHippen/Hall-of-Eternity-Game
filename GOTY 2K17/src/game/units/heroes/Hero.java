package game.units.heroes;

import java.awt.image.BufferedImage;
import java.util.List;
import java.util.Random;
import java.util.Set;

import game.Tile;
import game.TileWorld;
import game.units.LivingUnit;
import game.units.MovementTask;
import game.units.monsters.Monster;
import game.units.status.StatusEffects;
import game.util.Direction;

public abstract class Hero extends LivingUnit {
	
	private static final Random RANDOM = new Random();
	
	private final float MOVE_TIMER = 5; // Time before moving to next tile
	
	private int dropAmount;
	private StatusEffects status;
	private HeroDamageType heroDamageType;
	private float movementTimer = MOVE_TIMER;
	
	protected Hero(BufferedImage image, TileWorld world, int maxHealth, HeroDamageType type) {
		super(image, world, maxHealth, 1, 1);
		heroDamageType = type;
		status = new StatusEffects(this, heroDamageType);
		this.setHorizontalFrameNum(1);
		this.setVerticalFrameNum(1);
	}
	
	protected void updateHero(float delta) {
		// status effects determine update speed
		delta = status.processStatus(delta);
	}

	
	public void applyDamage(int damage) {
		if(status.isVulnerable()) {
			this.setHealth(this.getHealth() - damage*2);
		}
		else this.setHealth(this.getHealth() - damage);
	}
	
	@Override
	public void update(float delta) {
		super.update(delta);
		if (getMovementTask() != null) {
			boolean taskComplete = getMovementTask().contributeTask(this, delta);
			if (taskComplete) {
				setMovementTask(null);
			}
		} else {
			Tile tile = getWorld().getTileAtPosition(getLocation());
			Set<Direction> dirs = null;
			boolean nearMonster = false;
			for (Tile surroundingTile : getWorld().getSurroundingTiles(tile.getLocation(), 2)) {
				List<Monster> monsters = surroundingTile.getMonsters();
				if (getHeroType().getRange() == 2) {
					setMovementTask(new MovementTask(null, 1, 0));
				}
				if (!monsters.isEmpty()) {
					nearMonster = true;
					dirs = tile.getAggroPathfinding().getDirections();
					break;
				}
			}
			if (nearMonster && getHeroType().getRange() == 1) {
				for (Tile surroundingTile : getWorld().getSurroundingTiles(tile.getLocation(), 1)) {
					List<Monster> monsters = surroundingTile.getMonsters();
					if (!monsters.isEmpty()) {
						setMovementTask(new MovementTask(null, 1, 0));
						break;
					}
				}
			}
			
			if (getMovementTask() != null) {
				// Hero has been stopped
				return;
			}
			if (dirs == null) {
				dirs = tile.getStandardPathfinding().getDirections();
			}
			if (dirs.isEmpty()) {
				System.out.println("WARNING: NO PATHFINDING CONFIGURED.");
				return;
			}
			Direction dir = (Direction) dirs.toArray()[RANDOM.nextInt(dirs.size())];
			switch (dir) {
			case UP:
			case DOWN:
				setMovementTask(new MovementTask(dir, getWorld().getTileSizeY(), 0.25f));
				break;
			case LEFT:
			case RIGHT:
				setMovementTask(new MovementTask(dir, getWorld().getTileSizeX(), 0.25f));
				break;
			}
		}
	}

	public int getDropAmount() {
		return dropAmount;
	}
	
	public void setDropAmount(int amount) {
		dropAmount = amount;
	}
	
	// can use this to apply status effects
	// Ex. hero.getStatusEffects().applyPoison();
	public StatusEffects getStatusEffects() {
		return status;
	}
	
	public HeroDamageType getHeroType() {
		return heroDamageType;
	}
}
