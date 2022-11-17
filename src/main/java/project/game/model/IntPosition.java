package project.game.model;

public class IntPosition {
    public int x;
    public int y;

    public IntPosition(float x, float y) {
        this.x = (int) x;
        this.y = (int) y;
    }

    public IntPosition(double x, double y) {
        this.x = (int) x;
        this.y = (int) y;
    }

    public IntPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        return "Position [" + x + ";" + y + "]";
    }
}
