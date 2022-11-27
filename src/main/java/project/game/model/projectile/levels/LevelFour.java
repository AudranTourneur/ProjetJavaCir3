package project.game.model.projectile.levels;

import project.game.model.general.WorldModel;
import project.game.model.projectile.ProjectileWavesManager;

public class LevelFour {

	public static void manageLevelFour(ProjectileWavesManager man) {
		final WorldModel model = man.getModel();
		if (model.getCurrentTick() % 60 == 0) {
			man.launchRandomTarget(0.05);
		}
		if (model.getCurrentTick() % (45 * 60) == (5 * 60)) {
			man.launchTargetedEncirclementAroundPlayer(1, 0.025, 2);
		}

		if (model.getCurrentTick() % (45 * 60) == (10 * 60)) {
			man.launchTargetedEncirclementAroundPlayer(1, 0.035, 3);
		}

		if (model.getCurrentTick() % (45 * 60) == (15 * 60)) {
			man.launchTargetedEncirclementAroundPlayer(1, 0.045, 4);
		}
		if (model.getCurrentTick() % (45 * 60) == (20 * 60)) {
			man.launchTargetedEncirclementAroundPlayer(1, 0.05, 5);
		}
		if (model.getCurrentTick() % (45 * 60) == (25 * 60)) {
			man.launchTargetedEncirclementAroundPlayer(1, 0.045, 4);
		}
		if (model.getCurrentTick() % (45 * 60) == (35 * 60)) {
			man.launchTargetedEncirclementAroundPlayer(1, 0.035, 3);
		}
		if (model.getCurrentTick() % (45 * 60) == (40 * 60)) {
			man.launchTargetedEncirclementAroundPlayer(1, 0.025, 2);
		}

	}
	
}
