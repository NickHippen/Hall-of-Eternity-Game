package game.units.heroes;

import java.awt.image.BufferedImage;

import game.TileFramework;
import game.vectors.AxisAlignedBoundingBox;
import game.vectors.Vector2f;

public class Freelancer extends Hero {
	private final static int MAX_HEALTH = 75;
	private final static int DAMAGE = 20;
	
	private static BufferedImage BASE_IMAGE;
	
	static {
		try {
//			URL url = RockSprite.class.getResource("/sprites/Tiles.png");
//			BufferedImage spriteSheet = ImageIO.read(url);
//			BASE_IMAGE = spriteSheet.getSubimage(0, 48, 48, 48);
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
	}
	
	public Freelancer() {
		super(BASE_IMAGE, MAX_HEALTH, HeroDamageType.MELEE);
		setOuterBound(new AxisAlignedBoundingBox(
				new Vector2f(-TileFramework.TILE_SIZE_X / 2F, TileFramework.TILE_SIZE_Y / 2F),
				new Vector2f(TileFramework.TILE_SIZE_X / 2F, -TileFramework.TILE_SIZE_Y / 2F)));
		setScale(0.95);
		
		this.setDamagePerHit(DAMAGE);
	}
}
