package project.game.model;

public class Player extends Entity {


    Direction currentDirection = null;
    public Direction desiredDirection = null;

    public GridMap world;

    public Player(GridMap map) {
        this.world = map;
    }

    //Renvoit true si le player touche cette entité
    boolean isCollidingWithEntity(Position pos, GridTile tile) {
        var map = world.map;
        try {
        return (map[(int) Math.floor(pos.y)][ (int) Math.floor(pos.x)] == tile
                || map[(int) Math.floor(pos.y+0.98)][ (int)Math.floor(pos.x)] == tile
                || map[(int) Math.floor(pos.y)][ (int)Math.floor(pos.x+0.98)] == tile
                || map[(int) Math.floor(pos.y+0.98)][ (int)Math.floor(pos.x+0.98)] == tile
        );
        } catch(ArrayIndexOutOfBoundsException e) {
            System.out.println("Warning: ArrayIndexOutOfBoundsException isCollidingWithEntity " + pos);
            return false;
        }
    }
    
    //boolean isDirectionLegal(Direction dir) {
//
    //}

    @Override
    public void move(double delta) {
        if (desiredDirection == null) return;
        double velocity = 1 * delta / 1000;

        double desiredPosX = position.x + desiredDirection.getX() * velocity;
        double desiredPosY = position.y + desiredDirection.getY() * velocity;
        Position desiredPos= new Position(desiredPosX,desiredPosY);
        if (!isCollidingWithEntity(desiredPos, GridTile.WALL)) {
            currentDirection=desiredDirection;
            position.x = (float) desiredPosX;
            position.y = (float) desiredPosY;
        }else if (currentDirection != null) {
            double nextPosX = position.x + currentDirection.getX() * velocity;
            double nextPosY = position.y + currentDirection.getY() * velocity;
            Position nextPos = new Position(nextPosX, nextPosY);

            if (!isCollidingWithEntity(nextPos, GridTile.WALL)) {
                position.x = (float) nextPosX;
                position.y = (float) nextPosY;
            }
        }
    }

    //A partir de coordonnes x,y on definit notre premier lieu d'apparation du joueur
    public void setSpawn(int x, int y) {
        position.x = x;
        position.y = y;

        //gridPosition.x=x;
        //gridPosition.y=y;
    }

    public String toString() {
        return this.position + " " + this.currentDirection + " " + this.desiredDirection;
    }
}
