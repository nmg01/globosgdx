package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class LoadScreen extends BaseScreen {

    Texture fondo = new Texture("fondo3.jpg");
    SpriteBatch  spriteBatch = new SpriteBatch();;
    private boolean jugando = false;

    public LoadScreen(MyGdxGame game) {
        super(game);
    }

    Stage stage;

    @Override
    public void show() {
        // Boton START

        ImageButton.ImageButtonStyle buttonStartStyle = new ImageButton.ImageButtonStyle();
        buttonStartStyle.up = new TextureRegionDrawable(new TextureRegion(new Texture("button_start_up.png")));
        buttonStartStyle.over = new TextureRegionDrawable(new TextureRegion(new Texture("button_start_over.png")));

        ImageButton buttonStart = new ImageButton(buttonStartStyle);
        buttonStart.setPosition(280, 200);
        buttonStart.setSize(24*3, 10*3);
        buttonStart.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if (!jugando){
                    setScreen(new GameScreen(game));
                    jugando=true;
                    return true;
                }else {
                    return false;
                }

            }
        });

        // Boton QUIT
        ImageButton.ImageButtonStyle buttonQuitStyle = new ImageButton.ImageButtonStyle();
        buttonQuitStyle.up = new TextureRegionDrawable(new TextureRegion(new Texture("button_quit_up.png")));
        buttonQuitStyle.over = new TextureRegionDrawable(new TextureRegion(new Texture("button_quit_over.png")));

        ImageButton buttonQuit = new ImageButton(buttonQuitStyle);
        buttonQuit.setPosition(280, 160);
        buttonQuit.setSize(24*3, 10*3);
        buttonQuit.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if (!jugando) {
                    Gdx.app.exit();
                    System.exit(0);
                    return true;
                }else {
                    return false;
                }
            }
        });

        Gdx.input.setInputProcessor(stage = new Stage());
        stage.addActor(buttonStart);
        stage.addActor(buttonQuit);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);



        spriteBatch.begin();
        spriteBatch.draw(fondo, 0, 0, 640, 480);
        spriteBatch.end();
        stage.act();
        stage.draw();
    }
}
