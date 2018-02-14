/**
 * a01010327_assignment1
 * Bcmc.java
 * June 19th, 2017
 * 10:47:21 AM
 */
package a01010327;

import java.awt.EventQueue;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.ConfigurationSource;
import org.apache.logging.log4j.core.config.Configurator;

import a01010327.database.DatabaseController;
import a01010327.database.DbConstants;
import a01010327.exceptions.ApplicationException;
import a01010327.io.CustomerReader;
import a01010327.io.InventoryReader;
import a01010327.io.MotorcycleReader;
import a01010327.ui.MainFrame;

/**
 * @author amir deris, a01010327
 *
 */
public class Bcmc {

	public static final String CUSTOMERS_FILE_NAME = "customers.dat";
	public static final String INVENTORY_FILE_NAME = "inventory.dat";
	public static final String MOTORCYCLES_FILE_NAME = "motorcycles.dat";
	public static final String LOG4J_CONFIG_FILENAME = "log_configuration.xml";

	static {
		configureLogging();
	}
	private static final Logger LOG = LogManager.getLogger();

	/**
	 * main method of the application. use -drop as command line argument to
	 * drop tables and reinsert data from files.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		LocalDateTime start = LocalDateTime.now();
		LOG.info("program started at: " + start);
		new Bcmc().run(args);
		LocalDateTime end = LocalDateTime.now();
		LOG.info("program finished at: " + end);
		LOG.info("program duration: " + Duration.between(start, end).toMillis() + " ms");
	}

	/**
	 * runs the application. Customers data must be in the file "customers.dat"
	 * with this format:
	 * ID|FIRST_NAME|LAST_NAME|STREET|CITY|POSTAL_CODE|PHONE|EMAIL|JOIN_DATE
	 * Motorcycles data must in the file "motorcycles.dat" with this format:
	 * ID|MAKE|MODEL|YEAR|SERIAL_NUMBER|MILEAGE|CUSTOMER_ID Inventory data must
	 * be in the file "inventory.dat" with this format:
	 * MOTORCYCLE_ID|DESCRIPTION|PART#|PRICE|QUANTITY The first line of each
	 * file is field names and is ignored. In each file, "|" character is the
	 * delimiter. use -drop as command line argument to drop tables and reinsert
	 * data from files.
	 */
	public void run(String[] args) {
		boolean shouldDropTables = false;
		for (String arg : args) {
			if (arg.indexOf("drop") != -1) {
				shouldDropTables = true;
				break;
			}
		}
		DatabaseController dbController = DatabaseController.getInstance();
		if (shouldDropTables) {
			reinitializeDatabase(dbController);
		}
		if (!dbController.tableExists(DbConstants.CUSTOMER_TABLE_NAME)
				|| !dbController.tableExists(DbConstants.MOTORCYCLE_TABLE_NAME)
				|| !dbController.tableExists(DbConstants.INVENTORY_TABLE_NAME)) {
			reinitializeDatabase(dbController);
		}
		createGUI(dbController);
	}

	// helper method to reinitialize database
	private void reinitializeDatabase(DatabaseController dbController) {
		readDataFiles();
		dbController.dropTables();
		dbController.createTables();
		dbController.insertDataCollections();
	}

	// helper method to read data files
	private void readDataFiles() {
		try {
			CustomerReader.readDataFromFile(CUSTOMERS_FILE_NAME);
			MotorcycleReader.readDataFromFile(MOTORCYCLES_FILE_NAME);
			InventoryReader.readDataFromFile(INVENTORY_FILE_NAME);
		} catch (FileNotFoundException e) {
			LOG.error("file not found: " + CUSTOMERS_FILE_NAME);
		} catch (ApplicationException e) {
			LOG.error(e.getMessage());
		}
	}

	// helper method for creating gui
	private void createGUI(DatabaseController dbController) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainFrame frame = new MainFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	private static void configureLogging() {
		ConfigurationSource source;
		try {
			source = new ConfigurationSource(new FileInputStream(LOG4J_CONFIG_FILENAME));
			Configurator.initialize(null, source);
		} catch (IOException e) {
			System.err.println(
					String.format("Can't find the log4j logging configuration file %s.", LOG4J_CONFIG_FILENAME));
		}
	}
}
