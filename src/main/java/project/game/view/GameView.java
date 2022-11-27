package project.game.view;

import javafx.beans.value.ChangeListener;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import project.Main;
import project.game.model.WorldModel;
import project.game.controller.GameWindowController;
import project.game.model.Entity;
import project.game.model.Ghost;
import project.game.model.GridMap;
import project.game.model.GridTile;
import project.game.model.IntPosition;
import project.game.model.Projectile;
import project.game.model.ProjectileSpawner;
import java.util.HashMap;

/* 
 *   Gestion de l'interface graphique en cours de partie
 *   Responsable de l'ffichage de tous les éléments du jeu (joueur, fantômes, projectiles...)
 */

public class GameView {
    public Stage stage;
    GraphicsContext ctx;
    WorldModel world;

    final int defaultTileSize = 80;

    double tileSizeX = defaultTileSize;
    double tileSizeY = defaultTileSize;

    double offsetX = 0;
    double offsetY = 0;

    HashMap<String, Image> spriteMap = new HashMap<>();
    private GameWindowController window;

    private HUDControllerView hud;

    // Gestion des redimensionnement du canvas lorsque la fenêtre est redimensionné par l'utilisateur
    void handleResize(Canvas canvas) {
        Pane pane = this.window.pane;

        this.window.canvas.setWidth(this.window.stage.getWidth());
        this.window.canvas.setHeight(this.window.stage.getHeight() - this.window.menuVBox.getHeight());

        double desiredTileSizeX = ((int) ((pane.getWidth()) / (GridMap.TILES_WIDTH + 1)));
        double desiredTileSizeY = ((int) (pane.getHeight() / (GridMap.TILES_HEIGHT + 1)));

        tileSizeX = Math.min(desiredTileSizeX, desiredTileSizeY);
        tileSizeY = tileSizeX;

        double desiredX = tileSizeX * (GridMap.TILES_WIDTH + 1);
        double desiredY = tileSizeY * (GridMap.TILES_HEIGHT + 1);

        double actualX = canvas.getWidth();
        double actualY = canvas.getHeight();

        offsetX = Math.round(Math.max(0, (actualX - desiredX) / 2));
        offsetY = Math.round(Math.max(0, (actualY - desiredY) / 2));
    }

    // Intialisation de la vue
    public GameView(GameWindowController window, WorldModel world) {
        this.window = window;
        this.world = world;

        this.stage = window.stage;
        Canvas canvas = window.canvas;

        this.ctx = canvas.getGraphicsContext2D();

        load();

        this.hud = new HUDControllerView(this.window);

        ChangeListener<Number> stageSizeListener = (observable, oldValue, newValue) -> {
            handleResize(canvas);
        };

        stage.widthProperty().addListener(stageSizeListener);
        stage.heightProperty().addListener(stageSizeListener);

        handleResize(canvas);

    }

    // Affichage de l'entièreté du jeu
    public void display() {
        ctx.setFill(Color.BLACK);
        ctx.fillRect(0, 0, ctx.getCanvas().getWidth(), ctx.getCanvas().getHeight());
        drawMap();
        PlayerPainter.drawPlayer(this);
        drawFoods();
        drawGhosts();
        drawProjectiles();
        drawSpawners();
        EndScreen.showEndScreenIfNeeded(this, this.world);
        // LeftBarHUD.drawLeftBar(this);
        hud.updateHUD(this.world);

        if (this.world.getCurrentTick() % 60 == 0)
            handleResize(this.window.canvas);
    }

    // Affichage de la carte
    void drawMap() {
        for (int i = 0; i < GridMap.TILES_WIDTH; i++) {
            for (int j = 0; j < GridMap.TILES_HEIGHT; j++) {
                if (world.map.map[i][j] == GridTile.WALL) {
                    drawWall(i, j);
                }
                if (world.map.map[i][j] == GridTile.VOID || world.map.map[i][j] == GridTile.PLAYER_SPAWN
                        || world.map.map[i][j] == GridTile.GHOST_SPAWN) {
                    drawVoid(i, j);
                }
            }
        }
    }

    // Affichage des murs
    void drawWall(int x, int y) {
        Image img = spriteMap.get("wall");
        if (img == null)
            return;

        final DisplayData dispData = new DisplayData(this, x + 0.5, y + 0.5, 1);
        ctx.drawImage(img, dispData.x, dispData.y, dispData.width, dispData.height);
    }

