package com.example.projetcir3;

import com.example.projetcir3.Player;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class GameWorldModel {
    public Player player = new Player();

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
        entities.add(player);


        // ajouter ghosts


        // --- MAP ---

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

    void update(double deltaMs) {
        for (Entity e : entities) {
            e.move(deltaMs);
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
}
