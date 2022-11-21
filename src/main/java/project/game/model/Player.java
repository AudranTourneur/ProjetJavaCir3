package project.game.model;

public class Player extends Entity {

    public static final float RADIUS_HITBOX_SIZE = 0.07f;
    Direction currentDirection = null;
    public Direction desiredDirection = null;

    public WorldModel world;
    public GridMap map;

    public boolean speedX2 = false;

    int gridPositionX;
    int gridPositionY;
    public int deaths;

    public final static int MAX_STAMINA = 10 * 60;
    public int stamina = MAX_STAMINA;
    public int score;

    public int getGridPositionX() {
        return gridPositionX;
    }

    public int getGridPositionY() {
        return gridPositionY;
    }

    public Player(WorldModel world) {
        this.world = world;
        this.map = world.map;
    }

    FloatPosition getNormalizedPosition() {
        return new FloatPosition(
                (float) this.gridPositionX / (2 * GridMap.STEP),
                (float) this.gridPositionY / (2 * GridMap.STEP));

        // return new FloatPosition(
        // (float) this.gridPositionX / 2 * GridMap.STEP + GridMap.STEP,
        // (float) this.gridPositionY / 2 * GridMap.STEP + GridMap.STEP);
    }

    public IntPosition getTilePosition() {
        return new IntPosition((gridPositionX - GridMap.STEP) / (2 * GridMap.STEP), (gridPositionY - GridMap.STEP) / (2 * GridMap.STEP));
    }


    public IntPosition getCenteredTilePosition() {
        return new IntPosition((gridPositionX) / (2 * GridMap.STEP), (gridPositionY) / (2 * GridMap.STEP));
    }

    @Override
    public void move(double delta) {
        if (desiredDirection != null) {
            int speed = 1;

            if (speedX2 && stamina <= 0) {
                speedX2 = false;
            }

            if (speedX2) {
                if (gridPositionX % 2 == 0 && gridPositionY % 2 == 0)
                    speed = 2;
            }

            stamina += speedX2 ? -1 : 1; 
            stamina = Math.min(MAX_STAMINA, stamina);
            stamina = Math.max(0, stamina);

            int dtx = gridPositionX + desiredDirection.getX() * speed;
            int dty = gridPositionY + desiredDirection.getY() * speed;
            if (map.isAbstractPositionAllowed(dtx, dty)) {
                this.currentDirection = desiredDirection;
            }

            if (currentDirection != null) {
                int ctx = gridPositionX + currentDirection.getX() * speed;
                int cty = gridPositionY + currentDirection.getY() * speed;
                if (map.isAbstractPositionAllowed(ctx, cty)) {
                    this.gridPositionX += currentDirection.getX() * speed;
                    this.gridPositionY += currentDirection.getY() * speed;
                }
            }
        }

        this.position = getNormalizedPosition();
    }

    // A partir de coordonnes x,y on definit notre premier lieu d'apparation du
    // joueur
    public void setSpawn(int x, int y) {
        position.x = x;
        position.y = y;

        gridPositionX = GridMap.STEP + x * 2 * GridMap.STEP;
        gridPositionY = GridMap.STEP + y * 2 * GridMap.STEP;
    }

    public String toString() {
        return this.position + " " + this.currentDirection + " " + this.desiredDirection;
    }

	public int getScore() {
		return score;
	}
}
