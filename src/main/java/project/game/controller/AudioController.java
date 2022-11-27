// Création d'un fichier permettant de jouer et de contrôler une musique préalablement téléchargé sur l'ordinateur.

package project.game.controller;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;
import project.Main;

public class AudioController {

	// L'intance est mise en static pour éviter que le Garbage Collector de la JVM
	// élimine l'objet et stoppe la musique en cours
	static MediaPlayer musicPlayer;

	static void playMusic() {	//jouer la musique
		if (!Configuration.AUDIO_ENABLED) return;
		try {
			String path = Main.class.getResource("audio/music.mp3").toURI().toString();

			final Media audio = new Media(path);

			musicPlayer = new MediaPlayer(audio);

			musicPlayer.setVolume(0.9);

			musicPlayer.play();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	static MediaPlayer hitPlayer;

	public static void playHitSound() { //jouer le son "Hit"
		if (!Configuration.AUDIO_ENABLED) return;
		try {
			String path = Main.class.getResource("audio/hit.mp3").toURI().toString();

			final Media audio = new Media(path);

			hitPlayer = new MediaPlayer(audio);

			hitPlayer.setVolume(1);

			hitPlayer.setStartTime(Duration.millis(700));
			hitPlayer.play();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
