package game.cards.curses;

import java.awt.image.BufferedImage;
import java.net.URL;

import javax.imageio.ImageIO;

import game.TileWorld;
import game.units.Unit;
import game.units.heroes.Hero;
import game.units.monsters.Zombie;

public class FirestormCard extends AreaCard {

	private static BufferedImage BASE_IMAGE;

	static {
		try {
			URL url = Zombie.class.getResource("/resources/cards/curses/firestorm.png");
			BufferedImage spriteSheet = ImageIO.read(url);
			BASE_IMAGE = spriteSheet;
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
	}

	public FirestormCard(TileWorld world) {
		super(BASE_IMAGE, world);
	}
	
	public String getName(){
		return "Firestorm";
	}

	@Override
	public void affectUnit(Unit unit) {
		if (unit instanceof Hero) {
			Hero hero = (Hero) unit;
			hero.getStatusEffects().applyBurnStatus();
		}
	}
	
}
