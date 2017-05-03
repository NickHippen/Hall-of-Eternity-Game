package game.cards.curses;

import java.awt.image.BufferedImage;
import java.net.URL;

import javax.imageio.ImageIO;

import game.TileWorld;
import game.cards.Card;
import game.units.Unit;
import game.units.heroes.Hero;
import game.units.monsters.Zombie;

public class TouchOfDeathCard extends UnitSelectCard {

	private static BufferedImage BASE_IMAGE;

	static {
		try {
			URL url = Zombie.class.getResource("/resources/cards/curses/touchOfDeath.png");
			BufferedImage spriteSheet = ImageIO.read(url);
			BASE_IMAGE = spriteSheet;
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
	}

	public TouchOfDeathCard(TileWorld world) {
		super(BASE_IMAGE, world);
	}
	
	public String getName(){
		return "Touch of Death";
	}

	@Override
	public void performAction(Unit unit) {
		if (unit instanceof Hero) {
			Hero hero = (Hero) unit;
			hero.kill();
		}
	}
	
	public Card copy (Card card){
		  Card f = new TouchOfDeathCard(world);
		  return f;
	}
	
	public int getCost(){
		return 40;
	}
}
