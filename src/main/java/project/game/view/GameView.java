package project.game.view;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import project.Main;
import project.game.model.GameWorldModel;
import project.game.model.GridTile;
import project.menu.MenuConstants;

import java.io.InputStream;
import java.util.HashMap;

public class GameView {
    Stage stage;
    GraphicsContext ctx;
    GameWorldModel world;
    int TILE_SIZE = 80;

    int FULL_SIZE = TILE_SIZE * 10;

    HashMap<String, Image> spriteMap = new HashMap<>();

    public GameView(Stage stage, GameWorldModel world) {
        this.stage = stage;
        this.world = world;

        // create a canvas
        Canvas canvas = new Canvas();

        // set height and width
        canvas.setHeight(MenuConstants.windowHeight);
        canvas.setWidth(MenuConstants.windowWidth);

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
    }

    public void display(boolean log) {
        if (log)
            System.out.println("display is called");

        ctx.clearRect(0, 0, FULL_SIZE, FULL_SIZE);
        drawMap();
        drawPlayer();
        drawValidPositions();

    }

    void drawMap() {
        for (int i = 0; i < world.map.map.length; i++) {
            for (int j = 0; j < world.map.map.length; j++) {
                if (world.map.map[i][j] == GridTile.WALL) {
                    drawWall(i, j);
                }
                if (world.map.map[i][j] == GridTile.VOID) {
                    drawVoid(i, j);
                }
            }
        }
    }

    void drawWall(int x, int y) {
        // set fill for rectangle
        float gray = 0.2f;
        ctx.setFill(new Color(gray, gray, gray, 1));
        ctx.fillRect(x * TILE_SIZE, y * TILE_SIZE, TILE_SIZE, TILE_SIZE);

    }

    void drawVoid(int x, int y) {
        ctx.setFill(Color.WHITE);
        ctx.fillRect(x * TILE_SIZE, y * TILE_SIZE, TILE_SIZE, TILE_SIZE);
    }

    void drawPlayer() {
        Image img = spriteMap.get("player");
        if (img == null)
            return;

        double arraySize = world.map.validPositions.length;

        ctx.setFill(Color.RED);
        double x = (world.player.getGridPositionX()) / arraySize * FULL_SIZE;
        double y = (world.player.getGridPositionY()) / arraySize * FULL_SIZE;

        //ctx.drawImage(img, x + TILE_SIZE/2, y + TILE_SIZE/2, TILE_SIZE, TILE_SIZE);
        //ctx.drawImage(img, x, y, TILE_SIZE, TILE_SIZE);
        ctx.drawImage(img, x - TILE_SIZE/2, y - TILE_SIZE/2, TILE_SIZE, TILE_SIZE);

        System.out.println("draw + " + x + " " + y);

        // if (img != null)
        // ctx.drawImage(img, world.player.position.x * TILE_SIZE,
        // world.player.position.y * TILE_SIZE, TILE_SIZE,
        // TILE_SIZE);
    }

    void drawValidPositions() {
        final double fullSize = TILE_SIZE * 10;
        for (int i = 0; i < world.map.validPositions.length; i++) {
            for (int j = 0; j < world.map.validPositions.length; j++) {
                if (world.map.validPositions[i][j]) {
                    // ctx.fillRect(world.map.validPositions[i][0] * TILE_SIZE,
                    // world.map.validPositions[i][1] * TILE_SIZE,
                    // TILE_SIZE, TILE_SIZE);
                    double arraySize = world.map.validPositions.length;
                    final double radius = 10;

                    ctx.setFill(Color.RED);
                    ctx.fillOval((i) / arraySize * fullSize, (j) / (arraySize) * fullSize, radius, radius);

                }
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
