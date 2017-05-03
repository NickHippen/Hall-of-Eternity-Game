package game.cards.curses;

import java.awt.image.BufferedImage;
import java.net.URL;

import javax.imageio.ImageIO;

import game.TileWorld;
import game.cards.Card;
import game.units.LivingUnit;
import game.units.Unit;

public class FlashFreezeCard extends AreaCard {

	private static BufferedImage BASE_IMAGE;

	static {
		try {
			URL url = FlashFreezeCard.class.getResource("/resources/cards/curses/flashFreeze.png");
			BufferedImage spriteSheet = ImageIO.read(url);
			BASE_IMAGE = spriteSheet;
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
	}

	public FlashFreezeCard(TileWorld world) {
		super(BASE_IMAGE, world);
	}
	
	public String getName(){
		return "Flash Freeze";
	}

	@Override
	public void affectUnit(Unit unit) {
		if (!(unit instanceof LivingUnit)) {
			return;
		}
		System.out.println("Hit");
		LivingUnit livingUnit = (LivingUnit) unit;
		livingUnit.getStatusEffects().applyChillStatus();
	}

	public Card copy (Card card){
		  Card f = new FlashFreezeCard(world);
		  return f;
	}
	
	public int getCost(){
		return 50;
	}
}
