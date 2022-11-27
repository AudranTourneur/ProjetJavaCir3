package project.game.view;

import javafx.scene.image.Image;
import project.game.model.GridMap;
import project.game.model.GridTile;

public class MapPainter {
    // Affichage de la carte
    public static void drawMap(GameView view) {
        for (int i = 0; i < GridMap.TILES_WIDTH; i++) {
            for (int j = 0; j < GridMap.TILES_HEIGHT; j++) {
                if (view.world.map.map[i][j] == GridTile.WALL) {
                    drawWall(view, i, j);
                }
                if (view.world.map.map[i][j] == GridTile.VOID || view.world.map.map[i][j] == GridTile.PLAYER_SPAWN
                        || view.world.map.map[i][j] == GridTile.GHOST_SPAWN) {
                    drawVoid(view, i, j);
                }
            }
        }
    }

    // Affichage des murs
    private static void drawWall(GameView view, int x, int y) {
        Image img = view.spriteMap.get("wall");
        if (img == null)
            return;

        final DisplayData dispData = new DisplayData(view, x + 0.5, y + 0.5, 1);
        view.ctx.drawImage(img, dispData.x, dispData.y, dispData.width, dispData.height);
    }

    // Affichage des chemins (cases non remplis)
    private static void drawVoid(GameView view, int x, int y) {
        Image img = view.spriteMap.get("path");
        if (img == null)
            return;

        final DisplayData dispData = new DisplayData(view, x + 0.5, y + 0.5, 1);
        view.ctx.drawImage(img, dispData.x, dispData.y, dispData.width, dispData.height);
    }


}
