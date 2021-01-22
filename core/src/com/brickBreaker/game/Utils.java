package com.brickBreaker.game;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import com.badlogic.gdx.Gdx;

/**
 * @author Jose Magana
 *
 * @brief Class with methods to handle various collision checks
 */

public class Utils {

	/**
	 * Implementation of:
	 * https://en.wikipedia.org/wiki/Distance_from_a_point_to_a_line#Line_defined_by_two_points
	 * 
	 * If the distance from the center of a ball to a wall is less than the size of
	 * its radius we have a collision
	 * 
	 * @param a point on a line
	 * @param b different point on the line
	 * @param p point we are computing distance for
	 * @return distance from point to line
	 */

	public static float linePointDistance(Vec2f a, Vec2f b, Vec2f p) {
		final float deltaY = a.y - b.y;
		final float deltaX = b.x - a.x;
		final float numerator = Math.abs(deltaY * p.x + deltaX * p.y + a.x * b.y - b.x * a.y);
		final float denominator = (float) Math.sqrt(deltaX * deltaX + deltaY * deltaY);

		return numerator / denominator;
	}

	/**
	 * Detect and handle collision with walls
	 * 
	 * @param ball the ball we're dealing with
	 */
	public static void wallCollisions(Ball ball) {
		final Vec2f origin = ball.getOrigin();
		final float radius = ball.getRadius();

		if (origin.x + radius >= Gdx.graphics.getWidth())
			ball.deflectLeft();// bounce off right side
		else if (origin.x - radius <= 0)
			ball.deflectRight(); // bounce off left side
		else if (origin.y + radius >= Gdx.graphics.getHeight())
			ball.deflectDown(); // bounce off ceiling
		else if (origin.y - radius <= 0)
			ball.setHealth(0); // destroy ball if we hit the floor

	}

	/**
	 * Generate bricks at random spots within an area
	 * 
	 * @return a set of bricks
	 */
	public static Set<Brick> generateLevel() {
		Set<Brick> level = new HashSet<Brick>();
		Random rand = new Random();

		// dimensions of bricks
		final float brickWidth = 30;
		final float brickHeight = 10;
		final float brickPadding = 15;
		final float sidePadding = 2 * brickWidth;
		// the bottom edge from which bricks can spawn
		final float bottom = 200;

		for (float y = bottom; y < Gdx.graphics.getHeight() - brickPadding; y += brickHeight + brickPadding) {
			for (float x = sidePadding; x < Gdx.graphics.getWidth() - sidePadding; x += brickWidth + brickPadding) {
				// essentially 2/3 times we will make a brick
				if (rand.nextInt(3) > 0)
					level.add(new Brick(new Vec2f(x, y), brickWidth, brickHeight, 1));
			}
		}

		return level;
	}
}
