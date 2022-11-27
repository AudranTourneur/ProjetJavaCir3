package project.menu;

import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

//Bouton pour commencer la partie
public class StartButton extends StackPane {

    public final static int buttonWidth = 300;

    // Intialisation du bouton pour commencer le jeu
    StartButton(String name) {
        Text text = new Text(name);
        text.setFont(Font.font(20)); // taille du texte
        text.setFill(Color.WHITE); // couleur du texte

        Rectangle rct = new Rectangle(buttonWidth, 50); // forme

        rct.setOpacity(0.8); // opacité
        rct.setFill(Color.PURPLE); // couleur de l'arrière plan
        getChildren().addAll(rct, text);

    }
}
