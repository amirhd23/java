/**
 * a01010327_assignment2
 * CustomerDao.java
 * Jun 19, 2017
 * 2:10:22 PM
 */
package a01010327.database.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import a01010327.data.Customer;
import a01010327.database.Database;
import a01010327.database.DbConstants;
import a01010327.database.util.DbUtil;

/**
 * @author amir deris, a01010327 Data Access Object for Customer This class uses
 *         singleton pattern
 */
public class CustomerDao extends Dao {

	public static final Logger LOG = LogManager.getLogger();

	private static Database database;
	private static CustomerDao customerDaoInstance;

	/**
	 * private constructor for singleton pattern. Use getInstance() method
	 * instead.
	 * 
	 * @param database,
	 *            a Database
	 */
	private CustomerDao(Database database) {
		super(database, DbConstants.CUSTOMER_TABLE_NAME);
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
	 * allows access to this instance of CustomerDao, so it can be re-used.
	 * 
	 * @return an instance of CustomerDao.
	 */
	public static CustomerDao getInstance() {
		if (customerDaoInstance == null) {
			customerDaoInstance = new CustomerDao(database);
		}
		return customerDaoInstance;
	}

	/**
	 * creates the Customer table, if it does not already exist
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
				"CREATE TABLE %s (%s %s, %s %s(%d), %s %s(%d), %s %s(%d), %s %s(%d), "
						+ "%s %s(%d), %s %s(%d), %s %s(%d), %s %s, PRIMARY KEY (%s) )",
				tableName, CustomerTable.CUSTOMER_ID.getColumnName(), CustomerTable.CUSTOMER_ID.getDataType(),
				CustomerTable.FIRST_NAME.getColumnName(), CustomerTable.FIRST_NAME.getDataType(),
				CustomerTable.FIRST_NAME.getSize(), CustomerTable.LAST_NAME.getColumnName(),
				CustomerTable.LAST_NAME.getDataType(), CustomerTable.LAST_NAME.getSize(),
				CustomerTable.STREET_NAME.getColumnName(), CustomerTable.STREET_NAME.getDataType(),
				CustomerTable.STREET_NAME.getSize(), CustomerTable.CITY_NAME.getColumnName(),
				CustomerTable.CITY_NAME.getDataType(), CustomerTable.CITY_NAME.getSize(),
				CustomerTable.POSTAL_CODE.getColumnName(), CustomerTable.POSTAL_CODE.getDataType(),
				CustomerTable.POSTAL_CODE.getSize(), CustomerTable.PHONE.getColumnName(),
				CustomerTable.PHONE.getDataType(), CustomerTable.PHONE.getSize(),
				CustomerTable.EMAIL_ADDRESS.getColumnName(), CustomerTable.EMAIL_ADDRESS.getDataType(),
				CustomerTable.EMAIL_ADDRESS.getSize(), CustomerTable.DATE_JOINED.getColumnName(),
				CustomerTable.DATE_JOINED.getDataType(), CustomerTable.CUSTOMER_ID.getColumnName());
		executeUpdateSql(sql);

	}

	/**
	 * adds a Customer to the customer table
	 * 
	 * @param customer,
	 *            a Customer
	 * @throws SQLException
	 */
	public void add(Customer customer) throws SQLException {
		if (customer == null) {
			return;
		}
		String sql = String.format("INSERT INTO %s VALUES( %d, '%s', '%s',  '%s', '%s', '%s', '%s', '%s', '%s' )",
				tableName, customer.getId(), customer.getFirstName(), customer.getLastName(), customer.getStreetName(),
				customer.getCity(), customer.getPostalCode(), customer.getPhone(), customer.getEmailAddress(),
				DbUtil.toTimestamp(customer.getDateJoined()));
		executeUpdateSql(sql);
	}

	/**
	 * finds a Customer by given ID
	 * 
	 * @param customerId,
	 *            an int
	 * @return a Customer whose ID is customerId.
	 * @throws Exception
	 */
	public Customer findByCustomerId(int customerId) throws Exception {
		Customer customer = null;
		String sql = String.format("SELECT * FROM %s WHERE %s = %d", tableName,
				CustomerTable.CUSTOMER_ID.getColumnName(), customerId);
		ResultSet resultSet = executeQuearySql(sql);
		// get the customer, throw an exception if we get more than one result
		int count = 0;
		while (resultSet.next()) {
			count++;
			if (count > 1) {
				throw new Exception(String.format("Expected one result, but received %d", count));
			}
			customer = new Customer();
			customer.setId(resultSet.getInt(CustomerTable.CUSTOMER_ID.getColumnName()));
			customer.setFirstName(resultSet.getString(CustomerTable.FIRST_NAME.getColumnName()));
			customer.setLastName(resultSet.getString(CustomerTable.LAST_NAME.getColumnName()));
			customer.setStreetName(resultSet.getString(CustomerTable.STREET_NAME.getColumnName()));
			customer.setCity(resultSet.getString(CustomerTable.CITY_NAME.getColumnName()));
			customer.setPostalCode(resultSet.getString(CustomerTable.POSTAL_CODE.getColumnName()));
			customer.setPhone(resultSet.getString(CustomerTable.PHONE.getColumnName()));
			customer.setEmailAddress(resultSet.getString(CustomerTable.EMAIL_ADDRESS.getColumnName()));
			customer.setDateJoined(
					DbUtil.toLocalDate(resultSet.getTimestamp(CustomerTable.DATE_JOINED.getColumnName())));
		}
		return customer;
	}

	/**
	 * updates a Customer record
	 * 
	 * @param customer,
	 *            a Customer
	 * @throws SQLException
	 */
	public void update(Customer customer) throws SQLException {
		String sql = String.format(
				"UPDATE %s SET %s=%d, %s='%s', %s='%s', %s='%s', %s='%s', %s='%s', %s='%s', %s='%s', %s='%s' WHERE %s=%d",
				tableName, CustomerTable.CUSTOMER_ID.getColumnName(), customer.getId(),
				CustomerTable.FIRST_NAME.getColumnName(), customer.getFirstName(),
				CustomerTable.LAST_NAME.getColumnName(), customer.getLastName(),
				CustomerTable.STREET_NAME.getColumnName(), customer.getStreetName(),
				CustomerTable.CITY_NAME.getColumnName(), customer.getCity(), CustomerTable.POSTAL_CODE.getColumnName(),
				customer.getPostalCode(), CustomerTable.PHONE.getColumnName(), customer.getPhone(),
				CustomerTable.EMAIL_ADDRESS.getColumnName(), customer.getEmailAddress(),
				CustomerTable.DATE_JOINED.getColumnName(), DbUtil.toTimestamp(customer.getDateJoined()),
				CustomerTable.CUSTOMER_ID.getColumnName(), customer.getId());
		executeUpdateSql(sql);
	}

	/**
	 * deletes a Customer from customer table
	 * 
	 * @param customer,
	 *            a Customer
	 * @throws SQLException
	 */
	public void delete(Customer customer) throws SQLException {
		String sql = String.format("DELETE FROM %s WHERE %s=%d", tableName, CustomerTable.CUSTOMER_ID.getColumnName(),
				customer.getId());
		int rowCount = executeUpdateSql(sql);
		LOG.debug(String.format("deleted %d rows", rowCount));
	}

	/**
	 * retrieves the IDs of all customers in the customer table
	 * 
	 * @return a List containing all IDs of customers in table
	 * @throws SQLException
	 */
	public List<Integer> getIds() throws SQLException {
		String sql = String.format("SELECT %s FROM %s", CustomerTable.CUSTOMER_ID.getColumnName(), tableName);
		ResultSet resultSet = executeQuearySql(sql);
		List<Integer> ids = new ArrayList<Integer>();
		while (resultSet.next()) {
			ids.add(resultSet.getInt(CustomerTable.CUSTOMER_ID.getColumnName()));
		}
		return ids;
	}

	/**
	 * finds a Customer for given customer's id
	 * 
	 * @param id,
	 *            an int. represents Customer id
	 * @return a Customer for given id
	 * @throws Exception
	 * @throws SQLException
	 */
	public Customer getCustomer(int id) throws SQLException, Exception {
		return findByCustomerId(id);
	}

	/**
	 * inserts a Collection of customers into database
	 * 
	 * @param customers,
	 *            a Collection<Customer>
	 * @throws SQLException
	 */
	public void insertCustomers(Collection<Customer> customers) throws SQLException {
		LOG.debug("inserting customers into database");
		for (Customer c : customers) {
			add(c);
		}
	}

	// enum representing customer table
	protected enum CustomerTable {
		// format: column name, data type, max-size
		CUSTOMER_ID("customerId", "INTEGER", 6), FIRST_NAME("firstName", "VARCHAR", 20), LAST_NAME("lastName",
				"VARCHAR", 20), STREET_NAME("streetName", "VARCHAR", 25), CITY_NAME("cityName", "VARCHAR",
						20), POSTAL_CODE("postalCode", "CHAR", 7), PHONE("phone", "CHAR", 14), EMAIL_ADDRESS(
								"emailAddress", "VARCHAR", 30), DATE_JOINED("dateJoined", "TIMESTAMP", -1);

		private final String columnName;
		private final String dataType;
		private final int size;

		/**
		 * @param columnName
		 * @param dataType
		 * @param size
		 */
		private CustomerTable(String columnName, String dataType, int size) {
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
