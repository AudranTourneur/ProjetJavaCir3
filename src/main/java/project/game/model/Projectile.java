//fichier pour creer et gérer les projectiles (trajectoires, position, vitesse)

package project.game.model;

public class Projectile extends Entity {

    FloatPosition trajectory;
    public final static float RADIUS_SIZE = 0.35f;

    Projectile(FloatPosition initialPos, FloatPosition trajectory) {
        this.position = initialPos.copy();
        this.trajectory = trajectory.normalize();
    }

    @Override
    public void move(double delta) {
        final double speed = 0.025;

        final double dx = speed * (double) trajectory.x;
        final double dy = speed * (double) trajectory.y;

        this.position.x += dx;
        this.position.y += dy;

        if (this.position.x < 0 || this.position.x >= GridMap.TILES_WIDTH || this.position.y < 0
                || this.position.y >= GridMap.TILES_HEIGHT) {
            this.markedForDeletion = true;
        }
    }

    static void spawnProjectile(WorldModel model, FloatPosition initialPos, FloatPosition trajectory) {
        //model.entities.add(new Projectile(initialPos, trajectory));
        model.addEntity(new Projectile(initialPos, trajectory));
    }

}
