package model.view;

//teste
import javafx.concurrent.Task;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.stage.Stage;
import javafx.util.Duration;
//

import java.io.IOException;

import javax.sound.sampled.UnsupportedAudioFileException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.scene.Scene;
import model.GetWave;
import model.Main;
import model.Player;

/**
 * @author radu.vancea
 *
 */
public class BMWWindowController {

	private Player player = new Player();
	private GetWave gw = new GetWave();
	private Main mainApp = new Main();

	@FXML
	private TextField inputField1;
	@FXML
	private TextField inputField2;
	@FXML
	private TextField numberInputField;
	@FXML
	Label inProgressLabel;
	@FXML
	private Button playVariableButton1;
	@FXML
	private Button playVariableButton2;
	@FXML
	private Button numberVariableButton1;
	@FXML
	private Button numberVariableButton2;
	@FXML
	private Button numberVariableButton3;
	@FXML
	private Button numberVariableButton4;
	@FXML
	private Button numberVariableButton5;
	@FXML
	private Button numberVariableButton6;
	@FXML
	private Button numberVariableButton7;
	@FXML
	private Button numberVariableButton8;
	@FXML
	private Button numberVariableButton9;

	@FXML
	private void projectOverviewButton() {

		mainApp.showMainWindow();
	}

	@FXML
	private void streetButton() {
		player.playSound("/street.wav");

	}

	@FXML
	private void destinationInputButton() {
		player.playSound("/destination input.wav");

	}

	@FXML
	private void playButton1() throws IOException {
		playButtons(inputField1.getText());

	}

	@FXML
	private void playButton2() throws IOException {
		playButtons(inputField2.getText());
	}

	@FXML
	private void spellButton1() throws IOException {
		playButtons(gw.spellFormat(inputField1.getText()));

	}

	@FXML
	private void spellButton2() throws IOException {
		playButtons(gw.spellFormat(inputField2.getText()));
	}

	@FXML
	private void numberButton1() throws IOException {
		playFromResourceButtons(numberVariableButton1.getText());
	}

	@FXML
	private void numberButton2() throws IOException {
		playFromResourceButtons(numberVariableButton2.getText());
	}

	@FXML
	private void numberButton3() throws IOException {
		playFromResourceButtons(numberVariableButton3.getText());
	}

	@FXML
	private void numberButton4() throws IOException {
		playFromResourceButtons(numberVariableButton4.getText());
	}

	@FXML
	private void numberButton5() throws IOException {
		playFromResourceButtons(numberVariableButton5.getText());
	}

	@FXML
	private void numberButton6() throws IOException {
		playFromResourceButtons(numberVariableButton6.getText());
	}

	@FXML
	private void numberButton7() throws IOException {
		playFromResourceButtons(numberVariableButton7.getText());
	}

	@FXML
	private void numberButton8() throws IOException {
		playFromResourceButtons(numberVariableButton8.getText());
	}

	@FXML
	private void numberButton9() throws IOException {
		playFromResourceButtons(numberVariableButton9.getText());
	}

	// Set the Edit button action (show the Edit Page), de revizuit
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
	////////// DE REVIZUIT TOAATA SECTIUNEA ASTA, e o sectiune de teste
	//
	//
	//
	@FXML
	private ProgressIndicator ind = new ProgressIndicator();

	/*
	 * ACEASTA METODA TREBUIE MUTATA IN ALTA CLASA, dupa revizie amauntita, deoarece ea va mai fi
	 * folosita si in PorcheProject si in altele.
	 * 
	 * Method that makes a thread (task) and ties it to a progress indicator. In
	 * this thread the audio is retrieved from IBM server and it is also played.
	 * 
	 * @param input is of type String and the method actually plays the string
	 * from a field
	 */
	public void playButtons(String input) throws IOException {

		Task<Void> task = new Task<Void>() {
			@Override
			public Void call() throws IOException, UnsupportedAudioFileException {
				updateProgress(-1, 10);
				//parametrul eng trebuie schimbat de la Limba la Limba
				player.playInputStream(gw.getWave(input, "notImplemented","eng"));
				updateProgress(10, 10);
				Timeline timeline = new Timeline(new KeyFrame(Duration.millis(1000), ae -> updateProgress(0, 10)));
				timeline.play();
				return null;
			}

		};
		ind.progressProperty().bind(task.progressProperty());
		new Thread(task).start();

	}

	/*
	 * Aceasta metoda trebuie mutata in alta clasa pentru ca va fi folosita de
	 * mai multe clase ca si PorcheWindow si altele. Aceasta metoda va face play
	 * la o resursa
	 * 
	 * @param input reprezinta textul care e scris pe buton. Acest text trebuie
	 * sa fie identic cu fisierul .wav care e in resource eu voi folosi clasa
	 * asta doar pentru butoanele cu numere
	 */
	public void playFromResourceButtons(String input) {
		inProgressLabel.setText("In Progress...");
		Task<Void> task = new Task<Void>() {
			@Override
			public Void call() throws IOException {
				updateProgress(-1, 10);
				gw.getWave(input, "notImplemented","eng");
				player.playSound("/" + input + ".wav/");
				updateProgress(10, 10);
				Timeline timeline = new Timeline(new KeyFrame(Duration.millis(1000), ae -> updateProgress(0, 10)));
				timeline.play();
				return null;
			}

		};
		ind.progressProperty().bind(task.progressProperty());
		new Thread(task).start();
		inProgressLabel.setText("Audio Progress");
	}

	/*
	 * Metoda care e legata de actiunea butonului Play de la sectiunea numbers.
	 * In cazul in care sunt introduse litere, acestea nu vor putea fi
	 * transformate in int, si se va arunca un Exception, prins de un Alert
	 * prompt.
	 */
	@FXML
	public void playCustomNumber() throws IOException {
		int numere = 0;

		try {
			numere = Integer.parseInt(numberInputField.getText());

		} catch (Exception e) {
			e.getMessage();
			Alert prompt = new Alert(AlertType.INFORMATION);
			prompt.initOwner(mainApp.getPrimaryStage());
			prompt.setHeaderText("Insert Number");
			prompt.setTitle("Information Prompt");
			prompt.setContentText("Please insert a number in the field!");
			prompt.showAndWait();
		}
		playButtons(Integer.toString(numere));
	}
}

// .
// .
// .
// .
// .
