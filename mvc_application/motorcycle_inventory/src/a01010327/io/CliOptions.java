/**
 * a01010327_assignment1
 * CliOptions.java
 * May 24, 2017
 * 11:37:26 AM
 */
package a01010327.io;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

/**
 * @author amir deris, a01010327 This class uses Apache Commons CLI to parse
 *         command line options
 */
public class CliOptions {

	private static CommandLine cmd;

	public static void process(String[] args) throws ParseException {
		Options options = createOptions();
		CommandLineParser cli = new DefaultParser();

		cmd = cli.parse(options, args);
	}

	private static Options createOptions() {
		Options options = new Options();
		options.addOption(
				Option.builder(Value.SERVICE.option).longOpt(Value.SERVICE.longOption).desc("service").build());
		options.addOption(
				Option.builder(Value.INVENTORY.option).longOpt(Value.INVENTORY.longOption).desc("inventory").build());
		options.addOption(
				Option.builder(Value.CUSTOMERS.option).longOpt(Value.CUSTOMERS.longOption).desc("customers").build());
		options.addOption(Option.builder(Value.TOTAL.option).longOpt(Value.TOTAL.longOption).desc("total").build());
		options.addOption(Option.builder(Value.BY_DESCRIPTION.option).longOpt(Value.BY_DESCRIPTION.longOption)
				.desc("by description").build());
		options.addOption(
				Option.builder(Value.BY_COUNT.option).longOpt(Value.BY_COUNT.longOption).desc("by count").build());
		options.addOption(Option.builder(Value.BY_JOIN_DATE.option).longOpt(Value.BY_JOIN_DATE.longOption)
				.desc("by join date").build());
		options.addOption(
				Option.builder(Value.MAKE.option).longOpt(Value.MAKE.longOption).hasArg().desc("make").build());
		options.addOption(Option.builder(Value.DESC.option).longOpt(Value.DESC.longOption).desc("descending").build());
		return options;
	}

	/**
	 * 
	 * @return whether service option is set.
	 */
	public static boolean isServiceOptionSet() {
		return cmd.hasOption(Value.SERVICE.option);
	}

	/**
	 * 
	 * @return whether inventory option is set.
	 */
	public static boolean isInventoryOptionSet() {
		return cmd.hasOption(Value.INVENTORY.option);
	}

	/**
	 * 
	 * @return whether customers option is set.
	 */
	public static boolean isCustomersOptionSet() {
		return cmd.hasOption(Value.CUSTOMERS.option);
	}

	/**
	 * 
	 * @return whether total option is set.
	 */
	public static boolean isTotalOptionSet() {
		return cmd.hasOption(Value.TOTAL.option);
	}

	/**
	 * 
	 * @return whether by_description option is set.
	 */
	public static boolean isByDescriptionOptionSet() {
		return cmd.hasOption(Value.BY_DESCRIPTION.option);
	}

	/**
	 * 
	 * @return whether by_count option is set.
	 */
	public static boolean isByCountOptionSet() {
		return cmd.hasOption(Value.BY_COUNT.option);
	}

	/**
	 * 
	 * @return whether by_join_date option is set.
	 */
	public static boolean isByJoinDateOptionSet() {
		return cmd.hasOption(Value.BY_JOIN_DATE.option);
	}

	/**
	 * 
	 * @return the value of 'make=' option
	 */
	public static String getMake() {
		return cmd.getOptionValue(Value.MAKE.option);
	}

	/**
	 * 
	 * @return whether descending option is set.
	 */
	public static boolean isDescOptionSet() {
		return cmd.hasOption(Value.DESC.option);
	}

	/**
	 * enum representing command line options
	 * 
	 * @author amir deris, a01010327 here is the meaning of options: -s or
	 *         -service: Print the service report
	 * 
	 *         -i or -inventory: Print the inventory report
	 * 
	 *         -c or -customers: Print the customer report
	 * 
	 *         -t or -total: Print the inventory report adding a Value column
	 *         and calculated value for each part and the total value of the
	 *         inventory is added to the end of the report.
	 * 
	 *         -D or -by_description: Sorts the inventory report by part
	 *         description name ascending. This is ignored if ‘inventory’ isn’t
	 *         also specified.
	 * 
	 *         -C or -by_count: Sorts the inventory report by part count
	 *         ascending. This is ignored if ‘inventory’ isn’t also specified
	 * 
	 *         -J or -by_join_date: Sorts the customer report by join date. This
	 *         is ignored if ‘customers’ isn’t also specified
	 * 
	 *         -m or -make="make": Filters the service or inventory report by
	 *         make ascending
	 * 
	 *         -d or -desc: Any sorted value is sorted in a descending order
	 * 
	 */
	public enum Value {
		SERVICE("s", "service"), INVENTORY("i", "inventory"), CUSTOMERS("c", "customers"), TOTAL("t",
				"total"), BY_DESCRIPTION("D", "by_description"), BY_COUNT("C",
						"by_count"), BY_JOIN_DATE("J", "by_join_date"), MAKE("m", "make"), DESC("d", "desc");

		private final String option;
		private final String longOption;

		Value(String option, String longOption) {
			this.option = option;
			this.longOption = longOption;
		}
	}
}
