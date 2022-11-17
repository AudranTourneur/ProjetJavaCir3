package project.game.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import project.Main;

public class GameWorldModel {
    public Player player;

    public List<Entity> entities = new ArrayList<>();

    public GridMap map = new GridMap();

    public GameWorldModel() {
        init();

    }

    void init() {
        // ajouter ghosts

        // --- MAP ---

        Scanner in = new Scanner(
                new Main().getClass().getResourceAsStream("game/map.txt"), "UTF-8").useDelimiter("\\A");

        String text = in.next();

        in.close();

        System.out.println("Test = " + text);

        BufferedReader bufReader = new BufferedReader(new StringReader(text));

        String line = null;
        int spawnX = 0;
        int spawnY = 0;
        try {
            int y = 0;
            while ((line = bufReader.readLine()) != null) {

                for (int x = 0; x < line.length(); x++) {
                    char ch = line.charAt(x);

                    // System.out.println(x + " " + y + " " + ch);

                    map.setAt(x, y, getTileFromChar(ch));
                    if (map.getAt(x, y) == GridTile.PLAYER_SPAWN) {
                        spawnX = x;
                        spawnY = y;
                    }

                    // map.validPositions[x][] (GridMap.STEP);
                }
                y++;
            }
        } catch (IOException exception) {
            System.out.println("Fatal error");
        }

        for (int x = 0; x < GridMap.SIZE; x++) {
            for (int y = 0; y < GridMap.SIZE; y++) {
                if (map.getAt(x, y) != GridTile.WALL) {
                    final int step = GridMap.STEP;
                    final int cx = x * 2 * step + step;
                    final int cy = y * 2 * step + step;
                    map.validPositions[cx][cy] = true;

                    for (Direction dir : Direction.values()) {
                        final int bigTargetX = x + dir.getX();
                        final int bigTargetY = y + dir.getY();

                        if (map.isPositionAccessible(new Position(bigTargetX, bigTargetY))) {
                            for (int i = 0; i < step; i++) {
                                for (int j = 0; j < step; j++) {
                                    final int tx = cx + dir.getX() * i;
                                    final int ty = cy + dir.getY() * j;
                                    map.validPositions[tx][ty] = true;
                                    System.out.println("Set " + tx + " " + ty + " = TRUE");
                                }
                            }
                        }
                    }
                }
            }
        }

        this.player = new Player(map);
        player.setSpawn(spawnX, spawnY);
        entities.add(player);
    }

    public void update(double deltaMs) {
        for (Entity e : entities) {
            e.move(deltaMs);
            // checkCollision();
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
        // throw new Exception("pas encore fait");
    }
}
