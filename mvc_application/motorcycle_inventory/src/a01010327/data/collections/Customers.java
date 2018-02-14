/**
 * a01010327_assignment1
 * Customers.java
 * May 23, 2017
 * 5:20:27 PM
 */
package a01010327.data.collections;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import a01010327.data.Customer;

/**
 * @author amir deris, a01010327 this class stores Customers information in a
 *         Map<String, Customer> key of the map is Customer's ID and value is
 *         the Customer object.
 */
public class Customers {

	private static final Logger LOG = LogManager.getLogger();

	private static Map<String, Customer> data = new HashMap<String, Customer>();

	/**
	 * adds a new Customer to the map of Customers.
	 * 
	 * @param customerId,
	 *            a String
	 * @param customer,
	 *            a Customer
	 */
	public static void addCustomer(String customerId, Customer customer) {
		data.put(customerId, customer);
	}

	/**
	 * returns a Customer for the given customerId
	 * 
	 * @param customerId,
	 *            a String
	 * @return Customer with the given customerId
	 */
	public static Customer getCustomer(String customerId) {
		return data.get(customerId);
	}

	/**
	 * 
	 * @return the number of Customers in the data map.
	 */
	public static int getSize() {
		return data.size();
	}

	/**
	 * sorts the Map<String, Customer> based on join date.
	 * 
	 * @param isDescending,
	 *            a boolean specifying whether sorting in descending order.
	 * @return a sorted Map<String, Customer> based on join date.
	 */
	public static Map<String, Customer> sortByJoinDate(boolean isDescending) {
		LOG.info("started sorting Customer's data by join date -descending: " + isDescending);
		List<Map.Entry<String, Customer>> list = new LinkedList<>(data.entrySet());
		Collections.sort(list, new Comparator<Map.Entry<String, Customer>>() {

			@Override
			public int compare(Entry<String, Customer> o1, Entry<String, Customer> o2) {
				if (isDescending) {
					return o2.getValue().getDateJoined().compareTo(o1.getValue().getDateJoined());
				} else {
					return o1.getValue().getDateJoined().compareTo(o2.getValue().getDateJoined());
				}
			}

		});
		// linked hash map preserves order of insertion
		Map<String, Customer> sortedMap = new LinkedHashMap<>();
		for (Map.Entry<String, Customer> entry : list) {
			sortedMap.put(entry.getKey(), entry.getValue());
		}
		LOG.info("finished sorting Customer's data.");
		return sortedMap;
	}

	/**
	 * 
	 * @return a Map<String, Customer>, a copy of the Customers data map.
	 */
	public static Map<String, Customer> getData() {
		return new HashMap<String, Customer>(data);
	}

}
