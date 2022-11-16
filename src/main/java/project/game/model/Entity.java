package project.game.model;

abstract public class Entity {
    public Position position = new Position(0f, 0f);

    public abstract void move(double delta);
}
