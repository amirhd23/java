/**
 * a01010327_assignment2
 * DbUtil.java
 * Jun 19, 2017
 * 2:13:00 PM
 */
package a01010327.database.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author amir deris, a01010327
 *
 */
public class DbUtil {
	private static final Logger LOG = LogManager.getLogger();

	/**
	 * converts LocalDate to Timestamp
	 * 
	 * @param date,
	 *            a LocalDate
	 * @return a Timestamp for the given LocalDate
	 */
	public static Timestamp toTimestamp(LocalDate date) {
		return Timestamp.valueOf(LocalDateTime.of(date, LocalTime.now()));
	}

	/**
	 * converts a Timestamp to LocalDate
	 * 
	 * @param timestamp,
	 *            a Timestamp
	 * @return a LocalDate for the given Timestamp
	 */
	public static LocalDate toLocalDate(Timestamp timestamp) {
		if (timestamp != null) {
			return timestamp.toLocalDateTime().toLocalDate();
		} else {
			return null;
		}
	}

	/**
	 * checks if a given table exists in the database
	 * 
	 * @param connection,
	 *            a Connection
	 * @param tableName,
	 *            a String
	 * @return true if table exists in database, false otherwise.
	 * @throws SQLException
	 */
	public static boolean tableExists(Connection connection, String tableName) throws SQLException {
		DatabaseMetaData databaseMetaData = connection.getMetaData();
		ResultSet resultSet = null;
		String rsTableName = null;

		try {
			resultSet = databaseMetaData.getTables(connection.getCatalog(), "%", "%", null);
			while (resultSet.next()) {
				rsTableName = resultSet.getString("TABLE_NAME");
				if (rsTableName.equalsIgnoreCase(tableName)) {
					return true;
				}
			}
		} finally {
			resultSet.close();
		}

		return false;
	}

	/**
	 * reads a property file
	 * 
	 * @return Properties read from given filename
	 */
	public static Properties readPropertyFile(String fileName) {
		Properties properties = new Properties();
		FileInputStream in = null;
		try {
			in = new FileInputStream(fileName);
			properties.load(in);
		} catch (IOException e) {
			LOG.error("cannot read property file " + fileName);
			System.exit(-1);
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					LOG.error(e.getMessage());
				}
			}
		}
		return properties;
	}
}
