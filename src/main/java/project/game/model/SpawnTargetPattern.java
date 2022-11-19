package project.game.model;

public class SpawnTargetPattern extends SpawnPatttern {

	SpawnTargetPattern(WorldModel model) {
		super(model);
	}

	@Override
	void spawn(ProjectileSpawner spawner) {
        final FloatPosition pos = spawner.position;

        Projectile.spawnProjectile(model, pos, model.player.position.minus(spawner.position).normalize());
	}	

}
