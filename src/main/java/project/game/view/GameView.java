//Creation de l'interface graphique (utilisation de canvas)
//affichage de tous les elements du jeu (joueur, projectiles...)

package project.game.view;

import javafx.beans.value.ChangeListener;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import project.Main;
import project.game.model.WorldModel;
import project.game.controller.GameWindowController;
import project.game.model.Entity;
import project.game.model.Ghost;
import project.game.model.GridMap;
import project.game.model.GridTile;
import project.game.model.IntPosition;
import project.game.model.FloatPosition;
import project.game.model.Projectile;
import project.game.model.ProjectileSpawner;
import java.util.HashMap;

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

    int getHightestEvenNumber(int n) {
        if (n % 2 == 0)
            return n;
        else
            return n - 1;
    }

    void handleResize(Canvas canvas) {
        //  System.out
        //  .println("Height: " + stage.getHeight() + " Width: " + stage.getWidth());
        //  canvas.setHeight(stage.getHeight());
        //  canvas.setWidth(stage.getWidth());

        Pane pane = this.window.pane;
        System.out.println("Pane : " + new FloatPosition(pane.getWidth(), pane.getHeight()));

        this.window.canvas.setWidth(this.window.stage.getWidth());
        this.window.canvas.setHeight(this.window.stage.getHeight()-this.window.menuVBox.getHeight());
        

        double desiredTileSizeX = ((int) ((pane.getWidth()) / (GridMap.TILES_WIDTH + 1)));
        double desiredTileSizeY = ((int) (pane.getHeight() / (GridMap.TILES_HEIGHT + 1)));

        System.out.println("Desired tile size: " + desiredTileSizeX + "x" + desiredTileSizeY);

        tileSizeX = Math.min(desiredTileSizeX, desiredTileSizeY);
        tileSizeY = tileSizeX;

        System.out.println("tile size: " + tileSizeX + "x" + tileSizeY);

        double desiredX = tileSizeX * (GridMap.TILES_WIDTH + 1);
        double desiredY = tileSizeY * (GridMap.TILES_HEIGHT + 1);

        double actualX = canvas.getWidth();
        double actualY = canvas.getHeight();

        System.out.println("desired : " + new FloatPosition(desiredX, desiredY));
        System.out.println("actual : " + new FloatPosition(actualX, actualY));

        //offsetX = Math.round(Math.max(0, (actualX - desiredX)));
        offsetX = Math.round(Math.max(0, (actualX - desiredX) / 2));
        offsetY = Math.round(Math.max(0, (actualY - desiredY) / 2));

        System.out.println("Offset : " + offsetX + " " + offsetY);
    }

    public GameView(GameWindowController window, WorldModel world) {
        this.window = window;
        this.world = world;

        this.stage = window.stage;
        Canvas canvas = window.canvas;

        this.ctx = canvas.getGraphicsContext2D();

        System.out.println("canvas = " + canvas.getWidth() + " " + canvas.getHeight());

        load();

        this.hud = new HUDControllerView(this.window);

        ChangeListener<Number> stageSizeListener = (observable, oldValue, newValue) -> {
            handleResize(canvas);
        };

        stage.widthProperty().addListener(stageSizeListener);
        stage.heightProperty().addListener(stageSizeListener);

        handleResize(canvas);

    }

    void drawEndScreenIfNeeded() {
        if (world.getCompletionPercent() < 100)
            return;
        ctx.setFill(Color.RED);
        ctx.setFont(new Font("System", 70));
        ctx.fillText("Final score : " + (this.world.player.score / (this.world.player.deaths + 1)), tileSizeX * 14,
                tileSizeY * 10);
    }

    public volatile static boolean isDrawing = false;

    public void display(boolean log) {
        isDrawing = true;
        if (log)
            System.out.println("display is  called");

        ctx.setFill(Color.BLACK);
        ctx.fillRect(0, 0, ctx.getCanvas().getWidth(), ctx.getCanvas().getHeight());
        drawMap();
        PlayerPainter.drawPlayer(this);
        drawFoods();
        drawGhosts();
        drawProjectiles();
        drawSpawners();
        drawTmpTexts();
        EndScreen.showEndScreenIfNeeded(this, this.world);
        //LeftBarHUD.drawLeftBar(this);
        hud.updateHUD(this.world);

        isDrawing = false;

        if (this.world.getCurrentTick() % 60 == 0)
            handleResize(this.window.canvas);
    }

    void drawTmpTexts() {
    }

    void drawMap() { // Notre map
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

    void drawWall(int x, int y) { // afficher les murs
        Image img = spriteMap.get("wall");
        if (img == null)
            return;

        final DisplayData dispData = new DisplayData(this, x + 0.5, y + 0.5, 1);
        ctx.drawImage(img, dispData.x, dispData.y, dispData.width, dispData.height);
    }

    void drawVoid(int x, int y) {
        Image img = spriteMap.get("path");
        if (img == null)
            return;

        final DisplayData dispData = new DisplayData(this, x + 0.5, y + 0.5, 1);
        ctx.drawImage(img, dispData.x, dispData.y, dispData.width, dispData.height);
    }

    void drawGhost(float x, float y) { // fantomes
        Image img = spriteMap.get("ghost");
        if (img == null)
            return;

        final DisplayData dispData = new DisplayData(this, x, y, 1);

        ctx.drawImage(img, dispData.x, dispData.y, dispData.width, dispData.height);
    }

    void drawGhosts() {
        for (Entity e : world.entities) {
            if (e instanceof Ghost) {
                Ghost tmp = (Ghost) e;
                drawGhost(tmp.position.x, tmp.position.y);
            }
        }
    }

    // affichage de la nourriture (bleu)
    void drawFood(int x, int y) {
       Image img =spriteMap.get("fish");
       if(img==null)return;
        final DisplayData dispData = new DisplayData(this, x + .5, y + .5, 1.2);
        ctx.drawImage(img, dispData.x, dispData.y, dispData.width, dispData.height);
    }

    void drawFoods() {
        for (IntPosition food : world.foods) {
            drawFood(food.x, food.y);
        }
    }

    // affichage de nos projectiles (rouge)
    void drawProjectile(float x, float y) {
        ctx.setFill(Color.RED);

        final DisplayData dispData = new DisplayData(this, x, y, Projectile.RADIUS_SIZE);
        ctx.fillOval(dispData.x, dispData.y, dispData.width, dispData.height);
    }

    void drawProjectiles() {
        for (Entity entity : world.entities) {
            if (entity instanceof Projectile) {
                drawProjectile(entity.position.x, entity.position.y);
            }
        }
    }

    // affichage des warnings prevenant l'arrivés de futurs projectiles
    void drawSpawner(ProjectileSpawner spawner) {
        Image img = spriteMap.get("warning");
        if (img == null)
            return;

        final DisplayData dispData = new DisplayData(this, spawner.position, 1);
        if (spawner.getTicksToLive() % 60 < 30)
            ctx.drawImage(img, dispData.x, dispData.y, dispData.width, dispData.height);
    }

    void drawSpawners() {
        for (Entity entity : world.entities)
            if (entity instanceof ProjectileSpawner)
                drawSpawner((ProjectileSpawner) entity);
    }

    // Fonction va charger tout nos sprites dans le futur
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

        spriteMap.put("fish",new Image(Main.class.getResourceAsStream("images/fish.png")));

        spriteMap.put("player-spritesheet", new Image(Main.class.getResourceAsStream("images/cat_spritesheet.png")));
    }

    public void displayDebugInfo() {
        System.out.println("ctx = " + ctx);
        System.out.println("spriteMap = " + spriteMap);
        System.out.println("spriteMap (keys)");
        spriteMap.keySet().forEach(System.out::println);
        System.out.println("spriteMap (values)");
        spriteMap.values().forEach(System.out::println);
    }

    public void setModel(WorldModel model) {
        this.world = model;
    }

}
