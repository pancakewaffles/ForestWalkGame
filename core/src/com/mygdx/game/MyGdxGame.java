package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class MyGdxGame extends Game {
	SpriteBatch batch;
	Texture img;
	public BitmapFont font;
	public final static int START = 0;
	public final static int GAME = 1;
	public final static int END = 2;
	public OrthographicCamera camera;
	public GameController gameController;

	private StartScreen startScreen;
	private GameScreen gameScreen;
	private EndScreen endScreen;

	
	@Override
	public void create () {
		batch = new SpriteBatch();
		font = new BitmapFont();
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 480, 720);
		gameController = new GameController();
		this.setScreen(new StartScreen(this));

	}

	@Override
	public void render () {
//		Gdx.gl.glClearColor(1, 0, 0, 1);
//		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
//		batch.begin();
//		batch.draw(img, 0, 0);
//		batch.end();
		super.render();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		font.dispose();
	}

	public void changeScreen(int screen){
		switch(screen){
			case START:
				if(startScreen == null) startScreen = new StartScreen(this); // added (this)
				this.setScreen(startScreen);
				break;
			case GAME:
				if(gameScreen == null) gameScreen = new GameScreen(this, gameController); // added (this)
				this.setScreen(gameScreen);
				break;
			case END:
				if(endScreen == null) endScreen = new EndScreen(this, gameController); //added (this)
				this.setScreen(endScreen);
				break;
		}
	}
}
