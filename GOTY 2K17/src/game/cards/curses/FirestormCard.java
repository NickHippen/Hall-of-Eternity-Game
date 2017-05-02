package game.cards.curses;

import java.awt.image.BufferedImage;
import java.net.URL;

import javax.imageio.ImageIO;

import game.TileWorld;
import game.units.Unit;
import game.units.heroes.Hero;
import game.units.monsters.Zombie;
import game.vectors.Vector2f;

public class FirestormCard extends ActionCard {

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
		return "Heal";
	}

	@Override
	public boolean performAction(Vector2f pos) {
		for (Unit unit : getWorld().getUnits()) {
			if (unit instanceof Hero) {
				Hero hero = (Hero) unit;
				hero.getStatusEffects().applyBurnStatus();
				System.out.println("APPLIED");
			}
		}
		return true;
	}
	
}
