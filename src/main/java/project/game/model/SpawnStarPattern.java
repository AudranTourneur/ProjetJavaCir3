package project.game.model;

public class SpawnStarPattern extends SpawnPatttern {

	SpawnStarPattern(WorldModel model) {
		super(model);
	}

	@Override
	void spawn(ProjectileSpawner spawner) {
        final FloatPosition pos = spawner.position;

        Projectile.spawnProjectile(model, pos, new FloatPosition(1, 0));
        Projectile.spawnProjectile(model, pos, new FloatPosition(-1, 0));
        Projectile.spawnProjectile(model, pos, new FloatPosition(0, 1));
        Projectile.spawnProjectile(model, pos, new FloatPosition(0, -1));

        Projectile.spawnProjectile(model, pos, new FloatPosition(Math.sqrt(2), Math.sqrt(2)));
        Projectile.spawnProjectile(model, pos, new FloatPosition(-Math.sqrt(2), Math.sqrt(2)));
        Projectile.spawnProjectile(model, pos, new FloatPosition(-Math.sqrt(2), -Math.sqrt(2)));
        Projectile.spawnProjectile(model, pos, new FloatPosition(Math.sqrt(2), -Math.sqrt(2)));
	}	
}
