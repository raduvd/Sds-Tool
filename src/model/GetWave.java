package model;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.ibm.watson.developer_cloud.text_to_speech.v1.TextToSpeech;
import com.ibm.watson.developer_cloud.text_to_speech.v1.model.AudioFormat;
import com.ibm.watson.developer_cloud.text_to_speech.v1.model.Voice;
import com.ibm.watson.developer_cloud.text_to_speech.v1.util.WaveUtils;

/**
 * @author radu.vancea
 * 
 */
public class GetWave {

	private String username = "5b75716c-b89d-4794-8703-72122fea2aae";
	private String password = "Sb7PMA47Mqds";
	private TextToSpeech synthesizer;

	public GetWave() {
		synthesizer = new TextToSpeech();
		synthesizer.setUsernameAndPassword(username, password);
	}

	/**
	 * 
	 * @param path
	 *            reprezinta locul unde sa fie downoadata acest output(in DB sau
	 *            in ceva loc specific sau nume specific..TODO here)
	 * @param name
	 *            reprezinta ce String se va transforma in wav si deasemenea ce
	 *            nume va avea acest wave
	 */
	public void getWave(String name, String path) throws IOException {

		String text = name;
		InputStream in = synthesizer.synthesize(text, Voice.EN_LISA, AudioFormat.WAV).execute();
		File f = new File(name + ".wav");
		writeToFile(WaveUtils.reWriteWaveHeader(in), f);

	}

	/**
	 * The string that is inputed for spelling is going to be formated
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
