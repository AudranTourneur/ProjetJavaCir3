package com.example.projetcir3;

public class Position {
    public int x;
    public int y;
    Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        return "Position [" + x + ";" + y + "]";
    }
}
