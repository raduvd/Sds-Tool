package model;

import com.mongodb.MongoClient;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.DBCursor;

/**
 * @author radu.vancea Class to insert a new document in the DB and to query the
 *         DB
 */
public class Database {

	private MongoClient mongoClient;
	private DB database;

	public Database() {
		mongoClient = new MongoClient("localhost", 27017);
		database = mongoClient.getDB("Audio");
	}

	/**
	 * Inserts a new document in MongoDB.
	 * 
	 * @param wave
	 *            is an audio in the form of a byte array
	 * 
	 * @param name
	 *            is the id of the new document from the database, and it is
	 *            also the audio content
	 * 
	 * @param language
	 *            is the collection name and also the language of the audio
	 */
	public void insertAudioInDatabase(byte[] wave, String name, String language) {

		DBCollection col = database.getCollection(language);
		DBObject audio = new BasicDBObject("_id", name).append("wav", wave);
		col.insert(audio);

	}

	/**
	 * Method to insert the byte array obtained from IBM API in the Database.
	 * This method also returns the same byte array from the DB.
	 * 
	 * @param waveIn
	 *            is the byte array from IBM API
	 * @param name
	 *            is the id of the new document from the database, and it is
	 *            also the audio content
	 * @param language
	 *            is the collection name and also the language of the audio
	 * @return the byte array from the DB
	 */
	public byte[] insertAndGet(byte[] waveIn, String name, String language) {
		DBCollection col = database.getCollection(language);
		DBObject audio = new BasicDBObject("_id", name).append("wav", waveIn);
		col.insert(audio);
		DBObject query = new BasicDBObject("_id", name);
		DBCursor cursor = col.find(query);
		DBObject numeObj = cursor.one();
		byte[] waveOut = new byte[1024];
		waveOut = (byte[]) numeObj.get("wav");
		return waveOut;
	}

	/**
	 * Makes a query in DB and returns a byte array from DB. The byte array is
	 * the audio. Method used for the predefined commands buttons. Is the same
	 * as the searchAndGet() method.
	 * 
	 * @param name
	 *            is the id of the news document from the database, and it is
	 *            also the audio content
	 * 
	 * @param language
	 *            is the collection name and also the language of the audio
	 */
	public byte[] getAudioFromDatabase(String name, String language) {

		DBCollection col = database.getCollection(language);
		DBObject query = new BasicDBObject("_id", name);
		DBCursor cursor = col.find(query);
		DBObject numeObj = cursor.one();
		byte[] wave = new byte[1024];
		wave = (byte[]) numeObj.get("wav");
		return wave;
	}

	/**
	 * Checks if a specific document is present in the Database. It is used for
	 * the Save button from Edit Predefined Buttons menu.
	 * 
	 * @param name
	 *            is the id of the document from DB
	 * @param collection
	 *            is the collection name.
	 * @return a conclusion of this check.
	 */
	public boolean searchDatabase(String name, String language) {
		boolean conclusion;
		DBCollection col = database.getCollection(language);
		DBObject query = new BasicDBObject("_id", name);
		DBCursor cursor = col.find(query);

		if (cursor.count() > 0) {
			conclusion = true;
		} else {
			conclusion = false;
		}
		return conclusion;
	}

	/**
	 * Search the DB and returns the byte array if it is found, otherwise it
	 * returns a null byte array. This method will be used for the custom Play
	 * buttons, Custom Spell buttons, Custom Number Button. If the return byte
	 * array is null, then the getWave from IBM method is used to download a new
	 * audio. After the audio is downloaded, it is played.
	 * 
	 * @param name
	 *            is the id from DB, also the content of the audio
	 * @param language
	 *            is the collection name from DB
	 * @return
	 */
	public byte[] searchAndGet(String name, String language) {
		DBCollection col = database.getCollection(language);
		DBObject query = new BasicDBObject("_id", name);
		DBCursor cursor = col.find(query);
		byte[] wave = new byte[1024];

		if (cursor.count() > 0) {
			DBObject numeObj = cursor.one();
			wave = (byte[]) numeObj.get("wav");
		} else {
			wave = null;
		}
		return wave;
	}

}