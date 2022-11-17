package project.game.model;

public class GridMap {
	public static final int STEP = 10;
	public static final int NUMBER_OF_TILES = 15;

	// public à déprécié
	public GridTile[][] map = new GridTile[NUMBER_OF_TILES][NUMBER_OF_TILES];

	public boolean[][] validPositions = new boolean[NUMBER_OF_TILES * 2 * STEP][NUMBER_OF_TILES * 2 * STEP];

	// validPositions[15][35]

	public GridMap(GridTile[][] initialMapData) {
		this.map = initialMapData;
	}

	public GridMap() {
	}

	public GridTile getAt(int x, int y) {
		if (x < 0)
			return GridTile.WALL;
		if (x >= NUMBER_OF_TILES)
			return GridTile.WALL;
		if (y < 0)
			return GridTile.WALL;
		if (y >= NUMBER_OF_TILES)
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
}
