package controller;

import java.io.IOException;

import javax.sound.sampled.LineUnavailableException;

import dao.Database;
import service.API;
import service.Player;
import service.Strings;

public class ButtonAction {
	private Strings strings;
	private Database database;
	private API getWave;
	private Player player;

	public ButtonAction() {
		strings = new Strings();
		database = new Database();
		getWave = new API();
		player = new Player();
	}

	/**
	 * Method designed for the custom input play buttons from the GUI. In which you
	 * take an inputed String and manipulate it: verify it (an alert is thrown
	 * if it is not passing the verification), format it, search it in the DB
	 * (if it is found in DB, it is played),obtain it from IBM api, store it in
	 * DB and played.
	 * 
	 * @throws IOException
	 * @throws LineUsnavailableException
	 */
	//TODO rename to "say" what it does
	public void customInputButton(String name) throws IOException, LineUnavailableException {

		String text = strings.formatString(name);
		byte[] wave = database.searchAndGet(text);
		if (wave == null) {
			wave = getWave.getWave(text);
			player.playWaveBytes(wave);
			database.insertAudioInDatabase(wave, text);
		} else {
			player.playWaveBytes(wave);
		}

	}

	/**
	 * Method that is formating the String for spelling, before contacting the API, saving it in the Database.
	 */
	public void customSpellButtons(String name) throws IOException, LineUnavailableException {
		customInputButton(strings.spellFormat(name));
	}

	/**
	 * Query the DB according to the name parameter and plays the
	 * byte array that is found. So the parameter name must be already present
	 * in the DB.
	 *
	 */
	public void predefinedButtons(String name) throws IOException, LineUnavailableException {
		
		player.playWaveBytes(database.getAudioFromDatabase(name));
	}
}
