package com.example.projetcir3;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class InputController {
    public GameWorldModel world;

    InputController(GameWorldModel world) {
        this.world = world;
    }
    void handle(KeyEvent event) {
        System.out.println("Handle " + event);
        if (event.getCode() == KeyCode.RIGHT)
            this.world.player.direction = Direction.RIGHT;

        if (event.getCode() == KeyCode.LEFT)
            this.world.player.direction = Direction.LEFT;

        if (event.getCode() == KeyCode.DOWN)
            this.world.player.direction = Direction.DOWN;

        if (event.getCode() == KeyCode.UP)
            this.world.player.direction = Direction.UP;

        /*
        if (event.getCode() == KeyCode.RIGHT)
            this.world.player.gridPosition.x++;

        if (event.getCode() == KeyCode.LEFT)
            this.world.player.gridPosition.x--;

        if (event.getCode() == KeyCode.DOWN)
            this.world.player.gridPosition.y++;

        if (event.getCode() == KeyCode.UP)
            this.world.player.gridPosition.y++;

         */

    }
}
