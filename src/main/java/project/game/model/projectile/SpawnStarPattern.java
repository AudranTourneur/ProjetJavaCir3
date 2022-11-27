package project.game.model.projectile;

import project.game.model.general.WorldModel;
import project.game.model.utils.FloatPosition;

/*Un pattern de projectile en forme d'étoile */
public class SpawnStarPattern extends SpawnPatttern {

        SpawnStarPattern(WorldModel model,double speed) {
                super(model,speed);
        }

        @Override
        void spawn(ProjectileSpawner spawner) {
                final FloatPosition pos = spawner.position;

                Projectile.spawnProjectile(model, pos, new FloatPosition(1, 0),speed);
                Projectile.spawnProjectile(model, pos, new FloatPosition(-1, 0),speed);
                Projectile.spawnProjectile(model, pos, new FloatPosition(0, 1),speed);
                Projectile.spawnProjectile(model, pos, new FloatPosition(0, -1),speed);

                Projectile.spawnProjectile(model, pos, new FloatPosition(Math.sqrt(2), Math.sqrt(2)),speed);
                Projectile.spawnProjectile(model, pos, new FloatPosition(-Math.sqrt(2), Math.sqrt(2)),speed);
                Projectile.spawnProjectile(model, pos, new FloatPosition(-Math.sqrt(2), -Math.sqrt(2)),speed);
                Projectile.spawnProjectile(model, pos, new FloatPosition(Math.sqrt(2), -Math.sqrt(2)),speed);
        }
}
