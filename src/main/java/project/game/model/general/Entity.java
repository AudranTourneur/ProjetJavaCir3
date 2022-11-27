//creation d'une entité
package project.game.model.general;

import project.game.model.utils.FloatPosition;

//Class absraite qui nous sert à créer les classes de Projectile,de Player et de Ghost
abstract public class Entity {
    public FloatPosition position = new FloatPosition(0f, 0f);

    public abstract void move(double delta);

    public boolean markedForDeletion = false;
}
