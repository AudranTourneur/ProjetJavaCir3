package project;

import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import project.game.controller.GameLoop;

public class SceneManager {

    private Stage main;

    public SceneManager(Stage main) {
        this.main = main;
    }

    public void switchToGame(){
        GameLoop.start(main);
    }
}
