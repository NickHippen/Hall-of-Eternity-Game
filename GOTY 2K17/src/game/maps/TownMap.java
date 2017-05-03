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

public class TownMap extends GameMap {

	private static final List<TileLocation> INVALID_TILE_LOCATIONS = new ArrayList<>();
	private static final List<TileLocation> GOAL_LOCATIONS = new ArrayList<>();
	private static final Map<TileLocation, Set<Direction>> PATHFINDING_DIRECTIONS = new HashMap<>(); 
	private static final List<TileLocation> SPAWN_LOCATIONS = new ArrayList<>();
	
	private static BufferedImage BASE_IMAGE;

	static {
		// Invalid Locations
		for (int x = 0; x < 30; x++) {
			for (int y = 0; y < 2; y++) {
				INVALID_TILE_LOCATIONS.add(new TileLocation(x, y));
			}
		}
		for (int x = 0; x < 3; x++) {
			for (int y = 5; y < 8; y++) {
				INVALID_TILE_LOCATIONS.add(new TileLocation(x, y));
			}
		}
		INVALID_TILE_LOCATIONS.add(new TileLocation(0, 8));
		INVALID_TILE_LOCATIONS.add(new TileLocation(0, 9));
		for (int x = 3; x < 5; x++) {
			for (int y = 6; y < 10; y++) {
				INVALID_TILE_LOCATIONS.add(new TileLocation(x, y));
			}
		}
		for (int y = 8; y < 10; y++) {
			INVALID_TILE_LOCATIONS.add(new TileLocation(5, y));
		}
		for (int x = 8; x < 11; x++) {
			for (int y = 8; y < 10; y++) {
				INVALID_TILE_LOCATIONS.add(new TileLocation(x, y));
			}
		}
		INVALID_TILE_LOCATIONS.add(new TileLocation(11, 9));
		for (int x = 10; x < 14; x++) {
			for (int y = 5; y < 8; y++) {
				INVALID_TILE_LOCATIONS.add(new TileLocation(x, y));
			}
		}
		for (int x = 14; x < 16; x++) {
			for (int y = 6; y < 8; y++) {
				INVALID_TILE_LOCATIONS.add(new TileLocation(x, y));
			}
		}
		for (int x = 14; x < 17; x++) {
			for (int y = 8; y < 10; y++) {
				INVALID_TILE_LOCATIONS.add(new TileLocation(x, y));
			}
		}
		for (int x = 19; x < 25; x++) {
			INVALID_TILE_LOCATIONS.add(new TileLocation(x, 9));
		}
		for (int x = 19; x < 24; x++) {
			INVALID_TILE_LOCATIONS.add(new TileLocation(x, 8));
		}
		for (int x = 20; x < 24; x++) {
			INVALID_TILE_LOCATIONS.add(new TileLocation(x, 7));
		}
		for (int x = 20; x < 22; x++) {
			INVALID_TILE_LOCATIONS.add(new TileLocation(x, 6));
		}
		for (int x = 0; x < 30; x++) {
			INVALID_TILE_LOCATIONS.add(new TileLocation(x, 12));
		}
		for (int x = 27; x < 30; x++) {
			for (int y = 9; y < 12; y++) {
				INVALID_TILE_LOCATIONS.add(new TileLocation(x, y));
			}
		}
		
		GOAL_LOCATIONS.add(new TileLocation(28, 5));
		GOAL_LOCATIONS.add(new TileLocation(29, 5));
		GOAL_LOCATIONS.add(new TileLocation(28, 6));
		GOAL_LOCATIONS.add(new TileLocation(29, 6));
		
		SPAWN_LOCATIONS.add(new TileLocation(2, 1));
		SPAWN_LOCATIONS.add(new TileLocation(12, 1));
		SPAWN_LOCATIONS.add(new TileLocation(1, 7));
		SPAWN_LOCATIONS.add(new TileLocation(12, 7));
		
		
		// Hardcoded Pathfinding
		addPathfindingDirection(new TileLocation(6, 10), Direction.RIGHT);
		addPathfindingDirection(new TileLocation(6, 10), Direction.UP);
		addPathfindingDirection(new TileLocation(6, 9), Direction.UP);
		addPathfindingDirection(new TileLocation(7, 9), Direction.UP);
		addPathfindingDirection(new TileLocation(17, 10), Direction.RIGHT);
		addPathfindingDirection(new TileLocation(17, 10), Direction.UP);
//		for (int x = 10; x < 15; x++) {
//			addPathfindingDirection(new TileLocation(x, 8), Direction.RIGHT);
//			addPathfindingDirection(new TileLocation(x, 5), Direction.RIGHT);
//		}c
//		for (int x = 14; x < 16; x++) {
//			for (int y = 8; y < 10; y++) {
//				addPathfindingDirection(new TileLocation(x, y), Direction.DOWN);
//			}
//			for (int y = 4; y < 6; y++) {
//				addPathfindingDirection(new TileLocation(x, y), Direction.UP);
//			}
//		}
//		for (int x = 14; x < 16; x++) {
//			addPathfindingDirection(new TileLocation(x, 10), Direction.RIGHT);
//			addPathfindingDirection(new TileLocation(x, 3), Direction.RIGHT);
//		}
		
		// Image
		try {
			URL url = TownMap.class.getResource("/resources/maps/town.png");
			BufferedImage spriteSheet = ImageIO.read(url);
			BASE_IMAGE = spriteSheet;
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
	}
	
	public TownMap() {
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
			world.addUnitToTile(new TileLocation(27, 6), unit);
//			world.getTiles()[27][6].getUnits().add(unit);
//			world.getTiles()[28][6].getUnits().add(unit);
//			world.getTiles()[27][7].getUnits().add(unit);
//			world.getTiles()[28][7].getUnits().add(unit);
			
			if (unit instanceof Monster) {
				world.policyIteration(tile -> tile.getAggroPathfinding());		
		}
	}
	@Override
	public void getSoung(PlayerControl bg) {
		bg.playStage3();	
	}

	@Override
	public List<TileLocation> getSpawnLocations() {
		return SPAWN_LOCATIONS;
	}

}
