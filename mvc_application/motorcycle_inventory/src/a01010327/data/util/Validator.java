/**
 * a01010327_assignment1
 * Validator.java
 * May 19, 2017
 * 11:32:51 AM
 */
package a01010327.data.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author amir deris, a01010327
 *
 */
public class Validator {

	/**
	 * validates an email address to be in the format word@word.word
	 * 
	 * @param data
	 * @return true if the email is valid, false otherwise.
	 */
	public static boolean validateEmail(String data) {
		String regex = "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$";
		Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(data);
		return matcher.matches();
	}

	/**
	 * validates a date to make sure the date is valid
	 * 
	 * @param data,
	 *            a String representing date in the format yyyyMMdd
	 * @return true if the date is valid, false otherwise.
	 */
	public static boolean validateDate(String data) {
		String dateFormat = "yyyyMMdd";
		try {
			LocalDate.parse(data, DateTimeFormatter.ofPattern(dateFormat));
			return true;
		} catch (DateTimeParseException e) {
			return false;
		}
	}
}
