package game.units.heroes;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import game.Tile;
import game.TileLocation;
import game.TileWorld;
import game.units.LivingUnit;
import game.units.monsters.Monster;
import game.units.status.StatusEffects;
import game.units.tasks.AttackTask;
import game.units.tasks.MoveTask;
import game.util.Direction;

public abstract class Hero extends LivingUnit {
	
	private static final Random RANDOM = new Random();
	
	private final float MOVE_TIMER = 5; // Time before moving to next tile
	
	private int dropAmount;
	private StatusEffects status;
	private HeroDamageType heroDamageType;
		
	private boolean attacking;
	private float animationTime = 0;
	protected int maxFrameNum = 1;
	protected int frameNum=0;
	protected int frameSize;
	protected float animationSpeed = .1f;
	private int selectedAnimation;
	private Direction dir;
	private TileLocation attackingDir; //Used to turn the unit based on location of monster
	private BufferedImage[][] animations;
	
	
	protected Hero(BufferedImage image, TileWorld world, int maxHealth, HeroDamageType type) {
		super(image, world, maxHealth, 13, 21);
		heroDamageType = type;
		status = new StatusEffects(this, heroDamageType);
		this.setHorizontalFrameNum(13);
		this.setVerticalFrameNum(21);
		this.animations = new BufferedImage[8][9];
		this.createHeroAnimations();
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
		
		if (getTask() != null) {
			boolean taskComplete = getTask().contributeTask(this, delta);
			if (taskComplete) {
			
				setTask(null);
			}
		} 
		else {
			Tile tile = getWorld().getTileAtPosition(getLocation());
			Set<Direction> dirs = null;
			boolean nearMonster = false;
			for (Tile surroundingTile : getWorld().getSurroundingTiles(tile.getLocation(), 2, Direction.LEFT)) {
				List<Monster> monsters = surroundingTile.getMonsters();
				if (!monsters.isEmpty()) {
					this.attacking = true;
					attackingDir = surroundingTile.getLocation();
					if (getHeroType().getRange() == 2) {
						setTask(new AttackTask(surroundingTile));
					}
					nearMonster = true;
					dirs = tile.getAggroPathfinding().getDirections();
					break;
				}
			}
			
			if (nearMonster && getHeroType().getRange() == 1) {
				for (Tile surroundingTile : getWorld().getSurroundingTiles(tile.getLocation(), 1, Direction.LEFT)) {
					List<Monster> monsters = surroundingTile.getMonsters();
					if (!monsters.isEmpty()) {
						this.attacking = true;
						attackingDir = surroundingTile.getLocation();
						setTask(new AttackTask(surroundingTile));
						break;
					}
				}
			}
			else{
				this.attacking = false;
				attackingDir = null;
			}
			
			if (getTask() != null) {
				// Hero has been stopped
				return;
			}
			if (dirs == null) {
				dirs = tile.getStandardDirections();
			}
			if (dirs.isEmpty()) {
				System.out.println("WARNING: NO PATHFINDING CONFIGURED.");
				return;
			}
			this.dir = (Direction) dirs.toArray()[RANDOM.nextInt(dirs.size())];
			switch (dir) {
			case UP:
			case DOWN:
				setTask(new MoveTask(dir, getWorld().getTileSizeY(), 0.25f));
				break;
			case LEFT:
			case RIGHT:
				setTask(new MoveTask(dir, getWorld().getTileSizeX(), 0.25f));
				break;
			}
		}
		this.updateAnimation(delta);
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
	
	public void createHeroAnimations(){
		//Add stabbing animations
		for(int i = 4; i < 8 ; i ++){
			for(int j = 0; j < 9; j++){
				animations[i-4][j] = (this.getSpriteSheet().getSubimage(j * 64, i * 64, 64, 64));
			}
		}
		//Add walking animations
		for(int i = 8; i < 12 ; i++){
			for(int j = 0; j < 8; j++){
				animations[i-4][j] = (this.getSpriteSheet().getSubimage(j * 64, i * 64, 64, 64));
			}
		}
	}
	
	public void updateAnimation(float delta) {
		switch (dir) {
			case UP:
				this.selectedAnimation = 0;
				break;
			case LEFT:
				this.selectedAnimation = 1;
				break;
			case DOWN:
				this.selectedAnimation = 2;
				break;
			case RIGHT:
				this.selectedAnimation = 3;
				break;
		}
		
		if(attackingDir != null){
			int dirX = attackingDir.getX() - getWorld().getTileAtPosition(getLocation()).getLocation().getX();
			int dirY = attackingDir.getY() - getWorld().getTileAtPosition(getLocation()).getLocation().getY();
		
		
			if (dirX == 0 && dirY == -1) this.selectedAnimation = 0;
			if (dirX == -1 && dirY == 0) this.selectedAnimation = 1;
			if (dirX == 0 && dirY == 1) this.selectedAnimation = 2;
			if (dirX == 1 && dirY == 0) this.selectedAnimation = 3;
		}
		
		if(!attacking) this.selectedAnimation += 4;
		animationTime += delta;
		if (animationTime > animationSpeed) {
			animationTime = 0;
			frameNum++;
			if (frameNum > 7) {
				frameNum = 0;
			}
		}
		this.setRenderedImage(this.animations[selectedAnimation][frameNum]);
	}
}
