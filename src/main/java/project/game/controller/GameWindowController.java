package project.game.controller;

import javafx.event.ActionEvent;
import javafx.event.EventType;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import project.Main;

public class GameWindowController {
	public Canvas canvas;
	public Stage stage;
	public Pane pane;
	public VBox menuVBox;

	public GameWindowController(Stage stage) {
		this.stage = stage;
		try {
			// Path to the FXML File
			// Create the Pane and all Details
			final VBox root = (VBox) FXMLLoader.load(Main.class.getResource("view/menu.fxml"));

			// Create the Scene
			Scene scene = new Scene(root);
			// Set the Scene to the Stage
			stage.setScene(scene);
			// Set the Title to the Stage
			stage.setTitle("Pac-Cat");
			// Display the Stage
			stage.show();

			this.canvas = (Canvas) scene.lookup("#canvas");

			this.pane = (Pane) stage.getScene().lookup("#canvas-pane");

			this.menuVBox = (VBox) stage.getScene().lookup("#menuvbox");

			manageMusicButtonPress();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	void manageMusicButtonPress() {
		Button button = (Button) stage.getScene().lookup("#music");
		button.setOnAction(event -> {
			//System.out.println(event);
			AudioController.clickMusicButton(button);
		});
		
	}
}
