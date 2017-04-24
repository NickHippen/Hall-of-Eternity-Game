package game.units.monsters;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import game.sprites.Direction;
import game.units.LivingUnit;

public class Monster extends LivingUnit {

	private boolean attacking;
	private float animationTime = 1;
	protected int frameNum;

	private final ArrayList<BufferedImage> actionSprites = new ArrayList<>();

	protected Monster(BufferedImage image, int maxHealth) {
		super(image, maxHealth);
		//All monster sprite sheets are 4x4
		this.horizontalFrameNum = 4;
		this.verticalFrameNum = 4;
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
