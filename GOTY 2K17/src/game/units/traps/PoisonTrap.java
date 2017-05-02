package game.units.traps;

import java.awt.image.BufferedImage;
import java.net.URL;

import javax.imageio.ImageIO;

import game.TileWorld;
import game.units.LivingUnit;

public class PoisonTrap extends Trap {

	private static BufferedImage IMAGE;
	
	static {
		try {
			URL url = PoisonTrap.class.getResource("/resources/units/traps/poison.png");
			IMAGE = ImageIO.read(url);
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
	}
	
	public PoisonTrap(TileWorld world) {
		super(IMAGE, world);
	}

	@Override
	public void triggerEffect(LivingUnit livingUnit) {
		livingUnit.getStatusEffects().applyPoisonStatus();
	}

	@Override
	public String getName() {
		return "Poison Trap";
	}

}
