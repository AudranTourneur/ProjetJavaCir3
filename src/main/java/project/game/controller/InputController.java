package project.game.controller;

import javafx.application.Platform;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import project.Main;
import project.game.model.Direction;
import project.game.model.WorldModel;

public class InputController {
    public GameStateContainer state;

    public InputController(GameStateContainer state) {
        this.state = state;
    }

    public void handle(KeyEvent event) {
        WorldModel world = state.model;
        System.out.println("Handle " + event);
        if (event.getCode() == KeyCode.RIGHT)
            world.player.desiredDirection = Direction.RIGHT;

        if (event.getCode() == KeyCode.LEFT)
            world.player.desiredDirection = Direction.LEFT;

        if (event.getCode() == KeyCode.DOWN)
            world.player.desiredDirection = Direction.DOWN;

        if (event.getCode() == KeyCode.UP)
            world.player.desiredDirection = Direction.UP;

        if (event.getCode() == KeyCode.ESCAPE) {
            Platform.exit();
            Main.alive = false;
        }

        if (event.getCode() == KeyCode.D) {
            state.view.displayDebugInfo();
            System.out.println("Debug info: " + state.model.player);
        }

        if (event.getCode() == KeyCode.SPACE) {
            state.model.speedX2 = !state.model.speedX2;
        }
    }
}
