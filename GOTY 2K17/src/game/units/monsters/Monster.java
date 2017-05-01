package game.units.monsters;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import game.TileWorld;
import game.units.LivingUnit;
import game.vectors.Vector2f;
import game.units.heroes.Hero;

public abstract class Monster extends LivingUnit {

	private boolean attacking;
	private float animationTime = 0;
	protected int maxFrameNum = 1;
	protected int frameNum=-1;
	protected int frameSize;
	protected double animationSpeed = 1.4;
	
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
					attacking = false;
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
	
	@Override //Draws the healthbar of monster
	public void draw(Graphics2D g) {
		super.draw(g);
		Vector2f adjustedLoc = adjustPoint(getLocation());
		g.setStroke(new BasicStroke(2));
		g.setColor(Color.RED);
		double width = 35;
		g.drawLine((int)adjustedLoc.x - 15, (int) adjustedLoc.y - 10, (int) adjustedLoc.x - 15 + (int) width, (int) adjustedLoc.y - 10);
		width *= (double) getHealth() / (double) getMaxHealth();
		if (width > 0) {
			g.setColor(Color.GREEN);
			g.drawLine((int)adjustedLoc.x - 15, (int) adjustedLoc.y - 10, (int) adjustedLoc.x - 15 + (int) width, (int) adjustedLoc.y - 10);
		}
	}
	
}
