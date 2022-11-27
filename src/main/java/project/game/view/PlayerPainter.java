package project.game.view;

import javafx.geometry.Rectangle2D;
import javafx.scene.SnapshotParameters;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import project.game.model.Direction;

/*
 *  Classe permettant de dessiner un joueur
 */

public class PlayerPainter {

    // Renvoie un sprite depuis la spritesheet en fonction d'une colonne et d'une
    // ligne de 32x32
    static Image getSpriteFromGrid(GameView view, int col, int row) {
        Image spritesheet = view.spriteMap.get("player-spritesheet");
        if (spritesheet == null)
            return null;

        ImageView iv = new ImageView(spritesheet);
        final int size = 32;
        iv.setViewport(new Rectangle2D(col * size, row * size, size, size));

        SnapshotParameters params = new SnapshotParameters();
        params.setFill(Color.TRANSPARENT);

        return iv.snapshot(params, null);
    }

    // Associe chaque direction à sa ligne dans la spritesheet 
    // (pour avoir le chat dans le bon sens quand il se déplace)
    static int directionEnumToY(Direction dir) {
        switch (dir) {
            case UP:
                return 11;
            case DOWN:
                return 7;
            case LEFT:
                return 13;
            case RIGHT:
                return 9;
            default:
                return 0;
        }
    }

    // Choisit la bonne image à afficher en fonction de la direction et (0, 0) si le chat ne bouge pas
    static Image selectCorrectSprite(GameView view) {
        int x = 0;
        int y = 0;

        if (view.world.player.isMoving && view.world.player.currentDirection != null) {
            x = (view.world.getCurrentTick() % 16) / 4;
            y = directionEnumToY(view.world.player.currentDirection);
        }

        return getSpriteFromGrid(view, x, y);
    }

    // Affiche le joueur
    static void drawPlayer(GameView view) {
        Image spritesheet = view.spriteMap.get("player-spritesheet");
        if (spritesheet == null)
            return;

        final DisplayData dispData = new DisplayData(view, view.world.player.position, 1.3);

        if (view.world.player.invulnerabilityTicks == 0 || view.world.getCurrentTick() % 2 == 0)
            view.ctx.drawImage(selectCorrectSprite(view), dispData.x, dispData.y, dispData.width, dispData.height);
    }
}
