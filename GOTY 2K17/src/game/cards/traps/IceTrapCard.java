package game.cards.traps;

import java.awt.image.BufferedImage;
import java.net.URL;

import javax.imageio.ImageIO;

import game.TileWorld;
import game.cards.Card;
import game.cards.curses.BlastCard;
import game.units.traps.TrapType;

public class IceTrapCard extends TrapSpawnCard {

	private static BufferedImage BASE_IMAGE;

	static {
		try {
			URL url = IceTrapCard.class.getResource("/resources/cards/traps/frost.png");
			BufferedImage spriteSheet = ImageIO.read(url);
			BASE_IMAGE = spriteSheet;
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
	}

	public IceTrapCard(TileWorld world) {
		super(BASE_IMAGE, world);
	}

	@Override
	public TrapType getType() {
		return TrapType.ICE;
	}

	public String getName(){
		return "Ice Trap";
	}
	
	public Card copy (Card card){
		  Card f = new IceTrapCard(world);
		  return f;
	}
	
}
