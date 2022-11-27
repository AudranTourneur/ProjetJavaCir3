package project.game.view;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.text.Text;
import project.game.controller.GameWindowController;
import project.game.model.Player;
import project.game.model.WorldModel;

/*
 * Menu en haut de l'écran lors du jeu
 */

public class HUDControllerView {
	private Scene scene;

	public HUDControllerView(GameWindowController window) {
		this.scene = window.stage.getScene();
	}

	// Mise à jour du score
	private void setScore(int score) {
		final Button textElem = (Button) scene.lookup("#score");
		textElem.setText("Score : " + score);
	}

	// Mise à jour de la barre de progression dans le jeu
	private void setProgress(double progress) {
		final ProgressBar progressBarElem = (ProgressBar) scene.lookup("#progress_bar");
		progressBarElem.setProgress(progress);
		((Text) scene.lookup("#progress_text")).setText((int) (progress * 100) + "%");
	}

	// Mise à jour du compteur de vies
	private void setLives(int lives) {
		((Text) scene.lookup("#lives")).setText("x" + lives);
	}

	// Mise à jour de la stamina (énergie pour courir)
	private void setStamina(double stamina) {
		((ProgressBar) scene.lookup("#stamina_bar")).setProgress(stamina);
		((Text) scene.lookup("#stamina_text")).setText((int) (stamina * 100) + "%");
	}

	// Mise à jour du texte central
	private void setText(String text) {
		final Text textElem = (Text) scene.lookup("#text");
		textElem.setText(text);
	}

	// Mise à jour générale de l'HUD
	public void updateHUD(WorldModel model) {
		setScore(model.player.score);
		setText("Level : " + model.levelProgressionManager.getCurrentLevel());
		setStamina((double) model.player.stamina / Player.MAX_STAMINA);
		setProgress(model.levelProgressionManager.getProgression());
		setLives(model.player.getLives());
	}

}
