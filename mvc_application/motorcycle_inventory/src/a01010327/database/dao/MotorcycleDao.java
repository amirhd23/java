/**
 * a01010327_assignment2
 * MotorcycleDao.java
 * Jun 19, 2017
 * 2:15:27 PM
 */
package a01010327.database.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import a01010327.data.Customer;
import a01010327.data.Motorcycle;
import a01010327.database.Database;
import a01010327.database.DbConstants;
import a01010327.database.dao.CustomerDao.CustomerTable;
import a01010327.database.util.DbUtil;

/**
 * @author amir deris, a01010327 Data Access Object for Motorcycle This class
 *         uses singleton pattern
 */
public class MotorcycleDao extends Dao {
	public static final Logger LOG = LogManager.getLogger();

	private static Database database;
	private static MotorcycleDao motorcycleDaoInstance;

	/**
	 * private constructor for singleton pattern. Use getInstance() method
	 * instead.
	 * 
	 * @param database,
	 *            a Database
	 */
	private MotorcycleDao(Database database) {
		super(database, DbConstants.MOTORCYCLE_TABLE_NAME);
	}

	/**
	 * setter method for database
	 * 
	 * @param database,
	 *            a Database
	 */
	public static void setDatabase(Database db) {
		database = db;
	}

	/**
	 * allows access to this instance of MotorcycleDao, so it can be re-used.
	 * 
	 * @return an instance of MotorcycleDao.
	 */
	public static MotorcycleDao getInstance() {
		if (motorcycleDaoInstance == null) {
			motorcycleDaoInstance = new MotorcycleDao(database);
		}
		return motorcycleDaoInstance;
	}

	/**
	 * creates the Motorcycle table, if it does not already exist.
	 * 
	 * @throws SQLException
	 */
	@Override
	public void create() throws SQLException {
		boolean tableExist = DbUtil.tableExists(database.getConnection(), tableName);
		if (tableExist) {
			LOG.debug(tableName + " table already exists, not recreated");
			return;
		}
		String sql = String.format(
				"CREATE TABLE %s (%s %s(%d), "
						+ "%s %s(%d), %s %s(%d), %s %s, %s %s(%d), %s %s, %s %s, PRIMARY KEY (%s), FOREIGN KEY (%s) REFERENCES %s(%s))",
				tableName, MotorcycleTable.MOTORCYCLE_ID.getColumnName(), MotorcycleTable.MOTORCYCLE_ID.getDataType(),
				MotorcycleTable.MOTORCYCLE_ID.getSize(), MotorcycleTable.MAKE.getColumnName(),
				MotorcycleTable.MAKE.getDataType(), MotorcycleTable.MAKE.getSize(),
				MotorcycleTable.MODEL.getColumnName(), MotorcycleTable.MODEL.getDataType(),
				MotorcycleTable.MODEL.getSize(), MotorcycleTable.YEAR.getColumnName(),
				MotorcycleTable.YEAR.getDataType(), MotorcycleTable.SERIAL_NUMBER.getColumnName(),
				MotorcycleTable.SERIAL_NUMBER.getDataType(), MotorcycleTable.SERIAL_NUMBER.getSize(),
				MotorcycleTable.MILEAGE.getColumnName(), MotorcycleTable.MILEAGE.getDataType(),
				MotorcycleTable.OWNER_ID.getColumnName(), MotorcycleTable.OWNER_ID.getDataType(),
				MotorcycleTable.MOTORCYCLE_ID.getColumnName(), MotorcycleTable.OWNER_ID.getColumnName(),
				DbConstants.CUSTOMER_TABLE_NAME, CustomerTable.CUSTOMER_ID.getColumnName());
		executeUpdateSql(sql);
	}

	/**
	 * adds a Motorcycle to motorcycle table
	 * 
	 * @param motorcycle,
	 *            a Motorcycle
	 * @throws SQLException
	 */
	public void add(Motorcycle motorcycle) throws SQLException {
		if (motorcycle == null) {
			return;
		}
		String sql = String.format("INSERT INTO %s VALUES('%s', '%s', '%s'," + "%d, '%s', %d, %d)", tableName,
				motorcycle.getId(), motorcycle.getMake(), motorcycle.getModel(), motorcycle.getYear(),
				motorcycle.getSerialNumber(), motorcycle.getMileage(), motorcycle.getOwner().getId());
		executeUpdateSql(sql);
	}

