package game.cards.monsters;

import java.awt.image.BufferedImage;
import java.net.URL;

import javax.imageio.ImageIO;

import game.TileWorld;
import game.cards.Card;
import game.cards.curses.BlastCard;
import game.units.monsters.MonsterType;
import game.units.monsters.Zombie;

public class CentaurCard extends MonsterSpawnCard {

	private static BufferedImage BASE_IMAGE;

	static {
		try {
			URL url = Zombie.class.getResource("/resources/cards/monsters/card_monster_centaur.png");
			BufferedImage spriteSheet = ImageIO.read(url);
			BASE_IMAGE = spriteSheet;
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
	}

	public CentaurCard(TileWorld world) {
		super(BASE_IMAGE, world);
	}

	@Override
	public MonsterType getType() {
		return MonsterType.CENTAUR;
	}

	public String getName(){
		return "Centaur";
	}
	
	public Card copy (Card card){
		  Card f = new CentaurCard(world);
		  return f;
	}
}
