package spas.reading;

/**
 * Exception used to throw around exceptions that happen during connecting to
 * the Noppa API.
 * 
 * @author Lauri Lavanti
 * @version 0.1
 * 
 */
public class ConnectionException extends Exception {
	/**
	 * Default serial version number.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Basic constructor, nothing special here.
	 * 
	 * @param message
	 *            The message this exception should hold.
	 */
	public ConnectionException(String message) {
		super(message);
	}
}
