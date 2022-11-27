package project.game.model.projectile;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.function.Consumer;

import javafx.util.Pair;
import project.game.model.general.GridMap;
import project.game.model.general.WorldModel;
import project.game.model.projectile.levels.LevelFive;
import project.game.model.projectile.levels.LevelFour;
import project.game.model.projectile.levels.LevelOne;
import project.game.model.projectile.levels.LevelThree;
import project.game.model.projectile.levels.LevelTwo;
import project.game.model.utils.Direction;
import project.game.model.utils.IntPosition;

//Gestion des vagues de projectiles en fonction du niveu actuel
public class ProjectileWavesManager {
	WorldModel model;

	public WorldModel getModel() {
		return model;
	}

	// Listes des spawners qui attendent à spawn
	public ArrayList<Consumer<Void>> queuedSpawners = new ArrayList<>();

	public ProjectileWavesManager(WorldModel model) {
		this.model = model;
	}

	// Appel la gestion du niveau actuel
	public void dispatchEvents() {
		if (model.levelProgressionManager.getCurrentLevel() == 0) {
			manageLevelOne();
		} else if (model.levelProgressionManager.getCurrentLevel() == 1) {
			manageLevelTwo();
		} else if (model.levelProgressionManager.getCurrentLevel() == 2) {
			manageLevelThree();
		} else if (model.levelProgressionManager.getCurrentLevel() == 3) {
			manageLevelFour();
		} else if (model.levelProgressionManager.getCurrentLevel() == 4) {
			manageLevelFive();
		}
	}

	
	public boolean startLevelOne = false;

	private void manageLevelOne() {
		LevelOne.manageLevelOne(this);
	}

	private void manageLevelTwo() {
		LevelTwo.manageLevelTwo(this);
	}

	private void manageLevelThree() {
		LevelThree.manageLevelThree(this);
	}

	private void manageLevelFour() {
		LevelFour.manageLevelFour(this);
	}

	private void manageLevelFive() {
		LevelFive.manageLevelFive(this);
	}

	static IntPosition getRandomPosition() {
		return new IntPosition((int) (Math.random() *
				GridMap.TILES_WIDTH),
				(int) (Math.random() * GridMap.TILES_HEIGHT));
	}

	public void addStarSpawner(IntPosition pos, double speed) {
		model.addEntity(new ProjectileSpawner(pos.toFloat().translate(0.5f), new SpawnStarPattern(model, speed)));
	}

	public void addTargetSpawner(IntPosition pos, double speed) {
		model.addEntity(new ProjectileSpawner(pos.toFloat().translate(0.5f), new SpawnTargetPattern(model, speed)));
	}

	public void addUnidirectionSpawner(IntPosition pos, Direction dir, double speed) {
		model.addEntity(
				new ProjectileSpawner(pos.toFloat().translate(0.5f),
						new SpawnUnidirectionalPattern(model, dir, speed)));
	}

	public void launchRandomStar(double speed) {
		addStarSpawner(getRandomPosition(), speed);
	}

	public void launchRandomTarget(double speed) {
		addTargetSpawner(getRandomPosition(), speed);
	}

	// tire x nombre de spawners qui sont dans l'array queuedspawners
	// si on met un x=0 on vide toute la queue
	// si on met un x negatif alors on ne fait rien
	public void shootQueuedSpawners(int x) {
		if (x == 0) {
			while (queuedSpawners.size() > 0) {
				queuedSpawners.get(0).accept(null);
				queuedSpawners.remove(0);
			}
		} else if (x > 0) {
			for (int i = 0; i < x; i++) {
				if (queuedSpawners.size() > 0) {
					queuedSpawners.get(0).accept(null);
					queuedSpawners.remove(0);
				}
			}
		}
	}

	public void launchUnidirectionEncirclement(int spacing, double speed) {
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
				addUnidirectionSpawner(dirAndPos.getValue(), dirAndPos.getKey(), speed);
			});
		}
	}

	// Spawn un carre autour du joueuer
	public void launchTargetedEncirclementAroundPlayer(int spacing, double speed, int distanceToPlayers) {
		LinkedHashSet<IntPosition> set = new LinkedHashSet<>();
		int playerX = model.player.getTilePosition().x;
		int playerY = model.player.getTilePosition().y;
		for (int i = Math.max(0, playerX - distanceToPlayers); i <= Math.min(GridMap.TILES_WIDTH,
				playerX + distanceToPlayers); i += spacing) {
			set.add(new IntPosition(i, (int) Math.max(0, playerY - distanceToPlayers)));
			set.add(new IntPosition(i, (int) Math.min(GridMap.TILES_HEIGHT, playerY + distanceToPlayers)));
		}
		for (int i = playerY - distanceToPlayers + 1; i < playerY + distanceToPlayers; i += spacing) {
			set.add(new IntPosition(playerX - distanceToPlayers, i));
			set.add(new IntPosition(playerX + distanceToPlayers, i));
		}
		for (IntPosition pos : set) {
			addTargetSpawner(pos, speed);
		}

	}

	// Tire des spawners qui vise le joueuer sur les extrémités de la carte avec un
	// espacement de spacing
	public void launchTargetedEncirclement(int spacing, double speed) {
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
				addTargetSpawner(pos, speed);
			});
		}
	}

	/* Tire un mur de projectiles avec un trou pour passer */
	public void shootWallLeft(int holePosition, double speed) {
		LinkedHashSet<IntPosition> set = new LinkedHashSet<>();
		for (int i = 0; i < GridMap.TILES_HEIGHT; i++) {
			if (i == holePosition - 1 ||
					i == holePosition + 1 ||
					i == holePosition)
				continue;
			set.add(new IntPosition(0, i));
		}
		for (IntPosition pos : set) {
			addUnidirectionSpawner(pos, Direction.RIGHT, speed);
		}
	}

	public void shootWallRight(int holePosition, double speed) {
		LinkedHashSet<IntPosition> set = new LinkedHashSet<>();
		for (int i = 0; i < GridMap.TILES_HEIGHT; i++) {
			if (i == holePosition - 1 ||
					i == holePosition + 1 ||
					i == holePosition)
				continue;
			set.add(new IntPosition(GridMap.TILES_WIDTH - 1, i));
		}
		for (IntPosition pos : set) {
			addUnidirectionSpawner(pos, Direction.LEFT, speed);
		}
	}

	public void shootWallTop(int holePosition, double speed) {
		LinkedHashSet<IntPosition> set = new LinkedHashSet<>();
		for (int i = 0; i < GridMap.TILES_WIDTH; i++) {
			if (i == holePosition - 1 ||
					i == holePosition + 1 ||
					i == holePosition)
				continue;
			set.add(new IntPosition(i, 0));
		}
		for (IntPosition pos : set) {
			addUnidirectionSpawner(pos, Direction.DOWN, speed);
		}
	}

	public void shootWallBottom(int holePosition, double speed) {
		LinkedHashSet<IntPosition> set = new LinkedHashSet<>();
		for (int i = 0; i < GridMap.TILES_WIDTH; i++) {
			if (i == holePosition - 1 ||
					i == holePosition + 1 ||
					i == holePosition)
				continue;
			set.add(new IntPosition(i, GridMap.TILES_HEIGHT - 1));
		}
		for (IntPosition pos : set) {
			addUnidirectionSpawner(pos, Direction.UP, speed);
		}
	}

}
