package project.game.model;

public class SpawnUnidirectionalPattern extends SpawnPatttern {

	Direction direction;

	SpawnUnidirectionalPattern(WorldModel model, Direction direction) {
		super(model);
		this.direction = direction;
	}

	@Override
	void spawn(ProjectileSpawner spawner) {
		final FloatPosition pos = spawner.position;

		Projectile.spawnProjectile(model, pos, new FloatPosition(direction.getX(), direction.getY()));
	}

}
