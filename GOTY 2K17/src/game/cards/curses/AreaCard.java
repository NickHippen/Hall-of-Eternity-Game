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
	
	@Override
	public boolean performAction(Vector2f pos) {
		ArrayList<Tile> affectedTiles = getWorld().getSurroundingTilesDiag(getWorld().getTileAtPosition(pos).getLocation(),1);
		for (Unit unit : getWorld().getUnits()) {
			Tile tile = unit.getTile();
			if (affectedTiles.contains(tile)) {
				affectUnit(unit);
			}
		}
			
		return true;
	}
	
	public abstract void affectUnit(Unit unit);

}
