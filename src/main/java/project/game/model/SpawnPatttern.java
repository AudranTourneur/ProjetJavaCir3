package project.game.model;

public abstract class SpawnPatttern {

	WorldModel model;
	protected double speed;

	SpawnPatttern(WorldModel model,double speed) {
		this.model = model;
		this.speed=speed;
	}

	abstract void spawn(ProjectileSpawner spawner);
}
