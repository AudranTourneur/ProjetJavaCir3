package project.game.controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import project.Main;

// Contrôle général de la fenêtre de jeu

public class GameWindowController {
	public Canvas canvas;
	public Stage stage;
	public Pane pane;
	public VBox menuVBox;

	// Intialise le stage
	public GameWindowController(Stage stage) {
		this.stage = stage;
		try {
			// Object racine de la scène chargée par FXMLLoader
			final VBox root = (VBox) FXMLLoader.load(Main.class.getResource("view/menu.fxml"));

			// Création de la scène
			Scene scene = new Scene(root);
			stage.setScene(scene);
			stage.setTitle("Pac-Cat");
			stage.show();

			// Récuparation par identifiant de certains éléments dans le fichier FXML
			this.canvas = (Canvas) scene.lookup("#canvas");
			this.pane = (Pane) stage.getScene().lookup("#canvas-pane");
			this.menuVBox = (VBox) stage.getScene().lookup("#menuvbox");

			manageMusicButtonPress();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	// Gestion d'un clic sur le bouton "Music" dans le HUD
	void manageMusicButtonPress() {
		Button button = (Button) stage.getScene().lookup("#music");
		button.setOnAction(event -> {
			AudioController.clickMusicButton(button);
		});
		
	}
}
