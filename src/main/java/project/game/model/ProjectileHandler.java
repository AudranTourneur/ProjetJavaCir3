package project.game.model;

public class ProjectileHandler {
    WorldModel model;

    ProjectileHandler(WorldModel model) {
        this.model = model;
    }

    void spawnProjectile(FloatPosition initialPos, FloatPosition trajectory) {
        model.entities.add(new Projectile(initialPos, trajectory));
    }

    void manageProjectileSpawn() {

        // if (model.compteur == 100) {
        if (model.compteur % 100 == 0) {
            final FloatPosition randomPosition = new FloatPosition(Math.random() * GridMap.TILES_WIDTH,
                    Math.random() * GridMap.TILES_HEIGHT);

            spawnWave((int) randomPosition.x, (int) randomPosition.y);

            addSpawner((int) randomPosition.x, (int) randomPosition.y);



            // spawnProjectile(
            // new FloatPosition(Math.random() * GridMap.TILES_WIDTH, Math.random() *
            // GridMap.TILES_HEIGHT),
            // new FloatPosition(2 * Math.random() - 1, 2 * Math.random() - 1).normalize());

        }
    }

    void manageProjectileCollisions() {
        for (Entity entity : model.entities) {
            if (entity instanceof Projectile) {
                Projectile projectile = (Projectile) entity;
                if (checkPlayerProjectileCollision(model.player, projectile)) {
                    // System.out.println("COLLISION!!!");
                    model.player.deaths++;
                    projectile.markedForDeletion = true;
                    // System.out.println("Player deaths: " + model.player.deaths);
                }

            }
        }
    }

    static boolean checkPlayerProjectileCollision(Player player, Projectile projectile) {
        double xDiff = (player.position.x + 0.5) - (projectile.position.x + Projectile.RADIUS_SIZE);
        double yDiff = (player.position.y + 0.5) - (projectile.position.y + Projectile.RADIUS_SIZE);

        double distance = Math.sqrt((Math.pow(xDiff, 2) + Math.pow(yDiff, 2)));

        return distance < (Projectile.RADIUS_SIZE + Player.RADIUS_HITBOX_SIZE);
    }

    void spawnWave(int tileX, int tileY) {
        System.out.println("spawn wave " + tileX + " " + tileY);
        final FloatPosition pos = new FloatPosition(tileX + 0.5, tileY + 0.5);

        spawnProjectile(pos, new FloatPosition(1, 0));
        spawnProjectile(pos, new FloatPosition(-1, 0));
        spawnProjectile(pos, new FloatPosition(0, 1));
        spawnProjectile(pos, new FloatPosition(0, -1));

        spawnProjectile(pos, new FloatPosition(Math.sqrt(2), Math.sqrt(2)));
        spawnProjectile(pos, new FloatPosition(-Math.sqrt(2), Math.sqrt(2)));
        spawnProjectile(pos, new FloatPosition(-Math.sqrt(2), -Math.sqrt(2)));
        spawnProjectile(pos, new FloatPosition(Math.sqrt(2), -Math.sqrt(2)));
    }

    void addSpawner(int tileX, int tileY) {

    }

}
