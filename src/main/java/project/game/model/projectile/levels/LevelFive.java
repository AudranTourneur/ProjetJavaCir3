package project.game.model.projectile.levels;

import project.game.model.general.GridMap;
import project.game.model.general.WorldModel;
import project.game.model.projectile.ProjectileWavesManager;

public class LevelFive {
	public static void manageLevelFive(ProjectileWavesManager man) {
		final WorldModel model = man.getModel();
		if (model.getCurrentTick() % (45 * 60) == (3 * 60)) {
			man.shootWallLeft(GridMap.TILES_HEIGHT / 2, 0.05);
		}
		if (model.getCurrentTick() % (45 * 60) == (5 * 60)) {
			man.launchTargetedEncirclementAroundPlayer(1, 0.055, 3);
		}
		if (model.getCurrentTick() % (45 * 60) == (8 * 60)) {
			man.launchUnidirectionEncirclement(1, 0.012);
		}
		if (model.getCurrentTick() % (45 * 60) == (10 * 60)) {
			for (int i = 0; i < 20; i++) {
				man.launchRandomTarget(0.08);
			}
		}
		if (model.getCurrentTick() % (45 * 60) == (13 * 60)) {
			man.shootWallRight(GridMap.TILES_HEIGHT / 2, 0.05);
		}
		if (model.getCurrentTick() % (45 * 60) == (15 * 60)) {
			man.launchTargetedEncirclementAroundPlayer(1, 0.055, 3);
		}
		if (model.getCurrentTick() % (45 * 60) == (18 * 60)) {
			man.launchUnidirectionEncirclement(1, 0.012);
		}
		if (model.getCurrentTick() % (45 * 60) == (20 * 60)) {
			man.shootWallTop(-10, 0.05);
		}
		if (model.getCurrentTick() % (45 * 60) == (22 * 60)) {
			man.shootWallTop(-10, 0.05);
			for (int i = 0; i < 5; i++) {
				man.launchRandomStar(0.05);
				man.launchRandomTarget(0.08);
			}
		}
		if (model.getCurrentTick() % (45 * 60) == (24 * 60)) {
			man.shootWallTop(-10, 0.05);
		}
		if (model.getCurrentTick() % (45 * 60) == (27 * 60)) {
			man.launchTargetedEncirclementAroundPlayer(1, 0.01, 4);
		}
		if (model.getCurrentTick() % (45 * 60) == (30 * 60) || model.getCurrentTick() % (45 * 60) == (35 * 60)
				|| model.getCurrentTick() % (45 * 60) == (40 * 60)) {
			man.shootWallBottom(GridMap.TILES_WIDTH / 2, 0.03);
			man.shootWallTop(GridMap.TILES_WIDTH / 2, 0.03);
			man.shootWallRight(GridMap.TILES_HEIGHT / 2, 0.03);
			man.shootWallLeft(GridMap.TILES_HEIGHT / 2, 0.03);
		}
		if (model.getCurrentTick() % (45 * 60) == (41 * 60) || model.getCurrentTick() % (45 * 60) == (43 * 60)
				|| model.getCurrentTick() % (45 * 60) == (44 * 60)) {
			man.launchUnidirectionEncirclement(4, 0.025);
		}
	}
}
