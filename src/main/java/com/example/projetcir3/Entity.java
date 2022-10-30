package com.example.projetcir3;

abstract public class Entity {
    Position position = new Position(0f, 0f);

    abstract void move(double delta);
}
