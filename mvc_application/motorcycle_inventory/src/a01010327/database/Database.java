/**
 * a01010327_assignment2
 * Database.java
 * Jun 19, 2017
 * 2:04:36 PM
 */
package a01010327.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author amir deris, a01010327
 *
 */
public class Database {
	private static Connection connection;
	private final Properties properties;

	private static Logger LOG = LogManager.getLogger();

	/**
	 * constructor
	 * 
	 * @param properties,
	 *            a Properties
	 */
	public Database(Properties properties) {
		LOG.debug("Loading database properties from properties file: " + DbConstants.DB_PROPERTIES_FILENAME);
		this.properties = properties;
	}

	/**
	 * getting the connection to database.
	 * 
	 * @return a Connection
	 * @throws SQLException
	 */
	public Connection getConnection() throws SQLException {
		// if already connected, return it.
		if (connection != null) {
			return connection;
		}
		try {
			connect();
		} catch (ClassNotFoundException e) {
			throw new SQLException(e);
		}
		return connection;
	}

	private void connect() throws ClassNotFoundException, SQLException {
		Class.forName(properties.getProperty(DbConstants.DB_DRIVER_KEY));
		LOG.info("Driver loaded");
		connection = DriverManager.getConnection(properties.getProperty(DbConstants.DB_URL_KEY),
				properties.getProperty(DbConstants.DB_USER_KEY), properties.getProperty(DbConstants.DB_PASSWORD_KEY));
		LOG.info("Database connected");
	}

	/**
	 * closes the database connection
	 */
	public void shutdown() {
		if (connection != null) {
			try {
				connection.close();
				connection = null;
			} catch (SQLException e) {
				LOG.error(e.getMessage());
			}
		}
	}
}
