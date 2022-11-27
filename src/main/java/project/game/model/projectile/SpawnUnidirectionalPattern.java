package project.game.model.projectile;

import project.game.model.general.WorldModel;
import project.game.model.utils.Direction;
import project.game.model.utils.FloatPosition;

/*Projectile qui va seulement dans un sens */
public class SpawnUnidirectionalPattern extends SpawnPatttern {

	Direction direction;

	SpawnUnidirectionalPattern(WorldModel model, Direction direction,double speed) {
		super(model,speed);
		this.direction = direction;
	}

	@Override
	void spawn(ProjectileSpawner spawner) {
		final FloatPosition pos = spawner.position;

		Projectile.spawnProjectile(model, pos, new FloatPosition(direction.getX(), direction.getY()),speed);
	}

}
