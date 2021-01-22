package com.brickBreaker.game;

import java.util.ArrayList;
import java.util.Random;
import java.util.Set;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

/**
 * @author Jose Magana
 *         Proffesor Kanemoto
 *         CPSC 39
 *         12.11.2020
 * 
 * @brief main game logic
 */

public class BrickBreaker extends ApplicationAdapter {
	/*************************
	 * LibGDX graphics stuff *
	 *************************/
	Batch batch;
	ShapeRenderer shapeRend;
	OrthographicCamera camera;
	BitmapFont font;

	/****************
	 * Game Objects *
	 ****************/
	Paddle player;
	Ball ball;
	Set<Brick> bricks;
	ArrayList<Ball> balls;

	Random rand;
	boolean paused;
	boolean dead;
	int score;

	/***************************
	 * Initializing everything *
	 **************************/
	@Override
	public void create() {
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 800, 480);
		shapeRend = new ShapeRenderer();
		batch = new SpriteBatch();
		font = new BitmapFont();

		rand = new Random();
		balls = new ArrayList<Ball>();
		player = new Paddle(0, 10);
		score = 0;
		// start with one ball in play
		balls.add(new Ball(new Vec2f(14, 14), new Vec2f(1, 3)));
		// get a randomly generated set of bricks
		bricks = Utils.generateLevel();
	}

	/******************
	 * Game functions *
	 ******************/

	// CLear the screen setting it to background color
	private void clearScreen() {
		Gdx.gl.glClearColor(29.f / 255, 31.f / 255, 33.f / 255, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
	}

	// Deal with player inputs
	private void handleInputs() {
		if (Gdx.input.isKeyPressed(Input.Keys.LEFT))
			player.moveLeft();
		if (Gdx.input.isKeyPressed(Input.Keys.RIGHT))
			player.moveRight();

		if (dead && Gdx.input.isKeyPressed(Input.Keys.R)) {
			score = 0;
			player.setHealth(1);
			bricks = Utils.generateLevel();
			dead = false;
		}

		// toggle pause on escape
		if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE))
			paused = !paused;
	}

	/**
	 * handle collisions and move balls
	 */
	private void updateBalls() {
		/**
		 * we have to remove dead balls outside of this loop otherwise we will raise an
		 * exception
		 */
		ArrayList<Ball> removedBalls = new ArrayList<Ball>();

		for (int i = 0; i < balls.size(); i++) {
			ball = balls.get(i);

			// handle the different kinds of collisions
			Utils.wallCollisions(ball);
			player.collision(ball);
			// collision with all other balls in play
			for (int j = i + 1; j < balls.size(); j++)
				ball.collision(balls.get(j));

			for (Brick b : bricks)
				b.collision(ball);

			// Add ball to remove list if its dead
			if (ball.getHealth() == 0)
				removedBalls.add(ball);
			// otherwise move it
			else
				ball.move();

		}

		// clean up dead balls
		balls.removeAll(removedBalls);
	}

	// clear any bricks that where destroyed
	private void updateBricks() {
		// pretty similar to the balls function
		ArrayList<Brick> removedBricks = new ArrayList<Brick>();

		for (Brick b : bricks) {
			if (b.getHealth() <= 0) {
				removedBricks.add(b);

				// one in four chance to spawn a new ball
				if (rand.nextInt(4) == 0) {
					// fly out in a random direction
					final float yDir = rand.nextFloat() * 2 - 1;
					final float xDir = rand.nextFloat() * 2 - 1;
					final Vec2f direction = new Vec2f(xDir, yDir).normalize();

					balls.add(new Ball(b.getPosition(), direction));
				}
			}
		}

		bricks.removeAll(removedBricks);

		// make a new level if this one is cleared
		if (bricks.size() == 0) {
			bricks = Utils.generateLevel();
			score++;
		}

	}

	// handle player "damage"
	private void updatePlayer() {
		if (balls.size() == 0) { // no balls in play
			if (player.health > 0) { // check if this is a lose condition
				balls.add(new Ball());
				player.damage();
			} else {
				dead = true;
			}
		}
	}

	// Draw Objects in scene
	private void drawScene() {
		shapeRend.setProjectionMatrix(camera.combined);
		shapeRend.begin(ShapeRenderer.ShapeType.Filled);

		player.draw(shapeRend);

		for (Ball b : balls)
			b.draw(shapeRend);
		for (Brick b : bricks)
			b.draw(shapeRend);

		shapeRend.end();

		// display everything
		camera.update();

	}

	// Display 'PAUSED' in the middle of the screen while paused
	void showPausedText() {
		batch.begin();

		final GlyphLayout layout = new GlyphLayout(font, "PAUSED");
		final float textX = Gdx.graphics.getWidth() / 2 - layout.width / 2;

		font.draw(batch, layout, textX, Gdx.graphics.getHeight() / 4);

		batch.end();
	}

	void showDeathScreen() {
		batch.begin();

		final GlyphLayout layout = new GlyphLayout(font,
				"Game Over!\nYou cleared: " + score + " Levels\nPress R to restart");
		final float textX = Gdx.graphics.getWidth() / 2 - layout.width / 2;

		font.draw(batch, layout, textX, Gdx.graphics.getHeight() / 2);

		batch.end();
	}

	/*************
	 * Main Loop *
	 *************/
	@Override
	public void render() {
		clearScreen();

		handleInputs();
		if (!paused && !dead) {
			updateBalls();
			updateBricks();
			updatePlayer();
		}

		if (dead) {
			showDeathScreen();
		} else {
			drawScene();
			if (paused)
				showPausedText();
		}
	}

	/************
	 * Clean up *
	 ************/
	@Override
	public void dispose() {
		shapeRend.dispose();
		batch.dispose();
	}
}
