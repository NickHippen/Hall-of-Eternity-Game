package edu.unomaha.nhippen.sprite.game;

import edu.unomaha.nhippen.sprite.sprites.RockSprite;

public class Game extends TileFramework {

	@Override
	protected void initialize() {
		super.initialize();
		
		for (int x = 0; x < TILES_X; x++) {
			for (int y = 0; y < TILES_Y; y++) {
				if ((y != 0 && y != TILES_Y - 1) && (x != 0 && x != TILES_X - 1)) {
					continue;
				}
				setTile(x, y, new RockSprite());
			}
		}
	}
	
	public static void main(String[] args) {
		launchApp(new Game());
	}
	
}
