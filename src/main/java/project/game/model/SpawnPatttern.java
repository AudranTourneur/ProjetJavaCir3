package project.game.model;

public abstract class SpawnPatttern {

	WorldModel model;

	SpawnPatttern(WorldModel model) {
		this.model = model;
	}

	abstract void spawn(ProjectileSpawner spawner);
}
