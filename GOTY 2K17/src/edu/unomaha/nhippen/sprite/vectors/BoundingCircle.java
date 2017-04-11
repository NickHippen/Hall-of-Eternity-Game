package edu.unomaha.nhippen.sprite.vectors;

import java.awt.Graphics;

import edu.unomaha.nhippen.sprite.util.Utility;

public class BoundingCircle extends VectorObject {

	private int radius;
	public int adjustedWidth;
	public int adjustedHeight;
	
	public BoundingCircle(Vector2f center, int radius) {
		super();
		setLocation(center);
		this.radius = radius;
	}
	
	@Override
	public void render(Graphics g) {
//		g.setColor(getColor());
//		Vector2f topLeft = new Vector2f(getLocation().x - radius, getLocation().y
//				+ radius);
//		topLeft = getViewportTranform().mul(topLeft);
//		Vector2f bottomRight = new Vector2f(getLocation().x + radius, getLocation().y
//				- radius);
//		bottomRight = getViewportTranform().mul(bottomRight);
//		int circleX = (int) topLeft.x;
//		int circleY = (int) topLeft.y;
//		int circleWidth = (int) (bottomRight.x - topLeft.x);
//		int circleHeight = (int) (bottomRight.y - topLeft.y);
//		g.drawOval(circleX, circleY, circleWidth, circleHeight);
//		adjustedWidth = circleWidth;
//		adjustedHeight = circleHeight;
		Vector2f adjLoc = adjustPoint(getLocation());
		int adjX = (int) (adjLoc.x - radius);
		int adjY = (int) (adjLoc.y - radius);
		g.drawOval(adjX, adjY, radius * 2, radius * 2);
	}

	@Override
	public boolean isIntersecting(VectorObject bound) {
		if (bound instanceof AxisAlignedBoundingBox) {
			return Utility.intersectCircleAABB(this, (AxisAlignedBoundingBox) bound);
		}
		return false;
	}
	
	public int getRadius() {
		return radius;
	}

}
