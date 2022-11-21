package project.game.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

import project.Main;
import project.game.view.GameView;

public class WorldModel {
    public Player player;

    public int score;
    private int currentTick; // compteur des update

    public List<Entity> entities = new ArrayList<>();

    public GridMap map = new GridMap();

    public HashSet<IntPosition> foods = new HashSet<>();

    public WorldModel() {
        init();
    }

    void init() {
        // ajouter ghosts

        // --- MAP ---

        Scanner in = new Scanner(
                new Main().getClass().getResourceAsStream("game/pacmap.txt"), "UTF-8").useDelimiter("\\A");

        String text = in.next();

        in.close();

        System.out.println("Test = " + text);

        BufferedReader bufReader = new BufferedReader(new StringReader(text));

        String line = null;
        int PlayerSpawnX = 0;
        int PlayerSpawnY = 0;
        int GhostSpawnX = 0;
        int GhostSpawnY = 0;
        try {
            int y = 0;
            while ((line = bufReader.readLine()) != null) {

                for (int x = 0; x < line.length(); x++) {
                    char ch = line.charAt(x);

                    map.setAt(x, y, getTileFromChar(ch));
                    if (map.getAt(x, y) == GridTile.PLAYER_SPAWN) {
                        PlayerSpawnX = x;
                        PlayerSpawnY = y;
                    } else if (map.getAt(x, y) == GridTile.GHOST_SPAWN) {
                        GhostSpawnX = x;
                        GhostSpawnY = y;
                    }

                }
                y++;
            }
        } catch (IOException exception) {
            System.out.println("Fatal error");
        }

        for (int x = 0; x < GridMap.TILES_WIDTH; x++) {
            for (int y = 0; y < GridMap.TILES_HEIGHT; y++) {
                if (map.getAt(x, y) != GridTile.WALL) {
                    final int step = GridMap.STEP;
                    final int cx = x * 2 * step + step;
                    final int cy = y * 2 * step + step;
                    map.validPositions[cx][cy] = true;

                    for (Direction dir : Direction.values()) {
                        final int bigTargetX = x + dir.getX();
                        final int bigTargetY = y + dir.getY();

                        if (map.isPositionAccessible(new FloatPosition(bigTargetX, bigTargetY))) {
                            for (int i = 0; i < step + 1; i++) {
                                for (int j = 0; j < step + 1; j++) {
                                    final int tx = cx + dir.getX() * i;
                                    final int ty = cy + dir.getY() * j;
                                    map.validPositions[tx][ty] = true;
                                    // System.out.println("Set " + tx + " " + ty + " = TRUE");
                                }
                            }
                        }
                    }
                }
            }
        }

        this.player = new Player(this);
        player.setSpawn(PlayerSpawnX, PlayerSpawnY);
        entities.add(player);
        Ghost g1 = new Ghost(this);
        g1.setSpawn(GhostSpawnX, GhostSpawnY);
        entities.add(g1);
        FoodHandler.generateFood(this, 50);
    }

    ProjectileHandler projectileHandler = new ProjectileHandler(this);

    ProjectileWavesManager waveManager = new ProjectileWavesManager(this);

    // Obligé d'utiliser un buffer pour les ajouts d'entités en cours d'itération à
    // cause des java.lang.ConcurrentModificationException
    private final ArrayList<Entity> entityBuffer = new ArrayList<>();

    void addEntity(Entity e) {
        entityBuffer.add(e);
    }

    synchronized public void update() {
        currentTick++;

        ArrayList<Entity> toDelete = new ArrayList<>();

        //
        try {
            // for (Entity e : entities) {
            for (Iterator<Entity> iterator = entities.iterator(); iterator.hasNext();) {
                var e = iterator.next();
                e.move(0);

                if (e instanceof Projectile) {
                    Projectile projectile = (Projectile) e;
                    if (getCompletionPercent() >= 100)
                        e.markedForDeletion = true;
                }

                if (e.markedForDeletion) {
                    toDelete.add(e);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (this.getCurrentTick() < 4 * 60 * 60) {
            FoodHandler.manageFoodGeneration(this);
            FoodHandler.manageFoodEating(this, (currentTick % 100 == 0));
        }

        if (!GameView.isDrawing) {

            for (Entity e : toDelete) {
                entities.remove(e);
            }

            entities.addAll(entityBuffer);
            entityBuffer.clear();
        } else {
            System.out.println("fail to acquire");
        }

        // projectileHandler.manageProjectileSpawn();

        waveManager.dispatchEvents();

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

    public int getCurrentTick() {
        return currentTick;
    }

    public static final int MAX_TICK = 4 * 60 * 60;

    public int getCompletionPercent() {
        return (int) (currentTick / (double) MAX_TICK * 100);
    }
}
