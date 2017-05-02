package game.units.traps;

import java.awt.image.BufferedImage;
import java.net.URL;

import javax.imageio.ImageIO;

import game.TileWorld;
import game.units.LivingUnit;

public class VulnerableTrap extends Trap {

	private static BufferedImage IMAGE;
	
	static {
		try {
			URL url = VulnerableTrap.class.getResource("/resources/units/traps/vulnerable.png");
			IMAGE = ImageIO.read(url);
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
	}
	
	public VulnerableTrap(TileWorld world) {
		super(IMAGE, world);
	}

	@Override
	public void triggerEffect(LivingUnit livingUnit) {
		livingUnit.getStatusEffects().applyVulnerableStatus();
	}

	@Override
	public String getName() {
		return "Vulnerable Trap";
	}

}
