package com.example.projetcir3;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.util.Scanner;

public class GameView {
    Stage stage;
    GraphicsContext ctx;

    private void init() {
        String text = new Scanner(
                getClass().getResourceAsStream("game/map.txt"), "UTF-8").useDelimiter("\\A")
                .next();

        System.out.println("Test = " + text);


        BufferedReader bufReader = new BufferedReader(new StringReader(text));

        String line = null;

        int y = 0;
        try {
            while ((line = bufReader.readLine()) != null) {

                for (int x = 0; x < line.length(); x++) {
                    char ch = line.charAt(x);

                    System.out.println(x + " " + y + " " + ch);

                    map[x][y] = getTileFromChar(ch);
                }
                y++;
            }
        } catch (IOException exception) {
            System.out.println("Fatal error");
        }
    }
    GameView(Stage stage) {
       this.stage = stage;

        // create a canvas
        Canvas canvas = new Canvas();

        // set height and width
        canvas.setHeight(400);
        canvas.setWidth(400);

        GraphicsContext ctx =
                canvas.getGraphicsContext2D();

        this.ctx = ctx;

        // create a Group
        Group group = new Group(canvas);

        // create a scene
        Scene scene = new Scene(group, 400, 400);

        stage.show();

        // set the scene
        stage.setScene(scene);

        init();
    }
   GridTile[][] map = new GridTile[10][10];

    GridTile getTileFromChar(char ch) {
        switch (ch) {
            case '#':
                return GridTile.WALL;
            case '.':
                return GridTile.VOID;
            case '@':
                return GridTile.PLAYER_SPAWN;
            case '&':
                return GridTile.GHOST_SPAWN;
            default:
                return GridTile.VOID;
        }


        //throw new Exception("pas encore fait");
    }

     void display() {
         // graphics context

         // set fill for rectangle
         ctx.setFill(Color.PINK);
         ctx.fillRect(40, 40, 100, 100);


         // set fill for oval
         ctx.setFill(Color.BLUE);
         ctx.fillOval(30, 30, 70, 70);

         InputStream test =  getClass().getResourceAsStream("images/cat_image.png");
         assert test != null;
         Image img = new Image(test);

         ctx.drawImage(img, 100, 100, 100, 100);

         for (int i = 0; i < map.length; i++) {
             for (int j = 0; j < map.length; j++) {
                 if (map[j][i] == GridTile.WALL) {
                     drawWall(j, i);
                 }
             }
         }
     }

     void drawWall(int x, int y) {
        int wallSize = 40;
         // set fill for rectangle
         ctx.setFill(Color.RED);
         ctx.fillRect(x * wallSize, y * wallSize, wallSize, wallSize);

     }
}
