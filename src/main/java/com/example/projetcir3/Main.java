package com.example.projetcir3;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

public class Main extends Application {
    /*
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Hello 2!");
        stage.setScene(scene);

        stage.show();
    }
     */

    @Override
    public void start(Stage stage) {

        //GameWorld world = new GameWorld();


        // set title for the stage
        stage.setTitle("creating canvas");


        GameWorldModel world = new GameWorldModel();
        InputController controller = new InputController(world);

        GameView view = new GameView(stage, world);
        view.display();

        stage.getScene().setOnKeyPressed(controller::handle);

        stage.show();


        Thread thread = new Thread(){
            public void run(){
                System.out.println("Thread Running");

                long last_time = System.nanoTime();

                boolean running = true;

                while (running) {
                    long time = System.nanoTime();
                    int delta_time = (int) ((time - last_time) / 1000000);
                    last_time = time;
                    //System.out.println(delta_time);
                    world.update(delta_time);
                    view.display();
                    try {
                        Thread.sleep(1000 / 60);
                    } catch (InterruptedException exception) {
                        System.out.println(exception);
                    }

                }
            }
        };

        thread.start();

    }

    public static void main(String[] args) {
        launch();
    }
}