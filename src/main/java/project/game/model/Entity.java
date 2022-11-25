//creation d'une entité
package project.game.model;

abstract public class Entity {
    public FloatPosition position = new FloatPosition(0f, 0f);

    public abstract void move(double delta);

    boolean markedForDeletion = false;
}
