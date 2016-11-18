package controller;

import java.io.IOException;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.concurrent.Task;
import javafx.util.Duration;
import main.Main;
import service.Strings;
import view.ProjectsWindow;

public class Threads {

	private boolean threadIsFree;
	private Strings strings;
	private ButtonAction buttonAction;
	private Thread playThread;
	private Main main;

	public Threads() {
		threadIsFree = true;
		strings = new Strings();
		buttonAction = new ButtonAction();
		main = new Main();
	}

	/**
	 * 
	 * Creates a thread for each customInput button action (Play or Spell). No
	 * thread can be created, if the previous one has not been completed. Also
	 * this method is verifying if the String inputed has the correct length.
	 * 
	 */
	// TODO use synchronized method instead of boolean threadIsFree :
	// https://docs.oracle.com/javase/tutorial/essential/concurrency/syncmeth.html
	public void playThread(String name) throws IOException, LineUnavailableException {
		if (threadIsFree) {
			if (strings.verifyString(name)) {
				threadIsFree = false;
				Task<Void> task = new Task<Void>() {
					@Override
					public Void call() throws IOException, UnsupportedAudioFileException, LineUnavailableException {
						updateProgress(-1l, 10l);
						buttonAction.customInputButton(name);
						updateProgress(10l, 10l);
						Timeline timeline = new Timeline(
								new KeyFrame(Duration.millis(1000), ae -> updateProgress(0l, 10l)));
						timeline.play();
						threadIsFree = true;
						return null;
					}
				};

				playThread = new Thread(task);
				playThread.start();
				((ProjectsWindow) this).getProgressIndicator().progressProperty().bind(task.progressProperty());
			} else

			{
				main.verifyAlert();
			}
		} else {
			main.taskInProgress();
		}
	}

	/**
	 * Creates a thread for the predefined button action. This method is not
	 * verifying the String, before playing it.
	 */
	public void playPredefinedThread(String name) throws IOException, LineUnavailableException {
		if (threadIsFree) {
			threadIsFree = false;
			Task<Void> task = new Task<Void>() {
				@Override
				public Void call() throws IOException, UnsupportedAudioFileException, LineUnavailableException {
					updateProgress(-1, 10);
					buttonAction.predefinedButtons(name);
					updateProgress(10, 10);
					Timeline timeline = new Timeline(new KeyFrame(Duration.millis(1000), ae -> updateProgress(0, 10)));
					timeline.play();
					threadIsFree = true;
					return null;
				}
			};

			playThread = new Thread(task);
			playThread.start();
			((ProjectsWindow) this).getProgressIndicator().progressProperty().bind(task.progressProperty());
		} else

		{
			main.taskInProgress();
		}
	}
}
