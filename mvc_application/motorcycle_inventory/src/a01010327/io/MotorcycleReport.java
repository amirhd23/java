/**
 * a01010327_assignment1
 * MotorcycleReport.java
 * May 24, 2017
 * 5:31:13 PM
 */
package a01010327.io;

import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import a01010327.data.Customer;
import a01010327.data.Motorcycle;

/**
 * @author amir deris, a01010327 this class is used for reporting Motorcycle
 *         data.
 */
public class MotorcycleReport {
	private static final String DASH_LINE = "---------------------------"
			+ "------------------------------------------------";

	private static final String ROW_FORMAT = "%-11s %-13s %-18s %-15s %-6d %,7d %n";
	private static final String HEADER_FORMAT = "%-11s %-13s %-18s %-15s %-6s %7s %n";

	private static final Logger LOG = LogManager.getLogger();

	/**
	 * prints data for motorcycles in a formatted fashion
	 * 
	 * @param data,
	 *            a Set<Motorcycle>.
	 */
	public static void printData(Set<Motorcycle> data) {
		LOG.info("printing Motorcycle data of size: " + data.size());
		System.out.println("Service Report");
		printHeaders();
		Customer owner = null;
		for (Motorcycle m : data) {
			owner = m.getOwner();
			if (owner != null) {
				System.out.format(ROW_FORMAT, owner.getFirstName(), owner.getLastName(), m.getMake(), m.getModel(),
						m.getYear(), m.getMileage());
			} else {
				System.out.format(ROW_FORMAT, "N/A", "N/A", m.getMake(), m.getModel(), m.getYear(), m.getMileage());
			}
		}
	}

	// prints the title of each column
	private static void printHeaders() {
		System.out.println(DASH_LINE);
		System.out.format(HEADER_FORMAT, "First Name", "Last Name", "Make", "Model", "Year", "Mileage");
		System.out.println(DASH_LINE);
	}
}
