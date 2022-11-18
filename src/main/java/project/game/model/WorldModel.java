package project.game.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;

import project.Main;

public class WorldModel {
    public Player player;

    public int score;
    private int compteur;   //compteur des update

    public List<Entity> entities = new ArrayList<>();

    public GridMap map = new GridMap();

    public HashSet<IntPosition> foods = new HashSet<>();

    public boolean speedX2 = false;

    public WorldModel() {
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

        for (int x = 0; x < GridMap.NUMBER_OF_TILES; x++) {
            for (int y = 0; y < GridMap.NUMBER_OF_TILES; y++) {
                if (map.getAt(x, y) != GridTile.WALL) {
                    final int step = GridMap.STEP;
                    final int cx = x * 2 * step + step;
                    final int cy = y * 2 * step + step;
                    map.validPositions[cx][cy] = true;

                    for (Direction dir : Direction.values()) {
                        final int bigTargetX = x + dir.getX();
                        final int bigTargetY = y + dir.getY();

                        if (map.isPositionAccessible(new FloatPosition(bigTargetX, bigTargetY))) {
                            for (int i = 0; i < step+1; i++) {
                                for (int j = 0; j < step+1; j++) {
                                    final int tx = cx + dir.getX() * i;
                                    final int ty = cy + dir.getY() * j;
                                    map.validPositions[tx][ty] = true;
                                    //System.out.println("Set " + tx + " " + ty + " = TRUE");
                                }
                            }
                        }
                    }
                }
            }
        }

        this.player = new Player(this);
        player.setSpawn(spawnX, spawnY);
        entities.add(player);

        FoodHandler.generateFood(this, 20);
    }


    ProjectileHandler projectileHandler = new ProjectileHandler(this);

    public void update(double deltaMs) {
        compteur++;

        ArrayList<Entity> toDelete = new ArrayList<>();

        for (Entity e : entities) {
            e.move(deltaMs);

            if (e.markedForDeletion) {
                toDelete.add(e);
            }
        }
        FoodHandler.eatFood(this,(compteur%100==0));

        for (Entity e : toDelete) {
            entities.remove(e);
        }


        projectileHandler.manageProjectileSpawn();
        projectileHandler.manageProjectileCollisions();
        
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
