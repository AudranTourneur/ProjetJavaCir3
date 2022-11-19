package project.game.model;

public class ProjectileSpawner extends Entity {

	SpawnPatttern pattern;

	int ticksToLive = 5 * 60;

	@Override
	public void move(double delta) {
		ticksToLive--;

		if (ticksToLive <= 0) {
			this.markedForDeletion = true;
		}
	}
}