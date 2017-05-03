package game.cards.traps;

import java.awt.image.BufferedImage;
import java.net.URL;

import javax.imageio.ImageIO;

import game.TileWorld;
import game.cards.Card;
import game.cards.curses.BlastCard;
import game.units.traps.TrapType;

public class BlindTrapCard extends TrapSpawnCard {

	private static BufferedImage BASE_IMAGE;

	static {
		try {
			URL url = BlindTrapCard.class.getResource("/resources/cards/traps/blind.png");
			BufferedImage spriteSheet = ImageIO.read(url);
			BASE_IMAGE = spriteSheet;
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
	}

	public BlindTrapCard(TileWorld world) {
		super(BASE_IMAGE, world);
	}

	@Override
	public TrapType getType() {
		return TrapType.BLIND;
	}

	public String getName(){
		return "Blind Trap";
	}
	
	public Card copy (Card card){
		  Card f = new BlindTrapCard(world);
		  return f;
	}
	
	public int getCost(){
		return 200;
	}
}
