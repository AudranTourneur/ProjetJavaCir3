package project.game.controller;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.util.Duration;
import project.Main;
import project.game.model.WorldModel;
import project.game.view.GameView;

//Controler la fenetre du jeu
public class GameLoop {

    public static void start(Stage stage) { // information de la page lorsque le bouton play est lancé
        // set title for the stage
        stage.setTitle("The Adventures of Pac-Cat");

        WorldModel model = new WorldModel();
        GameWindowController windowController = new GameWindowController(stage);
        GameView view = new GameView(windowController, model);

        GameStateContainer state = new GameStateContainer(model, view);

        InputController controller = new InputController(state);

        stage.getScene().addEventFilter(KeyEvent.KEY_PRESSED, (KeyEvent event) -> {
            controller.handle(event);
            event.consume();
        });

        stage.show();

        final int FPS_TARGET = 60;

        Thread thread = new Thread() {
            public void run() {
                int counter = 0;
                System.out.println("Thread Running");

                while (Main.alive) {

                    if (counter % 100 == 0)
                        counter++;

                    // model.update(delta_time);

                    try {
                        Thread.sleep(1000 / FPS_TARGET);
                    } catch (InterruptedException exception) {
                        exception.printStackTrace();
                    }

                }

                System.out.println("Game thread finished");
            }
        };

        thread.start();

        // runGraphicsOperations(view);
        initGameLoop(state);

        AudioController.playMusic();
    }

    // partie affichage/temps
    static long lastCall = System.nanoTime();
    static double sumMs = 0.0;
    static int frames = 1;

    static void initGameLoop(GameStateContainer state) {
        final Duration oneFrameAmt = Duration.millis(1000 / 60);
        final KeyFrame oneFrame = new KeyFrame(oneFrameAmt, actionEvent -> {
            state.model.update();

            long now = System.nanoTime();
            double diffMs = (now - lastCall) / 1_000_000.0;

            frames++;
            sumMs += diffMs;
            final double avgMs = sumMs / (double) frames;
            if (Configuration.FPS_COUNTER)
                System.out.println("FPS = " + (1000 / avgMs) + " | avg delta ms " + avgMs);
            lastCall = now;

            state.view.setModel(state.model);
            state.view.display();
        });

        final Timeline gameLoop = new Timeline();
        gameLoop.setCycleCount(Animation.INDEFINITE);
        gameLoop.getKeyFrames().add(oneFrame);
        gameLoop.playFromStart();
    }

}
