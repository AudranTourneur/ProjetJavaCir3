package project.game.model;

public class ProjectileHandler {
    WorldModel model;

    public final static float RADIUS_SIZE = 0.25f;

    ProjectileHandler(WorldModel model) {
        this.model = model;
    }

    void spawnProjectile(FloatPosition initialPos, FloatPosition trajectory) {
        model.entities.add(new Projectile(initialPos, trajectory));
    }

    void manageProjectileSpawn() {
        if (Math.random() < 0.1) {
            spawnProjectile(new FloatPosition(Math.random() * 15, Math.random() * 15),
                    new FloatPosition(2 * Math.random() - 1, 2 * Math.random() - 1).normalize());
        }
    }

    void manageProjectileCollisions() {
        for (Entity entity : model.entities) {
            if (entity instanceof Projectile) {
                Projectile projectile = (Projectile) entity;
                if (checkPlayerProjectileCollision(model.player, projectile)) {
                    //System.out.println("COLLISION!!!");
                    model.player.deaths++;
                    projectile.markedForDeletion = true;
                    //System.out.println("Player deaths: " + model.player.deaths);
                }

            }
        }
    }

    static boolean checkPlayerProjectileCollision(Player player, Projectile projectile) {
        double xDiff = (player.position.x + 0.5) - (projectile.position.x + RADIUS_SIZE);
        double yDiff = (player.position.y + 0.5) - (projectile.position.y + RADIUS_SIZE);

        double distance = Math.sqrt((Math.pow(xDiff, 2) + Math.pow(yDiff, 2)));

        return distance < (RADIUS_SIZE + Player.RADIUS_HITBOX_SIZE);
    }

}
