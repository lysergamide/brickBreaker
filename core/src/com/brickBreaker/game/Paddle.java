package com.brickBreaker.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

/**
 * @author Jose Magana
 * @brief class to represent the paddle
 */

public class Paddle extends Brick {
	private float speed;

	/**
	 * Default construct a Paddle
	 */
	public Paddle() {
		super(new Vec2f(0.f, 15.f), 100.f, 15.f, 2);
		this.speed = 10.f;
		center();
	}

	/**
	 * Create a new paddle
	 * 
	 * @param health How much health to start with
	 * @param speed  How fast the paddle will move
	 */
	public Paddle(int health, float speed) {
		this();
		this.health = health;
		this.speed = speed;
	}
	
	public boolean isAlive() {
		return health > 0;
	}

	// Center the paddle horizontally
	public void center() {
		position.x = (Gdx.graphics.getWidth() - width) / 2;
	}

	// take a step to the left
	public void moveRight() {
		// checks that we don't go past the right edge
		position.x = Math.min(position.x + speed, Gdx.graphics.getWidth() - width);
	}

	// take a step to the right
	public void moveLeft() {
		// checks that we don't go past the left edge
		position.x = Math.max(position.x - speed, 0.f);
	}

	@Override
	public void draw(ShapeRenderer rend) {
		// call Brick.draw()
		super.draw(rend);
		// Also draw how many lives are left in bottom left
		for (int i = 0; i < this.health; i++)
			rend.circle(6.f + (i * 20), 6.f, 5);
	}

	/***********************
	 * Collision overrides *
	 ***********************/

	/**
	 * instead of just deflecting, we will change the angle of deflection based on
	 * where the ball hits this gives the player control and stops the ball from
	 * looping in some pattern forever
	 */
	@Override
	public void collideTop(Ball ball) {
		// new direction is the vector from the center of paddle to center of ball
		Vec2f newDirection = ball.getOrigin().minus(new Vec2f(position.x + width / 2, position.y));
		ball.setDirection(newDirection.normalize());

	}

	/**
	 * for the rest of our collisions the only difference is not taking damage
	 */
	@Override
	public void collideRight(Ball ball) {
		ball.deflectRight();
	}

	@Override
	public void collideLeft(Ball ball) {
		ball.deflectLeft();
	}

	@Override
	public void collideBottom(Ball ball) {
		ball.deflectDown();
	}

	/**
	 * getters and setters
	 */

	public float getSpeed() {
		return speed;
	}

	public void setSpeed(float speed) {
		this.speed = speed;
	}

}
