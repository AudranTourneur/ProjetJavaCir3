package project.game.view;

import javafx.scene.image.Image;
import project.game.model.IntPosition;

public class FoodPainter {
    // Affichage d'une unité de nourriture (poisson)
    private static void drawFood(GameView view, int x, int y) {
        Image img = view.spriteMap.get("fish");
        if (img == null)
            return;
        final DisplayData dispData = new DisplayData(view, x + .5, y + .5, 1.2);
        view.ctx.drawImage(img, dispData.x, dispData.y, dispData.width, dispData.height);
    }

    // Affichage de la nourriture
    public static void drawFoods(GameView view) {
        for (IntPosition food : view.world.foods) {
            drawFood(view, food.x, food.y);
        }
    }
}
