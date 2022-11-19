package project.game.view;

import javafx.beans.value.ChangeListener;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;
import project.Main;
import project.game.model.WorldModel;
import project.game.model.GridMap;
import project.game.model.GridTile;
import project.game.model.IntPosition;
import project.game.model.Entity;
import project.game.model.FloatPosition;
import project.game.model.Projectile;
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

        // double availableSize = Math.min(canvas.getWidth(), canvas.getHeight());
        // tileSizeX = Math.round(availableSize / GridMap.TILES_WIDTH);
        // tileSizeY = Math.round(availableSize / GridMap.TILES_HEIGHT);

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
        //canvas.setHeight(MenuConstants.windowHeight);
        //canvas.setWidth(MenuConstants.windowWidth);

        System.out.println("offsets " + offsetX + " " + offsetY);

        GraphicsContext ctx = canvas.getGraphicsContext2D();

        this.ctx = ctx;

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

    synchronized public void display(boolean log) {
        if (log)
            System.out.println("display is  called");

        ctx.setFill(Color.BLACK);
        ctx.fillRect(0, 0, ctx.getCanvas().getWidth(), ctx.getCanvas().getHeight());
        drawMap();
        drawPlayer();
        drawFoods();
        drawProjectiles();
        // drawValidPositions();
    }

    void drawMap() {
        for (int i = 0; i < GridMap.TILES_WIDTH; i++) {
            for (int j = 0; j < GridMap.TILES_HEIGHT; j++) {
                if (world.map.map[i][j] == GridTile.WALL) {
                    drawWall(i, j);
                }
                if (world.map.map[i][j] == GridTile.VOID || world.map.map[i][j] == GridTile.PLAYER_SPAWN) {
                    drawVoid(i, j);
                }
            }
        }
    }

    void drawWall(int x, int y) {
        // set fill for rectangle
        float gray = 0.2f;
        ctx.setFill(new Color(gray, gray, gray, 1));

        final DisplayData dispData = new DisplayData(this, x + 0.5, y + 0.5, 1);
        ctx.fillRect(dispData.x, dispData.y, dispData.width, dispData.height);

        // ctx.fillRect(toDisplayX(x), toDisplayY(y), tileSizeX, tileSizeY);

    }

    void drawVoid(int x, int y) {
        float gray = 0.75f;
        ctx.setFill(new Color(gray, gray, gray, 1));

        final DisplayData dispData = new DisplayData(this, x + 0.5, y + 0.5, 1);
        ctx.fillRect(dispData.x, dispData.y, dispData.width, dispData.height);
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

        ctx.setFill(Color.RED);

        final DisplayData dispData = new DisplayData(this, world.player.position, 1);

        ctx.drawImage(img, dispData.x, dispData.y, dispData.width, dispData.height);
    }

    /*
     * void drawValidPositions() {
     * final double fullSizeX = tileSizeX * GridMap.TILES_WIDTH;
     * final double fullSizeY = tileSizeY * GridMap.TILES_HEIGHT;
     * for (int i = 0; i < world.map.validPositions.length; i++) {
     * for (int j = 0; j < world.map.validPositions.length; j++) {
     * if (world.map.validPositions[i][j]) {
     * // ctx.fillRect(world.map.validPositions[i][0] * TILE_SIZE,
     * // world.map.validPositions[i][1] * TILE_SIZE,
     * // TILE_SIZE, TILE_SIZE);
     * double arraySize = world.map.validPositions.length;
     * final double radius = 10;
     * 
     * ctx.setFill(Color.RED);
     * ctx.fillOval((i) / arraySize * fullSizeX, (j) / (arraySize) * fullSizeY,
     * radius, radius);
     * 
     * }
     * }
     * }
     * 
     * }
     */

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

    // Fonction va charger tout nos sprite dans le futur
    void load() {
        InputStream pacchat = new Main().getClass().getResourceAsStream("images/cat_image.png");
        // Test de la classe spirte animation
        // InputStream spriteSheet = new
        // Main().getClass().getResourceAsStream("images/spritesheet.png");
        assert pacchat != null;
        // assert spriteSheet != null;

        Image img = new Image(pacchat);
        /*
         * int COLUMNS = 4;
         * int COUNT = 10;
         * int OFFSET_X = 18;
         * int OFFSET_Y = 25;
         * int WIDTH = 374;
         * int HEIGHT = 243;
         * 
         * 
         * Image sprites = new Image(spriteSheet);
         * 
         * ImageView spriteView=new ImageView(sprites);
         * spriteView.setViewport(new Rectangle2D(OFFSET_X, OFFSET_Y, WIDTH, HEIGHT));
         * 
         * Animation animation = new SpriteAnimation(spriteView, Duration.millis(1000),
         * COUNT, COLUMNS, OFFSET_X, OFFSET_Y, WIDTH, HEIGHT);
         * animation.setCycleCount(Animation.INDEFINITE);
         * animation.play();
         * 
         * stage.setScene(new Scene(new Group(spriteView)));
         */

        spriteMap.put("player", img);
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
