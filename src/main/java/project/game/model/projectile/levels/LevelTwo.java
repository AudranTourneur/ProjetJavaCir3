package project.game.model.projectile.levels;

import project.game.model.general.WorldModel;
import project.game.model.projectile.ProjectileWavesManager;

public class LevelTwo {
	public static void manageLevelTwo(ProjectileWavesManager man) {
		final WorldModel model = man.getModel();
		if (model.getCurrentTick() % (2 * 60) == 0) {
			man.launchRandomTarget(0.1);
		}
	}
}
