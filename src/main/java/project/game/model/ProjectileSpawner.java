package project.game.model;

//Crée un pattern de projectile en une position puis disparaît
public class ProjectileSpawner extends Entity {

	SpawnPatttern pattern;
	int ticksToLive = 5 * 60;

	public int getTicksToLive() {
		return ticksToLive;
	}

	ProjectileSpawner(FloatPosition pos, SpawnPatttern pattern) {
		this.position = pos.copy();
		this.pattern = pattern;
	}

	@Override
	public void move(double delta) {
		ticksToLive--;

		if (ticksToLive <= 0) {
			this.markedForDeletion = true;

			pattern.spawn(this);
		}
	}
}
