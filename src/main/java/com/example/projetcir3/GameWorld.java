package com.example.projetcir3;

import java.io.InputStream;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class GameWorld {
    Player player;

    GameWorld() {
        String text = new Scanner(
                getClass().getResourceAsStream("game/map.txt"), "UTF-8").useDelimiter("\\A")
                .next();

        System.out.println("Test = " + text);

    }
}
