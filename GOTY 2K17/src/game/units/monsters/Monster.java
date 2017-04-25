package game.units.monsters;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

import game.TileWorld;
import game.units.LivingUnit;

public class Monster extends LivingUnit {

	private boolean attacking = true;
	private float animationTime = 0;
	protected int frameNum;
	protected int frameSize;

	protected Monster(BufferedImage image, TileWorld world, int maxHealth) {
		super(image, world, maxHealth, 4, 4);
	}
	
	public void attackAnimation(float delta) {
		if (attacking) {
			animationTime += delta;
			if (animationTime > 0.1) {
				animationTime = 0;
				frameNum++;
				if (frameNum > 14) {
					frameNum = 0;
				}
				this.setRenderedImage(this.getAttackAnimation().get(frameNum));
			}
		} else {
			this.setRenderedImage(this.getAttackAnimation().get(0));
		}
	}
}
