/**
 * a01010327_assignment2
 * Dao.java
 * Jun 19, 2017
 * 2:09:00 PM
 */
package a01010327.database.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import a01010327.database.Database;

/**
 * @author amir deris, a01010327
 *
 */
public abstract class Dao {
	protected final Database database;
	protected final String tableName;

	private static final Logger LOG = LogManager.getLogger();

	/**
	 * constructor
	 * 
	 * @param database,
	 *            a Database
	 * @param tableName,
	 *            a String
	 */
	protected Dao(Database database, String tableName) {
		this.database = database;
		this.tableName = tableName;
	}

	/**
	 * creates the table for child class DAO.
	 * 
	 * @throws SQLException
	 */
	public abstract void create() throws SQLException;

	/**
	 * drops table from database
	 * 
	 * @throws SQLException
	 */
	public void drop() throws SQLException {
		String sql = String.format("DROP TABLE %s", tableName);
		executeUpdateSql(sql);
	}

	/**
	 * updates database with an update statement: CREATE, UPDATE, DELETE
	 * operations
	 * 
	 * @param sql,
	 *            a String representing a SQL command for update
	 * @return an int, indicating number of rows affected
	 * @throws SQLException
	 */
	protected int executeUpdateSql(String sql) throws SQLException {
		Statement statement = null;
		int numberOfRowsAffected = 0;
		try {
			Connection connection = database.getConnection();
			statement = connection.createStatement();
			LOG.debug("executing: " + sql);
			numberOfRowsAffected = statement.executeUpdate(sql);
			LOG.debug(numberOfRowsAffected + " rows affected");
		} finally {
			close(statement);
		}
		return numberOfRowsAffected;
	}

	/**
	 * executes a query SQL operation, and returns the result: READ operation.
	 * 
	 * @param sql,
	 *            a String
	 * @return ResultSet for given query.
	 */
	protected ResultSet executeQuearySql(String sql) {
		ResultSet resultSet = null;
		try {
			Connection connection = database.getConnection();
			Statement statement = connection.createStatement();
			LOG.debug("executing: " + sql);
			resultSet = statement.executeQuery(sql);
		} catch (SQLException e) {
			LOG.error(e.getMessage());
		}
		return resultSet;
	}

	/**
	 * closes statement
	 * 
	 * @param statement,
	 *            a Statement
	 */
	protected void close(Statement statement) {
		try {
			if (statement != null) {
				statement.close();
			}
		} catch (SQLException e) {
			LOG.error(e.getMessage());
		}
	}
}
