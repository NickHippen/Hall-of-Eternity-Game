package game.units.traps;

import java.awt.image.BufferedImage;
import java.net.URL;

import javax.imageio.ImageIO;

import game.TileWorld;
import game.units.LivingUnit;

public class FireTrap extends Trap {

	private static BufferedImage IMAGE;
	
	static {
		try {
			URL url = FireTrap.class.getResource("/resources/units/traps/fire.png");
			IMAGE = ImageIO.read(url);
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
	}
	
	public FireTrap(TileWorld world) {
		super(IMAGE, world);
	}

	@Override
	public void triggerEffect(LivingUnit livingUnit) {
		livingUnit.getStatusEffects().applyBurnStatus();
	}

	@Override
	public String getName() {
		return "Fire Trap";
	}

}
