package game.units.monsters;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Set;

import game.Tile;
import game.TileWorld;
import game.units.LivingUnit;
import game.units.Unit;
import game.units.heroes.Hero;
import game.units.tasks.AttackTask;

public abstract class Monster extends LivingUnit {

	private boolean attacking;
	private float animationTime = 0;
	protected int maxFrameNum = 1;
	protected int frameNum=-1;
	protected int frameSize;
	protected double animationSpeed = 1.7;
	private boolean ranged = false;
	
	protected ArrayList<Hero> target;
	
	protected Monster(BufferedImage image, TileWorld world, int maxHealth) {
		super(image, world, maxHealth, 4, 4);
	}
	
	public void attackAnimation(float delta) {
		if (attacking) {
			animationTime += delta;
			//All monster attack animations last the same duration, regardless of frames
			if (animationTime > animationSpeed / maxFrameNum) {
				animationTime = 0;
				frameNum++;
				if (frameNum > maxFrameNum) {
					frameNum = 0;
					this.setAttacking(false);
				}
				this.setRenderedImage(this.getAttackAnimation().get(frameNum));
			}
		} else {
			this.setRenderedImage(this.getAttackAnimation().get(0));
		}
	}
	
	public void setAttacking(boolean attacking){
		this.attacking = attacking;
	}
	
	public boolean isRanged() {
		return ranged;
	}

	public void setRanged(boolean ranged) {
		this.ranged = ranged;
	}

	@Override
	public void update(float delta) {
		super.update(delta);
		if (getTask() == null) {
			Tile tile = getTile();
			Set<Tile> affectedTiles;
			if (isRanged()) {
				affectedTiles = getWorld().getSurroundingTiles(tile.getLocation(), 2);
			} else {
				affectedTiles = getWorld().getSurroundingTiles(tile.getLocation(), 1);
			}
			for (Unit unit : getWorld().getUnits()) {
				if (!(unit instanceof Hero)) {
					continue;
				}
				Tile unitTile = unit.getTile();
				if (affectedTiles.contains(unitTile)) {
				//	setAttacking(true);
					setTask(new AttackTask(unitTile, Hero.class));
					break;
				}
			}
		}
	}
	
}
