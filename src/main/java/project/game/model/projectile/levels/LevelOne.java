package project.game.model.projectile.levels;

import project.game.model.general.WorldModel;
import project.game.model.projectile.ProjectileWavesManager;

public class LevelOne {
	public static void manageLevelOne(ProjectileWavesManager man) {
		final WorldModel model = man.getModel();
		if (model.getCurrentTick() % (45 * 60) == (3 * 60)) {
			man.launchRandomStar(0.01);
		}
		if (model.getCurrentTick() % (45 * 60) == (7 * 60)) {
			man.launchRandomStar(0.02);
		}
		if (model.getCurrentTick() % (45 * 60) == (11 * 60)) {
			man.launchRandomStar(0.03);
		}
		if (model.getCurrentTick() % (45 * 60) == (15 * 60)) {
			man.launchRandomStar(0.035);
		}
		if (model.getCurrentTick() % (45 * 60) == (19 * 60)) {
			man.launchRandomStar(0.038);
		}
		if (model.getCurrentTick() % (45 * 60) == (23 * 60)) {
			man.launchRandomStar(0.040);
		}
		if (model.getCurrentTick() % (45 * 60) == (27 * 60)) {
			man.launchRandomStar(0.042);
		}
		if (model.getCurrentTick() % (45 * 60) == (31 * 60)) {
			man.launchRandomStar(0.045);
		}
		if (model.getCurrentTick() % (45 * 60) == (35 * 60)) {
			man.launchRandomStar(0.050);
		}
		if (model.getCurrentTick() % (45 * 60) == (39 * 60)) {
			man.launchRandomStar(0.052);
		}


	}	
}
