package project.game.controller;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.stage.Stage;
import javafx.util.Duration;
import project.Main;
import project.game.model.WorldModel;
import project.game.view.GameView;

public class GameLoop {
    public static void start(Stage stage) {
        // set title for the stage
        stage.setTitle("The Adventures of Pac-Cat");

        WorldModel model = new WorldModel();
        GameView view = new GameView(stage, model);

        GameStateContainer state = new GameStateContainer(model, view);

        InputController controller = new InputController(state);

        stage.getScene().setOnKeyPressed(controller::handle);

        stage.show();

        final int FPS_TARGET = 60;

        Thread thread = new Thread() {
            public void run() {
                int counter = 0;
                System.out.println("Thread Running");

                long lastTime = System.nanoTime();

                while (Main.alive) {

                    if (counter % 100 == 0)
                        // System.out.println("thread heartbeat");
                        counter++;
                    long time = System.nanoTime();
                    int delta_time = (int) ((time - lastTime) / 1000000);
                    lastTime = time;
                    // System.out.println(delta_time);
                    model.update(delta_time);

                    // Platform.runLater(() -> {
                    // view.display(false);
                    // });

                    try {
                        Thread.sleep(1000 / FPS_TARGET);
                    } catch (InterruptedException exception) {
                        System.out.println(exception);
                    }

                }

                System.out.println("Game thread finished");
            }
        };

        thread.start();

        // runGraphicsOperations(view);
        initGameLoop(view);
    }

    static long lastCall = System.nanoTime();
    static double sumMs = 0.0;
    static int frames = 1;

    static void initGameLoop(GameView view) {
        final Duration oneFrameAmt = Duration.millis(1000 / 60);
        final KeyFrame oneFrame = new KeyFrame(oneFrameAmt, actionEvent -> {
            long now = System.nanoTime();
            double diffMs = (now - lastCall) / 1_000_000.0;

            frames++;
            sumMs += diffMs;
            final double avgMs = sumMs / (double) frames;
            //System.out.println("FPS = " + (1000 / avgMs) + "| avg delta ms " + avgMs);
            lastCall = now;

            view.display(false);
        });

        final Timeline gameLoop = new Timeline();
        gameLoop.setCycleCount(Animation.INDEFINITE);
        gameLoop.getKeyFrames().add(oneFrame);
        gameLoop.playFromStart();
    }

}
