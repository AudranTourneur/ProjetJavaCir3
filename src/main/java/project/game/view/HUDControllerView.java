package project.game.view;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.text.Text;
import project.game.controller.GameWindowController;
import project.game.model.Player;
import project.game.model.WorldModel;

public class HUDControllerView {
	GameWindowController window;
	Scene scene;

	public HUDControllerView(GameWindowController window) {
		this.window = window;
		this.scene = window.stage.getScene();
	}

	public void setScore(int score) {
		final Button textElem = (Button) scene.lookup("#score");
		textElem.setText("Score : " + score);
	}

	public void setProgress(double progress) {
		final ProgressBar progressBarElem = (ProgressBar) scene.lookup("#progress_bar");
		progressBarElem.setProgress(progress);
		((Text) scene.lookup("#progress_text")).setText((int) (progress * 100) + "%");
	}

	public void setLives(int lives) {
		((Text) scene.lookup("#lives")).setText("x" + lives);
	}

	public void setStamina(double stamina) {
		((ProgressBar) scene.lookup("#stamina_bar")).setProgress(stamina);
		((Text) scene.lookup("#stamina_text")).setText((int) (stamina * 100) + "%");
	}

	public void setText(String text) {
		final Text textElem = (Text) scene.lookup("#text");
		textElem.setText(text);
	}

	public void updateHUD(WorldModel model) {
		setScore(model.player.score);
		setText("Level : " + model.levelProgressionManager.getCurrentLevel());
		setStamina((double) model.player.stamina / Player.MAX_STAMINA);
		setProgress(0.25);
		setLives(model.player.lives);
	}

}
