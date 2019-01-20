package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;


public class GameScreen implements Screen {
    private Game g;
    private Stage stage;
    public GameController gameController;
    TextButton button1;
    TextButton button2;
    TextButton button3;
    TextButton button4;
    TextButton healthButton;
    TextButton locationButton;
    Image background;


    String mText;
    HashMap<TextButton, AI> AIList = new HashMap<TextButton, AI>();

    private BitmapFont font = new BitmapFont();
    private SpriteBatch batch = new SpriteBatch();
    protected int state = 2; // -1 for dead end, 1 for escape, 2 for start
    protected String[] batTexts = new String[4];
    private String mainImg = "core\\assets\\Pictures\\main.png";
    private String deadEndImg = "core\\assets\\Pictures\\deadendscreen.png";
    private String escapeImg = "core\\assets\\Pictures\\winscreen.png";

    TextureRegionDrawable deadendTexture = new TextureRegionDrawable(new TextureRegion(new Texture(deadEndImg)));
    TextureRegionDrawable mainTexture = new TextureRegionDrawable(new TextureRegion(new Texture(mainImg)));
    TextureRegionDrawable escapeTexture = new TextureRegionDrawable(new TextureRegion(new Texture(escapeImg)));

    private String bluebat = "core\\assets\\Pictures\\bluebat.png";
    private String yellowbat = "core\\assets\\Pictures\\yellowbat.png";
    private String pinkbat = "core\\assets\\Pictures\\pinkbat.png";
    private String greenbat = "core\\assets\\Pictures\\greenbat.png";

    private String helloText = "These four bats seem like they want to help guide me out of the forest, but they are all pointing in different directions. Which bat should I trust?";
    private String okayText = "I followed my chosen bat until we hit another crossroads. Should I continue following the same bat?";
    private String deadEndText = "I arrived at an impassable terrain. This does not look good.";
    private String escapeText = "I followed my chosen bat until we saw a piercing light through the trees. Excited, I ran forward. In the horizon I could see my city’s glittering skyline. Escape!";


    public GameScreen(Game game, GameController gameController){
        this.g = game;
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);
        this.gameController = gameController;
        font.setColor(Color.WHITE);

    }
    @Override
    public void show() {
        Texture backg;
        if (state == 0 || state == 2) {
            backg = new Texture(Gdx.files.internal(mainImg));
        } else if (state == 1) {
            backg = new Texture(Gdx.files.internal(escapeImg));
        } else {
            backg = new Texture(Gdx.files.internal(deadEndImg));
        }
        background = new Image(backg);
        background.setSize(stage.getWidth(), stage.getHeight() * 0.8f);
        background.setPosition(Gdx.graphics.getWidth()/35, Gdx.graphics.getHeight()/4.5f);
        //background.setAlign(Align.top);
        System.out.println(background.getX());
        System.out.println(background.getY());
        stage.addActor(background);


        Table table = new Table();
        table.setDebug(true);
        table.setWidth(stage.getWidth());
        table.setHeight(stage.getHeight()*0.2f);

        Skin skin = new Skin(Gdx.files.internal("core\\assets\\skin\\plain-james\\skin\\plain-james-ui.json"));
        BitmapFont font = new BitmapFont();

        Drawable blueDrawable = new TextureRegionDrawable(new Texture(Gdx.files.internal(bluebat)));
        TextButton.TextButtonStyle blueButtonStyle = new TextButton.TextButtonStyle(blueDrawable, blueDrawable, blueDrawable, font);
        final TextButton button1 = new TextButton("", blueButtonStyle);
        AIList.put(button1, gameController.bots[0]);
        button1.getLabelCell().padTop(10);
        button1.getLabelCell().padLeft(10);
        button1.getLabelCell().padRight(10);
        button1.getLabel().setWrap(true);

        Drawable yellowDrawable = new TextureRegionDrawable(new Texture(Gdx.files.internal(yellowbat)));
        TextButton.TextButtonStyle yellowButtonStyle = new TextButton.TextButtonStyle(yellowDrawable, yellowDrawable, yellowDrawable, font);
        final TextButton button2 = new TextButton("", yellowButtonStyle);
        AIList.put(button2, gameController.bots[1]);
        button2.getLabelCell().padTop(10);
        button2.getLabelCell().padLeft(10);
        button2.getLabelCell().padRight(10);
        button2.getLabel().setWrap(true);

        Drawable pinkDrawable = new TextureRegionDrawable(new Texture(Gdx.files.internal(pinkbat)));
        TextButton.TextButtonStyle pinkButtonStyle = new TextButton.TextButtonStyle(pinkDrawable, pinkDrawable, pinkDrawable, font);
        final TextButton button3 = new TextButton("", pinkButtonStyle);
        AIList.put(button3, gameController.bots[2]);
        button3.getLabelCell().padTop(10);
        button3.getLabelCell().padLeft(10);
        button3.getLabelCell().padRight(10);
        button3.getLabel().setWrap(true);

        Drawable greenDrawable = new TextureRegionDrawable(new Texture(Gdx.files.internal(greenbat)));
        TextButton.TextButtonStyle greenButtonStyle = new TextButton.TextButtonStyle(greenDrawable, greenDrawable, greenDrawable, font);
        final TextButton button4 = new TextButton("", greenButtonStyle);
        AIList.put(button4, gameController.bots[3]);
        button4.getLabelCell().padTop(10);
        button4.getLabelCell().padLeft(10);
        button4.getLabelCell().padRight(10);
        button4.getLabel().setWrap(true);

        button1.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event,float x, float y){
                update(button1);
            }
        });

        button2.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event,float x, float y){
                update(button2);
            }
        });

        button3.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event,float x, float y){
                update(button3);
            }
        });

        button4.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event,float x, float y){
                update(button4);
            }
        });

        healthButton = new TextButton("100",skin);
        healthButton.setPosition(stage.getWidth()*0.9f, stage.getHeight()*0.9f);
        stage.addActor(healthButton);

        locationButton = new TextButton("Location: XX",skin);
        locationButton.setPosition(stage.getWidth()*0.0f, stage.getHeight()*0.9f);
        stage.addActor(locationButton);


        table.add(button1).width(Value.percentWidth(.25F, table)).height(Value.percentHeight(1F, table));
        table.add(button2).width(Value.percentWidth(.25F, table)).height(Value.percentHeight(1F, table));
        table.add(button3).width(Value.percentWidth(.25F, table)).height(Value.percentHeight(1F, table));
        table.add(button4).width(Value.percentWidth(.25F, table)).height(Value.percentHeight(1F, table));

