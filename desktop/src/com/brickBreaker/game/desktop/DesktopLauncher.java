package com.brickBreaker.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.brickBreaker.game.BrickBreaker;

public class DesktopLauncher {
	public static void main(String[] arg) {
		// create new config for app
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		// set config propertise
		config.title = "Brick Breaker thing";
		config.width = 800;
		config.height = 480;
		// start app
		new LwjglApplication(new BrickBreaker(), config);
	}
}
