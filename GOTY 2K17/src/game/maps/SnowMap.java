package game.maps;

import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.imageio.ImageIO;

import game.TileLocation;
import game.TileWorld;
import game.sound.PlayerControl;
import game.units.monsters.Boss;
import game.units.monsters.Monster;
import game.util.Direction;
import game.vectors.Vector2f;

public class SnowMap extends GameMap {

	private static final List<TileLocation> INVALID_TILE_LOCATIONS = new ArrayList<>();
	private static final List<TileLocation> GOAL_LOCATIONS = new ArrayList<>();
	private static final Map<TileLocation, Set<Direction>> PATHFINDING_DIRECTIONS = new HashMap<>();
	private static final List<TileLocation> SPAWN_LOCATIONS = new ArrayList<>();
	
	private static BufferedImage BASE_IMAGE;

	static {
		// Invalid Locations
		for (int x = 0; x < 30; x++) {
			for (int y = 0; y < 3; y++) {
				INVALID_TILE_LOCATIONS.add(new TileLocation(x, y));
			}
		}
		for (int x = 0; x < 30; x++) {
			for (int y = 12; y < 20; y++) {
				INVALID_TILE_LOCATIONS.add(new TileLocation(x, y));
			}
		}
		for (int x = 3; x < 6; x++) {
			for (int y = 3; y < 7; y++) {
				INVALID_TILE_LOCATIONS.add(new TileLocation(x, y));
			}
		}
		for (int x = 10; x < 16; x++) {
			for (int y = 5; y < 10; y++) {
				INVALID_TILE_LOCATIONS.add(new TileLocation(x, y));
			}
		}
		for (int x = 18; x < 27; x++) {
			INVALID_TILE_LOCATIONS.add(new TileLocation(x, 3));
		}
		for (int x = 18; x < 30; x++) {
			INVALID_TILE_LOCATIONS.add(new TileLocation(x, 11));
		}
		for (int y = 5; y < 10; y++) {
			INVALID_TILE_LOCATIONS.add(new TileLocation(10, y));
		}
		for (int y = 6; y < 10; y++) {
			INVALID_TILE_LOCATIONS.add(new TileLocation(9, y));
		}
		for (int y = 8; y < 10; y++) {
			INVALID_TILE_LOCATIONS.add(new TileLocation(8, y));
		}
		for (int y = 4; y < 6; y++) {
			INVALID_TILE_LOCATIONS.add(new TileLocation(18, y));
		}
		for (int y = 9; y < 11; y++) {
			INVALID_TILE_LOCATIONS.add(new TileLocation(18, y));
		}
		for (int x = 2; x < 8; x++) {
			INVALID_TILE_LOCATIONS.add(new TileLocation(x, 3));
		}
		INVALID_TILE_LOCATIONS.add(new TileLocation(6, 4));
		for (int x = 20; x < 26; x++) {
			INVALID_TILE_LOCATIONS.add(new TileLocation(x, 5));
			INVALID_TILE_LOCATIONS.add(new TileLocation(x, 9));
		}
		for (int x = 19; x < 26; x++) {
			INVALID_TILE_LOCATIONS.add(new TileLocation(x, 7));
		}
		INVALID_TILE_LOCATIONS.add(new TileLocation(28, 10));
		INVALID_TILE_LOCATIONS.add(new TileLocation(29, 10));
		INVALID_TILE_LOCATIONS.add(new TileLocation(29, 9));
		INVALID_TILE_LOCATIONS.add(new TileLocation(28, 3));
		INVALID_TILE_LOCATIONS.add(new TileLocation(29, 3));
		INVALID_TILE_LOCATIONS.add(new TileLocation(29, 4));
		INVALID_TILE_LOCATIONS.add(new TileLocation(0, 9));
		INVALID_TILE_LOCATIONS.add(new TileLocation(1, 10));
		INVALID_TILE_LOCATIONS.add(new TileLocation(0, 10));
		for (int x = 0; x < 3; x++) {
			INVALID_TILE_LOCATIONS.add(new TileLocation(x, 11));
		}

		GOAL_LOCATIONS.add(new TileLocation(29, 6));
		GOAL_LOCATIONS.add(new TileLocation(29, 7));
		
		for (int y = 3; y < 9; y++) {
			SPAWN_LOCATIONS.add(new TileLocation(0, y));
		}
		
		
		// Hardcoded Pathfinding
		addPathfindingDirection(new TileLocation(19, 8), Direction.RIGHT);
		for (int y = 8; y < 10; y++) {
			addPathfindingDirection(new TileLocation(19, y), Direction.DOWN);
		}
		addPathfindingDirection(new TileLocation(19, 10), Direction.RIGHT);
		
		addPathfindingDirection(new TileLocation(19, 6), Direction.RIGHT);
		for (int y = 5; y < 7; y++) {
			addPathfindingDirection(new TileLocation(19, y), Direction.UP);
		}
		addPathfindingDirection(new TileLocation(19, 4), Direction.RIGHT);
		addPathfindingDirection(new TileLocation(4, 7), Direction.RIGHT);
		addPathfindingDirection(new TileLocation(4, 7), Direction.DOWN);
		
		
		// Image
		try {
			URL url = SnowMap.class.getResource("/resources/maps/snow.png");
			BufferedImage spriteSheet = ImageIO.read(url);
			BASE_IMAGE = spriteSheet;
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
	}
	
	public SnowMap() {
		super(BASE_IMAGE);
	}

	@Override
	public List<TileLocation> getInvalidTileLocations() {
		return INVALID_TILE_LOCATIONS;
	}


	@Override
	public List<TileLocation> getGoalLocations() {
		return GOAL_LOCATIONS;
	}
	
	private static void addPathfindingDirection(TileLocation loc, Direction dir) {
		Set<Direction> directions = PATHFINDING_DIRECTIONS.get(loc);
		if (directions == null) {
			directions = new HashSet<>();
		}
		directions.add(dir);
		PATHFINDING_DIRECTIONS.put(loc, directions);
	}

	@Override
	public Set<Direction> getHardcodedDirections(TileLocation loc) {
		return PATHFINDING_DIRECTIONS.get(loc);
	}
	
	public void addBoss(TileWorld world, Boss unit ){
			unit.setLocation(new Vector2f(2.9f,.75f));
			world.addUnitToTile(new TileLocation(29, 6), unit);
			
			if (unit instanceof Monster) {
				world.policyIteration(tile -> tile.getAggroPathfinding());		
		}
	}
	@Override
	public void getSoung(PlayerControl bg) {
		bg.playStage2();	
	}

	@Override
	public List<TileLocation> getSpawnLocations() {
		return SPAWN_LOCATIONS;
	}

}
