package com.brickBreaker.game;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

/**
 * @author Jose Magana
 * 
 * @brief Class used to represent a ball
 */

public class Ball {
	private Vec2f origin; // current center of ball
	private Vec2f direction; // direction ball is traveling
	private float speed; // how fast ball travels
	private float radius; // size of ball
	private int health; // ball dies when it touches bottom of screen

	/**
	 * Created a default ball object
	 */
	public Ball() {
		this.origin = new Vec2f(7.f, 7.f);
		this.direction = new Vec2f(1, 1).normalize();
		this.speed = 7.f;
		this.radius = 7.f;
		this.health = 1;
	}

	/**
	 * Created a new Ball object
	 * 
	 * @param origin    center of the ball
	 * @param direction the direction the ball is traveling
	 * @param speed     how fast the ball is traveling
	 * @param radius    radius of the ball
	 */
	public Ball(Vec2f origin, Vec2f direction, float speed, float radius) {
		this.origin = origin;
		this.direction = direction.normalize();
		this.speed = speed;
		this.radius = radius;
		this.health = 1;
	}

	/**
	 * Create a new ball object with default speed and radius
	 * 
	 * @param origin
	 * @param direction
	 */
	public Ball(Vec2f origin, Vec2f direction) {
		this();
		this.origin = origin;
		this.direction = direction.normalize();
	}

	/*
	 * move the ball one step in current direction
	 */
	public void move() {
		this.origin = this.origin.plus(direction.times(speed));
	}

	/**
	 * Helper methods for making the ball bounce
	 * 
	 * The calls to Math.abs ensure that the ball does not get trapped inside
	 * something and flip its direction over and over
	 */

	public void deflectUp() {
		direction.y = Math.abs(direction.y);
	}

	public void deflectDown() {
		direction.y = -1 * Math.abs(direction.y);
	}

	public void deflectLeft() {
		direction.x = -1 * Math.abs(direction.x);
	}

	public void deflectRight() {
		direction.x = Math.abs(direction.x);
	}

	/**
	 * Handle collision with another ball
	 * 
	 * @param otherBall ball that we're checking against
	 */
	public void collision(Ball otherBall) {
		final Float sumOfRadius = (radius + otherBall.getRadius());
		final Float distance = origin.minus(otherBall.getOrigin()).norm();

		if (distance <= sumOfRadius) {
			// collision occurred

			// vector pointing from center of other ball to this ball
			Vec2f newDirection = origin.minus(otherBall.getOrigin()).normalize();

			// have the balls fly in opposite directions
			this.direction = newDirection;
			otherBall.setDirection(newDirection.times(-1));
		}
	}

	/**
	 * draw the ball to the screen
	 * 
	 * @param rend ShapeRenderer used to draw
	 */
	public void draw(ShapeRenderer rend) {
		rend.circle(origin.x, origin.y, radius);
	}

	/**
	 * Getters and setters
	 */

	public Vec2f getOrigin() {
		return origin;
	}

	public void setOrigin(Vec2f origin) {
		this.origin = origin;
	}

	public Vec2f getDirection() {
		return direction;
	}

	public void setDirection(Vec2f direction) {
		this.direction = direction.normalize();
	}

	public float getSpeed() {
		return speed;
	}

	public void setSpeed(float velocity) {
		this.speed = velocity;
	}

	public float getRadius() {
		return radius;
	}

	public void setRadius(float radius) {
		this.radius = radius;
	}

	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = health;
	}

	public String toString() {
		return "<Ball> { origin:" + origin + ", Direction:" + direction + ", Speed: " + speed + ", Health: " + health
				+ " }";
	}
}
