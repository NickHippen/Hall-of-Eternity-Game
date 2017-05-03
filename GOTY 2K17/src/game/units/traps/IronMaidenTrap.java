package game.units.traps;

import java.awt.image.BufferedImage;
import java.net.URL;

import javax.imageio.ImageIO;

import game.TileWorld;
import game.units.LivingUnit;

public class IronMaidenTrap extends Trap {

	private static BufferedImage IMAGE;
	private static final int BONE_GAIN = 10;
	private static final float TICK_RATE = 10;
	
	private float timeSinceLastGain;
	
	static {
		try {
			URL url = IronMaidenTrap.class.getResource("/resources/units/traps/maiden.png");
			IMAGE = ImageIO.read(url);
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
	}
	
	public IronMaidenTrap(TileWorld world) {
		super(IMAGE, world);
	}

	@Override
	public void triggerEffect(LivingUnit livingUnit) {
	}
	
	@Override
	public void update(float delta) {
		super.update(delta);
		if (!getWorld().waveStarted) {
			return;
		}
		timeSinceLastGain += delta;
		if (timeSinceLastGain >= TICK_RATE) {
			getWorld().addBones(BONE_GAIN);
			timeSinceLastGain -= TICK_RATE;
		}
	}

	@Override
	public String getName() {
		return "Iron Maiden Trap";
	}

}
