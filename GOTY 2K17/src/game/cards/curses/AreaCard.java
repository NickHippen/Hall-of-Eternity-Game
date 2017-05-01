package game.cards.curses;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

import game.Tile;
import game.TileWorld;
import game.units.Unit;
import game.vectors.Vector2f;

public abstract class AreaCard extends CurseCard {

	protected AreaCard(BufferedImage image, TileWorld world) {
		super(image, world);
	}
	
	public boolean performAction(Vector2f pos) {
		System.out.println("Perform");
		ArrayList<Tile> affectedTiles = getWorld().getSurroundingTilesDiag(getWorld().getTileAtPosition(pos).getLocation(),1);
		for (Unit unit : getWorld().getUnits()) {
			Tile tile = unit.getWorld().getTileAtPosition(unit.getLocation());
			if (affectedTiles.contains(tile)) {
				affectUnit(unit);
			}
		}
			
		return true;
	}
	
	public abstract void affectUnit(Unit unit);

}
