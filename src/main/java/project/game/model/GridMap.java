package project.game.model;

import java.util.HashSet;

//Creation de la map

public class GridMap { 
	public static final int STEP = 10;
	// public static final int NUMBER_OF_TILES = 15;
	// Small map coords
	// public static final int TILES_WIDTH = 20;
	// public static final int TILES_HEIGHT = 15;
	// Big map
	public static final int TILES_WIDTH = 29; //dimension d'une unité
	public static final int TILES_HEIGHT = 21;

	// public à déprécié
	public GridTile[][] map = new GridTile[TILES_WIDTH][TILES_HEIGHT];

	public static final int MAX_ABSTRACT_WIDTH = TILES_WIDTH * 2 * STEP;
	public static final int MAX_ABSTRACT_HEIGHT = TILES_HEIGHT * 2 * STEP;

	public boolean[][] validPositions = new boolean[MAX_ABSTRACT_WIDTH][MAX_ABSTRACT_HEIGHT];

	// validPositions[15][35]

	public GridMap(GridTile[][] initialMapData) {
		this.map = initialMapData;
	}

	public GridMap() {
	}

	public GridTile getAt(int x, int y) { // conditions de bords de cartes
		if (x < 0)
			return GridTile.WALL;
		if (x >= TILES_WIDTH)
			return GridTile.WALL;
		if (y < 0)
			return GridTile.WALL;
		if (y >= TILES_HEIGHT)
			return GridTile.WALL;

		return map[x][y];
	}

	public GridTile getAt(FloatPosition pos) {
		return getAt((int) pos.x, (int) pos.y);
	}

	public void setAt(int x, int y, GridTile object) {
		map[x][y] = object;
	}

	boolean isPositionAccessible(FloatPosition pos) {
		boolean ok = getAt(pos) != null && getAt(pos) != GridTile.WALL;
		return ok;
	}
	// INVALID, OUTSIDE_TELEPORT, VALID

	//condition de teleportation
	SquareValidityResponse isAbstractPositionAllowed(int x, int y) { 
		if (x < 0 || y < 0 || x >= MAX_ABSTRACT_WIDTH || y >= MAX_ABSTRACT_HEIGHT) {
			return SquareValidityResponse.INVALID;
		}

		if (x <= STEP || y < STEP || x >= (MAX_ABSTRACT_WIDTH - STEP) || y >= (MAX_ABSTRACT_HEIGHT - STEP)) {
			return SquareValidityResponse.TELEPORT;
		}

		return validPositions[x][y] ? SquareValidityResponse.VALID : SquareValidityResponse.INVALID;
	}

	HashSet<IntPosition> getEmptyPositions() {
		HashSet<IntPosition> emptyPositions = new HashSet<IntPosition>();
		for (int x = 0; x < TILES_WIDTH; x++)
			for (int y = 0; y < TILES_HEIGHT; y++)
				if (map[x][y] != GridTile.WALL)
					emptyPositions.add(new IntPosition(x, y));

		return emptyPositions;
	}

	// acceleration x1 ou x2
	IntPosition getNextTeleportPosition(Direction direction, IntPosition objectPosition, int speed) {
		IntPosition pos = new IntPosition(objectPosition);
		if (direction == Direction.LEFT) {
			pos.x = GridMap.MAX_ABSTRACT_WIDTH - GridMap.STEP - speed;
		}

		if (direction == Direction.RIGHT) {
			pos.x = -1 + GridMap.STEP + speed;
		}

		if (direction == Direction.UP) {
			pos.y = GridMap.MAX_ABSTRACT_HEIGHT - GridMap.STEP - speed;
		}

		if (direction == Direction.DOWN) {
			pos.y = -1 + GridMap.STEP + speed;
		}

		return pos;
	}
}
