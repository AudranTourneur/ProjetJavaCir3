package project.game.model;

public class Player extends Entity {

    Direction currentDirection = null;
    public Direction desiredDirection = null;

    public GameWorldModel world;
    public GridMap map;

    int gridPositionX;
    int gridPositionY;

    // Position gridPosition;

    public int getGridPositionX() {
        return gridPositionX;
    }

    public int getGridPositionY() {
        return gridPositionY;
    }

    public Player(GameWorldModel world) {
        this.world = world;
        this.map = world.map;
    }

    @Override
    public void move(double delta) {
        if (desiredDirection == null)
            return;

        int speed = 1;
        if (world.speedX2)
            if (gridPositionX % 2 == 0 && gridPositionY % 2 == 0)
                speed = 2;

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

    /*
     * @Override
     * public void move(double delta) {
     * if (desiredDirection == null)
     * return;
     * double velocity = 1 * delta / 1000;
     * 
     * double desiredPosX = position.x + desiredDirection.getX() * velocity;
     * double desiredPosY = position.y + desiredDirection.getY() * velocity;
     * Position desiredPos = new Position(desiredPosX, desiredPosY);
     * 
     * if (isDirectionLegal(desiredDirection, desiredPos)) {
     * currentDirection = desiredDirection;
     * }
     * 
     * if (!isDirectionLegal(currentDirection, desiredPos)) {
     * 
     * if (currentDirection != null) {
     * System.out.println("arrêt du mouvement");
     * 
     * if (currentDirection == Direction.UP || currentDirection == Direction.DOWN)
     * this.position.x = Math.round(desiredPosX);
     * 
     * if (currentDirection == Direction.LEFT || currentDirection ==
     * Direction.RIGHT)
     * this.position.y = Math.round(desiredPosY);
     * }
     * 
     * currentDirection = null;
     * }
     * 
     * if (currentDirection != null) {
     * position.x = (float) desiredPosX;
     * position.y = (float) desiredPosY;
     * }
     * 
     * /*
     * 
     * if (!isCollidingWithEntity(desiredPos, GridTile.WALL)) {
     * currentDirection = desiredDirection;
     * position.x = (float) desiredPosX;
     * position.y = (float) desiredPosY;
     * } else if (currentDirection != null) {
     * double nextPosX = position.x + currentDirection.getX() * velocity;
     * double nextPosY = position.y + currentDirection.getY() * velocity;
     * Position nextPos = new Position(nextPosX, nextPosY);
     * 
     * if (!isCollidingWithEntity(nextPos, GridTile.WALL)) {
     * position.x = (float) nextPosX;
     * position.y = (float) nextPosY;
     * }
     * }
     * }
     */

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
}
