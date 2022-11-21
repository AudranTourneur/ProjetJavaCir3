package project.game.controller;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import project.Main;

public class AudioController {

	static MediaPlayer mediaPlayer;

	static void play() {
		try {
			System.out.println("playing audio");
			String path = Main.class.getResource("audio/music.mp3").toURI().toString();

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
