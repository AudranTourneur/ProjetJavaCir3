package project.game.model.projectile;

import project.game.model.general.WorldModel;
import project.game.model.utils.FloatPosition;

/*Un projectile qui est orienté vers le joueuer */
public class SpawnTargetPattern extends SpawnPatttern {

	SpawnTargetPattern(WorldModel model,double speed) {
		super(model,speed);
	}

	@Override
	void spawn(ProjectileSpawner spawner) {
		final FloatPosition pos = spawner.position;

		Projectile.spawnProjectile(model, pos, model.player.position.minus(spawner.position).normalize(),speed);
	}

}
