package game;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.KeyEvent;

import game.maps.Map2;
import game.sprites.BoundingSprite;
import game.units.Unit;
import game.util.Direction;
import game.util.Matrix3x3f;
import game.util.SimpleFramework;
import game.vectors.Vector2f;
import game.vectors.VectorObject;


public class TileFramework extends SimpleFramework {

	public static final int TILES_X = 30;
	public static final int TILES_Y = 20;
	
	private static final float APP_WORLD_WIDTH = 6f;
	private static final float APP_WORLD_HEIGHT = 4f;

	public static final float TILE_SIZE_X = APP_WORLD_WIDTH / (float) TILES_X;
	public static final float TILE_SIZE_Y = APP_WORLD_HEIGHT / (float) TILES_Y;
	
	protected boolean displayBounds;
	protected boolean displayDirections;
	protected boolean displayCoordinates;
	
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
		if (keyboard.keyDownOnce(KeyEvent.VK_D)) {
			getWorld().policyIteration();
			displayDirections = !displayDirections;
		}
		if (keyboard.keyDownOnce(KeyEvent.VK_C)) {
			displayCoordinates = !displayCoordinates;
		}
	}

	public boolean onBoard(Vector2f pos){
		if (pos.y > (-TILE_SIZE_Y * 2f)) return true;
		return false;
	}
	
	public void renderSelectedTile(Graphics2D g, TileLocation location){
		g.setColor(Color.GREEN);
		g.drawRect(location.getX() * 48, location.getY() * 48, 48, 48);
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
		int stopY = 13;
		g.setColor(Color.RED);
		for (int y = 0; y < (48 * stopY); y += 48) {
			g.drawLine(0, y, appWidth, y);
		}
		for (int x = 0; x < appWidth; x += 48) {
			g.drawLine(x, 0, x, (48 * (stopY - 1)));
		}
		g.setStroke(new BasicStroke(2));
		for (TileLocation loc : getWorld().getMap().getInvalidTileLocations()) {
			if (loc.getY() > 11) {
				continue;
			}
			Point p = convertTileLocationToPoint(loc);
			g.drawLine(p.x, p.y, p.x + 48, p.y + 48);
			g.drawLine(p.x, p.y + 48, p.x + 48, p.y);
		}
	}
	
	public void renderDirections(Graphics2D g) {
		g.setColor(Color.BLUE);
		g.setStroke(new BasicStroke(2));
		for (int x = 0; x < TILES_X; x++) {
			for (int y = 0; y < TILES_Y; y++) {
				TileLocation loc = new TileLocation(x, y);
				if (!getWorld().getMap().isTileLocationValid(loc)) {
					continue;
				}
				Point p = convertTileLocationToPoint(loc);
				for (Direction dir : getWorld().getTiles()[x][y].getPathfindingDirections()) {
					switch (dir) {
					case DOWN:
						g.drawLine(p.x + 18, p.y + 36, p.x + 24, p.y + 48);
						g.drawLine(p.x + 30, p.y + 36, p.x + 24, p.y + 48);
						break;
					case LEFT:
						g.drawLine(p.x + 12, p.y + 18, p.x + 0, p.y + 24);
						g.drawLine(p.x + 12, p.y + 30, p.x + 0, p.y + 24);
						break;
					case RIGHT:
						g.drawLine(p.x + 36, p.y + 18, p.x + 48, p.y + 24);
						g.drawLine(p.x + 36, p.y + 30, p.x + 48, p.y + 24);
						break;
					case UP:
						g.drawLine(p.x + 18, p.y + 12, p.x + 24, p.y + 0);
						g.drawLine(p.x + 30, p.y + 12, p.x + 24, p.y + 0);
						break;
					}
				}
			}
		}
	}
	
	public void renderCoordinates(Graphics2D g) {
		g.setFont(new Font("TimesRoman", Font.PLAIN, 10)); 
		for (int x = 0; x < TILES_X; x++) {
			for (int y = 0; y < TILES_Y; y++) {
				TileLocation loc = new TileLocation(x, y);
				Point p = convertTileLocationToPoint(loc);
				g.setColor(Color.BLACK);
				g.fillRect(p.x, p.y + 14, 36, 15);
				g.setColor(Color.RED);
				g.drawString(String.format("(%d, %d)", x, y), p.x, p.y + 24);
			}
		}
	}
	
	public Point convertTileLocationToPoint(TileLocation loc) {
		return new Point(loc.getX() * 48, loc.getY() * 48);
	}
	
	public TileWorld getWorld() {
		return tileWorld;
	}
	
}
