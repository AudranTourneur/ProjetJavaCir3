//Gestion des lieux d'apparition des objets.

package project.game.model.general;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

import project.game.model.projectile.ProjectileHandler;
import project.game.model.projectile.ProjectileWavesManager;
import project.game.model.utils.Direction;
import project.game.model.utils.FloatPosition;
import project.game.model.utils.GridTile;
import project.game.model.utils.IntPosition;
import project.main.Main;

/* Modèle général*/
public class WorldModel {
    public Player player;

    private int currentTick; // compteur des update

    public int endTick = 0;

    public int getEndTick() {
        return endTick;
    }

    /* Liste de nos entitées */
    public List<Entity> entities = new ArrayList<>();

    // Obligé d'utiliser un buffer pour les ajouts d'entités en cours d'itération à
    // cause des java.lang.ConcurrentModificationException
    private final ArrayList<Entity> entityBuffer = new ArrayList<>();

    // Carte
    public GridMap map = new GridMap();

    // Position de la nourriture
    public HashSet<IntPosition> foods = new HashSet<>();

    // Notre gestionnaire de projectile
    ProjectileHandler projectileHandler = new ProjectileHandler(this);

    // Gestionnaire de vagues de projectiles
    ProjectileWavesManager waveManager = new ProjectileWavesManager(this);

    // Gestionnaire de fantômes
    public GhostHandler ghostHandler = new GhostHandler(this);

    // Gestionnaire d'avancé de niveau
    public LevelProgressionManager levelProgressionManager = new LevelProgressionManager(this);

    public WorldModel() {
        init();
    }

    // Lit notre ficheir map.txt et met a jour map avec les bonnes valeurs
    void init() {

        Scanner in = new Scanner(
                new Main().getClass().getResourceAsStream("logic/map.txt"), "UTF-8").useDelimiter("\\A");

        String text = in.next();

        in.close();

        BufferedReader bufReader = new BufferedReader(new StringReader(text));

        String line = null;
        int PlayerSpawnX = 0;
        int PlayerSpawnY = 0;

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
                        ghostHandler.addSpawnPoint(x, y);

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
        levelProgressionManager.firstSpawn();
    }

    public void addEntity(Entity e) {
        entityBuffer.add(e);
    }

    void removeEntity(Entity e) {
        entities.remove(e);
    }

    // Met a jour notre modèle
    synchronized public void update() {
        if (hasFinished() && endTick == 0) {
            endTick = currentTick;
            player.finalScore = player.score;
        }

        currentTick++;

        ArrayList<Entity> toDelete = new ArrayList<>();

        try {

            for (Iterator<Entity> iterator = entities.iterator(); iterator.hasNext();) {
                var e = iterator.next();
                e.move(0);

                if (e.markedForDeletion) {
                    toDelete.add(e);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        FoodHandler.manageFoodEating(this);
        levelProgressionManager.manage();
        waveManager.dispatchEvents();
        ghostHandler.manage();
        projectileHandler.manageProjectileCollisions();

        for (Entity e : toDelete) {
            entities.remove(e);
        }

        entities.addAll(entityBuffer);
        entityBuffer.clear();
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

    public boolean hasFinished() {
        return this.player.getLives() <= 0 || levelProgressionManager.isVictory();
    }
}
