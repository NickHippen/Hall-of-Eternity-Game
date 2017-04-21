package edu.unomaha.nknc.game;

import java.awt.event.MouseEvent;

import edu.unomaha.nknc.game.sprites.RockSprite;
import edu.unomaha.nknc.game.sprites.SpriteObject;

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
	
	@Override
	protected void processInput(float delta) {
		super.processInput(delta);
		
		if (mouse.buttonDownOnce(MouseEvent.BUTTON1)) {
			System.out.println(getCenteredRelativeWorldMousePosition());
			SpriteObject sprite = getTileAtPosition(getCenteredRelativeWorldMousePosition());
			System.out.println(sprite);
		}
	}
	
	public static void main(String[] args) {
		launchApp(new Game());
	}
	
}
