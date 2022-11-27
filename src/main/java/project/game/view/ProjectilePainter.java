package project.game.view;

import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import project.game.model.Entity;
import project.game.model.Projectile;
import project.game.model.ProjectileSpawner;

public class ProjectilePainter {

    // Affichage d'un projectile (en rouge)
    private static void drawProjectile(GameView view, float x, float y) {
        view.ctx.setFill(Color.RED);

        final DisplayData dispData = new DisplayData(view, x, y, Projectile.RADIUS_SIZE);
        view.ctx.fillOval(dispData.x, dispData.y, dispData.width, dispData.height);
    }

    // Affichage des projectiles
    public static void drawProjectiles(GameView view) {
        for (Entity entity : view.world.entities) {
            if (entity instanceof Projectile) {
                drawProjectile(view, entity.position.x, entity.position.y);
            }
        }
    }

    // Affichage d'un avertissement prevenant l'arrivés de futurs projectiles
    // (panneau de signalisation en jaune)
    private static void drawSpawner(GameView view, ProjectileSpawner spawner) {
        Image img = view.spriteMap.get("warning");
        if (img == null)
            return;

        final DisplayData dispData = new DisplayData(view, spawner.position, 1);
        if (spawner.getTicksToLive() % 60 < 30)
            view.ctx.drawImage(img, dispData.x, dispData.y, dispData.width, dispData.height);
    }

    // Affichage des avertissements
    public static void drawSpawners(GameView view) {
        for (Entity entity : view.world.entities)
            if (entity instanceof ProjectileSpawner)
                drawSpawner(view, (ProjectileSpawner) entity);
    }

}
