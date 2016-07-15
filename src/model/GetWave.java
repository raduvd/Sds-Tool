package model;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.commons.io.IOUtils;

import com.ibm.watson.developer_cloud.text_to_speech.v1.TextToSpeech;
import com.ibm.watson.developer_cloud.text_to_speech.v1.model.AudioFormat;
import com.ibm.watson.developer_cloud.text_to_speech.v1.model.Voice;
import com.ibm.watson.developer_cloud.text_to_speech.v1.util.WaveUtils;

import model.Main;
import model.Database;

/**
 * @author radu.vancea
 * 
 */
public class GetWave {

	private String username = "d825669b-1183-4d32-9101-fd7d140e8b17";// "5b75716c-b89d-4794-8703-72122fea2aae";
	private String password = "kwG6w3F8TTji";// "Sb7PMA47Mqds";
	private TextToSpeech synthesizer;
	public Main mainApp = new Main();

	public GetWave() {
		synthesizer = new TextToSpeech();
		synthesizer.setUsernameAndPassword(username, password);
	}

	/**
	 * metoda aceasta practic face contact cu IBM server, dar aditonal eas si:
	 * insereaza in db si apoi face retreive din db si apoi face return la
	 * bytes, pe care ii transforma in inputstream, chestie ce trebuie
	 * modificata sa ramana doar IBM server contact
	 * 
	 * @param path
	 *            reprezinta locul unde sa fie downoadata acest output(in DB sau
	 *            in ceva loc specific sau nume specific..TODO here)
	 * @param name
	 *            reprezinta ce String se va transforma in wav si deasemenea ce
	 *            nume va avea acest wave
	 */
	public InputStream getWave(String name, String path, String collection) throws IOException {

		// TODO:acest input stream pe nume in, sa il stochez diret intr-un array
		// de bytes

		InputStream in = synthesizer.synthesize(name, Voice.EN_LISA, AudioFormat.WAV).execute();
		// test, am transformat input stream in bytes, apoi acestia trebuie sa
		// ii stochez in DB si apoi ca sa le dau play ii transform din nou in
		// inputstream
		// a e variabila care contine array de bites
		byte[] sound = new byte[1024];
		sound = IOUtils.toByteArray(in);
		Database db = new Database();
		db.insertAudioInDatabase(sound, name, collection);

		InputStream is = new ByteArrayInputStream(db.getAudioFromDatabase(name, collection));

		// File f = new File(name + ".wav");
		// writeToFile(WaveUtils.reWriteWaveHeader(is), f);
		return is;
		//
		//
	}

	/**
	 * The string that is inputed for spelling is going to be formated like: a -
	 * b - c
	 * 
	 * @param name
	 *            is the actual string that is transformed and returned by this
	 *            method
	 */
	public String spellFormat(String name) {

		String formated = "";
		for (int i = 0; i < name.length(); i++) {
			formated = formated + name.substring(i, i + 1) + " - ";
		}
		formated = formated.substring(0, formated.length() - 2);
		return formated;
	}

	/**
	 * Write the input stream to a file
	 */
	private static void writeToFile(InputStream in, File file) {
		try {
			OutputStream out = new FileOutputStream(file);
			// biti astia pe nume "buf" sa ii pun intr-un document din Mongodb
			byte[] buf = new byte[1024];
			int len;
			while ((len = in.read(buf)) > 0) {
				out.write(buf, 0, len);
			}
			out.close();
			in.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
