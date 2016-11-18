package dao;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import service.API;

/**
 * @author radu.vancea
 *
 */
public class LocalDatabase {
	private static String projectName;
	private static ArrayList<String> localDatabase = new ArrayList<String>(300);

	public LocalDatabase() {

	}

	public static ArrayList<String> getLocalDatabse() {
		return localDatabase;
	}

	public static String getProjectName() {
		return projectName;
	}

	public static void setProjectName(String projectName) {
		LocalDatabase.projectName = projectName;
	}

	/**
	 * Returns a subList of the localDatabase List, according to the projectName
	 * and the language.
	 */
	public List<String> getButtonList() {
		List<String> buttonList;
		//TODO use startIndex and call sublist only once at the end
		//int startIndex = 0;
		if (projectName.equals("BMW"))
			switch (API.getLanguage()) {
			case "English":
				buttonList = localDatabase.subList(0, 20);
				break;
			case "French":
				buttonList = localDatabase.subList(20, 40);
				break;
			case "Spanish":
				buttonList = localDatabase.subList(40, 60);
				break;
			default:
				buttonList = localDatabase.subList(60, 80);
			}
		else if (projectName.equals("Audi"))
			switch (API.getLanguage()) {
			case "English":
				buttonList = localDatabase.subList(80, 100);
				break;
			case "French":
				buttonList = localDatabase.subList(100, 120);
				break;
			case "Spanish":
				buttonList = localDatabase.subList(120, 140);
				break;
			default:
				buttonList = localDatabase.subList(140, 160);
			}
		else if (projectName.equals("PSA/PCM"))
			switch (API.getLanguage()) {
			case "English":
				buttonList = localDatabase.subList(160, 180);
				break;
			case "French":
				buttonList = localDatabase.subList(180, 200);
				break;
			case "Spanish":
				buttonList = localDatabase.subList(200, 220);
				break;
			default:
				buttonList = localDatabase.subList(220, 240);
			}
		else {
			buttonList = localDatabase.subList(0, 20);
		}
		return buttonList;
	}

	/**
	 * Creates a file, from an ArrayList, each element in the ArrayList will be
	 * placed, alone on a single row.
	 */
	//TODO investigate using java Properties and a properties file, with texts in one row for each language
	public void createLocalDatabaseFile() {
		FileWriter resourceFile = null;
		PrintWriter writer = null;
		try {
			resourceFile = new FileWriter("resources/LocalDatabase.txt", true);
			writer = new PrintWriter(resourceFile);
			for (String buttonText : localDatabase)
				writer.printf("%s%n%s%n", "", buttonText);

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			writer.close();
		}
	}

	/**
	 * Will replace a value in the localDatabase field and file.
	 * 
	 * @param localDatabase
	 *            - is the master ArrayList, containing all the buttons texts
	 * @param positionToModify
	 *            - the index position from the localDatabase, that is being
	 *            modified
	 * @param newValue
	 *            - the new value that will be put on the positionToModify index
	 */
	public void modifyTheLocalDatabaseFile(int positionToModify, String newValue) {
		localDatabase.set(positionToModify, newValue);
		createLocalDatabaseFile();
	}

	/**
	 * Populates the localDatabase field, with elements from the LocalDatabase
	 * file. This method should be used after modification are made to the
	 * LocalDatabase file.
	 */
	public static void populateTheLocalDatabaseField() {
		FileReader fileReader = null;
		BufferedReader reader = null;
		try {
			fileReader = new FileReader("resources/LocalDatabase.txt");
			reader = new BufferedReader(fileReader);
			while (reader.readLine() != null) {
				localDatabase.add(reader.readLine());
			}
			reader.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();

		}
	}
}
