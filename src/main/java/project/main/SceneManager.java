package project.main;

import javafx.stage.Stage;
import project.game.controller.GameLoop;

// Passe du menu principal au jeu

public class SceneManager {

    private Stage main;

    public SceneManager(Stage main) {
        this.main = main;
    }

    // Change de scène
    public void switchToGame(){
        GameLoop.start(main);
    }
}
