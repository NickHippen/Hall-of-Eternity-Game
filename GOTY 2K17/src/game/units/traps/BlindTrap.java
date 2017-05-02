package game.units.traps;

import java.awt.image.BufferedImage;
import java.net.URL;

import javax.imageio.ImageIO;

import game.TileWorld;
import game.units.LivingUnit;

public class BlindTrap extends Trap {

	private static BufferedImage IMAGE;
	
	static {
		try {
			URL url = BlindTrap.class.getResource("/resources/units/traps/blind.png");
			IMAGE = ImageIO.read(url);
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
	}
	
	public BlindTrap(TileWorld world) {
		super(IMAGE, world);
	}

	@Override
	public void triggerEffect(LivingUnit livingUnit) {
		livingUnit.getStatusEffects().applyBlindStatus();
	}

	@Override
	public String getName() {
		return "Blind Trap";
	}

}
