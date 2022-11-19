package project.game.model;

public class IntPosition  {
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

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + x;
        result = prime * result + y;
        return result;
    }

    @Override
    public boolean equals(Object tmp){
        if (tmp instanceof IntPosition) {
            IntPosition posTmp = (IntPosition) tmp;
            return posTmp.x==this.x && posTmp.y==this.y;
        }
        else 
            return false;
    }

    public FloatPosition toFloat() {
        return new FloatPosition(this.x, this.y);
    }
}
