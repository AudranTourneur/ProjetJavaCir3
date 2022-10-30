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
    }
}
