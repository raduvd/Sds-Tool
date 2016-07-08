package model;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;


public class Main extends Application {

	private Stage primaryStage;
	public static BorderPane borderPane;

	@Override
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("SDS Tool");
		this.primaryStage.getIcons().add(new Image("file:resources/1467130063_Microphone.png"));
		
		initBorderPane();

		showMainWindow();
	
	}

	/**
	 * Seteaza un BorderPane pe Scena
	 */
	public void initBorderPane() {
		try {

			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("view/borderPane.fxml"));
			borderPane = (BorderPane) loader.load();
			Scene scene = new Scene(borderPane);
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Seteaza pagina main in centrul la BorderPane
	 */
	public void showMainWindow() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("view/MainWindow.fxml"));
			AnchorPane personOverview = (AnchorPane) loader.load();
			borderPane.setCenter(personOverview);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Stage getPrimaryStage() {
		return primaryStage;
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
