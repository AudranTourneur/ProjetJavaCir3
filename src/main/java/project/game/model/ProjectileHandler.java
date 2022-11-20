package project.game.model;

public class ProjectileHandler {
    WorldModel model;

    ProjectileHandler(WorldModel model) {
        this.model = model;
    }

    void manageProjectileSpawn() {

        if (model.getCurrentTick() % 150 == 0) {
            final IntPosition randomPosition = new IntPosition((int) (Math.random() *
                    GridMap.TILES_WIDTH),
                    (int) (Math.random() * GridMap.TILES_HEIGHT));

            // addSpawner(randomPosition);
            addStarSpawner(randomPosition);

        }

        if (model.getCurrentTick() % 150 == 74) {
            final IntPosition randomPosition = new IntPosition((int) (Math.random() *
                    GridMap.TILES_WIDTH),
                    (int) (Math.random() * GridMap.TILES_HEIGHT));

            // addSpawner(randomPosition);
            addTargetSpawner(randomPosition);
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
        //double xDiff = (player.position.x + 0.5) - (projectile.position.x + Projectile.RADIUS_SIZE);
        //double yDiff = (player.position.y + 0.5) - (projectile.position.y + Projectile.RADIUS_SIZE);

        double xDiff = (player.position.x) - (projectile.position.x);
        double yDiff = (player.position.y) - (projectile.position.y);

        double distance = Math.sqrt((Math.pow(xDiff, 2) + Math.pow(yDiff, 2)));

        return distance < (Projectile.RADIUS_SIZE + Player.RADIUS_HITBOX_SIZE);
    }

    void addStarSpawner(IntPosition pos) {
        model.addEntity(new ProjectileSpawner(pos.toFloat().translate(0.5f), new SpawnStarPattern(model)));
    }

    void addTargetSpawner(IntPosition pos) {
        model.addEntity(new ProjectileSpawner(pos.toFloat().translate(0.5f), new SpawnTargetPattern(model)));
    }

}
