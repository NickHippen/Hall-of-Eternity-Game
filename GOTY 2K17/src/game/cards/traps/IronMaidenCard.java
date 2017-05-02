package game.cards.traps;

import java.awt.image.BufferedImage;
import java.net.URL;

import javax.imageio.ImageIO;

import game.TileWorld;
import game.units.traps.TrapType;

public class IronMaidenCard extends TrapSpawnCard {

	private static BufferedImage BASE_IMAGE;

	static {
		try {
			URL url = IronMaidenCard.class.getResource("/resources/cards/traps/ironMaiden.png");
			BufferedImage spriteSheet = ImageIO.read(url);
			BASE_IMAGE = spriteSheet;
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
	}

	public IronMaidenCard(TileWorld world) {
		super(BASE_IMAGE, world);
	}

	@Override
	public TrapType getType() {
		return TrapType.IRON_MAIDEN;
	}

	public String getName(){
		return "Iron Maiden Trap";
	}
	
}
