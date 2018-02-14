/**
 * a01010327_assignment1
 * MotorcycleReader.java
 * May 19, 2017
 * 12:16:24 PM
 */
package a01010327.io;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import a01010327.data.Customer;
import a01010327.data.Motorcycle;
import a01010327.data.collections.Customers;
import a01010327.data.collections.Motorcycles;
import a01010327.exceptions.ApplicationException;

/**
 * @author amir deris, a01010327 This class is responsible for parsing
 *         Motorcycle input data
 */
public class MotorcycleReader {
	// index numbers for data in the array
	private static final int ID_INDEX = 0;
	private static final int MAKE_INDEX = 1;
	private static final int MODEL_INDEX = 2;
	private static final int YEAR_INDEX = 3;
	private static final int SERIAL_NUMBER_INDEX = 4;
	private static final int MILEAGE_INDEX = 5;
	private static final int CUSTOMER_ID_INDEX = 6;
	private static final int NUMBER_OF_REQUIRED_FIELDS = 7;

	private static final String DELIMITER = "\\|";
	private static final Logger LOG = LogManager.getLogger();

	/**
	 * reads Motorcycle data from a file, and then adds them to the data member
	 * of the Motorcycles class.
	 * 
	 * @param fileName,
	 *            the name of the file. The first line of the file is column
	 *            names. The format of data in that file should be ("|" is
	 *            delimiter):
	 *            ID|MAKE|MODEL|YEAR|SERIAL_NUMBER|MILEAGE|CUSTOMER_ID each row
	 *            contains one record.
	 * @throws FileNotFoundException,
	 *             if the inputFile is not found.
	 * @throws ApplicationException,
	 *             if any other exception occurs.
	 */
	public static void readDataFromFile(String fileName) throws FileNotFoundException, ApplicationException {
		LOG.info("started reading Motorcycle's data from file: " + fileName);
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
				Motorcycle m = readOneBikeData(row.split(DELIMITER));
				Motorcycles.addMotorcycle(m);
			}
			LOG.debug("number of Motorcycles read from file, is: " + Motorcycles.getSize());
			LOG.info("finished reading Motorcycle data.");
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

	// helper method for reading record of one Motorcycle
	private static Motorcycle readOneBikeData(String[] record) throws ApplicationException {
		// validate the number of input fields
		if (record.length != NUMBER_OF_REQUIRED_FIELDS) {
			generateExceptionForNotEnoughInput(record);
		}
		String motorcycleId = record[ID_INDEX];
		String make = record[MAKE_INDEX];
		String model = record[MODEL_INDEX];
		int year = Integer.parseInt(record[YEAR_INDEX]);
		String serialNumber = record[SERIAL_NUMBER_INDEX];
		int mileage = Integer.parseInt(record[MILEAGE_INDEX]);
		String customerId = record[CUSTOMER_ID_INDEX];
		Customer owner = Customers.getCustomer(customerId);

		Motorcycle motorcycle = new Motorcycle(make, model, motorcycleId, year, serialNumber, mileage, owner);

		return motorcycle;
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
