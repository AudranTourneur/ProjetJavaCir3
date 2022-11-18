package project.game.model;

public class Projectile extends Entity {

    FloatPosition trajectory;

    Projectile(FloatPosition initialPos, FloatPosition trajectory) {
        this.position = initialPos;
        this.trajectory = trajectory;
    }

    @Override
    public void move(double delta) {
        final float speed = 0.05f;
        this.position.x += speed * trajectory.x;
        this.position.y += speed * trajectory.y;

        if (this.position.x < 0 || this.position.x  >= GridMap.NUMBER_OF_TILES || this.position.y < 0 || this.position.y >= GridMap.NUMBER_OF_TILES) { 
            this.markedForDeletion = true;
        }
    }

}
