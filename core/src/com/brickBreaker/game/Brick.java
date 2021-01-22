package com.brickBreaker.game;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

/**
 * @author Jose Magana
 * @brief class to represent a brick
 */

public class Brick {
	protected Vec2f position; // top left corner of brick
	protected float width;
	protected float height;
	protected int health; // how many hits the brick can take before dying

	/**
	 * Default construct a Brick object
	 */
	public Brick() {
		this.position = new Vec2f(0, 0);
		this.width = 0;
		this.height = 0;
		this.health = 0;
	}

	/**
	 * Create a new brick object
	 * 
	 * @param position Vec2f that holds the bottom left corner
	 * @param width    brick width
	 * @param height   brick height
	 * @param health   brick HP
	 */
	public Brick(Vec2f position, float width, float height, int health) {
		this.position = position;
		this.width = width;
		this.height = height;
		this.health = health;
	}

	public void draw(ShapeRenderer rend) {
		rend.rect(position.x, position.y, width, height);
	}

	public void damage() {
		--health;
	}

	/**********************
	 * Collision handling *
	 **********************/

	public void collision(Ball ball) {
		final Vec2f bottomLeft = position;
		final Vec2f bottomRight = new Vec2f(position.x + width, position.y);
		final Vec2f topLeft = new Vec2f(position.x, position.y + height);
		final Vec2f topRight = new Vec2f(bottomRight.x, topLeft.y);

		// unpacking stuff just to make all these checks less annoying
		final Vec2f bOrigin = ball.getOrigin();
		final float bRadius = ball.getRadius();

		// checks to see if a collision is possible before computing distances
		final boolean withinLeft = bottomLeft.x <= bOrigin.x;
		final boolean withinRight = bottomRight.x >= bOrigin.x;
		final boolean withinBottom = bottomLeft.y <= bOrigin.y;
		final boolean withinTop = topRight.y + height >= bOrigin.y;

		if (withinLeft && withinRight) {
			// collision with bottom/top possible
			if (Utils.linePointDistance(topLeft, topRight, bOrigin) <= bRadius)
				collideTop(ball); // we hit the top of the brick
			else if (Utils.linePointDistance(bottomLeft, bottomRight, bOrigin) <= bRadius)
				collideBottom(ball);// we hit the bottom of a brick
		} else if (withinBottom && withinTop) {
			// collision with sides possible
			if (Utils.linePointDistance(topLeft, bottomLeft, bOrigin) <= bRadius)
				collideLeft(ball);// we hit the left
			else if (Utils.linePointDistance(topRight, bottomRight, bOrigin) <= bRadius)
				collideRight(ball);// we hit the right
		}
	}

	/**
	 * Helper methods These are here to be overriden by the Paddle object
	 */

	public void collideTop(Ball ball) {
		ball.deflectUp();
		damage();
	}

	public void collideBottom(Ball ball) {
		ball.deflectDown();
		damage();
	}

	public void collideRight(Ball ball) {
		ball.deflectRight();
		damage();
	}

	public void collideLeft(Ball ball) {
		ball.deflectLeft();
		damage();
	}

	/**
	 * Getters and setters
	 */
	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = health;
	}

	public Vec2f getPosition() {
		return position;
	}

	public void setPosition(Vec2f position) {
		this.position = position;
	}

	public float getHeight() {
		return height;
	}

	public void setHeight(float length) {
		this.height = length;
	}

	public float getWidth() {
		return width;
	}

	public void setWidth(float width) {
		this.width = width;
	}
}
