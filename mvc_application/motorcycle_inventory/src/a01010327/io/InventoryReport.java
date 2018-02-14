/**
 * a01010327_assignment1
 * InventoryReport.java
 * May 24, 2017
 * 6:02:14 PM
 */
package a01010327.io;

import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import a01010327.data.Inventory;

/**
 * @author amir deris, a01010327 this class is used for reporting Inventory
 *         data.
 */
public class InventoryReport {

	private static final String DASH_LINE_SHORT = "-----------------------------"
			+ "-----------------------------------------------------------------";

	private static final String DASH_LINE_LONG = "-----------------------------"
			+ "--------------------------------------------------------------" + "---------------";
	private static final String ROW_WITH_TOTAL = "%-28s %-28s %-13s %,9.2f %11d %,12.2f%n";
	private static final String ROW_WITHOUT_TOTAL = "%-28s %-28s %-13s %,9.2f %11d%n";

	private static final String HEADER_ROW_WITH_TOTAL = "%-28s %-28s %-13s %9s %11s %12s%n";
	private static final String HEADER_ROW_WITHOUT_TOTAL = "%-28s %-28s %-13s %9s %11s%n";

	private static final Logger LOG = LogManager.getLogger();

	/**
	 * prints data for Inventories in a formatted fashion
	 * 
	 * @param data,
	 *            a Set<Inventory>
	 * @param isValueSet,
	 *            a boolean indicating whether Value column is requested. if
	 *            true, a Value column is added for calculated value for each
	 *            part and the total value of the inventory is added to the end
	 *            of the report.
	 */
	public static void printData(Set<Inventory> data, boolean isValueSet) {
		StringBuilder builder = new StringBuilder();
		builder.append("printing Inventory report, for data of size: ");
		builder.append(data.size());
		builder.append(", ");
		builder.append(isValueSet ? "with value column" : "without value column");
		LOG.info(builder.toString());

		System.out.println("Inventory Report");
		printHeaders(isValueSet);
		double valueSum = 0;
		double value = 0;
		for (Inventory i : data) {
			value = i.getPrice() * i.getQuantity();
			valueSum = valueSum + value;
			if (isValueSet) {
				System.out.format(ROW_WITH_TOTAL, i.getMotorcycleId(), i.getDescription(), i.getPartNumber(),
						i.getPrice(), i.getQuantity(), value);
			} else {
				System.out.format(ROW_WITHOUT_TOTAL, i.getMotorcycleId(), i.getDescription(), i.getPartNumber(),
						i.getPrice(), i.getQuantity());
			}
		}
		if (isValueSet) {
			System.out.println(DASH_LINE_LONG);
			System.out.format("Value of current inventory: %,.2f%n", valueSum);
			System.out.println(DASH_LINE_LONG);
		}
	}

	// prints the title of each column. If isValueSet=true, it adds a Value
	// column to the end.
	private static void printHeaders(boolean isValueSet) {
		System.out.println(isValueSet ? DASH_LINE_LONG : DASH_LINE_SHORT);
		if (isValueSet) {
			System.out.format(HEADER_ROW_WITH_TOTAL, "Make+Model", "Description", "Part#", "Price", "Quantity",
					"Value");
		} else {
			System.out.format(HEADER_ROW_WITHOUT_TOTAL, "Make+Model", "Description", "Part#", "Price", "Quantity");
		}
		System.out.println(isValueSet ? DASH_LINE_LONG : DASH_LINE_SHORT);
	}
}
