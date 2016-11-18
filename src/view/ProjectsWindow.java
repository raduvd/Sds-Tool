package view;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.stage.Stage;
import main.Main;
import service.API;
import service.Strings;

import java.io.IOException;
import java.util.List;

import controller.Threads;
import dao.LocalDatabase;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.scene.Scene;

/**
 * 
 * 
 * @author radu.vancea
 *
 */
public class ProjectsWindow extends Threads {

	private Main mainApp;
	private Strings strings;
	private API api;
	private LocalDatabase localDB;

	public ProjectsWindow() {
		mainApp = new Main();
		strings = new Strings();
		api = new API();
		localDB = new LocalDatabase();

	}

	@FXML
	private ProgressIndicator progressIndicator = new ProgressIndicator();

	public ProgressIndicator getProgressIndicator() {
		return progressIndicator;
	}

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
	private Label inProgressLabel;
	@FXML
	private Label projectAndLanguage;

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
	private Button defined3;
	@FXML
	private Button defined4;
	@FXML
	private Button defined5;
	@FXML
	private Button defined6;
	@FXML
	private Button defined7;
	@FXML
	private Button defined8;
	@FXML
	private Button defined9;
	@FXML
	private Button defined10;
	@FXML
	private Button defined11;
	@FXML
	private Button defined12;
	@FXML
	private Button defined13;
	@FXML
	private Button defined14;
	@FXML
	private Button defined15;
	@FXML
	private Button defined16;
	@FXML
	private Button defined17;
	@FXML
	private Button defined18;
	@FXML
	private Button defined19;
	@FXML
	private Button defined20;
	@FXML
	private Button edit1;

	@FXML
	private void initialize() {
		Button[][] buttons = {
				{ defined1, defined2, defined3, defined4, defined5, defined6, defined7, defined8, defined9, defined10,
						defined11, defined12, defined13, defined14, defined15, defined16, defined17, defined18,
						defined19, defined20 },
				{ defined_yes, defined_no }, { play1, play2, play3, play4 }, { spell1, spell2, spell3, spell4 },
				{ defined_1, defined_2, defined_3, defined_4, defined_5, defined_6, defined_7, defined_8, defined_9 } };

		projectAndLanguage.setText(LocalDatabase.getProjectName() + " - " + API.getLanguage());
		List<String> buttonList = localDB.getButtonList();
		for (int i = 0, j = 0; i < buttons[0].length && j < buttonList.size(); i++, j++) {

			buttons[0][i].setText(buttonList.get(j));
		}
		for (int i = 0; i < buttons.length; i++) {
			for (int j = 0; j < buttons[i].length; j++)
				setButtonAction(buttons[i][j]);
		}
	}

	@FXML
	private void projectOverviewButton() {

		mainApp.showMainWindow();

	}

	@FXML
	public void english() {
		api.setVoice("eng");
		initialize();
	}

	@FXML
	public void french() {
		api.setVoice("fr");
		initialize();
	}

	@FXML
	public void german() {
		api.setVoice("ger");
		initialize();
	}

	@FXML
	public void spanish() {
		api.setVoice("sp");
		initialize();
	}

	/**
	 * Shows the Edit Prompt / Page
	 */

	@FXML
	public void editButton() {

		try {

			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("/view/ButtonList.fxml"));
			AnchorPane page = (AnchorPane) loader.load();

			Stage editStage = new Stage();
			editStage.setTitle("Edit Buttons");
			editStage.initModality(Modality.APPLICATION_MODAL);
			editStage.initOwner(mainApp.getPrimaryStage());
			Scene scene = new Scene(page);
			editStage.setScene(scene);
			editStage.showAndWait();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Set an action / thread for a button, depending on the name of the button.
	 * 
	 * @param buttonClicked
	 *            a String representing the name of the button
	 */
	public void setButtonAction(Button button) {
		button.setOnAction((event) -> {
			String buttonId = button.getId();
			char startingCharacter = buttonId.charAt(0);
			switch (startingCharacter) {
			case 'p':
				try {
					playThread(getField(buttonId));
				} catch (Exception e) {
					e.printStackTrace();
				}
				break;
			case 's':
				try {
					playThread(strings.spellFormat(getField(buttonId)));
				} catch (Exception e) {
					e.printStackTrace();
				}
				break;
			case 'd':
				try {
					playPredefinedThread(button.getText().toLowerCase());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

		});
	}

	/**
	 * Returns the String from the field corresponding to the play button
	 * number.
	 * 
	 * @param buttonClicked
	 *            a String representing the name of the button
	 * @return the String from the field corresponding to buttonClicked
	 */
	// Nu functioneaza daca il pun in alt paket!?
	public String getField(String buttonId) {
		char fieldNumber = buttonId.charAt(buttonId.length() - 1);
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
			selectedField = field1;
			break;
		}

		String input;
		input = selectedField.getText();

		return input;
	}

}
