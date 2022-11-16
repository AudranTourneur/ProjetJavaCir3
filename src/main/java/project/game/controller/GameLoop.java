package project.game.controller;

import javafx.stage.Stage;
import project.Main;
import project.game.model.GameWorldModel;
import project.game.view.GameView;

public class GameLoop {
    public static void start(Stage stage) {
        // set title for the stage
        stage.setTitle("creating canvas");


        
        GameWorldModel model = new GameWorldModel();
        GameView view = new GameView(stage, model);

        GameStateContainer state = new GameStateContainer(model, view);

        InputController controller = new InputController(state);

        view.display();

        stage.getScene().setOnKeyPressed(controller::handle);

        stage.show();


        Thread thread = new Thread(){
            public void run(){
                System.out.println("Thread Running");

                long lastTime = System.nanoTime();

                while (Main.alive) {
                    long time = System.nanoTime();
                    int delta_time = (int) ((time - lastTime) / 1000000);
                    lastTime = time;
                    //System.out.println(delta_time);
                    model.update(delta_time);
                    view.display();
                    try {
                        Thread.sleep(1000 / 60);
                    } catch (InterruptedException exception) {
                        System.out.println(exception);
                    }

                }

                System.out.println("Game thread finished");
            }
        };

        thread.start();
    }
}
