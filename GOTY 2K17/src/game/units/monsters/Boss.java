package game.units.monsters;

import java.awt.image.BufferedImage;
import java.net.URL;

import javax.imageio.ImageIO;

import game.TileFramework;
import game.TileWorld;
import game.vectors.AxisAlignedBoundingBox;
import game.vectors.Vector2f;

public class Boss extends Monster {

	private final static int MAX_HEALTH = 200;
	private final static int DAMAGE = 35;
	
	private static BufferedImage spriteSheet;
	
	static {
		try {
			URL url = Zombie.class.getResource("/resources/units/monsters/boss.png");
			spriteSheet = ImageIO.read(url);
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
	}
	
	public Boss(TileWorld world) {
		super(spriteSheet, world, MAX_HEALTH);
		this.maxFrameNum = 15;
		
		
		setOuterBound(new AxisAlignedBoundingBox(
				new Vector2f(-TileFramework.TILE_SIZE_X / 2F, TileFramework.TILE_SIZE_Y / 2F),
				new Vector2f(TileFramework.TILE_SIZE_X / 2F, -TileFramework.TILE_SIZE_Y / 2F)));
		
		this.setDamage(DAMAGE);
		setRanged(true);
	}
	
	public String getName(){
		return "Lazrius";
	}
	
	@Override
	public void kill() {
		super.kill();
		getWorld().setGameover(true);
	}
	
}