    // Affichage des chemins (cases non remplis)
    void drawVoid(int x, int y) {
        Image img = spriteMap.get("path");
        if (img == null)
            return;

        final DisplayData dispData = new DisplayData(this, x + 0.5, y + 0.5, 1);
        ctx.drawImage(img, dispData.x, dispData.y, dispData.width, dispData.height);
    }

    // Affichage d'un fantôme
    void drawGhost(float x, float y) {
        Image img = spriteMap.get("ghost");
        if (img == null)
            return;

        final DisplayData dispData = new DisplayData(this, x, y, 1);

        ctx.drawImage(img, dispData.x, dispData.y, dispData.width, dispData.height);
    }

    // Affichage des fantômes
    void drawGhosts() {
        for (Entity e : world.entities) {
            if (e instanceof Ghost) {
                Ghost tmp = (Ghost) e;
                drawGhost(tmp.position.x, tmp.position.y);
            }
        }
    }

    // Affichage d'une unité de nourriture (poisson)
    void drawFood(int x, int y) {
        Image img = spriteMap.get("fish");
        if (img == null)
            return;
        final DisplayData dispData = new DisplayData(this, x + .5, y + .5, 1.2);
        ctx.drawImage(img, dispData.x, dispData.y, dispData.width, dispData.height);
    }

    // Affichage de la nourriture
    void drawFoods() {
        for (IntPosition food : world.foods) {
            drawFood(food.x, food.y);
        }
    }

    // Affichage d'un projectile (en rouge)
    void drawProjectile(float x, float y) {
        ctx.setFill(Color.RED);

        final DisplayData dispData = new DisplayData(this, x, y, Projectile.RADIUS_SIZE);
        ctx.fillOval(dispData.x, dispData.y, dispData.width, dispData.height);
    }

    // Affichage des projectiles
    void drawProjectiles() {
        for (Entity entity : world.entities) {
            if (entity instanceof Projectile) {
                drawProjectile(entity.position.x, entity.position.y);
            }
        }
    }

    // Affichage d'un avertissement prevenant l'arrivés de futurs projectiles
    // (panneau de signalisation en jaune)
    void drawSpawner(ProjectileSpawner spawner) {
        Image img = spriteMap.get("warning");
        if (img == null)
            return;

        final DisplayData dispData = new DisplayData(this, spawner.position, 1);
        if (spawner.getTicksToLive() % 60 < 30)
            ctx.drawImage(img, dispData.x, dispData.y, dispData.width, dispData.height);
    }

    // Affichage des avertissements
    void drawSpawners() {
        for (Entity entity : world.entities)
            if (entity instanceof ProjectileSpawner)
                drawSpawner((ProjectileSpawner) entity);
    }

    // Méthode qui charge tous les sprites depuis le système de fichier vers la
    // mémoire
    void load() {
        spriteMap.put("player", new Image(Main.class.getResourceAsStream("images/cat_image.png")));
        spriteMap.put("ghost", new Image(Main.class.getResourceAsStream("images/ghost.png")));
        spriteMap.put("warning", new Image(Main.class.getResourceAsStream("images/warning.png")));

        spriteMap.put("path", new Image(Main.class.getResourceAsStream("images/tile_path.png")));
        spriteMap.put("wall", new Image(Main.class.getResourceAsStream("images/tile_brick.png")));

        spriteMap.put("skull", new Image(Main.class.getResourceAsStream("images/skull.png")));
        spriteMap.put("apple", new Image(Main.class.getResourceAsStream("images/apple.png")));
        spriteMap.put("zap", new Image(Main.class.getResourceAsStream("images/zap.png")));
        spriteMap.put("hourglass", new Image(Main.class.getResourceAsStream("images/hourglass.png")));
        spriteMap.put("party", new Image(Main.class.getResourceAsStream("images/party.png")));

        spriteMap.put("fish", new Image(Main.class.getResourceAsStream("images/fish.png")));

        spriteMap.put("player-spritesheet", new Image(Main.class.getResourceAsStream("images/cat_spritesheet.png")));
    }

    // Utilisé pour ré-initialiser la vue avec un autre modèle
    public void setModel(WorldModel model) {
        this.world = model;
    }
}
