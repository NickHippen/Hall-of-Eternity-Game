package game.units.monsters;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

import game.TileWorld;
import game.units.LivingUnit;

public class Monster extends LivingUnit {

	private boolean attacking;
	private float animationTime = 1;
	protected int frameNum;
	protected int frameSize;

	private final ArrayList<BufferedImage> actionSprites = new ArrayList<>();

	protected Monster(BufferedImage image, TileWorld world, int maxHealth) {
		super(image, world, maxHealth);
		//All monster sprite sheets are 4x4
	}
	
//	public void attackAnimation(float delta) {
//		if (attacking) {
//			animationTime += delta;
//			if (animationTime > 0.25) {
//				animationTime = 0;
//				frameNum++;
//				if (frameNum > 1) {
//					frameNum = 0;
//				}
//				updateImage(WALKING_ANIMATIONS.get(direction).get(walkingFrame));
//			}
//		} else {
//			updateImage(BASE_IMAGES.get(direction));
//			animationTime = 1;
//		}
//	}
}
