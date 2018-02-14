/**
 * a01010327_assignment1
 * CustomerReader.java
 * May 16, 2017
 * 12:37:53 PM
 */
package a01010327.io;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Scanner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import a01010327.data.Customer;
import a01010327.data.collections.Customers;
import a01010327.data.util.Validator;
import a01010327.exceptions.ApplicationException;

/**
 * @author amir deris, a01010327
 *
 */
public class CustomerReader {

	// index numbers for the data in the array
	private static final int ID_INDEX = 0;
	private static final int FIRST_NAME_INDEX = 1;
	private static final int LAST_NAME_INDEX = 2;
	private static final int STREET_NAME_INDEX = 3;
	private static final int CITY_INDEX = 4;
	private static final int POSTAL_CODE_INDEX = 5;
	private static final int PHONE_NUMBER_INDEX = 6;
	private static final int EMAIL_INDEX = 7;
	private static final int DATE_JOINED_INDEX = 8;
	private static final int NUMBER_OF_REQUIRED_FIELDS = 9;

	private static final String DELIMITER = "\\|";

	private static final Logger LOG = LogManager.getLogger();

	/**
	 * reads customers data from a file, and then adds them to the data member
	 * of the Customers class.
	 * 
	 * @param fileName,
	 *            the name of the input file. The first line of the file is
	 *            column names. The format of data in that file should be ("|"
	 *            is delimiter):
	 *            ID|FIRST_NAME|LAST_NAME|STREET|CITY|POSTAL_CODE|PHONE|EMAIL|JOIN_DATE
	 *            each row contains one record.
	 * @throws FileNotFoundException,
	 *             if the inputFile is not found.
	 * @throws ApplicationException,
	 *             if any other exception occurs.
	 */
	public static void readDataFromFile(String fileName) throws FileNotFoundException, ApplicationException {
		LOG.info("started reading Customer's data from file: " + fileName);
		File inputFile = new File(fileName);
		if (!inputFile.exists()) {
			throw new FileNotFoundException("File doesn't exist: " + fileName);
		}
		Scanner scanner = null;
		try {
			scanner = new Scanner(inputFile);
			scanner.nextLine();// ignore header line (first line)
			while (scanner.hasNext()) {
				String row = scanner.nextLine();
				Customer c = readOneCustomerData(row.split(DELIMITER));
				Customers.addCustomer(String.valueOf(c.getId()), c);
			}
			LOG.debug("length of Customer's map, read from file, is: " + Customers.getSize());
			LOG.info("finished reading data from file and generating Customer's map.");
		} catch (Exception e) {
			throw new ApplicationException(e);
		} finally {
			if (scanner != null) {
				try {
					scanner.close();
					LOG.debug("closed the Scanner.");
				} catch (Exception e) {
					throw new ApplicationException(e);
				}
			}
		}

	}

	// helper method for reading record of one Customer
	private static Customer readOneCustomerData(String[] record) throws ApplicationException {
		// validate the number of input fields
		if (record.length != NUMBER_OF_REQUIRED_FIELDS) {
			generateExceptionForNotEnoughInput(record);
		}
		int id = Integer.parseInt(record[ID_INDEX]);
		String firstName = record[FIRST_NAME_INDEX];
		String lastName = record[LAST_NAME_INDEX];
		String streetName = record[STREET_NAME_INDEX];
		String city = record[CITY_INDEX];
		String postalCode = record[POSTAL_CODE_INDEX];
		String phone = record[PHONE_NUMBER_INDEX];
		String emailAddress = null;
		if (Validator.validateEmail(record[EMAIL_INDEX])) {
			emailAddress = record[EMAIL_INDEX];
		} else {
			throw new ApplicationException(
					"a01010327.ApplicationException: " + record[EMAIL_INDEX] + " is an invalid email address");
		}
		String date = record[DATE_JOINED_INDEX];
		// validating the date
		if (!Validator.validateDate(date)) {
			throw new ApplicationException("Invalid date: " + date + " for record #" + id);
		}
		int dayJoined = Integer.parseInt(date.substring(6, 8));
		int monthJoined = Integer.parseInt(date.substring(4, 6));
		int yearJoined = Integer.parseInt(date.substring(0, 4));
		LocalDate dateJoined = LocalDate.of(yearJoined, monthJoined, dayJoined);

		Customer customer = new Customer.Builder(id, phone).firstName(firstName).lastName(lastName)
				.streetName(streetName).city(city).postalCode(postalCode).emailAddress(emailAddress)
				.dateJoined(dateJoined).build();
		return customer;

	}

	// helper method to generate error for not enough fields in the input
	private static void generateExceptionForNotEnoughInput(String[] fields) throws ApplicationException {
		LOG.debug("generating exception for not enough data for record: " + Arrays.toString(fields));
		StringBuilder sb = new StringBuilder();
		sb.append("Expected ");
		sb.append(NUMBER_OF_REQUIRED_FIELDS);
		sb.append(" elements but got ");
		sb.append(fields.length);
		sb.append(": [");
		for (String s : fields) {
			sb.append(s);
			sb.append(", ");
		}
		sb.delete(sb.length() - 2, sb.length());// deleting the last
												// comma
		sb.append("]");
		throw new ApplicationException(sb.toString());
	}
}
