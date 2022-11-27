
package project.game.controller;

import javafx.scene.control.Button;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;
import project.main.Main;

// Gestion de la musique et du son durant la partie

public class AudioController {

	static double VOLUME_MUSIC = 0.25;
	static double VOLUME_EFFECTS = 0.8;

	// L'intance est mise en static pour éviter que le Garbage Collector de la JVM
	// élimine l'objet et stoppe la musique en cours
	static MediaPlayer musicPlayer;

	// Joue la musique principale
	static void playMusic() {
		try {
			String path = Main.class.getResource("audio/music.mp3").toURI().toString();

			final Media audio = new Media(path);

			musicPlayer = new MediaPlayer(audio);

			musicPlayer.setVolume(Configuration.audioEnabled ? VOLUME_MUSIC : 0);

			// Répétition de la musique à la fin
			musicPlayer.setOnEndOfMedia(new Runnable() {
				public void run() {
					musicPlayer.seek(Duration.ZERO);
				}
			});

			musicPlayer.play();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	static MediaPlayer hitPlayer;

	// Joue le son de dégât lorsqu'un le joueur entre en contact avec un fantôme
	public static void playHitSound() {
		if (!Configuration.audioEnabled)
			return;

		try {
			String path = Main.class.getResource("audio/hit.mp3").toURI().toString();

			final Media audio = new Media(path);

			hitPlayer = new MediaPlayer(audio);

			hitPlayer.setVolume(VOLUME_EFFECTS);

			hitPlayer.setStartTime(Duration.millis(700));
			hitPlayer.play();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	// Joue le son de changement de niveau
	public static void playLevelUpSound() {
		if (!Configuration.audioEnabled)
			return;

		try {
			String path = Main.class.getResource("audio/levelup.mp3").toURI().toString();

			final Media audio = new Media(path);

			hitPlayer = new MediaPlayer(audio);

			hitPlayer.setVolume(VOLUME_EFFECTS);

			hitPlayer.play();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Gestion d'un clic sur le bouton "Music"
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
