package project.game.view;

import javafx.beans.value.ChangeListener;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Screen;
import javafx.stage.Stage;
import project.Main;
import project.game.model.WorldModel;
import project.game.model.Entity;
import project.game.model.Ghost;
import project.game.model.GridMap;
import project.game.model.GridTile;
import project.game.model.IntPosition;
import project.game.model.Player;
import project.game.model.FloatPosition;
import project.game.model.Projectile;
import project.game.model.ProjectileSpawner;
import project.menu.MenuConstants;

import java.io.InputStream;
import java.util.HashMap;

public class GameView {
    Stage stage;
    GraphicsContext ctx;
    WorldModel world;

    final int defaultTileSize = 80;

    double tileSizeX = defaultTileSize;
    double tileSizeY = defaultTileSize;

    double offsetX = 0;
    double offsetY = 0;

    HashMap<String, Image> spriteMap = new HashMap<>();

    int getHightestEvenNumber(int n) {
        if (n % 2 == 0)
            return n;
        else
            return n - 1;
    }

    void handleResize(Canvas canvas) {
        System.out
                .println("Height: " + stage.getHeight() + " Width: " + stage.getWidth());
        canvas.setHeight(stage.getHeight());
        canvas.setWidth(stage.getWidth());

        double desiredTileSizeX = getHightestEvenNumber((int) (canvas.getWidth() / (GridMap.TILES_WIDTH + 1)));
        double desiredTileSizeY = getHightestEvenNumber((int) (canvas.getHeight() / (GridMap.TILES_HEIGHT + 1)));

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

        offsetX = Math.round(Math.max(0, (actualX - desiredX) / 2));
        offsetY = Math.round(Math.max(0, (actualY - desiredY) / 2));

        System.out.println("Offset : " + offsetX + " " + offsetY);

    }

    public GameView(Stage stage, WorldModel world) {
        this.stage = stage;
        this.world = world;

        // create a canvas
        Canvas canvas = new Canvas();

        Rectangle2D bounds = Screen.getPrimary().getBounds();
        stage.setWidth(bounds.getWidth());
        stage.setHeight(bounds.getHeight());

        // set height and width
        canvas.setWidth(stage.getWidth());
        canvas.setHeight(stage.getHeight());

        System.out.println("offsets " + offsetX + " " + offsetY);

        this.ctx = canvas.getGraphicsContext2D();

        // create a Group
        Group group = new Group(canvas);

        // create a scene
        Scene scene = new Scene(group, MenuConstants.windowWidth, MenuConstants.windowHeight);

        stage.show();
        load();
        // set the scene
        stage.setScene(scene);

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
        drawPlayer();
        drawFoods();
        drawGhosts();
        drawProjectiles();
        drawSpawners();
        drawTmpTexts();
        drawEndScreenIfNeeded();

        isDrawing = false;
    }

    void drawTmpTexts() {
        ctx.setFont(Font.font("System", 20));
        ctx.setFill(Color.RED);
        ctx.fillText("Deaths : " + this.world.player.deaths, 10, 20);
        ctx.fillText("Stamina : " + (int) ((double) this.world.player.stamina / Player.MAX_STAMINA * 100.0) + "%", 210,
                20);
        ctx.fillText("Progress : " + this.world.getCompletionPercent() + "%", 410, 20);
        ctx.fillText("Score : " + this.world.player.getScore(), 610, 20);
        ctx.fillText("Est. time : " + this.world.getCurrentTick() / 60 + "s", 810, 20);
    }

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

    void drawWall(int x, int y) {
        float gray = 0.2f;
        ctx.setFill(new Color(gray, gray, gray, 1));

        final DisplayData dispData = new DisplayData(this, x + 0.5, y + 0.5, 1);
        ctx.fillRect(dispData.x, dispData.y, dispData.width, dispData.height);
    }

    void drawVoid(int x, int y) {
        float gray = 0.75f;
        ctx.setFill(new Color(gray, gray, gray, 1));

        final DisplayData dispData = new DisplayData(this, x + 0.5, y + 0.5, 1);
        ctx.fillRect(dispData.x, dispData.y, dispData.width, dispData.height);
    }

    void drawGhost(float x, float y) {
        Image img = spriteMap.get("ghost1");
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

    void drawFood(int x, int y) {
        ctx.setFill(Color.BLUE);
        final double radius = 0.35;

        final DisplayData dispData = new DisplayData(this, x + .5, y + .5, radius);
        ctx.fillOval(dispData.x, dispData.y, dispData.width, dispData.height);
    }

    void drawFoods() {
        for (IntPosition food : world.foods) {
            drawFood(food.x, food.y);
        }
    }

    void drawPlayer() {
        Image img = spriteMap.get("player");
        if (img == null)
            return;

        final DisplayData dispData = new DisplayData(this, world.player.position, 1);

        if (this.world.player.invulnerabilityTicks == 0 || this.world.getCurrentTick() % 2 == 0)
            ctx.drawImage(img, dispData.x, dispData.y, dispData.width, dispData.height);
    }

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
        InputStream pacchat = Main.class.getResourceAsStream("images/cat_image.png");
        InputStream ghost = Main.class.getResourceAsStream("images/ghost.png");
        InputStream warning = Main.class.getResourceAsStream("images/warning.png");

        assert pacchat != null;
        assert ghost != null;
        assert warning != null;

        Image img = new Image(pacchat);
        Image img2 = new Image(ghost);
        Image img3 = new Image(warning);

        spriteMap.put("player", img);
        spriteMap.put("ghost1", img2);
        spriteMap.put("warning", img3);
    }

    public void displayDebugInfo() {
        System.out.println("ctx = " + ctx);
        System.out.println("spriteMap = " + spriteMap);
        System.out.println("spriteMap (keys)");
        spriteMap.keySet().forEach(System.out::println);
        System.out.println("spriteMap (values)");
        spriteMap.values().forEach(System.out::println);
    }

}
