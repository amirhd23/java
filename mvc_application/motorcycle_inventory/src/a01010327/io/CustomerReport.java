/**
 * a01010327_assignment1
 * CustomerReport.java
 * May 24, 2017
 * 2:18:49 PM
 */
package a01010327.io;

import java.time.format.DateTimeFormatter;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import a01010327.data.Customer;

/**
 * @author amir deris, a01010327 this class is used for reporting Customer data.
 */
public class CustomerReport {
	private static final String DASH_LINE = "---------------------------"
			+ "----------------------------------------------------------"
			+ "----------------------------------------------------------";
	private static final String HEADER_FORMAT = "%3s. %-6s %-12s %-12s %-25s %-12s %-12s %-15s %-25s %-11s %n";
	private static final String ROW_FORMAT = "%3d. %06d %-12s %-12s %-25s %-12s %-12s %-15s %-25s %-11s %n";
	private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("MMM dd yyyy");

	private static final Logger LOG = LogManager.getLogger();

	/**
	 * prints data for customers in a formatted fashion
	 * 
	 * @param data,
	 *            a Map<String, Customer> which is a map of Customer's id to
	 *            Customer.
	 */
	public static void printData(Map<String, Customer> data) {
		LOG.info("printing Customer Report, for data of size: " + data.size());
		System.out.println("Customer Report");
		printHeaders();
		int index = 1;
		for (Customer c : data.values()) {
			System.out.format(ROW_FORMAT, index, c.getId(), c.getFirstName(), c.getLastName(), c.getStreetName(),
					c.getCity(), c.getPostalCode(), c.getPhone(), c.getEmailAddress(),
					DATE_FORMAT.format(c.getDateJoined()));
			index++;
		}
	}

	// prints the title of each column
	private static void printHeaders() {
		System.out.println(DASH_LINE);
		System.out.format(HEADER_FORMAT, "#", "ID", "First name", "Last name", "Street", "City", "Postal Code", "Phone",
				"Email", "Join Date");
		System.out.println(DASH_LINE);
	}
}
