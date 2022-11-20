package project.game.controller;

import javafx.beans.property.DoubleProperty;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import project.Main;

public class AudioController {

	static MediaPlayer mediaPlayer;

	static void play() {
		// final Media audio = new Media(new
		// Main().getClass().getResourceAsStream("sounds/myAudio.wav"));
		try {
			System.out.println("playing audio");
			String path = new Main().getClass().getResource("audio/music.mp3").toURI().toString();

			System.out.println("path = " + path);
			final Media audio = new Media(path);

			mediaPlayer = new MediaPlayer(audio);

			mediaPlayer.setVolume(0.5);

			mediaPlayer.play();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
