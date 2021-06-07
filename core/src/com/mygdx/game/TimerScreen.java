package com.mygdx.game;

public class TimerScreen extends BaseScreen{

    float tiempoJuego;
    float duracionAlarma = 1;
    float alarma;

    public TimerScreen(MyGdxGame game) {
        super(game);
    }

    @Override
    public void render(float delta) {
        super.render(delta);

        tiempoJuego = tiempoJuego + delta;
        if (tiempoJuego > alarma){
            alarma = tiempoJuego + duracionAlarma;
        }
    }
}
