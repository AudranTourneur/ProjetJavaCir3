package project.game.view;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import project.game.model.LevelProgressionManager;
import project.game.model.WorldModel;

/*
 *  Gestion de l'écran final de victoire ou de défaite
 */

public class EndScreen {

    // Affiche l'écran de fin (victoire ou défaite si nécessaire)
    static public void showEndScreenIfNeeded(GameView view, WorldModel model) {
        if (!model.hasFinished()) return;
        if (model.player.getLives() <= 0) {
            // Afficher l'écran de défaite si aucune vie restante
            showDefeatScreen(view, model);
        } else if (model.levelProgressionManager.currentLevel == LevelProgressionManager.NUMBER_OF_LEVELS - 1) {
            // Afficher l'écran de victoire si le dernier niveau est atteint
            showVictoryScreen(view, model);
        }
    }

    // Ticks de jeu formattés en minutes/secondes
    static private String formatTimeFromTicks(int gameTicks) {
        final int seconds = gameTicks/60;
        final int minutes = seconds/60;

        return String.format("%02d:%02d", minutes, seconds % 60);
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
    }


}
