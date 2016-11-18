package service;

//TODO rename class to XXXUtils and make all methods static
//TODO rename the whole package service == and make a note in Development on GoogleDrive about what is model and what is service, also the model package means propreties in principle.
// Externalize all properties that are subject to change:
//- db ip, port
//- api connection details
public class Strings {
	/**
	 * Format the string so that it is with no Uppercases or multiple spaces.
	 * This is to prevent unnecessary DB entries. Maybe a user will write
	 * "cojocarilor street" and another "Cojocarilor  Street", so there will be
	 * 1 unnecessary entry in the DB.
	 * 
	 * @param name
	 *            is the String inputed by the user
	 * @return the formated String
	 */
	public static String formatString(String name) {
		while (name.indexOf("  ") > 0) {
			name = name.replace("  ", " ");
		}

		return name.toLowerCase();
	}

	/**
	 * Verify the String inputed by the user, it has to be between 2-80
	 * characters long and not beginning with space. Too long Strings cannot be
	 * saved in the MongoDB, which supports only 16 MB documents, and to short
	 * Strings like one character or empty, may disturb some methods. Also
	 * String containing only spaces will return an error from IBM Api, so the
	 * first character must not be a space.
	 * 
	 * @param name
	 *            is the String inputed by the user
	 * @return the conclusion, true or false (true - to be accepted for further
	 *         processing or false to be rejected and to trigger an error prompt
	 */
	public static boolean verifyString(String name) {
		return !(name.isEmpty() || name.length() >= 80 || name.startsWith(" "));
	}

	/**
	 * The string that is inputed for spelling is going to be formated like: a -
	 * b - c
	 * 
	 * @param name
	 *            is the actual string that is transformed and returned by this
	 *            method
	 */
	public static String spellFormat(String name) {
		if (!(name.isEmpty())) {
			String formated = "";
			for (int i = 0; i < name.length() - 1; i++) {
				formated = formated + name.substring(i, i + 1) + " - ";
			}
			formated += name.charAt(name.length() - 1);
			return formated;
		} else {
			return name;
		}
	}
}
