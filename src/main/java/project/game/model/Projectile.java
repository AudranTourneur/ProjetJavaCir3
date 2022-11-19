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
        final double speed = 0.035;

        final double dx = speed * (double) trajectory.x;
        final double dy = speed * (double) trajectory.y;

        //this.position.x += dx;
        //this.position.y += dy;

        this.position.x += dx;
        this.position.y += dy;

        System.out.println("DX " + dx + " | DY " + dy);

        if (this.position.x < 0 || this.position.x >= GridMap.TILES_WIDTH || this.position.y < 0
                || this.position.y >= GridMap.TILES_HEIGHT) {
            System.out.println("projectile deletion");
            this.markedForDeletion = true;
        }
    }

}
