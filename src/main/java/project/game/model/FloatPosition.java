package project.game.model;

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
}
