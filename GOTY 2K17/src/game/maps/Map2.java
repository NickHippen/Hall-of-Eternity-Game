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
import game.units.Unit;
import game.units.monsters.Boss;
import game.units.monsters.Monster;
import game.util.Direction;
import game.vectors.Vector2f;

public class Map2 extends GameMap {

	private static final List<TileLocation> GOAL_LOCATIONS = new ArrayList<>();
	private static final Map<TileLocation, Set<Direction>> PATHFINDING_DIRECTIONS = new HashMap<>(); 
	
	private static BufferedImage BASE_IMAGE;

	static {
		// Invalid Locations
		for (int x = 0; x < 30; x++) {
			for (int y = 0; y < 2; y++) {
				INVALID_TILE_LOCATIONS.add(new TileLocation(x, y));
			}
		}
		for (int x = 5; x < 9; x++) {
			for (int y = 5; y < 9; y++) {
				INVALID_TILE_LOCATIONS.add(new TileLocation(x, y));
			}
		}
		for (int x = 12; x < 14; x++) {
			for (int y = 2; y < 5; y++) {
				INVALID_TILE_LOCATIONS.add(new TileLocation(x, y));
			}
		}
		for (int x = 0; x < 30; x++) {
			for (int y = 12; y < 20; y++) {
				INVALID_TILE_LOCATIONS.add(new TileLocation(x, y));
			}
		}
		for (int x = 0; x < 3; x++) {
			for (int y = 9; y < 12; y++) {
				INVALID_TILE_LOCATIONS.add(new TileLocation(x, y));
			}
		}
		for (int x = 28; x < 30; x++) {
			for (int y = 0; y < 20; y++) {
				INVALID_TILE_LOCATIONS.add(new TileLocation(x, y));
			}
		}
		for (int x = 12; x < 14; x++) {
			for (int y = 9; y < 12; y++) {
				INVALID_TILE_LOCATIONS.add(new TileLocation(x, y));
			}
		}
		for (int x = 16; x < 26; x++) {
			for (int y = 4; y < 6; y++) {
				INVALID_TILE_LOCATIONS.add(new TileLocation(x, y));
			}
		}
		for (int x = 16; x < 26; x++) {
			for (int y = 8; y < 10; y++) {
				INVALID_TILE_LOCATIONS.add(new TileLocation(x, y));
			}
		}
		for (int x = 0; x < 3; x++) {
			for (int y = 2; y < 5; y++) {
				INVALID_TILE_LOCATIONS.add(new TileLocation(x, y));
			}
		}
		
		GOAL_LOCATIONS.add(new TileLocation(28, 6));
		GOAL_LOCATIONS.add(new TileLocation(28, 7));
		
		// Hardcoded Pathfinding
		for (int x = 10; x < 15; x++) {
			addPathfindingDirection(new TileLocation(x, 8), Direction.RIGHT);
			addPathfindingDirection(new TileLocation(x, 5), Direction.RIGHT);
		}
		for (int x = 14; x < 16; x++) {
			for (int y = 8; y < 10; y++) {
				addPathfindingDirection(new TileLocation(x, y), Direction.DOWN);
			}
			for (int y = 4; y < 6; y++) {
				addPathfindingDirection(new TileLocation(x, y), Direction.UP);
			}
		}
		for (int x = 14; x < 16; x++) {
			addPathfindingDirection(new TileLocation(x, 10), Direction.RIGHT);
			addPathfindingDirection(new TileLocation(x, 3), Direction.RIGHT);
		}
		
		// Image
		try {
			URL url = Map2.class.getResource("/resources/maps/map02.png");
			BufferedImage spriteSheet = ImageIO.read(url);
			BASE_IMAGE = spriteSheet;
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
	}
	
	public Map2() {
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
			unit.setLocation(new Vector2f(2.6f,.75f));
			world.getTiles()[27][6].getUnits().add(unit);
			world.getTiles()[28][6].getUnits().add(unit);
			world.getTiles()[27][7].getUnits().add(unit);
			world.getTiles()[28][7].getUnits().add(unit);
			
			if (unit instanceof Monster) {
				world.policyIteration(tile -> tile.getAggroPathfinding());		
		}
	}

}
