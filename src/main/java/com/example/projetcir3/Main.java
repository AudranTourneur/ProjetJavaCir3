package com.example.projetcir3;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage stage) {

        SceneManager manager = new SceneManager(stage);

        try {
            Scene mainMenuScene = new MainMenu(manager).start(stage);
        } catch (IOException exception) {
            System.out.println("error");
        }


    }

    public static void main(String[] args) {
        launch();
    }
}