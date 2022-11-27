package project.game.view;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import project.game.model.FloatPosition;
import project.game.model.Player;

public class LeftBarHUD {
    // x et y entre 0% et 100%
    static FloatPosition toScreenPosition(GameView view, double x, double y) {
        return new FloatPosition((x / 100.0) * view.offsetX, y / 100.0 * view.stage.getHeight());
    }

    static void drawText(GraphicsContext ctx, String text, FloatPosition pos) {
        ctx.fillText(text, pos.x, pos.y);
    }

    // position et size entre 0% et 100%
    static void drawEmoji(GameView view, String emojiKey, FloatPosition position, double size) {
        final FloatPosition screenPos = toScreenPosition(view, position.x - size / 2, position.y - size / 2);
        final FloatPosition sizeScreen = toScreenPosition(view, size, size);
        view.ctx.drawImage(view.spriteMap.get(emojiKey), screenPos.x, screenPos.y, Math.min(sizeScreen.x, sizeScreen.y),
                Math.min(sizeScreen.x, sizeScreen.y));
    }

    public static void drawLeftBar(GameView view) {
        GraphicsContext ctx = view.ctx;
        ctx.setFont(Font.font("System", Math.min(view.offsetX, view.stage.getHeight()) / 13));
        ctx.setFill(Color.RED);

        final double textPosX = 10;
        final double sizeEmoji = 8;
        final double emojiPosX = 4;

        drawEmoji(view, "skull", new FloatPosition(emojiPosX, 20), sizeEmoji);
        drawText(ctx, "Deaths : " + view.world.player.deaths, toScreenPosition(view, textPosX, 20));

        drawEmoji(view, "zap", new FloatPosition(emojiPosX, 30), sizeEmoji);
        drawText(ctx, "Stamina : " + (int) ((double) view.world.player.stamina / Player.MAX_STAMINA * 100.0) + "%",
                toScreenPosition(view, textPosX, 30));

        //drawEmoji(view, "zap", new FloatPosition(emojiPosX, 40), sizeEmoji);
        //drawText(ctx, "Progress : " + view.world.getCompletionPercent() + "%", toScreenPosition(view, textPosX, 40));

        drawEmoji(view, "zap", new FloatPosition(emojiPosX, 50), sizeEmoji);
        drawText(ctx, "Score : " + view.world.player.getScore(), toScreenPosition(view, textPosX, 50));

        drawEmoji(view, "hourglass", new FloatPosition(emojiPosX, 60), sizeEmoji);
        drawText(ctx, "Timer : " + view.world.getCurrentTick() / 60 + "s", toScreenPosition(view, textPosX, 60));

        drawEmoji(view, "apple", new FloatPosition(emojiPosX, 70), sizeEmoji);
        drawText(ctx, "Food left : " + view.world.levelProgressionManager.getFoodRemaining(),
                toScreenPosition(view, textPosX, 70));

        drawText(ctx, "Level : " + view.world.levelProgressionManager.currentLevel,
                toScreenPosition(view, textPosX, 80));

    }
}
