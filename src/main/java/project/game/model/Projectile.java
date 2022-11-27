//fichier pour creer et gérer les projectiles (trajectoires, position, vitesse)

package project.game.model;


public class Projectile extends Entity {

    FloatPosition trajectory;
    public final static float RADIUS_SIZE = 0.35f;
    private final double speed;

    Projectile(FloatPosition initialPos, FloatPosition trajectory,double speed) {
        this.position = initialPos.copy();
        this.trajectory = trajectory.normalize();
        this.speed=speed;
    }

    @Override
    public void move(double delta) {
        

        final double dx = speed * (double) trajectory.x;
        final double dy = speed * (double) trajectory.y;

        this.position.x += dx;
        this.position.y += dy;

        if (this.position.x < 0 || this.position.x >= GridMap.TILES_WIDTH || this.position.y < 0
                || this.position.y >= GridMap.TILES_HEIGHT) {
            this.markedForDeletion = true;
        }
    }

    static void spawnProjectile(WorldModel model, FloatPosition initialPos, FloatPosition trajectory,double speed) {
        model.addEntity(new Projectile(initialPos, trajectory,speed));
    }

}
