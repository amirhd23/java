/**
 * a01010327_assignment1
 * InventoryReader.java
 * May 23, 2017
 * 8:08:55 PM
 */
package a01010327.io;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import a01010327.data.Inventory;
import a01010327.data.collections.Inventories;
import a01010327.exceptions.ApplicationException;

/**
 * @author amir deris, a01010327 This class is responsible for parsing Inventory
 *         input data
 */
public class InventoryReader {
	// index numbers for data in the array
	private static final int MOTORCYCLE_ID_INDEX = 0;
	private static final int DESCRIPTION_INDEX = 1;
	private static final int PART_NUMBER_INDEX = 2;
	private static final int PRICE_INDEX = 3;
	private static final int QUANTITY_INDEX = 4;
	private static final int NUMBER_OF_REQUIRED_FIELDS = 5;
	private static final String DELIMITER = "\\|";

	private static final Logger LOG = LogManager.getLogger();

	public static void readDataFromFile(String fileName) throws FileNotFoundException, ApplicationException {
		LOG.info("started reading Inventory data from file: " + fileName);
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
				Inventory i = readOneInventoryData(row.split(DELIMITER));
				Inventories.addInventory(i);
			}
			LOG.debug("number of Inventory items read from file, is: " + Inventories.getSize());
			LOG.info("finished reading Inventory data.");
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

	// helper method for reading record of one Inventory
	private static Inventory readOneInventoryData(String[] record) throws ApplicationException {
		// validate the number of input fields
		if (record.length != NUMBER_OF_REQUIRED_FIELDS) {
			generateExceptionForNotEnoughInput(record);
		}
		String motorcycleId = record[MOTORCYCLE_ID_INDEX];
		String description = record[DESCRIPTION_INDEX];
		String partNumber = record[PART_NUMBER_INDEX];
		double price = Double.parseDouble(record[PRICE_INDEX]);
		int quantity = Integer.parseInt(record[QUANTITY_INDEX]);
		Inventory inventory = new Inventory.Builder(motorcycleId, partNumber).description(description).price(price)
				.quantity(quantity).build();
		return inventory;
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
