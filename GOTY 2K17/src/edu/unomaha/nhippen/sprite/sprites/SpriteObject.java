package edu.unomaha.nhippen.sprite.sprites;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import edu.unomaha.nhippen.sprite.util.Matrix3x3f;
import edu.unomaha.nhippen.sprite.vectors.Vector2f;

public class SpriteObject {

	BufferedImage image;
	
	private Matrix3x3f view;
	
	private Vector2f location;
	private float rotation;
	private double scale;
	
	public SpriteObject(BufferedImage image) {
		this.image = image;
		this.location = new Vector2f(0F, 0F);
		this.rotation = 0F;
		this.scale = 1;
	}
	
	public void draw(Graphics2D g) {
		AffineTransform transform = createTransform();
		g.drawImage(image, transform, null);
	}
	
	private AffineTransform createTransform() {
		Vector2f screen = view.mul(location);
		AffineTransform transform = AffineTransform.getTranslateInstance(
				screen.x, screen.y);
		transform.rotate(rotation);
		if (scale != 1) {
			transform.translate(-image.getWidth() * (1 - scale) / 2, -image.getHeight() * (1 - scale) / 2);
		} else {
			transform.translate(-image.getWidth() / 2, -image.getHeight() / 2);
		}
		transform.scale(scale, scale);
		return transform;
	}
	
	public Matrix3x3f getView() {
		return view;
	}
	
	public void setView(Matrix3x3f view) {
		this.view = view;
	}
	
	public Vector2f getLocation() {
		return location;
	}
	
	public void setLocation(Vector2f location) {
		this.location = location;
	}
	
	public float getRotation() {
		return rotation;
	}
	
	public void setRotation(float rotation) {
		this.rotation = rotation;
	}
	
	public double getScale() {
		return scale;
	}
	
	public void setScale(double scale) {
		this.scale = scale;
	}
	
}
