/**
 * a01010327_assignment1
 * Motorcycles.java
 * May 23, 2017
 * 5:26:10 PM
 */
package a01010327.data.collections;

import java.util.Comparator;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import a01010327.data.Motorcycle;

/**
 * @author amir deris, a01010327 this class stores data of all Motorcycles.
 */
public class Motorcycles {

	private static final Logger LOG = LogManager.getLogger();

	private static Set<Motorcycle> data = new TreeSet<Motorcycle>();

	/**
	 * adds a Motorcycle to the set
	 * 
	 * @param motorcycle,
	 *            a Motorcycle
	 */
	public static void addMotorcycle(Motorcycle motorcycle) {
		data.add(motorcycle);
	}

	/**
	 * 
	 * @return the number of Motorcycle in data.
	 */
	public static int getSize() {
		return data.size();
	}

	/**
	 * filters the Motorcycle set based on make.
	 * 
	 * @param make,
	 *            a String used to filter.
	 * @param isDescending,
	 *            a boolean specifying whether sorting in descending order.
	 * @return a filtered set of motorcycle data, based on given make. result
	 *         will be sorted by make, depending on isDescending parameter.
	 */
	public static Set<Motorcycle> filterByMake(String make, boolean isDescending) {
		LOG.info("started filtering Motorcycles (Service) data by make = " + make + " -descending: " + isDescending);
		Set<Motorcycle> result = new TreeSet<Motorcycle>(new Comparator<Motorcycle>() {

			@Override
			public int compare(Motorcycle m1, Motorcycle m2) {
				if (m1.getMake().equals(m2.getMake())) {
					return m1.compareTo(m2);
				} else if (isDescending) {
					return m2.getMake().compareTo(m1.getMake());
				} else {
					return m1.getMake().compareTo(m2.getMake());
				}
			}

		});
		Iterator<Motorcycle> it = data.iterator();
		while (it.hasNext()) {
			Motorcycle motorcycle = it.next();
			if (motorcycle.getMake().indexOf(make) != -1) {
				result.add(motorcycle);
			}
		}
		LOG.info("finished filtering Motorcycle (Service) data");
		return result;
	}

	/**
	 * 
	 * @return a Set<Motorcycle>, a copy of the Motorcycles data
	 */
	public static Set<Motorcycle> getData() {
		return new TreeSet<Motorcycle>(data);
	}

}
