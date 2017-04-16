package edu.unomaha.nhippen.sprite.game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.sun.glass.events.KeyEvent;

import edu.unomaha.nhippen.sprite.sprites.ActorSprite;
import edu.unomaha.nhippen.sprite.sprites.BoundingSprite;
import edu.unomaha.nhippen.sprite.sprites.Direction;
import edu.unomaha.nhippen.sprite.sprites.RockSprite;
import edu.unomaha.nhippen.sprite.sprites.SpellSprite;
import edu.unomaha.nhippen.sprite.sprites.SpriteObject;
import edu.unomaha.nhippen.sprite.util.Location;
import edu.unomaha.nhippen.sprite.util.Matrix3x3f;
import edu.unomaha.nhippen.sprite.util.SimpleFramework;
import edu.unomaha.nhippen.sprite.vectors.Vector2f;
import edu.unomaha.nhippen.sprite.vectors.VectorObject;


public class TileFramework extends SimpleFramework {

	public static final int TILES_X_Y = 20;

	public static final float TILE_SIZE = 2F / (float) TILES_X_Y;
	
	private ActorSprite actor;
	private List<BoundingSprite> boundingSprites;
	private List<SpellSprite> spellSprites;
	
	private SpriteObject[][] tiles;
	
	private boolean displayBounds;

	public TileFramework() {
		appWidth = 960;
		appHeight = 960;
		appSleep = 0L;
		appTitle = "Sprite Game";
		appBackground = Color.DARK_GRAY;
		setResizable(false);
	}

	@Override
	protected void initialize() {
		super.initialize();
		initializeTiles();
		boundingSprites = new ArrayList<>();
		spellSprites = new ArrayList<>();
		
		// Actor
		actor = new ActorSprite();
		actor.setLocation(new Vector2f(0, 0));
	}
	
	private void initializeTiles() {
		 tiles = new SpriteObject[TILES_X_Y][];
		 for (int i = 0; i < tiles.length; i++) {
			 tiles[i] = new SpriteObject[TILES_X_Y];
		 }
	}

	@Override
	protected void processInput(float delta) {
		super.processInput(delta);
		// Movement
		boolean walking = false;
		if (keyboard.keyDown(KeyEvent.VK_W)) {
			actor.move(new Vector2f(0F, 0.2F), boundingSprites, delta);
			actor.setDirection(Direction.NORTH);
			walking = true;
		} else if (keyboard.keyDown(KeyEvent.VK_S)) {
			actor.move(new Vector2f(0F, -0.2F), boundingSprites, delta);
			actor.setDirection(Direction.SOUTH);
			walking = true;
		} else if (keyboard.keyDown(KeyEvent.VK_A)) {
			actor.move(new Vector2f(-0.2F, 0F), boundingSprites, delta);
			actor.setDirection(Direction.WEST);
			walking = true;
		} else if (keyboard.keyDown(KeyEvent.VK_D)) {
			actor.move(new Vector2f(0.2F, 0F), boundingSprites, delta);
			actor.setDirection(Direction.EAST);
			walking = true;
		}
		
		// Reset
		if (keyboard.keyDownOnce(KeyEvent.VK_R)) {
			actor.setDead(false);
			actor.setLocation(new Vector2f());
			actor.setRotation(0F);
			actor.setDirection(Direction.SOUTH);
		}
		
		// Spawn Spell
		if (keyboard.keyDownOnce(KeyEvent.VK_SPACE)) {
			SpellSprite spell = new SpellSprite();
			spell.setLocation(new Vector2f(-1.1F, 0F));
			spellSprites.add(spell);
		}
		
		// Display Bounds
		if (keyboard.keyDownOnce(KeyEvent.VK_B)) {
			displayBounds = !displayBounds;
		}
		actor.setWalking(walking);
		actor.updateAnimations(delta);
	}

	@Override
	protected void updateObjects(float delta) {
		super.updateObjects(delta);
		
		// Move spells
		for (int i = spellSprites.size() - 1; i >= 0; i--) {
			SpellSprite sprite = spellSprites.get(i);
			sprite.move(new Vector2f(0.4F, 0), new ArrayList<>(), delta);
			if (sprite.getLocation().x > 1.5F
					|| sprite.getLocation().y > 1.5F
					|| sprite.getLocation().y < -0.5F) {
				spellSprites.remove(i);
			}
			sprite.setRotation(sprite.getRotation() + 4F * delta);
			if (sprite.hasAnyBoundViolations(Arrays.asList(actor))) {
				spellSprites.remove(i);
				actor.setDead(true);
			}
		}
	}

	@Override
	protected void render(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		Matrix3x3f view = getViewportTransform();
		
		for (SpriteObject[] sprites : tiles) {
			for (SpriteObject sprite : sprites) {
				if (sprite == null) {
					continue;
				}
				sprite.setView(view);
				sprite.draw(g2d);
			}
		}

		// Actor
		actor.setView(view);
		actor.draw(g2d);
		if (displayBounds) {
			renderBounds(actor, g2d);
		}

		// Objects
		for (BoundingSprite sprite : boundingSprites) {
			sprite.setView(view);
			sprite.draw(g2d);
			if (displayBounds) {
				renderBounds(sprite, g2d);
			}
		}
		
		// Spells
		for (SpellSprite sprite : spellSprites) {
			sprite.setView(view);
			sprite.draw(g2d);
			if (displayBounds) {
				renderBounds(sprite, g2d);
			}
		}
		super.render(g);
	}
	
	private void renderBounds(BoundingSprite sprite, Graphics2D g) {
		g.setColor(Color.RED);
		if (sprite.getOuterBound() != null) {
			sprite.getOuterBound().render(g);
		}
		if (sprite.getInnerBounds() != null) {
			g.setColor(Color.BLUE);
			for (VectorObject innerBound : sprite.getInnerBounds()) {
				innerBound.render(g);
			}
		}
	}
	
	public void setTile(int x, int y, SpriteObject sprite) {
		sprite.setLocation(new Vector2f(TILE_SIZE * x - 1F + (TILE_SIZE / 2), TILE_SIZE * (TILES_X_Y - y - 1) - 1F + (TILE_SIZE / 2)));
		tiles[x][y] = sprite;
	}
	
	public void setTile(Location loc, SpriteObject sprite) {
		setTile(loc.getX(), loc.getY(), sprite);
	}

}