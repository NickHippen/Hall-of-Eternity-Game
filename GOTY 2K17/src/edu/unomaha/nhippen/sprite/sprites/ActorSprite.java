package edu.unomaha.nhippen.sprite.sprites;

import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;

import edu.unomaha.nhippen.sprite.game.SpriteGame;
import edu.unomaha.nhippen.sprite.vectors.AxisAlignedBoundingBox;
import edu.unomaha.nhippen.sprite.vectors.Vector2f;
import edu.unomaha.nhippen.sprite.vectors.VectorObject;

public class ActorSprite extends BoundingSprite {
	
	private static final Map<Direction, BufferedImage> BASE_IMAGES = new HashMap<>();
	private static final Map<Direction, List<BufferedImage>> WALKING_ANIMATIONS = new HashMap<>();
	private static BufferedImage DEAD_ANIMATION;
	
	static {
		try {
			URL url = ActorSprite.class.getResource("/sprites/MageActor.png");
			BufferedImage spriteSheet = ImageIO.read(url);
			BASE_IMAGES.put(Direction.NORTH, spriteSheet.getSubimage(48, 144, 48, 48));
			List<BufferedImage> northWalking = new ArrayList<>();
			northWalking.add(spriteSheet.getSubimage(0, 144, 48, 48));
			northWalking.add(spriteSheet.getSubimage(96, 144, 48, 48));
			WALKING_ANIMATIONS.put(Direction.NORTH, northWalking);
			
			BASE_IMAGES.put(Direction.SOUTH, spriteSheet.getSubimage(48, 0, 48, 48));
			List<BufferedImage> southWalking = new ArrayList<>();
			southWalking.add(spriteSheet.getSubimage(0, 0, 48, 48));
			southWalking.add(spriteSheet.getSubimage(96, 0, 48, 48));
			WALKING_ANIMATIONS.put(Direction.SOUTH, southWalking);
			
			BASE_IMAGES.put(Direction.EAST, spriteSheet.getSubimage(48, 96, 48, 48));
			List<BufferedImage> eastWalking = new ArrayList<>();
			eastWalking.add(spriteSheet.getSubimage(0, 96, 48, 48));
			eastWalking.add(spriteSheet.getSubimage(96, 96, 48, 48));
			WALKING_ANIMATIONS.put(Direction.EAST, eastWalking);
			
			BASE_IMAGES.put(Direction.WEST, spriteSheet.getSubimage(48, 48, 48, 48));
			List<BufferedImage> westWalking = new ArrayList<>();
			westWalking.add(spriteSheet.getSubimage(0, 48, 48, 48));
			westWalking.add(spriteSheet.getSubimage(96, 48, 48, 48));
			WALKING_ANIMATIONS.put(Direction.WEST, westWalking);
			
			DEAD_ANIMATION = spriteSheet.getSubimage(0, 192, 48, 48);
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
	}
	
	private Direction direction;
	private float animationTime = 1;
	private int walkingFrame;
	private boolean walking;
	private boolean dead;

	public ActorSprite() {
		super(BASE_IMAGES.get(Direction.SOUTH));
		direction = Direction.SOUTH;
		setOuterBound(new AxisAlignedBoundingBox(
				new Vector2f(-SpriteGame.TILE_SIZE / 2F, SpriteGame.TILE_SIZE / 2F),
				new Vector2f(SpriteGame.TILE_SIZE / 2F, -SpriteGame.TILE_SIZE / 2F)));
		// Although not really needed for this sprite, use inner bounds for an example
		List<VectorObject> innerBounds = new ArrayList<>();
		innerBounds.add(new AxisAlignedBoundingBox(
				new Vector2f(-SpriteGame.TILE_SIZE / 2F, SpriteGame.TILE_SIZE / 2F),
				new Vector2f(SpriteGame.TILE_SIZE / 2F, 0)));
		innerBounds.add(new AxisAlignedBoundingBox(
				new Vector2f(-SpriteGame.TILE_SIZE / 4F, 0),
				new Vector2f(SpriteGame.TILE_SIZE / 4F, -SpriteGame.TILE_SIZE / 2F)));
		setInnerBounds(innerBounds);
	}

	public Direction getDirection() {
		return direction;
	}

	public void setDirection(Direction direction) {
		if (isDead()) {
			return;
		}
		if (this.direction != direction) {
			this.direction = direction;
			updateImage(BASE_IMAGES.get(direction));
		}
	}
	
	public boolean isWalking() {
		return walking;
	}

	public void setWalking(boolean walking) {
		this.walking = walking;
	}

	public boolean isDead() {
		return dead;
	}

	public void setDead(boolean dead) {
		this.dead = dead;
	}

	public void updateAnimations(float delta) {
		if (dead) {
			updateImage(DEAD_ANIMATION);
			float mult = 0F;
			switch (getDirection()) {
			case NORTH:
				mult = 1F;
				break;
			case SOUTH:
				mult = 0F;
				break;
			case EAST:
				mult = 1.5F;
				break;
			case WEST:
				mult = 0.5F;
				break;
			}
			setRotation((float) Math.PI * mult);
		} else if (walking) {
			animationTime += delta;
			if (animationTime > 0.25) {
				animationTime = 0;
				walkingFrame++;
				if (walkingFrame > 1) {
					walkingFrame = 0;
				}
				updateImage(WALKING_ANIMATIONS.get(direction).get(walkingFrame));
			}
		} else {
			updateImage(BASE_IMAGES.get(direction));
			animationTime = 1;
		}
	}
	
	private void updateImage(BufferedImage image) {
		this.image = image;
	}
	
	@Override
	public void move(Vector2f vec, List<BoundingSprite> bounds, float delta) {
		if (isDead()) {
			return;
		}
		super.move(vec, bounds, delta);
	}

}
