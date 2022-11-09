package com.example.projetcir3;

import javafx.scene.Scene;
import javafx.stage.Stage;

public class SceneManager {

    private Stage main;

    public SceneManager(Stage main) {
        this.main = main;
    }

    protected void switchToGame(String name){
        System.out.println("Manager appelé avec name = " + name);
        GameLoop.start(main);
        //main.setScene(screenMap.get(name));
    }
}
