package model;

import java.io.IOException;

import javax.sound.sampled.LineUnavailableException;

public class Controller {
	private Strings strings;
	private Database database;
	private GetWave getWave;
	private Player player;

	public Controller() {
		strings = new Strings();
		database = new Database();
		getWave = new GetWave();
		player = new Player();
	}

	/**
	 * Method designed for the custom play buttons from the GUI. In which you
	 * take an inputed String and manipulate it in this order: verifies (an
	 * alert is thrown if it is not passing the verification), format , searched
	 * in the DB (if it is found in DB, it is played),obtained from IBM api,
	 * stored in DB and played.
	 * 
	 * @throws IOException
	 * @throws LineUnavailableException
	 */
	public void customButtons(String name, String language) throws IOException, LineUnavailableException {

		String text = strings.formatString(name);
		byte[] wave = database.searchAndGet(text, language);
		if (wave == null) {
			wave = getWave.getWave(text, language);
			//TODO only insert is needed, we already have the wave from the API
			player.playBytes(database.insertAndGet(wave, text, language));
		} 
		player.playBytes(wave);		
	}

	/**
	 * To use or not to use? Same as customButtons() with the single difference
	 * that the parameter "name" is formated for spelling.
	 */
	public void customSpellButtons(String name, String language) throws IOException, LineUnavailableException {
		customButtons(strings.spellFormat(name), language);
	}

	/**
	 * Query the DB according to the name and language parameter and plays the
	 * byte array that is found. So the parameter name must be already present
	 * in the DB.
	 *
	 */
	public void predefinedButtons(String name, String language) throws IOException, LineUnavailableException {
		player.playBytes(database.getAudioFromDatabase(name, language));
	}
}
