package edu.unomaha.nhippen.sprite.util;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.util.List;

import edu.unomaha.nhippen.sprite.vectors.AxisAlignedBoundingBox;
import edu.unomaha.nhippen.sprite.vectors.BoundingCircle;
import edu.unomaha.nhippen.sprite.vectors.Vector2f;

public class Utility {
	
	public static Matrix3x3f createViewport(
			float worldWidth, float worldHeight, 
			float screenWidth, float screenHeight ) {
		float sx = (screenWidth - 1) / worldWidth;
		float sy = (screenHeight - 1) / worldHeight;
		float tx = (screenWidth - 1) / 2.0f;
		float ty = (screenHeight - 1) / 2.0f;
		Matrix3x3f viewport = Matrix3x3f.scale(sx, -sy);
		viewport = viewport.mul(Matrix3x3f.translate(tx, ty));
		return viewport;
	}

	public static Matrix3x3f createReverseViewport(
			float worldWidth, float worldHeight, 
			float screenWidth, float screenHeight ) {
		float sx = worldWidth / (screenWidth - 1);
		float sy = worldHeight / (screenHeight - 1);
		float tx = (screenWidth - 1) / 2.0f;
		float ty = (screenHeight - 1) / 2.0f;
		Matrix3x3f viewport = Matrix3x3f.translate(-tx, -ty);
		viewport = viewport.mul(Matrix3x3f.scale(sx, -sy));
		return viewport;
	}
	
	public static void drawPolygon(Graphics g, Vector2f[] polygon) {
		Vector2f P;
		Vector2f S = polygon[polygon.length - 1];
		for (int i = 0; i < polygon.length; ++i) {
			P = polygon[i];
			g.drawLine((int) S.x, (int) S.y, (int) P.x, (int) P.y);
			S = P;
		}
	}

	public static void drawPolygon(Graphics g, List<Vector2f> polygon) {
		Vector2f S = polygon.get(polygon.size() - 1);
		for (Vector2f P : polygon) {
			g.drawLine((int) S.x, (int) S.y, (int) P.x, (int) P.y);
			S = P;
		}
	}

	public static void fillPolygon(Graphics2D g, Vector2f[] polygon) {
		Polygon p = new Polygon();
		for (Vector2f v : polygon) {
			p.addPoint((int) v.x, (int) v.y);
		}
		g.fill(p);
	}

	public static void fillPolygon(Graphics2D g, List<Vector2f> polygon) {
		Polygon p = new Polygon();
		for (Vector2f v : polygon) {
			p.addPoint((int) v.x, (int) v.y);
		}
		g.fill(p);
	}
	
	public static boolean intersectAABB(Vector2f minA, Vector2f maxA, Vector2f minB, Vector2f maxB) {
		if (minA.x > maxB.x || minB.x > maxA.x) //Check if Box A is to the right of Box B and vice-versa
			return false; //If so, they do not overlap
		if (minA.y > maxB.y || minB.y > maxA.y) //Check if Box is above Box B and vice-versa
			return false; //If so, they do not overlap
		return true; //Otherwise, they do overlap
	}
	
	public static boolean intersectAABB(AxisAlignedBoundingBox aabb1, AxisAlignedBoundingBox aabb2) {
		return intersectAABB(aabb1.getMinPoint(), aabb1.getMaxPoint(), aabb2.getMinPoint(), aabb2.getMaxPoint());
	}
	
	public static boolean intersectCircleAABB(Vector2f c, float r, Vector2f min, Vector2f max) {
		float dx = 0.0f; //Default if center is in x bounds
		if( c.x < min.x ) dx = c.x - min.x; //Center is left of x bounds, get distance in x
		if( c.x > max.x ) dx = c.x - max.x; //Center is right of x bounds, get distance in x

		float dy = 0.0f; //Default if center is in y bounds
		if( c.y < min.y ) dy = c.y - min.y; //Center is below y bounds, get distance in y
		if( c.y > max.y ) dy = c.y - max.y; //Center is above y bounds, get distance in y

		float d = dx*dx+dy*dy; //Find distance using Pythagorean Theorem
		return d < r*r; //Check against radius
	}

	public static boolean intersectCircleAABB(BoundingCircle circle, AxisAlignedBoundingBox aabb) {
		return intersectCircleAABB(circle.adjustPoint(circle.getLocation()), circle.getRadius(), aabb.getMinPoint(), aabb.getMaxPoint());
	}
	
}