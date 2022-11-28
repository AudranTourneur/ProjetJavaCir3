package project.game.view.painter;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import project.game.model.general.GridMap;
import project.game.model.general.WorldModel;
import project.game.model.utils.FloatPosition;
import project.game.view.DisplayData;
import project.game.view.GameView;

/*
 *  Gestion de l'écran final de victoire ou de défaite
 */

public class EndScreenPainter {

    // Affiche l'écran de fin (victoire ou défaite si nécessaire)
    static public void showEndScreenIfNeeded(GameView view, WorldModel model) {
        if (!model.hasFinished())
            return;

        drawTransparentPanel(view);

        if (model.player.getLives() <= 0) {
            // Afficher l'écran de défaite si aucune vie restante
            showDefeatScreen(view, model);
        } else if (model.levelProgressionManager.isVictory()) {
            // Afficher l'écran de victoire si le dernier niveau est atteint
            showVictoryScreen(view, model);
        }
    }

    // Ticks de jeu formattés en minutes/secondes
    static private String formatTimeFromTicks(int gameTicks) {
        final int seconds = gameTicks / 60;
        final int minutes = seconds / 60;

        return String.format("%02d:%02d", minutes, seconds % 60);
    }

    static private void drawTransparentPanel(GameView view) {
        final DisplayData dd = new DisplayData(view, new FloatPosition(GridMap.TILES_WIDTH/2.0, GridMap.TILES_HEIGHT/2.0), GridMap.TILES_WIDTH,
                GridMap.TILES_HEIGHT);
        view.ctx.setFill(Color.rgb(0, 0, 0, 0.5));
        view.ctx.fillRect(dd.x, dd.y, dd.width, dd.height);
    }

    // Écran de défaite
    static private void showDefeatScreen(GameView view, WorldModel model) {
        final GraphicsContext ctx = view.ctx;
        DisplayData dd = new DisplayData(view, 8, 5, 5);

        ctx.setFill(Color.RED);
        ctx.setStroke(Color.BLACK);
        ctx.setFont(Font.font("System", Math.min(view.stage.getWidth(), view.stage.getHeight()) / 7));

        ctx.drawImage(view.spriteMap.get("skull"), dd.x, dd.y, dd.width, dd.height);
        ctx.fillText("Defeat", dd.x + dd.width, dd.y + dd.height * 0.75);

        ctx.setFont(Font.font("System", Math.min(view.stage.getWidth(), view.stage.getHeight()) / 15));

        ctx.fillText("Final Score : " + model.player.finalScore, dd.x + dd.width, dd.y + dd.height * 1.5);
        ctx.fillText("Time : " + formatTimeFromTicks(model.getEndTick()), dd.x + dd.width, dd.y + dd.height * 2);

        ctx.fillText("Press R to Restart", dd.x + dd.width, dd.y + dd.height * 2.5);
    }

    // Écran de victoire
    static private void showVictoryScreen(GameView view, WorldModel model) {
        final GraphicsContext ctx = view.ctx;
        DisplayData dd = new DisplayData(view, 8, 5, 5);

        ctx.setFill(Color.LIME);
        ctx.setStroke(Color.BLACK);
        ctx.setFont(Font.font("System", Math.min(view.stage.getWidth(), view.stage.getHeight()) / 7));

        ctx.drawImage(view.spriteMap.get("party"), dd.x, dd.y, dd.width, dd.height);
        ctx.fillText("Victory", dd.x + dd.width, dd.y + dd.height * 0.75);

        ctx.setFont(Font.font("System", Math.min(view.stage.getWidth(), view.stage.getHeight()) / 15));

        ctx.fillText("Final Score : " + model.player.finalScore, dd.x + dd.width, dd.y + dd.height * 1.5);
        ctx.fillText("Time : " + formatTimeFromTicks(model.getEndTick()), dd.x + dd.width, dd.y + dd.height * 2);
        ctx.fillText("Lives left : " + model.player.getLives(), dd.x + dd.width, dd.y + dd.height * 2.5);
        ctx.fillText("Press R to Restart", dd.x + dd.width, dd.y + dd.height * 2.5);
    }

}
