package game.cards.traps;

import java.awt.image.BufferedImage;
import java.net.URL;

import javax.imageio.ImageIO;

import game.TileWorld;
import game.cards.Card;
import game.cards.curses.BlastCard;
import game.units.traps.TrapType;

public class FireTrapCard extends TrapSpawnCard {

	private static BufferedImage BASE_IMAGE;

	static {
		try {
			URL url = FireTrapCard.class.getResource("/resources/cards/traps/fire.png");
			BufferedImage spriteSheet = ImageIO.read(url);
			BASE_IMAGE = spriteSheet;
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
	}

	public FireTrapCard(TileWorld world) {
		super(BASE_IMAGE, world);
	}

	@Override
	public TrapType getType() {
		return TrapType.FIRE;
	}

	public String getName(){
		return "Fire Trap";
	}
	
	public Card copy (Card card){
		  Card f = new FireTrapCard(world);
		  return f;
	}
	
	public int getCost(){
		return 250;
	}
}
