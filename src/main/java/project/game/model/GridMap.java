package project.game.model;

import java.util.HashSet;

public class GridMap {
	public static final int STEP = 10;
	// public static final int NUMBER_OF_TILES = 15;
	public static final int TILES_WIDTH = 30;
	public static final int TILES_HEIGHT = 20;

	// public à déprécié
	public GridTile[][] map = new GridTile[TILES_WIDTH][TILES_HEIGHT];

	public boolean[][] validPositions = new boolean[TILES_WIDTH * 2 * STEP][TILES_HEIGHT * 2 * STEP];

	// validPositions[15][35]

	public GridMap(GridTile[][] initialMapData) {
		this.map = initialMapData;
	}

	public GridMap() {
	}

	public GridTile getAt(int x, int y) {
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
		// if (!ok) System.out.println("Failing " + pos);
		return ok;
	}

	boolean isAbstractPositionAllowed(int x, int y) {
		if (x < 0 || y < 0 || x >= validPositions.length || y >= validPositions.length)
			return false;

		return validPositions[x][y];
	}

	HashSet<IntPosition> getEmptyPositions() {
		HashSet<IntPosition> emptyPositions = new HashSet<IntPosition>();
		for (int x = 0; x < TILES_WIDTH; x++)
			for (int y = 0; y < TILES_HEIGHT; y++)
				if (map[x][y] != GridTile.WALL)
					emptyPositions.add(new IntPosition(x, y));

		return emptyPositions;
	}
}
