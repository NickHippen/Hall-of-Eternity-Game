package game;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.KeyEvent;

import game.maps.Map2;
import game.sprites.BoundingSprite;
import game.units.Unit;
import game.util.Matrix3x3f;
import game.util.SimpleFramework;
import game.vectors.VectorObject;


public class TileFramework extends SimpleFramework {

	public static final int TILES_X = 30;
	public static final int TILES_Y = 20;
	
	private static final float APP_WORLD_WIDTH = 6f;
	private static final float APP_WORLD_HEIGHT = 4f;

	public static final float TILE_SIZE_X = APP_WORLD_WIDTH / (float) TILES_X;
	public static final float TILE_SIZE_Y = APP_WORLD_HEIGHT / (float) TILES_Y;
	
	boolean displayBounds;
	
	private TileWorld tileWorld;

	public TileFramework() {
		appWidth = 1440;
		appHeight = 960;
		appWorldWidth = APP_WORLD_WIDTH;
		appWorldHeight = APP_WORLD_HEIGHT;
		appSleep = 0L;
		appTitle = "Sprite Game";
		appBackground = Color.DARK_GRAY;
		setResizable(false);
	}

	@Override
	protected void initialize() {
		super.initialize();
		tileWorld = new TileWorld(new Map2(), TILES_X, TILES_Y, TILE_SIZE_X, TILE_SIZE_Y, APP_WORLD_WIDTH, APP_WORLD_HEIGHT);
	}
	
	@Override
	protected void processInput(float delta) {
		super.processInput(delta);
		// Display Bounds
		if (keyboard.keyDownOnce(KeyEvent.VK_B)) {
			displayBounds = !displayBounds;
		}
	}

	public void renderTiles(Graphics2D g2d) {
		Matrix3x3f view = getViewportTransform();
		for (Tile[] tiles : tileWorld.getTiles()) {
			for (Tile tile : tiles) {
				for (Unit unit : tile.getUnits()) {
					unit.setView(view);
					unit.draw(g2d);
				}
			}
		}
	}
	
	public void renderBounds(BoundingSprite sprite, Graphics2D g) {
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
	
	public void renderGrid(Graphics2D g) {
		g.setColor(Color.RED);
		for (int y = 0; y < 674; y += 48) {
			g.drawLine(0, y, appWidth, y);
		}
		for (int x = 0; x < appWidth; x += 48) {
			g.drawLine(x, 0, x, 673);
		}
	}
	
	public Point convertTileLocationToPoint(TileLocation loc) {
		return new Point(loc.getX() * 48, loc.getY() * 48);
	}
	
	public TileWorld getWorld() {
		return tileWorld;
	}
	
}
