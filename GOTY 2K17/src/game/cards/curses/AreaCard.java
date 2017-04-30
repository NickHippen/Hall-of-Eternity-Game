package game.cards.curses;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

import game.Tile;
import game.TileWorld;
import game.units.LivingUnit;
import game.units.Unit;
import game.vectors.Vector2f;

public abstract class AreaCard extends CurseCard {

	protected AreaCard(BufferedImage image, TileWorld world) {
		super(image, world);
	}
	
	public boolean performAction(Vector2f pos){
		ArrayList<Tile> affectedTiles = getWorld().getSurroundingTilesDiag(getWorld().getTileAtPosition(pos).getLocation(),1);
		for(int i = 0; i < affectedTiles.size(); i++){
			for(Unit unit : affectedTiles.get(i).getUnits()){
				affectUnit(unit);
			}
		}
		return true;
	}
	
	public abstract void affectUnit(Unit unit);

}
