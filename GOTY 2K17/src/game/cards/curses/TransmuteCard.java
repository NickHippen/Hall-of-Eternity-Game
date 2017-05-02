package game.cards.curses;

import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.Random;

import javax.imageio.ImageIO;

import game.TileWorld;
import game.units.monsters.Zombie;
import game.vectors.Vector2f;

public class TransmuteCard extends ActionCard {

	private static BufferedImage BASE_IMAGE;
	
	private static final Random RANDOM = new Random();

	static {
		try {
			URL url = Zombie.class.getResource("/resources/cards/curses/transmute.png");
			BufferedImage spriteSheet = ImageIO.read(url);
			BASE_IMAGE = spriteSheet;
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
	}

	public TransmuteCard(TileWorld world) {
		super(BASE_IMAGE, world);
	}
	
	public String getName() {
		return "Transmute";
	}

	@Override
	public boolean performAction(Vector2f pos) {
		getWorld().addBones(RANDOM.nextInt(150));
		return true;
	}
	
}
