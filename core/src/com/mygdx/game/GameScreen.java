package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.ArrayList;
import java.util.List;

public class GameScreen extends BaseScreen {

    SpriteBatch spriteBatch;
    Texture background, globoRojo, globoVerde, globoAzul;
    List<Globo> ListaGlobos;
    float tiempo;
    float tiempoJuego;
    float masGlobos = 3;
    float masDificultad = 5;
    int puntuacion = 0;
    float masVelocidad =5;

    BitmapFont bitmapFont;
    BitmapFont bitmapFont2;
    BitmapFont bitmapFont3;
    BitmapFont bitmapFont4;

    Color clickColor;
    String clickTexto;
    String accion;
    String tiempoString = Float.toString(tiempo);

    public GameScreen(MyGdxGame game) {
        super(game);
    }

    @Override
    public void show() {
        super.show();
        background = new Texture("fondo1.jpg");
        globoRojo = new Texture("ballon_red.png");
        globoVerde = new Texture("ballon_green.png");
        globoAzul = new Texture("ballon_blue.png");
        spriteBatch = new SpriteBatch();
        ListaGlobos = new ArrayList<>();
        ListaGlobos.add(new Globo());
        ListaGlobos.add(new Globo());
        ListaGlobos.add(new Globo());
        bitmapFont = new BitmapFont();
        bitmapFont2 = new BitmapFont();
        bitmapFont3 = new BitmapFont();
        bitmapFont4 = new BitmapFont();
        clickColor = getRandomColor();
        tiempo = 60;
        clickTexto = getRandomText();
        accion = getRandomAction();
    }

    private void update(float delta) {
        tiempo-=0.01f;

        for(Globo globo: ListaGlobos){
            globo.update(delta);
        }

        if(tiempoJuego > masGlobos) {
            ListaGlobos.add(new Globo(masVelocidad));

            masGlobos = (float) (tiempoJuego + Math.random()*2+0.3);
        }

        if(tiempoJuego > masDificultad){
            masDificultad = tiempoJuego + 5;

            masVelocidad = masVelocidad +5;
        }

        for(Globo globo: ListaGlobos) {
            globo.movimientoLateral(tiempoJuego);
        }

        if(Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
            int mouseY = Gdx.graphics.getHeight() - Gdx.input.getY();
            for(Globo globo: ListaGlobos) {

                if (circunferencia(Gdx.input.getX(), mouseY, globo.getX(), globo.getY()) < globo.size/2) {

                    globo.borrar = true;
                    if (accion.equals("C")) {

                        if (clickColor == Color.RED && globo.getColor() == Globo.Color.ROJO) {
                            puntuacion++;
                            tiempo++;
                        } else if (clickColor == Color.GREEN && globo.getColor() == Globo.Color.VERDE) {
                            puntuacion++;
                            tiempo++;
                        } else if (clickColor == Color.BLUE && globo.getColor() == Globo.Color.AZUL) {
                            puntuacion++;
                            tiempo++;
                        } else {
                            puntuacion--;
                            tiempo -= 3;
                        }
                        clickColor = getRandomColor();
                        clickTexto = getRandomText();
                        accion = getRandomAction();
                        break;
                    }else if(accion.equals("T")){
                        if(clickTexto.equals("VERDE") && globo.getColor() == Globo.Color.VERDE){
                            puntuacion++;
                            tiempo++;
                        }else if (clickTexto.equals("ROJO") && globo.getColor() == Globo.Color.ROJO) {
                            puntuacion++;
                            tiempo++;
                        } else if (clickTexto.equals("AZUL") && globo.getColor() == Globo.Color.AZUL) {
                            puntuacion++;
                            tiempo++;
                        } else {
                            puntuacion--;
                            tiempo -= 3;
                        }
                        clickColor = getRandomColor();
                        clickTexto = getRandomText();
                        accion = getRandomAction();
                        break;
                    }
                }
            }
        }
        ListaGlobos.removeIf(globo -> globo.borrar);

        if(tiempo<=0){
            setScreen(new GameOverScreen(game));
        }
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        tiempoJuego = tiempoJuego + delta;

        update(delta);
        tiempoString = Float.toString(tiempo);

        spriteBatch.begin();

        spriteBatch.draw(background, 0, 0, 640, 480);
        for(Globo globo: ListaGlobos) globo.render(spriteBatch);

        bitmapFont.setColor(new Color(Color.WHITE));
        bitmapFont.getData().setScale(3);
        bitmapFont.draw(spriteBatch, String.valueOf(puntuacion), 550f, 450f);

        bitmapFont2.setColor(new Color(clickColor));
        bitmapFont2.getData().setScale(2);
        bitmapFont2.draw(spriteBatch, clickTexto, 100f, 450f);

        bitmapFont4.setColor(new Color(Color.RED));
        bitmapFont4.getData().setScale(2);
        bitmapFont4.draw(spriteBatch, tiempoString, 280f,450f);

        spriteBatch.end();
    }

    float circunferencia(float mx, float my, float gx, float gy){

        return (float) Math.sqrt(Math.pow((mx - gx),2) + Math.pow((my-gy),2));
    }

    public Color getRandomColor(){
        int a = (int) (Math.random()*3+1);

        if (a==1){
            return Color.RED;
        }

        if(a==2){
            return Color.BLUE;
        }

        return Color.GREEN;
    }

    public String getRandomText(){
        int a = (int) (Math.random()*3+1);

        if (a==1){
            return "ROJO";
        }

        if(a==2){
            return "AZUL";
        }
        return "VERDE";
    }

    public String getRandomAction(){
        int a = (int) (Math.random()*2+1);

        if (a==1){
            return "T";
        }
        return "C";
    }

}
