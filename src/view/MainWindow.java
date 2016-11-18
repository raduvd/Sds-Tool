package view;

import java.io.IOException;

import dao.LocalDatabase;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import main.Main;
import service.API;

public class MainWindow {

	private void loadFXMLResource(String projectName) {
			LocalDatabase.setProjectName(projectName);
			new API().setVoice("eng");
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("/view/ProjectsWindow.fxml"));
			AnchorPane personOverview;
			try {
				personOverview = (AnchorPane) loader.load();
				Main.borderPane.setCenter(personOverview);
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		
	}

	@FXML
	private void BMWButton() {
		loadFXMLResource("BMW");
	}

	@FXML
	private void AudiButton() {
		loadFXMLResource("Audi");
	}
	@FXML
	private void PSA_PCMButton() {
		loadFXMLResource("PSA/PCM");
	}
	
}
