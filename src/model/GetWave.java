package model;

import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;

import com.ibm.watson.developer_cloud.text_to_speech.v1.TextToSpeech;
import com.ibm.watson.developer_cloud.text_to_speech.v1.model.AudioFormat;
import com.ibm.watson.developer_cloud.text_to_speech.v1.model.Voice;

/**
 * @author radu.vancea Class that connects to IBM API and gets can get a byte
 *         array
 * 
 */
public class GetWave {

	private TextToSpeech synthesizer;

	public GetWave() {

		synthesizer = new TextToSpeech();
		String username = "63ca9cce-3a2a-4db6-b0bd-85a28e9119c5";
		String password = "6jsRyXi3wumC";
		synthesizer.setUsernameAndPassword(username, password);
	}

	/**
	 * Connects to IBM API and returns a byte array that is representing a wave
	 * 
	 * @param name
	 *            the string that is going to be transformed into wave
	 * @param language
	 *            the language of this returned wave
	 */
	public byte[] getWave(String name, String language) throws IOException {

		InputStream in = synthesizer.synthesize(name, setVoice(language), AudioFormat.WAV).execute();
		byte[] wave = new byte[1024];
		wave = IOUtils.toByteArray(in);
		return wave;

	}

	/**
	 * Metoda care va seta limba la wave
	 * 
	 * @param language
	 *            va fi mandatory "sp", "eng", "fr","de"
	 * @return un obiect de tip Voice care va seta mai departe limba catre IBM
	 *         API server
	 */
	private Voice setVoice(String language) {
		Voice voice = new Voice();
		switch (language) {
		case "sp": 
		}
		//TO DO switch instead of ifs
		if (language == "sp") {
			voice = Voice.ES_LAURA;
		} else if (language == "ger") {
			voice = Voice.DE_BIRGIT;
		} else if (language == "fr") {
			voice = Voice.FR_RENEE;
		} else {
			voice = Voice.EN_ALLISON;
		}
		return voice;
	}

}
