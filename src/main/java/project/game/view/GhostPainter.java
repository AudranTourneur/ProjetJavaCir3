package project.game.view;

import javafx.scene.image.Image;
import project.game.model.Entity;
import project.game.model.Ghost;

public class GhostPainter {
    // Affichage d'un fantôme
    private static void drawGhost(GameView view, float x, float y) {
        Image img = view.spriteMap.get("ghost");
        if (img == null)
            return;

        final DisplayData dispData = new DisplayData(view, x, y, 1);

        view.ctx.drawImage(img, dispData.x, dispData.y, dispData.width, dispData.height);
    }

    // Affichage des fantômes
    public static void drawGhosts(GameView view) {
        for (Entity e : view.world.entities) {
            if (e instanceof Ghost) {
                Ghost tmp = (Ghost) e;
                drawGhost(view, tmp.position.x, tmp.position.y);
            }
        }
    }
}
