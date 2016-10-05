package model;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
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

	/**
	 * nu stiu daca e nevoie de ea, dar nici daca nu e nevoie
	 * 
	 * @return
	 */
	public Stage getPrimaryStage() {
		return primaryStage;
	}

	/**
	 * If the inputed String, from the Custom fields, is not between 2-80 char
	 * and is starting with space, this alert will be shown.
	 */
	public void verifyAlert() {
		Alert alert = new Alert(AlertType.WARNING);
        alert.initOwner(primaryStage);
        alert.setTitle("Information");
        alert.setHeaderText("Review the inputted text!");
        alert.setContentText("The inputted text must not start with a space and must be between 1-80 characters for Play and 1-20 characters for Spell!");

        alert.showAndWait();
	}

	
	/**
	 * If the inputed String, from the Custom fields, is not between 2-80 char
	 * and is starting with space, this alert will be shown.
	 */
	public void taskInProgress() {
		Alert alert = new Alert(AlertType.WARNING);
        alert.initOwner(primaryStage);
        alert.setTitle("Information");
        alert.setHeaderText("Task is in progress!");
        alert.setContentText("Task is in progress! Please wait for the task to finish!");

        alert.showAndWait();
	}
	public static void main(String[] args) {
		launch(args);
	}
}
