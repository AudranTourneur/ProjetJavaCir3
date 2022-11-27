package project.game.model.projectile.levels;

import project.game.model.general.GridMap;
import project.game.model.general.WorldModel;
import project.game.model.projectile.ProjectileWavesManager;
import project.game.model.utils.IntPosition;

/*
 * Gestion du niveau 3
 * dans notre jeu chaque pattern de projectile dure 45s et se répètent
 * comme dans une seconde on a 60 ticks on multiplie par 60 pour réfléchir en
 * secondes
 */
public class LevelThree {
	public static void manageLevelThree(ProjectileWavesManager man) {
		final WorldModel model = man.getModel();
		double speed = 0.02;
		// Fais apparatitre les spawners en attente
		if (model.getCurrentTick() % 10 == 0 && man.queuedSpawners.size() > 0) {
			man.shootQueuedSpawners(0);
		}
		if (!man.startLevelOne) {
			man.addStarSpawner(new IntPosition(5, 5), speed);
			man.addStarSpawner(new IntPosition(GridMap.TILES_WIDTH - 5, 5), speed);
			man.addStarSpawner(new IntPosition(5, GridMap.TILES_HEIGHT - 5), speed);
			man.addStarSpawner(new IntPosition(GridMap.TILES_WIDTH - 5, GridMap.TILES_HEIGHT - 5), speed);
			man.startLevelOne = true;
		}
		// en une seconde on a 60 tick de jeu donc on multiplie par 60 pour recuperer
		// une unité de temps en s
		if (model.getCurrentTick() % (45 * 60) == (5 * 60)) {
			man.shootWallLeft(5, speed * 2);
		}
		if (model.getCurrentTick() % (45 * 60) == (8 * 60)) {
			man.shootWallLeft(8, speed * 2);
		}
		if (model.getCurrentTick() % (45 * 60) == (11 * 60)) {
			man.shootWallLeft(11, speed * 2);
		}
		if (model.getCurrentTick() % (45 * 60) == (14 * 60)) {
			man.shootWallLeft(14, speed * 2);
		}
		if (model.getCurrentTick() % (45 * 60) == (20 * 60)) {
			man.launchTargetedEncirclement(5, 0.08);
		}
		if (model.getCurrentTick() % (45 * 60) == (31 * 60)) {
			man.shootWallTop(5, speed * 2);
		}
		if (model.getCurrentTick() % (45 * 60) == (34 * 60)) {
			man.shootWallTop(8, speed * 2);
		}
		if (model.getCurrentTick() % (45 * 60) == (37 * 60)) {
			man.shootWallTop(11, speed * 2);
		}
		if (model.getCurrentTick() % (45 * 60) == (40 * 60)) {
			man.shootWallTop(14, speed * 2);
		}
		if (model.getCurrentTick() % (45 * 60) == 0) {
			man.startLevelOne = false;
		}

	}

}
