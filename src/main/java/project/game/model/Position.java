package project.game.model;

public class Position {
    public float x;
    public float y;
    public Position(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public Position(double x, double y) {
        this.x = (float) x;
        this.y = (float) y;
    }


    @Override
    public String toString() {
        return "Position [" + x + ";" + y + "]";
    }
}
