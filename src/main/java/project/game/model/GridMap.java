package project.game.model;

public class GridMap {
	final int SIZE = 10;

	// public à déprécié
	public GridTile[][] map = new GridTile[SIZE][SIZE];

	public GridMap(GridTile[][] initialMapData) {
		this.map = initialMapData;
	}

	public GridMap() {}

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

	public void setAt(int x, int y, GridTile object) {
		map[x][y] = object;
	}
}
