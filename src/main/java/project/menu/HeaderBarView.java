package com.example.aaa;

import com.sun.media.jfxmedia.MediaPlayer;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.layout.StackPane;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.ProgressBar;
//import com.almasb.fxgl.ui.ProgressBar;

import java.io.IOException;

public class HeaderBarView extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HeaderBarView.class.getResource("hello-view.fxml"));

        //Parent root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));

        Scene scene1 = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Hello!");

        //stage.initStyle(StageStyle.TRANSPARENT);
        stage.setScene(scene1);
        stage.show();


    }

    public static void main(String[] args) {

        launch();

    }



    public static int SetPercentageCompletion (Stage primaryStage) {

        final ProgressBar progressBar = new ProgressBar();
        final ProgressIndicator progressIndicator = new ProgressIndicator();
        progressBar.setProgress(0.5); // 0 vide et 1 pleine
        final StackPane root = new StackPane();
        root.getChildren().addAll(progressBar, progressIndicator);
        final Scene scene = new Scene(root, 100, 100);
        primaryStage.setTitle("Test");
        primaryStage.setScene(scene);
        primaryStage.show();
        return 0;
    }



    // public static int DefilerText(Stage stage){

    //     stage.setTitle("TextFlow");

    //     // create TextFlow
    //     TextFlow text_flow = new TextFlow();

    //     // create text
    //     Text text = new Text("Des fantomes vont arriver\n");

    //     // set the text color
    //     text.setFill(Color.RED);

    //     text_flow.getChildren().add(text);

    //     return 0;
    // }
}

/*
    public static int SetScore(int score){

    }

    
    public static int setDeathCounter(int a){

    }
*/

 