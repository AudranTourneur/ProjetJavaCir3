package com.example.projetcir3;

public class Player extends Entity {


    Direction currentDirection = null;
    Direction desiredDirection = null;
    public GridTile[][] map;

    public Player(GridTile[][] map) {
        this.map = map;
    }

    //Renvoit ture si le player touche cette entite
    boolean isCollidingWithEntity(Position pos, GridTile tile) {
        return (map[(int) Math.floor(pos.y)][ (int) Math.floor(pos.x)] == tile
                || map[(int) Math.floor(pos.y+0.98)][ (int)Math.floor(pos.x)] == tile
                || map[(int) Math.floor(pos.y)][ (int)Math.floor(pos.x+0.98)] == tile
                || map[(int) Math.floor(pos.y+0.98)][ (int)Math.floor(pos.x+0.98)] == tile
        );
    }

    @Override
    void move(double delta) {
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
}
