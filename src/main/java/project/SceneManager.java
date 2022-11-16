package project;

import javafx.stage.Stage;
import project.game.controller.GameLoop;

public class SceneManager {

    private Stage main;

    public SceneManager(Stage main) {
        this.main = main;
    }

    public void switchToGame(String name){
        System.out.println("Manager appelé avec name = " + name);
        GameLoop.start(main);
        //main.setScene(screenMap.get(name));
    }
}
