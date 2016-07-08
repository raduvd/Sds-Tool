package model.view;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import model.Main;

public class MainWindowController {

	@FXML
	private void BMWButton() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("view/BMWWindow.fxml"));
			AnchorPane personOverview = (AnchorPane) loader.load();
			Main.borderPane.setCenter(personOverview);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
