package com.example.projetcir3;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.InputStream;

public class HelloApplication extends Application {
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


        GameWorld world = new GameWorld();


        Position pos = new Position(3, 4);

        System.out.println("Ma position : " + pos);

        // set title for the stage
        stage.setTitle("creating canvas");

        // create a canvas
        Canvas canvas = new Canvas();

        // set height and width
        canvas.setHeight(400);
        canvas.setWidth(400);

        // graphics context
        GraphicsContext ctx =
                canvas.getGraphicsContext2D();

        // set fill for rectangle
        ctx.setFill(Color.PINK);
        ctx.fillRect(40, 40, 100, 100);

        // set fill for rectangle
        ctx.setFill(Color.RED);
        ctx.fillRect(20, 20, 70, 70);

        // set fill for oval
        ctx.setFill(Color.BLUE);
        ctx.fillOval(30, 30, 70, 70);

        InputStream test =  getClass().getResourceAsStream("images/cat_image.png");
        assert test != null;
        Image img = new Image(test);

        ctx.drawImage(img, 100, 100, 100, 100);

        // create a Group
        Group group = new Group(canvas);

        // create a scene
        Scene scene = new Scene(group, 400, 400);

        // set the scene
        stage.setScene(scene);

        final int[] x = {100};
        final int[] y = {100};

        final Player pacman = new Player();

        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                InputController.handle(event);
            }
        });

        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                System.out.println("key pressed ici : " + event);
                ctx.clearRect(0, 0, 400, 400);

                if (event.getCode() == KeyCode.RIGHT)
                    x[0]++;

                if (event.getCode() == KeyCode.LEFT)
                    x[0]--;

                if (event.getCode() == KeyCode.DOWN)
                    y[0]++;

                if (event.getCode() == KeyCode.UP)
                    y[0]--;


                ctx.drawImage(img, x[0], y[0], 100, 100);
            }
        });

        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}