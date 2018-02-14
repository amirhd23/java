/**
 * a01010327_assignment2
 * InventorySorters.java
 * Jun 21, 2017
 * 11:03:02 AM
 */
package a01010327.data.collections;

import java.util.Comparator;

import a01010327.data.Inventory;

/**
 * @author amir deris, a01010327 class can be used for sorting Inventory
 *         collections by different criteria
 */
public class InventorySorters {

	private static boolean isDescending;

	/**
	 * Sorts by description. If isDescending set to true, sorting is done in
	 * descending order and vice versa.
	 * 
	 * @author amir deris, a01010327
	 *
	 */
	public static class SortByDescription implements Comparator<Inventory> {

		/**
		 * constructor for all arguments
		 * 
		 * @param desc,
		 *            a boolean. Indicates whether sorting should be done in
		 *            descending order.
		 */
		public SortByDescription(boolean desc) {
			isDescending = desc;
		}

		/**
		 * Sorts by description. If isDescending set to true, sorting is done in
		 * descending order and vice versa.
		 */
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
	}

	public static class SortByCount implements Comparator<Inventory> {

		/**
		 * constructor for all arguments
		 * 
		 * @param desc,
		 *            a boolean. Indicates whether sorting should be done in
		 *            descending order.
		 */
		public SortByCount(boolean desc) {
			isDescending = desc;
		}

		/**
		 * Sorts by count. If isDescending set to true, sorting is done in
		 * descending order and vice versa.
		 */
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

	}
}
