package com.example.projetcir3;

import com.example.projetcir3.Player;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class GameWorldModel {
    public Player player;

    public List<Entity> entities = new ArrayList<>();

    public GridTile[][] map = new GridTile[10][10];

    public GameWorldModel() {
        String text = new Scanner(
                getClass().getResourceAsStream("game/map.txt"), "UTF-8").useDelimiter("\\A")
                .next();

        System.out.println("Test = " + text);

        init();

    }

    void init() {



        // ajouter ghosts


        // --- MAP ---

        String text = new Scanner(
                getClass().getResourceAsStream("game/map.txt"), "UTF-8").useDelimiter("\\A")
                .next();

        System.out.println("Test = " + text);


        BufferedReader bufReader = new BufferedReader(new StringReader(text));

        String line = null;
        int spawnX=0;
        int spawnY=0;
        int y = 0;
        try {
            while ((line = bufReader.readLine()) != null) {

                for (int x = 0; x < line.length(); x++) {
                    char ch = line.charAt(x);

                    System.out.println(x + " " + y + " " + ch);

                    map[x][y] = getTileFromChar(ch);
                    if(map[x][y]==GridTile.PLAYER_SPAWN){
                        spawnX=x;
                        spawnY=y;
                    }

                }
                y++;
            }
        } catch (IOException exception) {
            System.out.println("Fatal error");
        }
        this.player=new Player(map);
        player.setSpawn(spawnX,spawnY);
        entities.add(player);
    }

    void update(double deltaMs) {
        for (Entity e : entities) {
            e.move(deltaMs);
            //checkCollision();
        }
    }


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

    /*
    //Regarde si l'entite mis en parametre touche un mur ou pas
    void checkCollision(){
        System.out.println("Current grid position"+ player.gridPosition);
        System.out.println("Current position"+ player.position);
        System.out.println(map[(int)player.gridPosition.y][(int)player.gridPosition.x]);
        if(map[(int) player.gridPosition.y][(int)player.gridPosition.x]==GridTile.WALL){
            player.direction=null;
        }
    }*/
}
