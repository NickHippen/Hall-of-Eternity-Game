package game.units.monsters;

import java.awt.image.BufferedImage;
import java.net.URL;

import javax.imageio.ImageIO;

import game.TileFramework;
import game.units.Unit;
import game.vectors.AxisAlignedBoundingBox;
import game.vectors.Vector2f;

public class Zombie extends Monster {

	private final static int MAX_HEALTH = 200;
	private final static int DAMAGE = 35;
	
	private static BufferedImage BASE_IMAGE;
		
	static {
		try {
			URL url = Zombie.class.getResource("/resources/units/monsters/zombie.png");
			BufferedImage spriteSheet = ImageIO.read(url).getSubimage(0, 0, 64, 64);
			BASE_IMAGE = spriteSheet;
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
	}
	
	public Zombie() {
		super(BASE_IMAGE, MAX_HEALTH);
		setOuterBound(new AxisAlignedBoundingBox(
				new Vector2f(-TileFramework.TILE_SIZE_X / 2F, TileFramework.TILE_SIZE_Y / 2F),
				new Vector2f(TileFramework.TILE_SIZE_X / 2F, -TileFramework.TILE_SIZE_Y / 2F)));
		
		this.setDamagePerHit(DAMAGE);
	}
	
	public void setSize(){
		Unit.size = 64f;
	}
}
