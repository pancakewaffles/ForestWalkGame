package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;


public class EndScreen implements Screen {
    private Game g;
    private Stage stage;
    GameController gameController;
    public EndScreen(Game game, GameController gameController){
        this.g = game;
        this.gameController = gameController;
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);
        //TextureAtlas atlas = new TextureAtlas(Gdx.files.internal("core\\assets\\skin\\glassy-ui.atlas"));
        Skin skin  = new Skin(Gdx.files.internal("core\\assets\\skin\\glassy\\skin\\glassy-ui.json"));

        TextButton startBtn = new TextButton("Click here to start", skin);
        startBtn.setPosition(300, 300);
        startBtn.setSize(300 ,60);

        stage.addActor(startBtn);
    }
    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0,0,0,0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(delta);
        stage.draw();

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}