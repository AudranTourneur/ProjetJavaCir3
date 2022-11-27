package project.game.model;

public class SpawnStarPattern extends SpawnPatttern {

        SpawnStarPattern(WorldModel model,double speed) {
                super(model,speed);
        }
// donne la futur position  des projectiles 
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
