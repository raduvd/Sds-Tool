package model.view;

import javafx.scene.control.Button;

import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.scene.Scene;
import model.Main;
import model.Strings;
import model.Controller;

/**
 * CLasa asta trebuie modificata complect, am scris multe chesti de test in ea.
 * Orice metoda din aceasta clasa, care ramane definitiva, trebuie reverificata
 * si facut o descriere javadoc.
 * 
 * @author radu.vancea
 *
 */
public class BMWWindowController {

	private Main mainApp = new Main();
	private Controller controller = new Controller();
	private Strings strings;
	private Main main;
	private Thread playThread;
	private boolean threadIsFree;
	private String buttonClicked;

	public BMWWindowController() {

		strings = new Strings();
		main = new Main();
		threadIsFree = true;
		buttonClicked = "";

	}

	@FXML
	private ProgressIndicator ind = new ProgressIndicator();
	@FXML
	private TextField field1;
	@FXML
	private TextField field2;
	@FXML
	private TextField field3;
	@FXML
	private TextField field4;
	@FXML
	private TextField numberInputField;
	@FXML
	Label inProgressLabel;

	// Tests
	// Tests
	// Tests
	// Tests

	/**
	 * metoda de thread pentru play buttons
	 * 
	 * * Verificarea stringului sa nu fie mai lung sau mai scurt, trebuie facuta
	 * in afara unui thread. Deci e mai bine de facut in afara threadului.
	 * 
	 */
	public void playThread(String name, String language) throws IOException, LineUnavailableException {
		if (threadIsFree) {
			if (strings.verifyString(name)) {
				threadIsFree = false;
				Task<Void> task = new Task<Void>() {
					@Override
					public Void call() throws IOException, UnsupportedAudioFileException, LineUnavailableException {
						updateProgress(-1, 10);
						controller.customButtons(name, language);
						updateProgress(10, 10);
						Timeline timeline = new Timeline(
								new KeyFrame(Duration.millis(1000), ae -> updateProgress(0, 10)));
						timeline.play();
						// test
						threadIsFree = true;
						//
						return null;
					}
				};

				playThread = new Thread(task);
				playThread.start();
				ind.progressProperty().bind(task.progressProperty());
			} else

			{
				main.verifyAlert();
			}
		} else {
			main.taskInProgress();
		}
	}

	/**
	 * metoda de thread pentru predefined buttons.
	 * 
	 * 
	 */
	public void playPredefinedThread(String name, String language) throws IOException, LineUnavailableException {
		if (threadIsFree) {
			threadIsFree = false;
			Task<Void> task = new Task<Void>() {
				@Override
				public Void call() throws IOException, UnsupportedAudioFileException, LineUnavailableException {
					updateProgress(-1, 10);
					controller.predefinedButtons(name, language);
					updateProgress(10, 10);
					Timeline timeline = new Timeline(new KeyFrame(Duration.millis(1000), ae -> updateProgress(0, 10)));
					timeline.play();
					threadIsFree = true;
					return null;
				}
			};

			playThread = new Thread(task);
			playThread.start();
			ind.progressProperty().bind(task.progressProperty());
		} else

		{
			main.taskInProgress();
		}
	}

	@FXML
	private void initialize() {
		Button[] buttons = { play1, play2, play3, play4, spell1, spell2, spell3, spell4, defined_1, defined_2, defined_3, defined_4, defined_5, defined_6, defined_7, defined_8,
				defined_9, defined1, defined2, defined_yes, defined_no };
		for (int i = 0; i < buttons.length; i++) {
			setButtonAction(buttons[i]);
		}

	}

	public void setButtonAction(Button button) {
		button.setOnAction((event) -> {
			buttonClicked = button.getId();
			if (buttonClicked.startsWith("p")) {
				try {
					playThread(getField(buttonClicked), "eng");
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else if (buttonClicked.startsWith("s")) {
				try {
					playThread(strings.spellFormat(getField(buttonClicked)), "eng");
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else if (buttonClicked.startsWith("d")) {
				try {
					playPredefinedThread(button.getText().toLowerCase(), "eng");
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Metoda in functie de ce valoare avem in buttonclicked
	 * 
	 * @param buttonClicked
	 *            e un String care reprezinta numele butonului care e clickuit
	 * @return the string from the field corresponding to buttonClicked
	 */
	public String getField(String buttonClicked) {
		char fieldNumber = buttonClicked.charAt(buttonClicked.length() - 1);
		TextField selectedField;
		switch (fieldNumber) {
		case '1':
			selectedField = field1;
			break;
		case '2':
			selectedField = field2;
			break;
		case '3':
			selectedField = field3;
			break;
		case '4':
			selectedField = field4;
			break;
		default:
			selectedField = field2;// care sa il pun default?
			break;
		}

		String input = selectedField.getText();
		return input;
	}

	// Tests
	// Tests
	@FXML
	private Button play1;
	@FXML
	private Button play2;
	@FXML
	private Button play3;
	@FXML
	private Button play4;
	@FXML
	private Button spell1;
	@FXML
	private Button spell2;
	@FXML
	private Button spell3;
	@FXML
	private Button spell4;
	@FXML
	private Button defined_1;
	@FXML
	private Button defined_2;
	@FXML
	private Button defined_3;
	@FXML
	private Button defined_4;
	@FXML
	private Button defined_5;
	@FXML
	private Button defined_6;
	@FXML
	private Button defined_7;
	@FXML
	private Button defined_8;
	@FXML
	private Button defined_9;
	@FXML
	private Button defined_yes;
	@FXML
	private Button defined_no;
	@FXML
	private Button defined1;
	@FXML
	private Button defined2;
	@FXML
	private Button edit1;

	@FXML
	private void projectOverviewButton() {

		mainApp.showMainWindow();
	}

	/**
	 * Shows the Edit Prompt / Page
	 */

	@FXML
	public void editButton() {
		try {

			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("view/EditPage.fxml"));
			AnchorPane page = (AnchorPane) loader.load();

			Stage editStage = new Stage();
			editStage.setTitle("Edit Button");
			editStage.initModality(Modality.APPLICATION_MODAL);
			editStage.initOwner(mainApp.getPrimaryStage());
			Scene scene = new Scene(page);
			editStage.setScene(scene);
			editStage.showAndWait();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
