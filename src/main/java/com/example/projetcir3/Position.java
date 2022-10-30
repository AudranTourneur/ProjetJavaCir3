package com.example.projetcir3;

public class Position {
    public float x;
    public float y;
    Position(float x, float y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        return "Position [" + x + ";" + y + "]";
    }
}
