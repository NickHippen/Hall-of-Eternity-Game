package game.units.heroes;

import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.List;
import java.util.Set;

import javax.imageio.ImageIO;

import game.Tile;
import game.TileWorld;

public class Bard extends Hero {
	private final static int MAX_HEALTH = 175;
	private final static int DAMAGE = 10;
	
	private static BufferedImage spriteSheet;
	
	static {
		try {
			URL url = Bard.class.getResource("/resources/units/heroes/bard.png");
			spriteSheet = ImageIO.read(url);
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
	}
	
	public Bard(TileWorld world) {
		super(spriteSheet, world, MAX_HEALTH, HeroClassType.BARD);
		this.name="Bard";		
		this.setDamage(DAMAGE);
		this.setDropAmount(15);
	}
	
	public String getName() {
		return "Bard";
	}
	
	@Override
	public void update(float delta) {
		super.update(delta);
		Set<Tile> buffedTiles = getWorld().getSurroundingTiles(getTileLocation(), 1);
		buffedTiles.add(getTile());
		for (Tile buffedTile : buffedTiles) {
			List<Hero> heroes = buffedTile.getUnits(Hero.class);
			for (Hero hero : heroes) {
				hero.getStatusEffects().applySpeedUp();
			}
		}
	}
	
}
