package project.main;

import javafx.application.Application;
import javafx.stage.Stage;
import project.menu.MainMenu;

import java.io.IOException;

/*
 * Classe principale chargée de lancer l'application et d'appeler le menu principal
 */

public class Main extends Application {

    public static boolean alive = true;

    // Démarre l'application
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

    // Arrêt de l'application
    @Override
    public void stop() {
        System.out.println("Application is closing, bye!");
        alive = false;
    }

    // Méthode main qui appelle la méthode launch() de JavaFX
    public static void main(String[] args) {
        launch();
    }
}