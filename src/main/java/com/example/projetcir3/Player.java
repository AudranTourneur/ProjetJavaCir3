package com.example.projetcir3;

public class Player extends Entity {
    public Position gridPosition = new Position(1, 1);
    Direction direction = null;

    public Player() {

    }

    @Override
    void move(double delta) {
        if (direction == null) return;
        double velocity = 1 * delta/1000;

        position.x += direction.getX() * velocity;
        position.y += direction.getY() * velocity;

        //On divise par 40 parce que c'est la taille de TILE qu'on a definit
        gridPosition.x=(int)position.x;
        gridPosition.y=(int)position.y;
    }
    //A partir de coordonnes x,y on definit notre premier lieu d'apparation du joueur
    public void setSpawn(int x,int y){
        position.x=x;
        position.y=y;
    }
}