	/**
	 * finds a Motorcycle by given ID
	 * 
	 * @param motorcycleId,
	 *            a String
	 * @return a Motorcycle whose ID is motorcycleId
	 * @throws Exception
	 *             if more than one Motorcycle is found for given motorcycleId.
	 */
	public Motorcycle findById(String motorcycleId) throws Exception {
		Motorcycle motorcycle = null;
		Customer owner = null;
		CustomerDao customerDao = CustomerDao.getInstance();
		String sql = String.format("SELECT * FROM %s WHERE %s = '%s'", tableName,
				MotorcycleTable.MOTORCYCLE_ID.getColumnName(), motorcycleId);
		ResultSet resultSet = executeQuearySql(sql);
		int count = 0;
		while (resultSet.next()) {
			count++;
			if (count > 1) {
				throw new Exception(String.format("Expected one result, but received %d", count));
			}
			motorcycle = new Motorcycle();
			motorcycle.setId(resultSet.getString(MotorcycleTable.MOTORCYCLE_ID.getColumnName()));
			motorcycle.setMake(resultSet.getString(MotorcycleTable.MAKE.getColumnName()));
			motorcycle.setModel(resultSet.getString(MotorcycleTable.MODEL.getColumnName()));
			motorcycle.setYear(resultSet.getInt(MotorcycleTable.YEAR.getColumnName()));
			motorcycle.setMileage(resultSet.getInt(MotorcycleTable.MILEAGE.getColumnName()));
			Integer ownerId = resultSet.getInt(MotorcycleTable.OWNER_ID.getColumnName());
			owner = customerDao.findByCustomerId(ownerId);
			motorcycle.setOwner(owner);
			motorcycle.setSerialNumber(resultSet.getString(MotorcycleTable.SERIAL_NUMBER.getColumnName()));
		}
		return motorcycle;
	}

	/**
	 * updates a Motorcycle record
	 * 
	 * @param motorcycle,
	 *            a Motorcycle
	 * @throws SQLException
	 */
	public void update(Motorcycle motorcycle) throws SQLException {
		String sql = String.format(
				"UPDATE %s SET %s='%s', %s='%s', %s='%s', %s=%d, %s='%s', %s=%d, %s=%d WHERE %s='%s'", tableName,
				MotorcycleTable.MOTORCYCLE_ID.getColumnName(), motorcycle.getId(), MotorcycleTable.MAKE.getColumnName(),
				motorcycle.getMake(), MotorcycleTable.MODEL.getColumnName(), motorcycle.getModel(),
				MotorcycleTable.YEAR.getColumnName(), motorcycle.getYear(),
				MotorcycleTable.SERIAL_NUMBER.getColumnName(), motorcycle.getSerialNumber(),
				MotorcycleTable.MILEAGE.getColumnName(), motorcycle.getMileage(),
				MotorcycleTable.OWNER_ID.getColumnName(), motorcycle.getOwner().getId(),
				MotorcycleTable.MOTORCYCLE_ID.getColumnName(), motorcycle.getId());
		executeUpdateSql(sql);
	}

	/**
	 * deletes a Motorcycle from motorcycle table
	 * 
	 * @param motorcycle,
	 *            a Motorcycle
	 * @throws SQLException
	 */
	public void delete(Motorcycle motorcycle) throws SQLException {
		String sql = String.format("DELETE FROM %s WHERE %s='%s'", tableName,
				MotorcycleTable.MOTORCYCLE_ID.getColumnName(), motorcycle.getId());
		int rowCount = executeUpdateSql(sql);
		LOG.debug(String.format("deleted %d rows", rowCount));
	}

	/**
	 * inserts a Collection of motorcycles into database
	 * 
	 * @param motorcycles,
	 *            a Collection<Motorcycle>
	 * @throws SQLException
	 */
	public void insertMotorcycles(Collection<Motorcycle> motorcycles) throws SQLException {
		LOG.debug("inserting motorcycles into database");
		for (Motorcycle m : motorcycles) {
			add(m);
		}
	}

	// enum representing motorcycle table
	protected enum MotorcycleTable {
		// format: column name, data type, max-size
		MOTORCYCLE_ID("motorcycleId", "VARCHAR", 6), MAKE("make", "VARCHAR", 25), MODEL("model", "VARCHAR", 25), YEAR(
				"yearMade", "INTEGER", 1), SERIAL_NUMBER("serialNumber", "CHAR",
						6), MILEAGE("mileage", "INTEGER", 1), OWNER_ID("ownerId", "INTEGER", 1);

		private final String columnName;
		private final String dataType;
		private final int size;

		/**
		 * @param columnName
		 * @param dataType
		 * @param size
		 */
		private MotorcycleTable(String columnName, String dataType, int size) {
			this.columnName = columnName;
			this.dataType = dataType;
			this.size = size;
		}

		/**
		 * @return the columnName
		 */
		protected String getColumnName() {
			return columnName;
		}

		/**
		 * @return the dataType
		 */
		protected String getDataType() {
			return dataType;
		}

		/**
		 * @return the size
		 */
		protected int getSize() {
			return size;
		}

	}
}
