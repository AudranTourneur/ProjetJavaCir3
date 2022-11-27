
package project.game.controller;

import javafx.application.Platform;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import project.Main;
import project.game.model.Direction;
import project.game.model.WorldModel;

// Gestion des raccourcis clavier

public class InputController {
    public GameStateContainer state;

    public InputController(GameStateContainer state) {
        this.state = state;
    }

    // Gestion des entrés claviers qui nous intéressent
    public void handle(KeyEvent event) {
        WorldModel world = state.model;
        if (event.getCode() == KeyCode.RIGHT || event.getCode() == KeyCode.D) // Flèche droite ou D
            world.player.desiredDirection = Direction.RIGHT;

        if (event.getCode() == KeyCode.LEFT || event.getCode() == KeyCode.Q) // Flèche gauche ou Q
            world.player.desiredDirection = Direction.LEFT;

        if (event.getCode() == KeyCode.DOWN || event.getCode() == KeyCode.S) // Flèche du haut ou S
            world.player.desiredDirection = Direction.DOWN;

        if (event.getCode() == KeyCode.UP || event.getCode() == KeyCode.Z) // Flèche du bas ou Z
            world.player.desiredDirection = Direction.UP;

       if (event.getCode() == KeyCode.ESCAPE) { // Échap : sortir du jeu
            Platform.exit();
            Main.alive = false;
        }

        if (event.getCode() == KeyCode.SPACE) { // Espace : sprint
            state.model.player.speedX2 = !state.model.player.speedX2;
        }

        if (event.getCode() == KeyCode.R) { // R : Ré-initialiser le jeu
            System.out.println("Game reset!");
            state.model = new WorldModel();
        }
    }
}
