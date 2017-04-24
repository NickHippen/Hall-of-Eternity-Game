package game.sprites;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import game.util.Matrix3x3f;
import game.vectors.Vector2f;

public class SpriteObject {

	BufferedImage image;
	protected int horizontalFrameNum;
	protected int verticalFrameNum;
	protected int frameSize;
	
	
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
	
	public BufferedImage getFrame(int frameNum) {
		/*
		 * frameNum corresponds to a section of a sprite sheets as follows: 
		 * 1 2 3 4 5 
		 * 6 7 8 9 10 
		 * 11 12 etc... 
		 * This is consistent for frame sheets of different widths and heights.
		 */
		
		//Get the x and y of the start of our frame
		int x = ((frameNum - 1) % horizontalFrameNum) * (image.getWidth() / this.horizontalFrameNum);
		int y = ((frameNum - 1) / this.horizontalFrameNum) * (image.getHeight() / this.verticalFrameNum);

		//Return a subimage of the spritesheet the size of one frame at the requested position
		BufferedImage frame = image.getSubimage(x, y, image.getWidth() / this.horizontalFrameNum, image.getHeight() / this.verticalFrameNum);
		return frame;
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
	
	public void setHorizontalFrameNum(int horizontalFrameNum){
		this.horizontalFrameNum = horizontalFrameNum;
	}
	
	public int getHorizontalFrameNum(){
		return this.horizontalFrameNum;
	}
	
	public void setVerticalFrameNum(int verticalFrameNum){
		this.verticalFrameNum = verticalFrameNum;
	}
	
	public int getVerticalFrameNum(){
		return this.verticalFrameNum;
	}
	
	public void setFrameSize(int frameSize){
		this.frameSize = frameSize;
	}
	
	public int getFrameSize(){
		return this.frameSize;
	}
	
}
