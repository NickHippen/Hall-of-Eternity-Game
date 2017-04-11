package edu.unomaha.nhippen.sprite.sprites;

import java.awt.image.BufferedImage;
import java.net.URL;

import javax.imageio.ImageIO;

import edu.unomaha.nhippen.sprite.vectors.BoundingCircle;

public class SpellSprite extends BoundingSprite {

	private static BufferedImage BASE_IMAGE;
	
	static {
		try {
			URL url = SpellSprite.class.getResource("/sprites/Spell.png");
			BASE_IMAGE = ImageIO.read(url);
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
	}
	
	public SpellSprite() {
		super(BASE_IMAGE);
		setOuterBound(new BoundingCircle(getLocation(), 25));
		setScale(0.5);
	}

}
