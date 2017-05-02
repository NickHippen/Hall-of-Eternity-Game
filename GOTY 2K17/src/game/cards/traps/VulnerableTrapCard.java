package game.cards.traps;

import java.awt.image.BufferedImage;
import java.net.URL;

import javax.imageio.ImageIO;

import game.TileWorld;
import game.units.traps.TrapType;

public class VulnerableTrapCard extends TrapSpawnCard {

	private static BufferedImage BASE_IMAGE;

	static {
		try {
			URL url = VulnerableTrapCard.class.getResource("/resources/cards/traps/vulnerable.png");
			BufferedImage spriteSheet = ImageIO.read(url);
			BASE_IMAGE = spriteSheet;
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
	}

	public VulnerableTrapCard(TileWorld world) {
		super(BASE_IMAGE, world);
	}

	@Override
	public TrapType getType() {
		return TrapType.VULNERABLE;
	}

	public String getName(){
		return "Vulnerable Trap";
	}
	
}
