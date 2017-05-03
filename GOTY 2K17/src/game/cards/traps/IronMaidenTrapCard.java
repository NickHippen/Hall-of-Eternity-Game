package game.cards.traps;

import java.awt.image.BufferedImage;
import java.net.URL;

import javax.imageio.ImageIO;

import game.TileWorld;
import game.cards.Card;
import game.cards.curses.BlastCard;
import game.units.traps.TrapType;

public class IronMaidenTrapCard extends TrapSpawnCard {

	private static BufferedImage BASE_IMAGE;

	static {
		try {
			URL url = IronMaidenTrapCard.class.getResource("/resources/cards/traps/ironMaiden.png");
			BufferedImage spriteSheet = ImageIO.read(url);
			BASE_IMAGE = spriteSheet;
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
	}

	public IronMaidenTrapCard(TileWorld world) {
		super(BASE_IMAGE, world);
	}

	@Override
	public TrapType getType() {
		return TrapType.IRON_MAIDEN;
	}

	public String getName(){
		return "Iron Maiden Trap";
	}
	
	public Card copy (Card card){
		  Card f = new IronMaidenTrapCard(world);
		  return f;
	}
	
	public int getCost(){
		return 300;
	}
}
