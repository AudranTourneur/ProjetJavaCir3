package project.game.view;

import javafx.geometry.Rectangle2D;
import javafx.scene.SnapshotParameters;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import project.game.model.Direction;

public class PlayerPainter {

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

    static Image selectCorrectSprite(GameView view) {
        System.out.println("moving " + view.world.player.isMoving);
        int x = view.world.player.isMoving ? (view.world.getCurrentTick() % 16) / 4 : 0;
        int y = view.world.player.isMoving ? directionEnumToY(view.world.player.currentDirection) : 0;

        return getSpriteFromGrid(view, x, y);
    }

    static void drawPlayer(GameView view) {
        Image spritesheet = view.spriteMap.get("player-spritesheet");
        if (spritesheet == null)
            return;

        final DisplayData dispData = new DisplayData(view, view.world.player.position, 1.2);

        if (view.world.player.invulnerabilityTicks == 0 || view.world.getCurrentTick() % 2 == 0)
            view.ctx.drawImage(selectCorrectSprite(view), dispData.x, dispData.y, dispData.width, dispData.height);
    }
}
