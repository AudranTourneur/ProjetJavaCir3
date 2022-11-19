package project.game.controller;

import javafx.application.Platform;
import javafx.stage.Stage;
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
                        //System.out.println("thread heartbeat");
                    counter++;
                    long time = System.nanoTime();
                    int delta_time = (int) ((time - lastTime) / 1000000);
                    lastTime = time;
                    // System.out.println(delta_time);
                    model.update(delta_time);

                    //Platform.runLater(() -> {
                    //    view.display(false);
                    //});

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

        runGraphicsOperations(view);
    }

    static void runGraphicsOperations(GameView view) {
        final var loop = new GameLoopTimer(view);
        loop.start();
    }
}
