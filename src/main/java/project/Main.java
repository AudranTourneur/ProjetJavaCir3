package project;

import javafx.application.Application;
import javafx.stage.Stage;
import project.menu.MainMenu;

import java.io.IOException;

public class Main extends Application {

    public static boolean alive = true;

    @Override
    public void start(Stage stage) {

        System.out.println("Starting application");

        SceneManager manager = new SceneManager(stage);

        try {
            new MainMenu(manager).start(stage);
        } catch (IOException exception) {
            System.out.println("error");
        }

    }

    @Override
    public void stop() {
        System.out.println("Stage is closing");
        alive = false;
    }

    public static void main(String[] args) {
        launch();
    }
}