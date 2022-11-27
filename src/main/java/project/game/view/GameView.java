package project.game.view;

import javafx.beans.value.ChangeListener;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import project.game.view.painter.EndScreenPainter;
import project.game.view.painter.FoodPainter;
import project.game.view.painter.GhostPainter;
import project.game.view.painter.MapPainter;
import project.game.view.painter.PlayerPainter;
import project.game.view.painter.ProjectilePainter;
import project.main.Main;
import project.game.controller.GameWindowController;
import project.game.model.general.GridMap;
import project.game.model.general.WorldModel;

import java.util.HashMap;

/* 
 *   Gestion de l'interface graphique en cours de partie
 *   Responsable de l'ffichage de tous les éléments du jeu (joueur, fantômes, projectiles...)
 */

public class GameView {
    public Stage stage;
    public GraphicsContext ctx;
    public WorldModel world;

    final int defaultTileSize = 80;

    double tileSizeX = defaultTileSize;
    double tileSizeY = defaultTileSize;

    double offsetX = 0;
    double offsetY = 0;

    public HashMap<String, Image> spriteMap = new HashMap<>();
    private GameWindowController window;

    private HUDControllerView hud;

    // Gestion des redimensionnement du canvas lorsque la fenêtre est redimensionné
    // par l'utilisateur
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
        MapPainter.drawMap(this);
        PlayerPainter.drawPlayer(this);
        FoodPainter.drawFoods(this);
        GhostPainter.drawGhosts(this);
        ProjectilePainter.drawProjectiles(this);
        ProjectilePainter.drawSpawners(this);
        EndScreenPainter.showEndScreenIfNeeded(this, this.world);
        // LeftBarHUD.drawLeftBar(this);
        hud.updateHUD(this.world);

        if (this.world.getCurrentTick() % 60 == 0)
            handleResize(this.window.canvas);
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
