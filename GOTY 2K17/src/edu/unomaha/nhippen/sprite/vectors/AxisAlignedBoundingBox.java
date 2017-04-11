package edu.unomaha.nhippen.sprite.vectors;

import java.awt.Graphics;

import edu.unomaha.nhippen.sprite.util.Matrix3x3f;
import edu.unomaha.nhippen.sprite.util.Utility;

public class AxisAlignedBoundingBox extends VectorObject {

	private Vector2f minPoint;
	private Vector2f maxPoint;
	
	public AxisAlignedBoundingBox(Vector2f point1, Vector2f point2) {
		this.minPoint = point1;
		this.maxPoint = point2;
		setLocation(new Vector2f(0, 0));
		setRotation(0);
		setScale(1);
		setWorld(Matrix3x3f.identity());
	}
	
	public AxisAlignedBoundingBox(AxisAlignedBoundingBox clone) {
		this(clone.minPoint, clone.maxPoint);
	}
	
	@Override
	public void render(Graphics g) {
		if (isDeleted()) {
			return;
		}
		g.setColor(getColor());
		Vector2f adjPoint1 = adjustPoint(minPoint);
		Vector2f adjPoint2 = adjustPoint(maxPoint);
		g.drawRect((int) adjPoint1.x, (int) adjPoint1.y, (int) Math.abs(adjPoint1.x - adjPoint2.x), (int) Math.abs(adjPoint1.y - adjPoint2.y));
	}

	public Vector2f getMinPoint() {
		return adjustPoint(minPoint);
	}

	public Vector2f getMaxPoint() {
		return adjustPoint(maxPoint);
	}
	
	@Override
	public boolean isIntersecting(VectorObject bound) {
		if (bound instanceof AxisAlignedBoundingBox) {
			return Utility.intersectAABB(this, (AxisAlignedBoundingBox) bound);
		}
		return false;
	}
	
}
