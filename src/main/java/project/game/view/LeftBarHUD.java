package project.game.view;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import project.game.model.FloatPosition;
import project.game.model.Player;

public class LeftBarHUD {
    // x et y entre 0% et x0%
    static FloatPosition toScreenPosition(GameView view, double x, double y) {
        return new FloatPosition((x / 100.0) * view.offsetX, y / 100.0 * view.stage.getHeight());
    }

    static void drawText(GraphicsContext ctx, String text, FloatPosition pos) {
        ctx.fillText(text, pos.x, pos.y);
    }

    static void drawEmoji(GameView view, String emojiKey, FloatPosition position) {
        //final screenPos = new FloatPosition(0, 0);
        //view.ctx.drawImage(view.spriteMap.get(emojiKey));
    }

    public static void drawLeftBar(GameView view) {
        GraphicsContext ctx = view.ctx;
        ctx.setFont(Font.font("System", view.offsetX / 8));
        ctx.setFill(Color.RED);

        final double x = 5;



        drawEmoji(view, "skull", new FloatPosition(0, 10));
        drawText(ctx, "Deaths : " + view.world.player.deaths, toScreenPosition(view, x, 20));



        drawText(ctx, "Stamina : " + (int) ((double) view.world.player.stamina / Player.MAX_STAMINA * 100.0) + "%", toScreenPosition(view, x, 30));
        drawText(ctx, "Progress : " + view.world.getCompletionPercent() + "%", toScreenPosition(view, x, 40));
        drawText(ctx, "Score : " + view.world.player.getScore(), toScreenPosition(view, x, 50));
        drawText(ctx, "Est. time : " + view.world.getCurrentTick() / 60 + "s", toScreenPosition(view, x, 60));
        drawText(ctx, "Food left : " + view.world.levelProgressionManager.getFoodRemaining(), toScreenPosition(view, x, 70));
        drawText(ctx, "Level : " + view.world.levelProgressionManager.currentLevel, toScreenPosition(view, x, 80));

    }
}
