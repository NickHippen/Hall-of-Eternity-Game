package game.units.traps;

import java.awt.image.BufferedImage;
import java.net.URL;

import javax.imageio.ImageIO;

import game.TileWorld;
import game.units.LivingUnit;

public class IceTrap extends Trap {

	private static BufferedImage IMAGE;
	
	static {
		try {
			URL url = IceTrap.class.getResource("/resources/units/traps/ice.png");
			IMAGE = ImageIO.read(url);
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
	}
	
	public IceTrap(TileWorld world) {
		super(IMAGE, world);
	}

	@Override
	public void triggerEffect(LivingUnit livingUnit) {
		livingUnit.getStatusEffects().applyChillStatus();
	}

	@Override
	public String getName() {
		return "Ice Trap";
	}

}
