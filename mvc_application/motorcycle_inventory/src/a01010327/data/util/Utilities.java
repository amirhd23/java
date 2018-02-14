/**
 * a01010327_assignment2
 * Utilities.java
 * Jun 20, 2017
 * 6:24:37 PM
 */
package a01010327.data.util;

import java.util.LinkedHashSet;
import java.util.Set;

import a01010327.data.Inventory;

/**
 * @author amir deris, a01010327 utility methods
 */
public class Utilities {

	/**
	 * converts a Set<Inventory> to a Set<String>
	 * 
	 * @param data,
	 *            a Set<Inventory>
	 * @return a Set<String> representing each Inventory as a String.
	 */
	public static Set<String> convert(Set<Inventory> data) {
		Set<String> result = new LinkedHashSet<String>();
		for (Inventory i : data) {
			result.add(String.format("%-28s| %-28s| %-13s| %,9.2f| %11d", i.getMotorcycleId(), i.getDescription(),
					i.getPartNumber(), i.getPrice(), i.getQuantity()));
		}
		return result;
	}

	/**
	 * trims the elements of the array (removes extra spaces from beginning and
	 * end)
	 * 
	 * @param array,
	 *            a String[].
	 * @return a String[] whose elements are the trimmed elements of the
	 *         argument array.
	 */
	public static String[] trimElements(String[] array) {
		if (array == null || array.length == 0) {
			return array;
		}
		String[] result = new String[array.length];
		for (int i = 0; i < array.length; i++) {
			result[i] = array[i].trim();
		}
		return result;
	}
}
