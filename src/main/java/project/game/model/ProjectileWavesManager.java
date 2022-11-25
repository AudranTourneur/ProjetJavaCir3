// gere la vague des projectiles (le nombre augmente en fonction du niveau)

package project.game.model;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.function.Consumer;

import javafx.util.Pair;

public class ProjectileWavesManager {
	WorldModel model;

	ProjectileWavesManager(WorldModel model) {
		this.model = model;
	}

	void dispatchEvents() {

		if (model.levelProgressionManager.getCurrentLevel() == 0) {
			manageLevelZero();
		} else if (model.levelProgressionManager.getCurrentLevel() == 1) {
			manageLevelOne();
		}

		if (model.getCurrentTick() >= 4 * 60 * 60)
			return;

		if (model.getCurrentTick() % 10 == 0 && queuedSpawners.size() > 0) {
			queuedSpawners.get(0).accept(null);
			queuedSpawners.remove(0);
		}

		if (model.getCurrentTick() == 15 * 60) {
			for (int i = 0; i < 5; i++)
				launchRandomTarget();
		}

		if (model.getCurrentTick() % 300 == 150) {
			launchRandomTarget();
		}

		if (model.getCurrentTick() > 40 * 60 && model.getCurrentTick() % 300 == 0) {
			launchRandomStar();
		}

		if (model.getCurrentTick() == 100 * 60) {
			for (int i = 0; i < 10; i++)
				launchRandomTarget();
		}

		if (model.getCurrentTick() > 1.5 * 60 * 60 && model.getCurrentTick() % 200 == 0) {
			launchRandomStar();
		}

		if (model.getCurrentTick() > 2 * 60 * 60 && model.getCurrentTick() % 500 == 0) {
			launchRandomStar();
		}

		if (model.getCurrentTick() > 3 * 60 * 60 && model.getCurrentTick() % 350 == 0) {
			launchRandomTarget();
			launchRandomStar();
		}

		if (model.getCurrentTick() == (37 * 60)) {
			launchUnidirectionEncirclement(5);
			// launchTargetedEncirclement();
			for (int i = 0; i < 10; i++)
				launchRandomStar();
		}

		if (model.getCurrentTick() == ((116 - 5) * 60)) {
			launchTargetedEncirclement(3);
			for (int i = 0; i < 15; i++) {
				launchRandomStar();
			}
		}

		if (model.getCurrentTick() == 170 * 60) {
			launchUnidirectionEncirclement(3);
		}

		if (model.getCurrentTick() == 190 * 60) {
			launchUnidirectionEncirclement(3);
		}

		if (model.getCurrentTick() == 220 * 60) {
			launchTargetedEncirclement(2);
		}

	}

	private void manageLevelZero() {
		if (model.getCurrentTick() % 300 == 150) {
			launchRandomTarget();
		}
	}

	private void manageLevelOne() {
		if (model.getCurrentTick() % 300 == 150) {
			launchRandomTarget();
		}
		if (model.getCurrentTick() % 300 == 0) {
			launchRandomStar();
		}
	}

	static IntPosition getRandomPosition() {
		return new IntPosition((int) (Math.random() *
				GridMap.TILES_WIDTH),
				(int) (Math.random() * GridMap.TILES_HEIGHT));
	}

	void addStarSpawner(IntPosition pos) {
		model.addEntity(new ProjectileSpawner(pos.toFloat().translate(0.5f), new SpawnStarPattern(model)));
	}

	void addTargetSpawner(IntPosition pos) {
		model.addEntity(new ProjectileSpawner(pos.toFloat().translate(0.5f), new SpawnTargetPattern(model)));
	}

	void addUnidirectionSpawner(IntPosition pos, Direction dir) {
		model.addEntity(
				new ProjectileSpawner(pos.toFloat().translate(0.5f), new SpawnUnidirectionalPattern(model, dir)));
	}

	void launchRandomStar() {
		addStarSpawner(getRandomPosition());
	}

	void launchRandomTarget() {
		addTargetSpawner(getRandomPosition());
	}

	ArrayList<Consumer<Void>> queuedSpawners = new ArrayList<>();

	void launchUnidirectionEncirclement(int spacing) {
		LinkedHashSet<Pair<Direction, IntPosition>> set = new LinkedHashSet<>();
		for (int i = 0; i < GridMap.TILES_WIDTH; i += spacing)
			set.add(new Pair<Direction, IntPosition>(Direction.DOWN, new IntPosition(i, 0)));

		for (int i = 0; i < GridMap.TILES_HEIGHT; i += spacing)
			set.add(new Pair<Direction, IntPosition>(Direction.LEFT, new IntPosition(GridMap.TILES_WIDTH - 1, i)));

		for (int i = GridMap.TILES_WIDTH - 1; i >= 0; i -= spacing)
			set.add(new Pair<Direction, IntPosition>(Direction.UP, new IntPosition(i, GridMap.TILES_HEIGHT - 1)));

		for (int i = GridMap.TILES_HEIGHT - 1; i >= 0; i -= spacing)
			set.add(new Pair<Direction, IntPosition>(Direction.RIGHT, new IntPosition(0, i)));

		for (var dirAndPos : set) {
			queuedSpawners.add((Consumer<Void>) x -> {
				addUnidirectionSpawner(dirAndPos.getValue(), dirAndPos.getKey());
			});
		}
	}

	void launchTargetedEncirclement(int spacing) {
		LinkedHashSet<IntPosition> set = new LinkedHashSet<>();
		for (int i = 0; i < GridMap.TILES_WIDTH; i += spacing)
			set.add(new IntPosition(i, 0));

		for (int i = 0; i < GridMap.TILES_HEIGHT; i += spacing)
			set.add(new IntPosition(GridMap.TILES_WIDTH - 1, i));

		for (int i = GridMap.TILES_WIDTH - 1; i >= 0; i -= spacing)
			set.add(new IntPosition(i, GridMap.TILES_HEIGHT - 1));

		for (int i = GridMap.TILES_HEIGHT - 1; i >= 0; i -= spacing)
			set.add(new IntPosition(0, i));

		for (IntPosition pos : set) {
			queuedSpawners.add((Consumer<Void>) x -> {
				addTargetSpawner(pos);
			});
		}
	}
}