//        button1.getLabel().setFontScale(0.5f, 1.0f);
//        button2.getLabel().setFontScale(0.5f, 1.0f);
//        button3.getLabel().setFontScale(0.5f, 1.0f);
//        button4.getLabel().setFontScale(0.5f, 1.0f);
        stage.addActor(table);
        updateText();
    }
    public void update(TextButton buttonPassed){
        AI ai = AIList.get(buttonPassed);
        gameController.update(ai.getRecommendedPath());
        updateText();
    }
    public void updateText(){
        for(TextButton button : AIList.keySet()){
            AI ai_t = AIList.get(button);
            double conf = ai_t.getProbability();
            String batTexts;
            if (conf <= 25) {
                batTexts = "I don't really know the way but just give me a chance!";
            } else if (conf <= 50) {
                batTexts = "I'm not certain of the way but I have a few hunches!";
            } else if (conf <= 75) {
                batTexts = "I'm fairly sure of the way!";
            } else {
                batTexts = "I definitely know where to go! Follow me!";
            }
            if (gameController.isExitNode()){
                batTexts = "We are out!";
            }
            if (gameController.isDead || gameController.isExitNode()){
                ai_t.recommendedPath = null;
            }
            button.setText(batTexts);
        }
        healthButton.setText(String.valueOf(gameController.health));
        locationButton.setText("Location: " + String.valueOf(gameController.playerPosition));
        if (gameController.isExitNode()){
            state = 1;
            background.setDrawable(escapeTexture);
        }
        else if (gameController.isDeadEnd()){
            background.setDrawable(deadendTexture);
            state = -1;
        }else if (gameController.isDead){
            state = 3;
        } else {
            background.setDrawable(mainTexture);
            state = 0;
        }


    }
    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0f, 0f, 0f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();

        batch.begin();

        if (state == 2) {
            mText = helloText;
            //state = 0;
        } else if (state == 0) {
            mText = okayText;
        } else if (state == 1) {
            mText = escapeText;
        } else if (state == 3) {
            mText = "We wandered away for too long and starved";
        } else {
            mText = deadEndText;
        }
        font.draw(batch, mText, 10, (int) (stage.getHeight() * 0.2) + 50, stage.getWidth() - 20, 10, true);

        batch.end();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
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