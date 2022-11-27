//fichier qui s'occupe des collisions entre les projectiles et le joueur

package project.game.model;

public class ProjectileHandler {
    WorldModel model;

    ProjectileHandler(WorldModel model) {
        this.model = model;
    }

    void manageProjectileCollisions() {
        for (Entity entity : model.entities) {
            if (entity instanceof Projectile) {
                Projectile projectile = (Projectile) entity;
                if (checkPlayerProjectileCollision(model.player, projectile)) {
                    model.player.hit();
                    projectile.markedForDeletion = true;
                }

            }
        }
    }

    static boolean checkPlayerProjectileCollision(Player player, Projectile projectile) { // verifie les positions du joueur et du projectile
        double xDiff = (player.position.x) - (projectile.position.x);
        double yDiff = (player.position.y) - (projectile.position.y);

        double distance = Math.sqrt((Math.pow(xDiff, 2) + Math.pow(yDiff, 2)));

        return distance < (Projectile.RADIUS_SIZE + Player.RADIUS_HITBOX_SIZE);
    }
    /*Code redondant je pense on peut le suprimer tout ce fait deja dans projectile wave manager
    void addStarSpawner(IntPosition pos,double speed) {
        model.addEntity(new ProjectileSpawner(pos.toFloat().translate(0.5f), new SpawnStarPattern(model,speed)));
    }

    void addTargetSpawner(IntPosition pos,double speed) {
        model.addEntity(new ProjectileSpawner(pos.toFloat().translate(0.5f), new SpawnTargetPattern(model,speed)));
    }
    */

}
