/**
 * a01010327_assignment2
 * InventoryDao.java
 * Jun 19, 2017
 * 5:54:44 PM
 */
package a01010327.database.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import a01010327.data.Inventory;
import a01010327.data.collections.InventorySorters;
import a01010327.database.Database;
import a01010327.database.DbConstants;
import a01010327.database.util.DbUtil;

/**
 * @author amir deris, a01010327 Data Access Object for Inventory This class
 *         uses singleton pattern
 */
public class InventoryDao extends Dao {
	public static final Logger LOG = LogManager.getLogger();

	private static Database database;
	private static InventoryDao inventoryDaoInstance;

	/**
	 * private constructor for singleton pattern. Use getInstance() method
	 * instead.
	 * 
	 * @param database,
	 *            a Database
	 */
	private InventoryDao(Database database) {
		super(database, DbConstants.INVENTORY_TABLE_NAME);
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
	 * allows access to this instance of InventoryDao, so it can be re-used.
	 * 
	 * @return an instance of InventoryDao.
	 */
	public static InventoryDao getInstance() {
		if (inventoryDaoInstance == null) {
			inventoryDaoInstance = new InventoryDao(database);
		}
		return inventoryDaoInstance;
	}

	/**
	 * creates the Inventory table, if it does not already exist. primary key of
	 * inventory table assumed to be a composite key consisting of motorcyleId
	 * and partNumber
	 * 
	 * @throws SQLException
	 * 
	 */
	public void create() throws SQLException {
		boolean tableExist = DbUtil.tableExists(database.getConnection(), tableName);
		if (tableExist) {
			LOG.debug(tableName + " table already exists, not recreated");
			return;
		}
		String sql = String.format(
				"CREATE TABLE %s (%s %s(%d), " + "%s %s(%d), %s %s(%d), %s %s(%d,%d), %s %s, PRIMARY KEY(%s, %s))",
				tableName, InventoryTable.MOTORCYCLE_ID.getColumnName(), InventoryTable.MOTORCYCLE_ID.getDataType(),
				InventoryTable.MOTORCYCLE_ID.getSize(), InventoryTable.DESCRIPTION.getColumnName(),
				InventoryTable.DESCRIPTION.getDataType(), InventoryTable.DESCRIPTION.getSize(),
				InventoryTable.PART_NUMBER.getColumnName(), InventoryTable.PART_NUMBER.getDataType(),
				InventoryTable.PART_NUMBER.getSize(), InventoryTable.PRICE.getColumnName(),
				InventoryTable.PRICE.getDataType(), InventoryTable.PRICE.getSize(), 2,
				InventoryTable.QUANTITY.getColumnName(), InventoryTable.QUANTITY.getDataType(),
				InventoryTable.MOTORCYCLE_ID.getColumnName(), InventoryTable.PART_NUMBER.getColumnName());
		executeUpdateSql(sql);
	}

	/**
	 * adds an Inventory to inventory table
	 * 
	 * @param inventory,
	 *            an Inventory
	 * @throws SQLException
	 */
	public void add(Inventory inventory) throws SQLException {
		if (inventory == null) {
			return;
		}
		String sql = String.format("INSERT INTO %s VALUES('%s', '%s', '%s', %f, %d)", tableName,
				inventory.getMotorcycleId(), inventory.getDescription(), inventory.getPartNumber(),
				inventory.getPrice(), inventory.getQuantity());
		executeUpdateSql(sql);
	}

	/**
	 * finds an Inventory item for given motorcycleId and partNumber. primary
	 * key of inventory table assumed to be a composite key consisting of
	 * motorcyleId and partNumber
	 * 
	 * @param motorcycleId,
	 *            a String
	 * @param partNumber,
	 *            a String
	 * @return an Inventory item with given motorcycleId and partNumber.
	 * @throws Exception
	 *             if more than one inventory item is found for given
	 *             motorcycleId and partNumber.
	 */
	public Inventory findById(String motorcycleId, String partNumber) throws Exception {
		Inventory inventory = null;
		String sql = String.format("SELECT * FROM %s WHERE %s='%s' AND %s='%s'", tableName,
				InventoryTable.MOTORCYCLE_ID.getColumnName(), motorcycleId, InventoryTable.PART_NUMBER.getColumnName(),
				partNumber);
		ResultSet resultSet = executeQuearySql(sql);
		int count = 0;
		while (resultSet.next()) {
			count++;
			if (count > 1) {
				throw new Exception(String.format("Expected one result, but received %d", count));
			}
			inventory = new Inventory();
			inventory.setMotorcycleId(resultSet.getString(InventoryTable.MOTORCYCLE_ID.getColumnName()));
			inventory.setDescription(resultSet.getString(InventoryTable.DESCRIPTION.getColumnName()));
			inventory.setPartNumber(resultSet.getString(InventoryTable.PART_NUMBER.getColumnName()));
			inventory.setPrice(resultSet.getDouble(InventoryTable.PRICE.getColumnName()));
			inventory.setQuantity(resultSet.getInt(InventoryTable.QUANTITY.getColumnName()));
		}
		return inventory;
	}

	/**
	 * updates an Inventory record
	 * 
	 * @param inventory,
	 *            an Inventory
	 * @throws SQLException
	 */
	public void update(Inventory inventory) throws SQLException {
		String sql = String.format("UPDATE %s SET %s='%s', %s='%s', %s='%s', %s=%f, %s=%d WHERE %s='%s' AND %s='%s'",
				tableName, InventoryTable.MOTORCYCLE_ID.getColumnName(), inventory.getMotorcycleId(),
				InventoryTable.DESCRIPTION.getColumnName(), inventory.getDescription(),
				InventoryTable.PART_NUMBER.getColumnName(), inventory.getPartNumber(),
				InventoryTable.PRICE.getColumnName(), inventory.getPrice(), InventoryTable.QUANTITY.getColumnName(),
				inventory.getQuantity(), InventoryTable.MOTORCYCLE_ID.getColumnName(), inventory.getMotorcycleId(),
				InventoryTable.PART_NUMBER.getColumnName(), inventory.getPartNumber());
		executeUpdateSql(sql);
	}

	/**
	 * updates an Inventory record, whose motorcycleId and partNumber matches
	 * the oldItem, with values taken from modifiedItem. Since primary key in
	 * inventory table is based on motorcycleId and partNumber, and partNumber
	 * can be changed in the edit GUI screen, the other update method is not
	 * suitable for this scenario.
	 * 
	 * @param oldItem,
	 *            an Inventory that has been changed.
	 * @param modifiedItem,
	 *            an Inventory which is the modified version of oldItem.
	 * @throws SQLException
	 */
	public void update(Inventory oldItem, Inventory modifiedItem) throws SQLException {
		String sql = String.format("UPDATE %s SET %s='%s', %s='%s', %s='%s', %s=%f, %s=%d WHERE %s='%s' AND %s='%s'",
				tableName, InventoryTable.MOTORCYCLE_ID.getColumnName(), modifiedItem.getMotorcycleId(),
				InventoryTable.DESCRIPTION.getColumnName(), modifiedItem.getDescription(),
				InventoryTable.PART_NUMBER.getColumnName(), modifiedItem.getPartNumber(),
				InventoryTable.PRICE.getColumnName(), modifiedItem.getPrice(), InventoryTable.QUANTITY.getColumnName(),
				modifiedItem.getQuantity(), InventoryTable.MOTORCYCLE_ID.getColumnName(), oldItem.getMotorcycleId(),
				InventoryTable.PART_NUMBER.getColumnName(), oldItem.getPartNumber());
		executeUpdateSql(sql);
	}

	/**
	 * deletes an Inventory record from inventory table
	 * 
	 * @param inventory,
	 *            an Inventory
	 * @throws SQLException
	 */
	public void delete(Inventory inventory) throws SQLException {
		String sql = String.format("DELETE FROM %s WHERE %s='%s' AND %s='%s'", tableName,
				InventoryTable.MOTORCYCLE_ID.getColumnName(), inventory.getMotorcycleId(),
				InventoryTable.PART_NUMBER.getColumnName(), inventory.getPartNumber());
		int rowCount = executeUpdateSql(sql);
		LOG.debug(String.format("deleted %d rows", rowCount));
	}

	/**
	 * inserts a Collection of inventories into database
	 * 
	 * @param inventories,
	 *            a Collection<Inventory>
	 * @throws SQLException
	 */
	public void insertInventories(Collection<Inventory> inventories) throws SQLException {
		LOG.debug("inserting inventories into database");
		for (Inventory m : inventories) {
			add(m);
		}
	}

	/**
	 * gets all inventory data from database
	 * 
	 * @return a Set<Inventory> containing all data from database.
	 * @throws SQLException
	 */
	public Set<Inventory> getAllInventory() throws SQLException {
		Set<Inventory> result = new HashSet<Inventory>();
		String sql = String.format("SELECT * FROM %s", tableName);
		ResultSet resultSet = executeQuearySql(sql);
		while (resultSet.next()) {
			Inventory inventory = new Inventory();
			inventory.setMotorcycleId(resultSet.getString(InventoryTable.MOTORCYCLE_ID.getColumnName()));
			inventory.setDescription(resultSet.getString(InventoryTable.DESCRIPTION.getColumnName()));
			inventory.setPartNumber(resultSet.getString(InventoryTable.PART_NUMBER.getColumnName()));
			inventory.setPrice(resultSet.getDouble(InventoryTable.PRICE.getColumnName()));
			inventory.setQuantity(resultSet.getInt(InventoryTable.QUANTITY.getColumnName()));
			result.add(inventory);
		}
		return result;
	}

	/**
	 * finds all Inventory data for given requirements.
	 * 
	 * @param isDescending,
	 *            a boolean. If True, the sorting is done in descending order.
	 * @param sortByDescription,
	 *            a boolean. If True, the sorting is done by Description.
	 * @param sortByCount,
	 *            a boolean. If True, the sorting is done by Count.
	 * @param filterByMake,
	 *            a String. If not null, result is filtered for given make.
	 * @return a Set<Inventory> for all Inventory data for given requirements.
	 */
	public Set<Inventory> getInventoryData(boolean isDescending, boolean sortByDescription, boolean sortByCount,
			String filterByMake) {
		Set<Inventory> result = new TreeSet<Inventory>();
		try {
			Set<Inventory> dataFromDB = getAllInventory();
			if (sortByDescription) {
				result = new TreeSet<Inventory>(new InventorySorters.SortByDescription(isDescending));
			} else if (sortByCount) {
				result = new TreeSet<Inventory>(new InventorySorters.SortByCount(isDescending));
			}
			if (filterByMake != null && filterByMake != "") {
				Iterator<Inventory> it = dataFromDB.iterator();
				while (it.hasNext()) {
					Inventory item = it.next();
					if (item.getMotorcycleId().indexOf(filterByMake) == -1) {
						it.remove();
					}
				}
			}
			result.addAll(dataFromDB);

		} catch (SQLException e) {
			LOG.error(e.getMessage());
		}
		return result;
	}

	/**
	 * finds the total value of the inventory.
	 * 
	 * @return a double, total value of the inventory.
	 */
	public double getTotalInventoryValue() {
		Set<Inventory> data = getInventoryData(false, false, false, null);
		int sum = 0;
		for (Inventory i : data) {
			sum += i.getQuantity() * i.getPrice();
		}
		return sum;
	}

	/**
	 * finds all the distinct motorcycleId values. Can be used for validation
	 * when filtering by make.
	 * 
	 * @return a Set<String> containing all distinct motorcycleId values.
	 * @throws SQLException
	 */
	public Set<String> findAllMotorcycleMakes() throws SQLException {
		Set<String> result = new HashSet<String>();
		String sql = String.format("SELECT DISTINCT %s FROM %s", InventoryTable.MOTORCYCLE_ID.getColumnName(),
				tableName);
		ResultSet resultSet = executeQuearySql(sql);
		while (resultSet.next()) {
			String makeAndModel = resultSet.getString(InventoryTable.MOTORCYCLE_ID.getColumnName());
			if (makeAndModel != null) {
				result.add(makeAndModel.split("\\+")[0]);
			}
		}
		return result;
	}

	/**
	 * filters all Inventory data that contains the given make in their
	 * motorcycleId field.
	 * 
	 * @param make,
	 *            a String
	 * @return a Set<Inventory> for all Inventory data that contains the given
	 *         make in their motorcycleId field.
	 * @throws SQLException
	 */
	public Set<Inventory> filterByMake(String make) throws SQLException {
		Set<Inventory> result = new HashSet<Inventory>();
		String sql = String.format("SELECT * FROM %s WHERE %s LIKE %s", tableName,
				InventoryTable.MOTORCYCLE_ID.getColumnName(), make);
		ResultSet resultSet = executeQuearySql(sql);
		while (resultSet.next()) {
			Inventory inventory = new Inventory();
			inventory.setMotorcycleId(resultSet.getString(InventoryTable.MOTORCYCLE_ID.getColumnName()));
			inventory.setDescription(resultSet.getString(InventoryTable.DESCRIPTION.getColumnName()));
			inventory.setPartNumber(resultSet.getString(InventoryTable.PART_NUMBER.getColumnName()));
			inventory.setPrice(resultSet.getDouble(InventoryTable.PRICE.getColumnName()));
			inventory.setQuantity(resultSet.getInt(InventoryTable.QUANTITY.getColumnName()));
			result.add(inventory);
		}
		return result;
	}

	// enum representing inventory table
	protected enum InventoryTable {
		MOTORCYCLE_ID("motorcycleId", "VARCHAR", 30), DESCRIPTION("description", "VARCHAR", 35), PART_NUMBER(
				"partNumber", "VARCHAR", 15), PRICE("price", "DECIMAL", 10), QUANTITY("quantity", "INTEGER", 1);

		private final String columnName;
		private final String dataType;
		private final int size;

		/**
		 * @param columnName
		 * @param dataType
		 * @param size
		 */
		private InventoryTable(String columnName, String dataType, int size) {
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
