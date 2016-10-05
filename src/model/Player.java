package model;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;

public class Player {

	private int BUFFER_SIZE;
	private AudioInputStream audioStream;
	private AudioFormat audioFormat;
	private SourceDataLine sourceLine;

	public Player(){
		BUFFER_SIZE = 128000;
		audioFormat = new AudioFormat(22050.0F,16,1,true,false);
		
	}

	/**
	 * Method that plays a byte array
	 * @param wave is a byte array
	 * @throws LineUnavailableException
	 */
	public void playBytes(byte[] wave) throws LineUnavailableException {

		InputStream input = new ByteArrayInputStream(wave);
		audioStream = new AudioInputStream(input, audioFormat, wave.length / audioFormat.getFrameSize());
		DataLine.Info info = new DataLine.Info(SourceDataLine.class, audioFormat);
		sourceLine = (SourceDataLine) AudioSystem.getLine(info);

		try {
			sourceLine = (SourceDataLine) AudioSystem.getLine(info);
			sourceLine.open(audioFormat);
		} catch (LineUnavailableException e) {
			e.printStackTrace();
			System.exit(1);
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}

		sourceLine.start();
		int nBytesRead = 0;
		byte[] abData = new byte[BUFFER_SIZE];
		while (nBytesRead != -1) {
			try {
				nBytesRead = audioStream.read(abData, 0, abData.length);

			} catch (IOException e) {
				e.printStackTrace();
			}
			if (nBytesRead >= 0) {
				@SuppressWarnings("unused")
				int nBytesWritten = sourceLine.write(abData, 0, nBytesRead);
			}
		}
		sourceLine.drain();
		sourceLine.close();
	}

}
