// Création d'un fichier permettant de jouer et de contrôler une musique préalablement téléchargé sur l'ordinateur.

package project.game.controller;

import javafx.scene.control.Button;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;
import project.Main;

public class AudioController {

	static double VOLUME_MUSIC = 0.25;
	static double VOLUME_HIT = 0.8;

	// L'intance est mise en static pour éviter que le Garbage Collector de la JVM
	// élimine l'objet et stoppe la musique en cours
	static MediaPlayer musicPlayer;

	static void playMusic() { // jouer la musique
		try {
			String path = Main.class.getResource("audio/music.mp3").toURI().toString();

			final Media audio = new Media(path);

			musicPlayer = new MediaPlayer(audio);

			musicPlayer.setVolume(Configuration.audioEnabled ? VOLUME_MUSIC : 0);

			musicPlayer.play();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	static MediaPlayer hitPlayer;

	public static void playHitSound() { // jouer le son "Hit"
		if (!Configuration.audioEnabled)
			return;

		try {
			String path = Main.class.getResource("audio/hit.mp3").toURI().toString();

			final Media audio = new Media(path);

			hitPlayer = new MediaPlayer(audio);

			hitPlayer.setVolume(VOLUME_HIT);

			hitPlayer.setStartTime(Duration.millis(700));
			hitPlayer.play();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void clickMusicButton(Button btn) {
		Configuration.audioEnabled = !Configuration.audioEnabled;

		if (Configuration.audioEnabled) {
			musicPlayer.setVolume(VOLUME_MUSIC);
			btn.setText("Music [ON]");
			
		} else {
			musicPlayer.setVolume(0);
			btn.setText("Music [OFF]");
		}
	}

}
