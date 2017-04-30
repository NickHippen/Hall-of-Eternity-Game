package game.cards.curses;

import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import game.Tile;
import game.TileLocation;
import game.TileWorld;
import game.units.LivingUnit;
import game.units.Unit;
import game.units.monsters.Monster;
import game.units.monsters.MonsterType;
import game.units.monsters.Zombie;
import game.vectors.Vector2f;

public class BlastCard extends AreaCard {

	private static BufferedImage BASE_IMAGE;

	static {
		try {
			URL url = Zombie.class.getResource("/resources/cards/curses/blast.png");
			BufferedImage spriteSheet = ImageIO.read(url);
			BASE_IMAGE = spriteSheet;
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
	}

	public BlastCard(TileWorld world) {
		super(BASE_IMAGE, world);
	}
	
	@Override
	public void performAction() {
		
	}
	
	public String getName(){
		return "Blast";
	}

	@Override
	public boolean performAction(Vector2f pos) {
		ArrayList<Tile> blastedTiles = getWorld().getSurroundingTilesDiag(getWorld().getTileAtPosition(pos).getLocation(),1);
		for(int i = 0; i < blastedTiles.size(); i++){
			for(Unit unit : blastedTiles.get(i).getUnits()){
				if (unit instanceof LivingUnit) ((LivingUnit) unit).setHealth(((LivingUnit) unit).getHealth() - 50);
			}
		}
		return true;
	}

}
