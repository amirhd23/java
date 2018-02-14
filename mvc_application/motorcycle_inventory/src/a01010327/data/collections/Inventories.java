/**
 * a01010327_assignment1
 * Inventories.java
 * May 23, 2017
 * 5:33:20 PM
 */
package a01010327.data.collections;

import java.util.Comparator;
import java.util.Iterator;
import java.util.Set;

import java.util.TreeSet;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import a01010327.data.Inventory;

/**
 * @author amir deris, a01010327 this class holds data for all inventory objects
 */
public class Inventories {

	private static final Logger LOG = LogManager.getLogger();

	private static Set<Inventory> data = new TreeSet<Inventory>();

	/**
	 * adds an Inventory object to the set.
	 * 
	 * @param inventory,
	 *            an Inventory.
	 */
	public static void addInventory(Inventory inventory) {
		data.add(inventory);
	}

	/**
	 * 
	 * @return the number of elements in the data
	 */
	public static int getSize() {
		return data.size();
	}

	/**
	 * 
	 * @return a Set<Inventory>, a copy of the Inventory data.
	 */
	public static Set<Inventory> getData() {
		return new TreeSet<Inventory>(data);
	}

	/**
	 * @param isDescending,
	 *            a boolean specifying whether sorting in descending order.
	 * @return a sorted inventory data based on description.
	 */
	public static Set<Inventory> sortByDescription(boolean isDescending) {
		LOG.info("started sorting Inventories data by description -descending: " + isDescending);
		Set<Inventory> result = new TreeSet<Inventory>(new Comparator<Inventory>() {
			@Override
			public int compare(Inventory arg0, Inventory arg1) {
				if (arg0.getDescription().equals(arg1.getDescription())) {
					return arg0.compareTo(arg1);
				} else if (isDescending) {
					return arg1.getDescription().compareTo(arg0.getDescription());
				} else {
					return arg0.getDescription().compareTo(arg1.getDescription());
				}
			}
		});
		result.addAll(data);
		LOG.info("finished sorting Inventories data.");
		return result;
	}

	/**
	 * @param isDescending,
	 *            a boolean specifying whether sorting in descending order.
	 * @return a sorted inventory data based on quantity (count).
	 */
	public static Set<Inventory> sortByCount(boolean isDescending) {
		LOG.info("started sorting Inventories data by count -descending: " + isDescending);
		Set<Inventory> result = new TreeSet<Inventory>(new Comparator<Inventory>() {
			@Override
			public int compare(Inventory arg0, Inventory arg1) {
				if (arg0.getQuantity() == arg1.getQuantity()) {
					return arg0.compareTo(arg1);
				} else if (isDescending) {
					return arg1.getQuantity() - arg0.getQuantity();
				} else {
					return arg0.getQuantity() - arg1.getQuantity();
				}

			}
		});
		result.addAll(data);
		LOG.info("finished sorting Inventories data.");
		return result;
	}

	/**
	 * filters a given Set of Inventory, by given make.
	 * 
	 * @param input,
	 *            a Set<Inventory> to filter.
	 * @param make,
	 *            a String, used to filter the Set.
	 * @return a Set<Inventory> containing the filtered elements by make.
	 */
	public static Set<Inventory> filterByMake(Set<Inventory> input, String make) {
		if (make == null || make.equals("")) {
			return input;
		}
		LOG.info("started filtering Inventories data by make = " + make);
		Iterator<Inventory> it = input.iterator();
		while (it.hasNext()) {
			Inventory inventory = it.next();
			if (inventory.getMotorcycleId().indexOf(make) == -1) {
				it.remove();
			}
		}
		LOG.info("finished filtering Inventories data.");
		return input;
	}

}
