package com.example.projetcir3;

import javafx.stage.Stage;

public class GameLoop {
    public static void start(Stage stage) {
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
}
