package com.quickbite.tothetop;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.quickbite.tothetop.screens.GameScreen;

public class MyGame extends Game {
	static public OrthographicCamera camera;
	static public SpriteBatch batch;

	static public Viewport cameraViewport;

	static public ShapeRenderer shapeRenderer;

	@Override
	public void create () {
		batch = new SpriteBatch();
		camera = new OrthographicCamera(480, 800);
        shapeRenderer = new ShapeRenderer();

        cameraViewport = new ScreenViewport(camera);

		setScreen(new GameScreen());
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		super.render();

		camera.update();
	}

	@Override
	public void dispose () {
		batch.dispose();
	}

	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
		cameraViewport.update(width, height, true); //Only update the viewport and not the camera
	}
}
