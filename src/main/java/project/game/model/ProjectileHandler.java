

package project.game.model;

//Gesion de la collision des projectiles avec la joueuer
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
    

}
