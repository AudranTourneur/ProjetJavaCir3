// fichier decrivant l'orientation

package project.game.model;

//Un enum de direction utiliser pour le deplacement des entitées
public enum Direction { 
    UP(0, -1),
    RIGHT(1, 0),
    DOWN(0, 1),
    LEFT(-1, 0);

    final int x;
    final int y;

    Direction(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getY() {
        return y;
    }

    public int getX() {
        return x;
    }

    @Override
    public String toString() {
        return this.name();
    }

    //Donne un angle en fonction de la direction choisi en degre
    public double toDeg() {
        if (this == Direction.DOWN) {
            return 0;
        } else if (this == Direction.LEFT) {
            return 90;
        } else if (this == Direction.UP) {
            return 180;
        } else if (this == Direction.RIGHT) {
            return 270;
        }
        return 0;
    }

    public IntPosition getBaseVector() {
        return new IntPosition(x, y);
    }
}
