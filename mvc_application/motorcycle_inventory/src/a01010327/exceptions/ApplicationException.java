/**
 * a01010327_assignment1
 * ApplicationException.java
 * May 19, 2017
 * 11:32:21 AM
 */
package a01010327.exceptions;

/**
 * @author amir deris, a01010327
 *
 */
@SuppressWarnings("serial")
public class ApplicationException extends Exception {

	/**
	 * no-argument constructor
	 */
	public ApplicationException() {
		super();
	}

	/**
	 * @param arg0
	 * @param arg1
	 * @param arg2
	 * @param arg3
	 */
	public ApplicationException(String arg0, Throwable arg1, boolean arg2, boolean arg3) {
		super(arg0, arg1, arg2, arg3);
	}

	/**
	 * @param arg0
	 * @param arg1
	 */
	public ApplicationException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	/**
	 * @param arg0,
	 *            the exception message as a String
	 */
	public ApplicationException(String arg0) {
		super(arg0);
	}

	/**
	 * @param arg0
	 */
	public ApplicationException(Throwable arg0) {
		super(arg0);
	}

}
