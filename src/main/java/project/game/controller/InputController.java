//fichier pour les raccourci clavier

package project.game.controller;

import javafx.application.Platform;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import project.Main;
import project.SceneManager;
import project.game.model.Direction;
import project.game.model.WorldModel;

public class InputController {
    public GameStateContainer state;

    public InputController(GameStateContainer state) {
        this.state = state;
    }

    public void handle(KeyEvent event) {
        WorldModel world = state.model;
        if (event.getCode() == KeyCode.RIGHT || event.getCode() == KeyCode.D) // fleche droite
            world.player.desiredDirection = Direction.RIGHT;

        if (event.getCode() == KeyCode.LEFT || event.getCode() == KeyCode.Q) // fleche gauche
            world.player.desiredDirection = Direction.LEFT;

        if (event.getCode() == KeyCode.DOWN || event.getCode() == KeyCode.S) // fleche haut
            world.player.desiredDirection = Direction.DOWN;

        if (event.getCode() == KeyCode.UP || event.getCode() == KeyCode.Z) // fleche bas
            world.player.desiredDirection = Direction.UP;

        if (event.getCode() == KeyCode.ESCAPE) { // echapb : sortir du jeu
            Platform.exit();
            Main.alive = false;
        }

        if (event.getCode() == KeyCode.SPACE) { // espace : sprint
            System.out.println("Space pressed");
            state.model.player.speedX2 = !state.model.player.speedX2;
        }

        if (event.getCode() == KeyCode.R) {
            System.out.println("Reset model");
            state.model = new WorldModel();
        }

        System.out.println(event.getCode());
    }
}
