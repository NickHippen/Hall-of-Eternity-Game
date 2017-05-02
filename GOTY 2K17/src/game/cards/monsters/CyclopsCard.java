package game.cards.monsters;

import java.awt.image.BufferedImage;
import java.net.URL;

import javax.imageio.ImageIO;

import game.TileWorld;
import game.units.monsters.MonsterType;
import game.units.monsters.Zombie;

public class CyclopsCard extends MonsterSpawnCard {

	private static BufferedImage BASE_IMAGE;

	static {
		try {
			URL url = Zombie.class.getResource("/resources/cards/monsters/card_monster_cyclops.png");
			BufferedImage spriteSheet = ImageIO.read(url);
			BASE_IMAGE = spriteSheet;
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
	}

	public CyclopsCard(TileWorld world) {
		super(BASE_IMAGE, world);
	}

	@Override
	public MonsterType getType() {
		return MonsterType.CYCLOPS;
	}

	public String getName(){
		return "Cyclops";
	}
	
}
