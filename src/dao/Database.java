package dao;

import com.mongodb.MongoClient;

import service.API;

import com.mongodb.DB;
import com.mongodb.DBCollection;

import java.io.IOException;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.DBCursor;

/**
 * @author radu.vancea - Class to insert a new document in the DB and to query
 *         the DB
 */
public class Database {

	private MongoClient mongoClient;
	private DB database;

	@SuppressWarnings("deprecation")
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
	 */
	public void insertAudioInDatabase(byte[] wave, String name) {
		DBCollection colection = database.getCollection(API.getLanguage());
		DBObject document = new BasicDBObject("_id", name).append("wav", wave);
		colection.insert(document);

	}

	/**
	 * Method to insert the byte array obtained from IBM API in the Database.
	 * This method also returns the same byte array from the DB.
	 * 
	 * @param waveIn
	 *            the byte array from IBM API
	 * @param name
	 *            the id of the new document from the database, and it is also
	 *            the audio content
	 * @return the byte array from the DB
	 */
	public byte[] insertAndGet(byte[] wave, String name) {
		DBCollection colection = database.getCollection(API.getLanguage());
		DBObject document = new BasicDBObject("_id", name).append("wav", wave);
		colection.insert(document);
		DBObject query = new BasicDBObject("_id", name);
		DBCursor cursor = colection.find(query);
		DBObject searchedDocument = cursor.one();
		byte[] waveOutput = new byte[1024];
		waveOutput = (byte[]) searchedDocument.get("wav");
		return waveOutput;
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
	 * @throws IOException
	 */
	public byte[] getAudioFromDatabase(String name) throws IOException {
		DBCollection colection = database.getCollection(API.getLanguage());
		DBObject query = new BasicDBObject("_id", name);
		DBCursor cursor = colection.find(query);
		DBObject searchedDocument = cursor.one();
		byte[] wave = new byte[1024];
		// TODO check for null
		wave = (byte[]) searchedDocument.get("wav");
		// test in care incerc sa transform fara cast returnul metodei
		// .get("wav") in byte array
		// numeObj.get("wav") - face return la un Obiect, nu e prea bine, nu e
		// alta clasa
		// ---
		// bos = new ByteArrayOutputStream();
		// ObjectOutput out = new ObjectOutputStream(bos);
		// out.writeObject(numeObj.get("wav"));
		// return bos.toByteArray();
		// ---
		// byte[] wave = SerializationUtils.serialize((Serializable)
		// numeObj.get("wav"));
		// test
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
	public boolean searchDatabase(String name) {
		DBCollection colection = database.getCollection(API.getLanguage());
		DBObject query = new BasicDBObject("_id", name);
		DBCursor cursor = colection.find(query);
		return cursor.count() > 0;
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
	 * @return
	 */
	// TODO - this method seems to be the same with getAudioFromTheDatabase,
	// from this 2, make 3 but shorter ;), because they duplicate a lot
	public byte[] searchAndGet(String name) {
		DBCollection col = database.getCollection(API.getLanguage());
		DBObject query = new BasicDBObject("_id", name);
		DBCursor cursor = col.find(query);
		byte[] wave = new byte[1024];

		if (cursor.count() > 0) {
			DBObject searchedDocument = cursor.one();
			wave = (byte[]) searchedDocument.get("wav");
		} else {
			wave = null;
		}
		return wave;
	}

}