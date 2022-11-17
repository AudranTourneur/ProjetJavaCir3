package project.game.model;

public class GridMap {
	public static final int STEP = 10;
	static final int SIZE = 10;

	// public à déprécié
	public GridTile[][] map = new GridTile[SIZE][SIZE];

	public boolean[][] validPositions = new boolean[SIZE * 2 * STEP][SIZE * 2 * STEP]; 

	// validPositions[15][35]

	public GridMap(GridTile[][] initialMapData) {
		this.map = initialMapData;
	}

	public GridMap() {
	}

	public GridTile getAt(int x, int y) {
		if (x < 0)
			return GridTile.WALL;
		if (x >= SIZE)
			return GridTile.WALL;
		if (y < 0)
			return GridTile.WALL;
		if (y >= SIZE)
			return GridTile.WALL;

		return map[x][y];
	}

	public GridTile getAt(Position pos) {
		return getAt((int) pos.x, (int) pos.y);
	}

	public void setAt(int x, int y, GridTile object) {
		map[x][y] = object;
	}

	boolean isPositionAccessible(Position pos) {
		boolean ok = getAt(pos) != null && getAt(pos) != GridTile.WALL;
		//if (!ok) System.out.println("Failing " + pos);
		return ok;
	}
}
