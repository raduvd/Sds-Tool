package model;

///
//
//
//
///   Clasa asta e un test, trebuie apoi revizuita
//
///
//
//
import com.mongodb.MongoClient;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.DBCursor;

public class Database {
	
	private static MongoClient mongoClient = new MongoClient("localhost", 27017);
	private static DB database = mongoClient.getDB("Audio");

	private byte[] sound = new byte[1024];

	/*
	 * Metoda care adauga la MongoDB, un array de bytes care reprezinta un
	 * fisier wav in forma de bites.
	 * 
	 * @param sound reprezinta arrayul de bytes
	 * 
	 * @param name reprezinta valoarea fieldului "name" din documentul MongoDB
	 * si totodata reprezinta si textul care e transformat in wav
	 * 
	 * @param collection se va specifica unde sa se faca acest save in DB, in
	 * collectia de eng, fr, sp sau ger
	 */
	public void insertAudioInDatabase(byte[] wave, String name, String collection) {

		setSound(sound);
		DBCollection col = database.getCollection(collection);
		DBObject audio = new BasicDBObject("_id", name)
				.append("wav", wave);
		col.insert(audio);
		
	}

	public byte [] getAudioFromDatabase(String name, String collection) {

		DBCollection col = database.getCollection(collection);
		DBObject query = new BasicDBObject("_id", name);
		DBCursor cursor = col.find(query);
		DBObject numeObj = cursor.one();
		
		byte [] wave = new byte[1024];
		wave = (byte[])numeObj.get("wav");
		return wave;
	}

	public void queryForAudio(String name) {

	}

	public void setSound(byte[] sound) {
		this.sound = sound;
	}
}