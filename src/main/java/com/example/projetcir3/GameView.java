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
import java.util.HashMap;
import java.util.Scanner;

public class GameView {
    Stage stage;
    GraphicsContext ctx;
    GameWorldModel world;
    int TILE_SIZE = 40;
    HashMap<String, Image> spriteMap = new HashMap<>();

    public GameView(Stage stage, GameWorldModel world) {
       this.stage = stage;
       this.world = world;

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
        load();
        // set the scene
        stage.setScene(scene);
    }


    public void display() {

        /*
         ctx.setFill(Color.PINK);
         ctx.fillRect(40, 40, 100, 100);


         // set fill for oval
         ctx.setFill(Color.BLUE);
         ctx.fillOval(30, 30, 70, 70);
    */
        drawMap();
        drawPlayer();


    }
    void drawMap(){
        for (int i = 0; i < world.map.length; i++) {
            for (int j = 0; j < world.map.length; j++) {
                if (world.map[j][i] == GridTile.WALL) {
                    drawWall(j, i);
                }
                if(world.map[j][i] == GridTile.VOID){
                    drawVoid(j,i);
                }
            }
        }
    }

     void drawWall(int x, int y) {
         // set fill for rectangle
         ctx.setFill(Color.RED);
         ctx.fillRect(x * TILE_SIZE, y * TILE_SIZE, TILE_SIZE, TILE_SIZE);

     }

     void drawVoid(int x, int y){
         ctx.setFill(Color.WHITE);
         ctx.fillRect(x * TILE_SIZE, y * TILE_SIZE, TILE_SIZE, TILE_SIZE);
     }

     void drawPlayer(){
         Image img = spriteMap.get("player");
         ctx.drawImage(img, world.player.position.x * TILE_SIZE, world.player.position.y * TILE_SIZE, TILE_SIZE, TILE_SIZE);
         //System.out.println("We have drawn player");
     }

    //Fonction va charger tout nos sprite dans le futur
    void load() {
        InputStream pacchat =  getClass().getResourceAsStream("images/cat_image.png");
        assert pacchat != null;
        Image img = new Image(pacchat);

        spriteMap.put("player", img);

    }

}