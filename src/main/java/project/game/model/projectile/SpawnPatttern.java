package project.game.model.projectile;

import project.game.model.general.WorldModel;

/*Nous permet de créer des pattern de projectiles */
public abstract class SpawnPatttern {

	WorldModel model;
	protected double speed;

	SpawnPatttern(WorldModel model,double speed) {
		this.model = model;
		this.speed=speed;
	}

	abstract void spawn(ProjectileSpawner spawner);
}
