package game.units.monsters;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

import game.TileWorld;
import game.units.LivingUnit;

public abstract class Monster extends LivingUnit {

	private boolean attacking = true;
	private float animationTime = 0;
	protected int maxFrameNum = 1;
	protected int frameNum=-1;
	protected int frameSize;
	protected double animationSpeed = 1.4;
	
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
				}
				this.setRenderedImage(this.getAttackAnimation().get(frameNum));
			}
		} else {
			this.setRenderedImage(this.getAttackAnimation().get(0));
		}
	}
}
