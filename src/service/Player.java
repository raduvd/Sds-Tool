package service;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class Player {

	private int BUFFER_SIZE;
	private AudioInputStream audioStream;
	private AudioFormat audioFormat;
	private SourceDataLine sourceLine;

	public Player() {
		BUFFER_SIZE = 128000;
		// Other settings for audioFormat: (11025,16,2,true,true); /
		// For the waves on IBM API:(22050.0F, 16, 1,true,false)
		// For the waves on the old database : (18050.0F, 16, 1,true,false)
		audioFormat = new AudioFormat(22050.0F, 16, 1,true,false);

	}

	/**
	 * Method that plays a byte array, containing a wave sound.
	 * 
	 * @param wave
	 *            is a byte array
	 * @throws LineUnavailableException
	 */
	public void playWaveBytes(byte[] wave) throws LineUnavailableException {

		InputStream input = new ByteArrayInputStream(wave);
		audioStream = new AudioInputStream(input, audioFormat, wave.length / audioFormat.getFrameSize());
		// Another way to play wav bytes, using Clip (it seems to not work with
		// the waves from the old database):
		/*
		 * Clip clip = AudioSystem.getClip(); try { clip.open(audioStream); }
		 * catch (IOException e) { // TODO Auto-generated catch block
		 * e.printStackTrace(); } clip.start();
		 * 
		 */ 
		DataLine.Info info = new DataLine.Info(SourceDataLine.class, audioFormat);
		sourceLine = (SourceDataLine) AudioSystem.getLine(info);

		try {
			sourceLine = (SourceDataLine) AudioSystem.getLine(info);
			sourceLine.open(audioFormat);
		} catch (LineUnavailableException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
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

	/**
	 * Method that plays a byte array, containing an mp3 sound. I may use this
	 * method, when I play bytes from the old SDS tool, that takes its bytes
	 * from Google Translate API.
	 */
	public void playMp3Bytes(byte[] mp3) {
		// TODO
		String path = "resources/temp1.mp3";
		File someFile = new File(path);
		FileOutputStream fos;
		try {
			fos = new FileOutputStream(someFile);
			fos.write(mp3);
			fos.flush();
			fos.close();

		} catch (FileNotFoundException e) {

			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();

		} finally {
			someFile.deleteOnExit();
		}

		Media hit = new Media(Paths.get(path).toUri().toString());
		MediaPlayer mediaPlayer = new MediaPlayer(hit);
		mediaPlayer.play();
	}
}
