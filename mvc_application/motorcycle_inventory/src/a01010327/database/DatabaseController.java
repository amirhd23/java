/**
 * a01010327_assignment2
 * DatabaseController.java
 * Jun 20, 2017
 * 12:53:09 PM
 */
package a01010327.database;

import java.sql.SQLException;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import a01010327.data.collections.Customers;
import a01010327.data.collections.Inventories;

import a01010327.data.collections.Motorcycles;
import a01010327.database.dao.CustomerDao;
import a01010327.database.dao.InventoryDao;
import a01010327.database.dao.MotorcycleDao;
import a01010327.database.util.DbUtil;

/**
 * @author amir deris, a01010327 class responsible for operations on database.
 */
public class DatabaseController {

	private static final Logger LOG = LogManager.getLogger();
	private static Database database;
	private final Properties dbProperties;
	private CustomerDao customerDao;
	private InventoryDao inventoryDao;
	private MotorcycleDao motorcycleDao;

	private static DatabaseController controllerInstance;

	/**
	 * private constructor using singleton pattern. Use getInstance method
	 * instead.
	 */
	private DatabaseController() {
		dbProperties = DbUtil.readPropertyFile(DbConstants.DB_PROPERTIES_FILENAME);
		database = new Database(dbProperties);

		CustomerDao.setDatabase(database);
		customerDao = CustomerDao.getInstance();

		InventoryDao.setDatabase(database);
		inventoryDao = InventoryDao.getInstance();

		MotorcycleDao.setDatabase(database);
		motorcycleDao = MotorcycleDao.getInstance();

		LOG.info("database controller initialized");
	}

	/**
	 * allows access to this instance of DatabaseController, so it can be
	 * re-used.
	 * 
	 * @return an instance of DatabaseController.
	 */
	public static DatabaseController getInstance() {
		if (controllerInstance == null) {
			controllerInstance = new DatabaseController();
		}
		return controllerInstance;
	}

	/**
	 * inserts collections of data from "a01010327.data.collections" package
	 * (read from file), into database
	 */
	public void insertDataCollections() {
		try {
			customerDao.insertCustomers(Customers.getData().values());
			LOG.debug("inserted all customer data to database");
		} catch (SQLException e) {
			LOG.error(e.getMessage());
		}
		try {
			motorcycleDao.insertMotorcycles(Motorcycles.getData());
			LOG.debug("inserted all motorcycle data to database");
		} catch (SQLException e) {
			LOG.error(e.getMessage());
		}
		try {
			inventoryDao.insertInventories(Inventories.getData());
			LOG.debug("inserted all inventory data to database");
		} catch (SQLException e) {
			LOG.error(e.getMessage());
		}
	}

	/**
	 * drops Customer, Motorcycle, Inventory tables from database
	 */
	public void dropTables() {
		try {
			motorcycleDao.drop();
		} catch (SQLException e) {
			LOG.error(e.getMessage());
		}
		try {
			customerDao.drop();
		} catch (SQLException e) {
			LOG.error(e.getMessage());
		}
		try {
			inventoryDao.drop();
		} catch (SQLException e) {
			LOG.error(e.getMessage());
		}
	}

	/**
	 * creates Customer, Motorcycle, Inventory tables
	 */
	public void createTables() {
		try {
			customerDao.create();
		} catch (SQLException e) {
			LOG.error(e.getMessage());
		}
		try {
			motorcycleDao.create();
		} catch (SQLException e) {
			LOG.error(e.getMessage());
		}
		try {
			inventoryDao.create();
		} catch (SQLException e) {
			LOG.error(e.getMessage());
		}
	}

	/**
	 * checks if the given table exist in database
	 * 
	 * @param tableName,
	 *            a String
	 * @return true if table exists, false otherwise
	 */
	public boolean tableExists(String tableName) {
		boolean result = false;
		try {
			result = DbUtil.tableExists(database.getConnection(), tableName);
		} catch (SQLException e) {
			LOG.error(e.getMessage());
		}
		return result;
	}

}
