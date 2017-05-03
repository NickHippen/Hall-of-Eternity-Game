package game.cards.monsters;

import java.awt.image.BufferedImage;
import java.net.URL;

import javax.imageio.ImageIO;

import game.TileWorld;
import game.cards.Card;
import game.cards.curses.BlastCard;
import game.units.monsters.MonsterType;
import game.units.monsters.Zombie;

public class SpiderCard extends MonsterSpawnCard {

	private static BufferedImage BASE_IMAGE;

	static {
		try {
			URL url = Zombie.class.getResource("/resources/cards/monsters/card_monster_giant_spider.png");
			BufferedImage spriteSheet = ImageIO.read(url);
			BASE_IMAGE = spriteSheet;
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
	}

	public SpiderCard(TileWorld world) {
		super(BASE_IMAGE, world);
	}

	@Override
	public MonsterType getType() {
		return MonsterType.SPIDER;
	}

	public String getName(){
		return "Spider";
	}
	
	public Card copy (Card card){
		  Card f = new SpiderCard(world);
		  return f;
	}
	
	public int getCost(){
		return 80;
	}
}
