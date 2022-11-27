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

// Boucle de jeu principale chargée de mettre à jour le modèle et la vue tous les 60eme de seconde

public class GameLoop {

    // Intialisation de la fenêtre
    public static void start(Stage stage) { 
        stage.setTitle("The Adventures of Pac-Cat");

        // Initialisation MVC
        WorldModel model = new WorldModel();
        GameWindowController windowController = new GameWindowController(stage);
        GameView view = new GameView(windowController, model);
        GameStateContainer state = new GameStateContainer(model, view);
        InputController controller = new InputController(state);

        // Redirige les évènements clavier vers le contrôleur InputController
        stage.getScene().addEventFilter(KeyEvent.KEY_PRESSED, (KeyEvent event) -> {
            controller.handle(event);
            event.consume();
        });

        stage.show();

        initGameLoop(state);
        AudioController.playMusic();
    }

    // Démarre la boucle de jeu, la fonctionnalité Timeline de JavaFX est utilisée pour obtenir une cadence stable
    static void initGameLoop(GameStateContainer state) {
        final Duration oneFrameAmt = Duration.millis(1000 / 60);
        final KeyFrame oneFrame = new KeyFrame(oneFrameAmt, actionEvent -> {
            state.model.update();

            state.view.setModel(state.model);
            state.view.display();
        });

        final Timeline gameLoop = new Timeline();
        gameLoop.setCycleCount(Animation.INDEFINITE);
        gameLoop.getKeyFrames().add(oneFrame);
        gameLoop.playFromStart();
    }

}
