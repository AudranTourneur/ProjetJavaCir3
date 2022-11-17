package project.menu;

import javafx.animation.FadeTransition;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;
import project.Main;
import project.SceneManager;

import java.io.IOException;
import java.io.InputStream;

public class MainMenu {
    SceneManager manager;

    public MainMenu(SceneManager manager) {
        this.manager = manager;
    }

    GameMenu gamemenu;

    public Scene start(Stage stage) throws IOException {

        // image de fond
        Pane root = new Pane();
        root.setPrefSize(MenuConstants.windowWidth, MenuConstants.windowHeight);

        gamemenu = new GameMenu();

        InputStream inputStreamBackground = new Main().getClass().getResourceAsStream("images/menu_background.jpg");

        System.out.println("InputStream value = " + inputStreamBackground);

        Image img = new Image(inputStreamBackground);
        inputStreamBackground.close();
        ImageView imgV = new ImageView(img);
        imgV.setFitHeight(MenuConstants.windowHeight); // taille image
        imgV.setFitWidth(MenuConstants.windowWidth);

        root.getChildren().addAll(imgV, gamemenu);

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

        Rectangle rct = new Rectangle(250, 50); // (pour ensuite afficher)

        rct.setFill(Color.YELLOW);

        root.setPrefSize(900, 700);

        VBox menu = new VBox(10); // reste en colonne (si plusieurs boutons)

        StartButton play = new StartButton("PLAY");

        play.setOnMouseClicked(event -> { // fenetre de transition quand on clique
            FadeTransition ft = new FadeTransition(Duration.seconds(0.75), gamemenu); // duree transition
            ft.setFromValue(1);
            ft.setToValue(0);
            ft.setOnFinished(evt -> gamemenu.setVisible(false));
            ft.play();

            manager.switchToGame();
        });

        play.setTranslateX(MenuConstants.windowWidth / 2 - StartButton.buttonWidth / 2);
        play.setTranslateY(550);

        root.getChildren().add(play);
        // root.getChildren().add(rct); //si besoin d'un 2e bouton
        root.getChildren().add(menu);

        // getChildren().addAll(root);

        root.getScene().setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.ENTER) {
                manager.switchToGame();
            }
        });


        return scene;

    }
}
