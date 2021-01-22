package com.brickBreaker.game;

/**
 * @author Jose Magana
 * 
 * @brief Class to represent math vectors in 2D space
 */

public class Vec2f {
	public float x;
	public float y;

	public Vec2f() {
		x = 0.f;
		y = 0.f;
	}

	public Vec2f(float x, float y) {
		this.x = x;
		this.y = y;
	}

	// scalar product
	public Vec2f times(float n) {
		return new Vec2f(x * n, y * n);
	}

	// vector sum
	public Vec2f plus(Vec2f vec) {
		return new Vec2f(x + vec.x, y + vec.y);
	}

	// vector difference
	public Vec2f minus(Vec2f vec) {
		return new Vec2f(x - vec.x, y - vec.y);
	}

	// the norm or magnitude of a vector
	public float norm() {
		return (float) Math.sqrt(x * x + y * y);
	}

	// normalize a vector, make it into a unit vector
	public Vec2f normalize() {
		return this.times(1.f / this.norm());
	}

	public String toString() {
		return "<" + x + ", " + y + ">";
	}
}
