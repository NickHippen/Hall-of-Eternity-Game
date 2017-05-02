package game.cards.curses;

import java.awt.image.BufferedImage;
import java.net.URL;

import javax.imageio.ImageIO;

import game.TileWorld;
import game.units.monsters.Zombie;
import game.vectors.Vector2f;

public class CardBack extends ActionCard {

	private static BufferedImage BASE_IMAGE;

	static {
		try {
			URL url = Zombie.class.getResource("/resources/cards/card_back.png");
			BufferedImage spriteSheet = ImageIO.read(url);
			BASE_IMAGE = spriteSheet;
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
	}

	public CardBack(TileWorld world) {
		super(BASE_IMAGE, world);
	}
	
	@Override
	public boolean performAction(Vector2f pos) {
		// TODO Auto-generated method stub
		return false;
	}

	public String getName(){
		return "Card Back";
	}
}
