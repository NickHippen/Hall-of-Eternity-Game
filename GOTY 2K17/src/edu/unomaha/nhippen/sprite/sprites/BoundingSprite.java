package edu.unomaha.nhippen.sprite.sprites;

import java.awt.image.BufferedImage;
import java.util.List;

import edu.unomaha.nhippen.sprite.util.Matrix3x3f;
import edu.unomaha.nhippen.sprite.vectors.Vector2f;
import edu.unomaha.nhippen.sprite.vectors.VectorObject;

public class BoundingSprite extends SpriteObject {
	
	private VectorObject outerBound;
	private List<VectorObject> innerBounds;

	public BoundingSprite(BufferedImage image) {
		super(image);
	}

	@Override
	public void setLocation(Vector2f location) {
		super.setLocation(location);
		if (outerBound != null) {
			outerBound.setLocation(location);
		}
		if (innerBounds != null) {
			for (VectorObject innerBound : innerBounds) {
				innerBound.setLocation(location);
			}
		}
		updateWorld();
	}
	
	public VectorObject getOuterBound() {
		return outerBound;
	}

	public void setOuterBound(VectorObject outerBound) {
		this.outerBound = outerBound;
	}

	public List<VectorObject> getInnerBounds() {
		return innerBounds;
	}

	public void setInnerBounds(List<VectorObject> innerBounds) {
		this.innerBounds = innerBounds;
	}

	public void move(Vector2f vec, List<BoundingSprite> bounds, float delta) {
		setLocation(getLocation().add(vec.mul(delta)));
		if (outerBound == null) {
			return;
		}
		Vector2f negVec = vec.mul(-delta);
		while (hasAnyBoundViolations(bounds)) {
			setLocation(getLocation().add(negVec));
		}
	}
	
	private boolean hasBoundViolationsOnPersonalBound(List<BoundingSprite> bounds, VectorObject personalBound) {
		for (BoundingSprite boundingSprite : bounds) {
			if (personalBound.isIntersecting(boundingSprite.getOuterBound())) {
				if (boundingSprite.getInnerBounds() != null && !boundingSprite.getInnerBounds().isEmpty()) {
					for (VectorObject innerBound : boundingSprite.getInnerBounds()) {
						if (personalBound.isIntersecting(innerBound)) {
							return true;
						}
					}
				} else {
					return true;
				}
			}
		}
		return false;
	}
	
	public boolean hasAnyBoundViolations(List<BoundingSprite> bounds) {
		if (hasBoundViolationsOnPersonalBound(bounds, outerBound)) {
			// Check inner, if applicable
			if (innerBounds != null && !innerBounds.isEmpty()) {
				for (VectorObject innerBound : innerBounds) {
					if (hasBoundViolationsOnPersonalBound(bounds, innerBound)) {
						return true;
					}
				}
			} else {
				return true;
			}
		}
		return false;
	}
	
	public void updateWorld() {
		if (outerBound == null) {
			return;
		}
		outerBound.updateWorld();
		if (innerBounds == null) {
			return;
		}
		for (VectorObject innerBound : innerBounds) {
			innerBound.updateWorld();
		}
	}
	
	@Override
	public void setView(Matrix3x3f view) {
		super.setView(view);
		if (outerBound == null) {
			return;
		}
		outerBound.setViewportTransform(view);
		if (innerBounds == null) {
			return;
		}
		for (VectorObject innerBound : innerBounds) {
			innerBound.setViewportTransform(view);
		}
	}
	
}
