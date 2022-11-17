package project.game.model;

public class Player extends Entity {

    Direction currentDirection = null;
    public Direction desiredDirection = null;

    public GridMap world;

    public Player(GridMap map) {
        this.world = map;
    }

    // Renvoit true si le player touche cette entité
    boolean isCollidingWithEntity(Position pos, GridTile tile) {
        var map = world.map;
        try {
            return (map[(int) Math.floor(pos.y)][(int) Math.floor(pos.x)] == tile
                    || map[(int) Math.floor(pos.y + 0.98)][(int) Math.floor(pos.x)] == tile
                    || map[(int) Math.floor(pos.y)][(int) Math.floor(pos.x + 0.98)] == tile
                    || map[(int) Math.floor(pos.y + 0.98)][(int) Math.floor(pos.x + 0.98)] == tile);
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Warning: ArrayIndexOutOfBoundsException isCollidingWithEntity " + pos);
            return false;
        }
    }

    // static final float epsilonOutside = .02f; // offset de la bordure vers
    // l'extérieur
    // static final float epsilonInside = 0.01f; // rentre vers la figure

    static final float epsilonOutside = 0.025f; // offset de la bordure vers l'extérieur
    static final float epsilonInside = 0.025f; // rentre vers la figure

    boolean isDirectionLegal(Direction dir, Position pos) {
        if (dir == null)
            return false;

        final float x = pos.x;
        final float y = pos.y;

        // Couple de points de controle pour la collision
        Position upLeftUp = new Position(x + epsilonInside, y - epsilonOutside);
        Position upLeftLeft = new Position(x - epsilonOutside, y + epsilonInside);

        Position upRightUp = new Position(x - epsilonInside + 1, y - epsilonOutside);
        Position upRightRight = new Position(x + epsilonOutside + 1, y + epsilonInside);

        Position downLeftLeft = new Position(x - epsilonOutside, y + 1 - epsilonInside);
        Position downLeftDown = new Position(x + epsilonInside, y + 1 + epsilonOutside);

        Position downRightDown = new Position(x + 1 - epsilonInside, y + 1 + epsilonOutside);
        Position downRightRight = new Position(x + 1 + epsilonOutside, y + 1 - epsilonInside);

        if (dir == Direction.UP) {

            return world.isPositionAccessible(upLeftUp) && world.isPositionAccessible(upRightUp);
        }
        if (dir == Direction.DOWN) {
            System.out.println("Current chat position = " + this.position);
            System.out.println("Future chat position = " + pos);
            System.out.println("Down Left Down : " + world.isPositionAccessible(downLeftDown) + " " + downLeftDown);
            return world.isPositionAccessible(downLeftDown) && world.isPositionAccessible(downRightDown);
        }
        if (dir == Direction.LEFT)
            return world.isPositionAccessible(upLeftLeft) && world.isPositionAccessible(downLeftLeft);
        if (dir == Direction.RIGHT)
            return world.isPositionAccessible(upRightRight) && world.isPositionAccessible(downRightRight);

        /*
         * if (dir == Direction.UP)
         * return world.isPositionAccessible(up);
         * if (dir == Direction.DOWN)
         * return world.isPositionAccessible(down);
         * if (dir == Direction.RIGHT)
         * return world.isPositionAccessible(right);
         * if (dir == Direction.LEFT)
         * return world.isPositionAccessible(left);
         */

        // Position target = new Position(center.x + dir.getX(), center.y + dir.getY());

        return false;
    }

    @Override
    public void move(double delta) {
        if (desiredDirection == null)
            return;
        double velocity = 1 * delta / 1000;

        double desiredPosX = position.x + desiredDirection.getX() * velocity;
        double desiredPosY = position.y + desiredDirection.getY() * velocity;
        Position desiredPos = new Position(desiredPosX, desiredPosY);

        if (isDirectionLegal(desiredDirection, desiredPos)) {
            currentDirection = desiredDirection;
        }

        if (!isDirectionLegal(currentDirection, desiredPos)) {

            if (currentDirection != null) {
                System.out.println("arrêt du mouvement");

                if (currentDirection == Direction.UP || currentDirection == Direction.DOWN) 
                    this.position.x = Math.round(desiredPosX);

                if (currentDirection == Direction.LEFT || currentDirection == Direction.RIGHT) 
                    this.position.y = Math.round(desiredPosY);
            }

            currentDirection = null;
        }

        if (currentDirection != null) {
            position.x = (float) desiredPosX;
            position.y = (float) desiredPosY;
        }

        /*
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
         */
    }

    // A partir de coordonnes x,y on definit notre premier lieu d'apparation du
    // joueur
    public void setSpawn(int x, int y) {
        position.x = x;
        position.y = y;

        // gridPosition.x=x;
        // gridPosition.y=y;
    }

    public String toString() {
        return this.position + " " + this.currentDirection + " " + this.desiredDirection;
    }
}
