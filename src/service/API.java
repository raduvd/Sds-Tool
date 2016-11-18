package service;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

import org.apache.commons.io.IOUtils;

import com.ibm.watson.developer_cloud.text_to_speech.v1.TextToSpeech;
import com.ibm.watson.developer_cloud.text_to_speech.v1.model.AudioFormat;
import com.ibm.watson.developer_cloud.text_to_speech.v1.model.Voice;

/**
 * @author radu.vancea Class that connects to IBM API and gets a byte array
 * 
 */
public class API {

	private TextToSpeech synthesizer;
	private static Voice voice;
	private static String language;

	public static void setLanguage(String lang) {
		language = lang;

	}

	public static String getLanguage() {
		return language;
	}

	public API() {

		synthesizer = new TextToSpeech();
		String username = "483d5570-b00b-4aa8-99ca-24c888136e12";
		String password = "oIdJCrQ5Do5E";
		synthesizer.setUsernameAndPassword(username, password);

	}

	/**
	 * Connects to IBM API and returns a byte array that is representing a wave
	 * 
	 * @param text
	 *            the string that is going to be transformed into wave
	 * @param language
	 *            the language of this returned wave
	 */
	public byte[] getWave(String text) throws IOException {
		InputStream in = null;
		byte[] wave = null;
		try {

			in = synthesizer.synthesize(text, voice, AudioFormat.WAV).execute();
			byte [] temp = IOUtils.toByteArray(in);
			//the first 100 elements contain a clicking sound when the byte[] is played:
			wave = Arrays.copyOfRange(temp, 100,temp.length);
		} catch (Exception e) {
			// TODO a Prompt in the GUI for this? When 'in' is null or when wave is null.
		}

		return wave;

	}

	/**
	 * This method is setting the language
	 * 
	 * @param language
	 *            Mandatory "sp", "eng", "fr","ger"
	 * @return a Voice object
	 */
	public void setVoice(String lang) {

		switch (lang) {
		case "sp":
			voice = Voice.ES_LAURA;
			language = "Spanish";
			break;
		case "ger":
			voice = Voice.DE_BIRGIT;
			language = "German";
			break;
		case "fr":
			voice = Voice.FR_RENEE;
			language = "French";
			break;
		case "eng":
			voice = Voice.EN_ALLISON;
			language = "English";
		}

	}

}
