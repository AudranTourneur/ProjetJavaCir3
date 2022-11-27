package project.game.model;

//Des positions en float pour afficher à l'écran et calculer les positions
public class FloatPosition {
    public float x;
    public float y;

    public FloatPosition(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public FloatPosition(double x, double y) {
        this.x = (float) x;
        this.y = (float) y;
    }

    @Override
    public String toString() {
        return "Position [" + x + ";" + y + "]";
    }

    public float norm() {
        return (float) Math.sqrt(x * x + y * y);
    }

    public FloatPosition normalize() {
        return new FloatPosition(x / norm(), y / norm());
    }

    public FloatPosition copy() {
        return new FloatPosition(this.x, this.y);
    }

    public FloatPosition translate(float offset) {
        return new FloatPosition(this.x + offset, this.y + offset);
    }

    public FloatPosition minus(FloatPosition other) {
        return new FloatPosition(this.x - other.x, this.y - other.y);
    }
}
