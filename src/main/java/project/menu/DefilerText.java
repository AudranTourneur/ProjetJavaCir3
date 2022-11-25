package com.example.aaa;

import javafx.application.Application;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

public class DefilerText extends Application {

    // launch the application
    public void start(Stage stage) {

        try {

            // set title for the stage
            stage.setTitle("TextFlow");

            // create TextFlow
            TextFlow text_flow = new TextFlow();

            // create text
            Text text = new Text("Des fantomes vont arriver\n");

            // set the text color
            text.setFill(Color.RED);

            text_flow.getChildren().add(text);

        } finally {

        }
    }


}
