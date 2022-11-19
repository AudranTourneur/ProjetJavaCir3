package project.game.controller;

import javafx.animation.AnimationTimer;
import project.game.view.GameView;

public class GameLoopTimer extends AnimationTimer {
    GameView view;

    GameLoopTimer(GameView view) {
        this.view = view;
        System.out.println("Création d'un AnimationTimer");
    }

    long lastCall = System.nanoTime();
    double sumMs = 0.0;
    int frames = 1;

    final static int FPS_TARGET = 60;

    @Override
    public void handle(long now) {

        double diffMs = (now - lastCall) / 1_000_000.0;

        if (diffMs < ((double) 1000 / FPS_TARGET)) {
            // System.out.println("skip " + diffMs);
            return;
        }

        frames++;
        sumMs += diffMs;
        final double avgMs = sumMs / (double) frames;

        // System.out.println("Sum ms = " + sumMs);
        // System.out.println("Diff ms = " + diffMs);
        System.out.println("FPS = " + (1000 / avgMs) + "| avg delta ms " + avgMs);
        lastCall = now;
        // System.out.println("now : " + now);

        view.display(false);

    }
}